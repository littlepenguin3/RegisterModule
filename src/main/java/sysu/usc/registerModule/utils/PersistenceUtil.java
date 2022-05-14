package sysu.usc.registerModule.utils;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import sysu.usc.registerModule.domain.template.BaseObject;
import sysu.usc.registerModule.dto.CacheDTO;
import sysu.usc.registerModule.dto.PersistenceDTO;
import sysu.usc.registerModule.structMapper.CacheDtoMapper;
import sysu.usc.registerModule.structMapper.PersistenceDtoMapper;
import sysu.usc.registerModule.structMapper.abs.AbstractBaseObjectStructMapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * <p>
 *      多线程持久化抽取工具
 *      内部为了性能耦合了类型
 * </p>
 * @author littlepenguin
 * @since 2022/05/12 10:47
 */
@Slf4j
public class PersistenceUtil {
    private static final PersistenceDtoMapper PersistenceDtoMapper = Mappers.getMapper(PersistenceDtoMapper.class);
    private static final ThreadPoolExecutor threadPool;
    static {
        threadPool = (ThreadPoolExecutor) ApplicationContextUtil.getBean("persistenceThreadPool");
    }

    public static <T extends BaseObject,M extends BaseMapper> void asyncQueryBmcAndInsert(CacheDTO cacheDTO,
                                                                                          Class<T> entityClass,
                                                                                          M mapper) throws ExecutionException, InterruptedException {
        Future<Integer> submit = threadPool.submit(() -> {
            PersistenceDTO persistenceDTO = PersistenceDtoMapper.toTarget(cacheDTO);
            //填充Bmc信息
            try {
                BmcUtil.FillPersistenceDtoWithBmcInfo(persistenceDTO);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            //经过适配器将persistenceDTO转化为DO对象
            //值得注意的是structMapper应该定义好对long to sql时间戳的具体转换规则
            T domain = null;
            try {
                domain = domainConverter(entityClass,persistenceDTO);
            } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
            if(domain != null) {
                return mapper.insert(domain);
            }
            //如何不知为何转换失败了返回0，表示影响0行
            return 0;
        });
        //加上这句 会阻塞
        submit.get();
    }

    /**
     * 反射适配器
     * @param entityClass
     * @param persistenceDTO
     * @param <T>
     * @return
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    private static <T extends BaseObject> T domainConverter(Class<T> entityClass, PersistenceDTO persistenceDTO) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        T ret = null;
        outer:
        for (Class clazz : ClassUtil.getAllClassByAbstractInterface(AbstractBaseObjectStructMapper.class)) {
            if(clazz.isInterface()) {
                //是接口则判断其父类接口的模板是否与entityClass的name相同
                for (Type genericInterface : clazz.getGenericInterfaces()) {
                    String typeName = genericInterface.getTypeName();
                    String[] split = typeName.split(">|<");
                    String entityClazzName = split[split.length - 1];
                    if(entityClass.getName().equals(entityClazzName)){
                        //如果相同即调用INSTANCE进行转换
                        AbstractBaseObjectStructMapper mapper = (AbstractBaseObjectStructMapper) Mappers.getMapper(clazz);
                        ret = (T) mapper.toTarget(persistenceDTO);
                        break outer;
                    }
                }
            }
        }
        return ret;
    }

    private static void fillSignaturePathAndTimeStamp(CacheDTO cacheDTO, PersistenceDTO persistenceDTO) {
        long timeStamp = SnowFlakeUtil.parseStampFromId(cacheDTO.getId());
    }


}
