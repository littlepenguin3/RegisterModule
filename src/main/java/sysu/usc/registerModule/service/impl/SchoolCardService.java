package sysu.usc.registerModule.service.impl;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import sysu.usc.registerModule.domain.SchoolCard;
import sysu.usc.registerModule.VO.Result;
import sysu.usc.registerModule.dto.CacheDTO;
import sysu.usc.registerModule.dto.FormDTO;
import sysu.usc.registerModule.mapper.SchoolCardMapper;
import sysu.usc.registerModule.service.ISchoolCardService;
import sysu.usc.registerModule.utils.CacheUtil;
import sysu.usc.registerModule.utils.PersistenceUtil;
import sysu.usc.registerModule.utils.SnowFlakeUtil;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

import static sysu.usc.registerModule.constants.RedisConstants.COUNTER_NAME;
import static sysu.usc.registerModule.constants.RedisConstants.REGISTER_SCHOOL_CARD_KEY;
import static sysu.usc.registerModule.constants.ServiceConstants.SCHOOL_CARD;

/**
 * <p></p>
 * @author littlepenguin
 * @since 2022/05/12 7:44
 */
@Service
@Slf4j
public class SchoolCardService
        extends ServiceImpl<SchoolCardMapper, SchoolCard>
        implements ISchoolCardService {

    private final StringRedisTemplate redis;
    private final SchoolCardMapper mapper;
    private final SnowFlakeUtil snowFlakeUtil;

    public SchoolCardService(StringRedisTemplate redis,
                             SchoolCardMapper mapper,
                             ThreadPoolExecutor threadPool) {
        this.redis = redis;
        this.mapper = mapper;
        this.snowFlakeUtil = new SnowFlakeUtil(SCHOOL_CARD,0);
    }

    @Override
    public Result saveRecord(FormDTO schoolCardForm) {
        //同步保存进入Redis
        CacheDTO cacheDTO = CacheUtil.generateCacheDto(schoolCardForm, snowFlakeUtil);
        CacheUtil.saveCacheDtoToRedis(cacheDTO,redis);
        //异步查询并写入mysql基本信息
        try {
            PersistenceUtil.asyncQueryBmcAndInsert(cacheDTO, entityClass, mapper);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        //返回
        return Result.ok();
    }

}
