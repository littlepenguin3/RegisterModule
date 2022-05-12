package sysu.usc.registerModule.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import sysu.usc.registerModule.domain.template.BaseObject;
import sysu.usc.registerModule.dto.FormDTO;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * <p>
 *      多线程持久化抽取工具
 * </p>
 * @author littlepenguin
 * @since 2022/05/12 10:47
 */
public class PersistenceUtil {

    public static <T,R> R asyncPersistence(FormDTO formDTO,
                                      Class<T> entityClass,
                                      ThreadPoolExecutor threadPool,
                                      Function<T,R> handler) throws ExecutionException, InterruptedException {
        Future<R> submit = threadPool.submit(() -> {
            //t为存储对象
            T storageObject = null;
            try {
                storageObject = InfoUtil.getInfoFromDTO(formDTO, entityClass);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            //不可能返回空,storageObject内含name和department
            assert storageObject != null;
            BeanUtil.copyProperties(formDTO,storageObject,
                                    CopyOptions.create().ignoreNullValue());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String format = simpleDateFormat.format(new Date(formDTO.getTimeStamp()));
            String signaturePath = formDTO.getId() + "_" + format;
            Map<String,String> map= new HashMap<>(1);
            map.put("signaturePath",signaturePath);
            BeanUtil.fillBeanWithMap(map,storageObject,
                                    CopyOptions.create().ignoreNullValue());
            //TODO 保存文件
            //操作数据库函数
            return handler.apply(storageObject);
        });
        return submit.get();
    }
}
