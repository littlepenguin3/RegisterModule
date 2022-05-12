package sysu.usc.registerModule.service.impl;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import sysu.usc.registerModule.domain.SchoolCard;
import sysu.usc.registerModule.dto.FormDTO;
import sysu.usc.registerModule.dto.Result;
import sysu.usc.registerModule.mapper.SchoolCardMapper;
import sysu.usc.registerModule.service.ISchoolCardService;
import sysu.usc.registerModule.utils.PersistenceUtil;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static sysu.usc.registerModule.constants.RedisConstants.COUNTER_NAME;
import static sysu.usc.registerModule.constants.RedisConstants.REGISTER_SCHOOL_CARD_KEY;

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
    private final ThreadPoolExecutor threadPool;

    public SchoolCardService(StringRedisTemplate redis,
                             SchoolCardMapper mapper,
                             ThreadPoolExecutor threadPool) {
        this.redis = redis;
        this.mapper = mapper;
        this.threadPool = threadPool;
    }

    @Override
    public Result saveRecord(FormDTO schoolCardForm) {
        //存入时间戳
        schoolCardForm.setTimeStamp(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));
        //异步查询并写入mysql基本信息
        try {
            PersistenceUtil.asyncPersistence(schoolCardForm,
                    entityClass,
                    threadPool,
                    mapper::insert);
        } catch (ExecutionException | InterruptedException e) {
            //TODO 点了一下异常就合并了
            e.printStackTrace();
        }
        String id = schoolCardForm.getId();
        String redisPrefix = REGISTER_SCHOOL_CARD_KEY + id + ":";
        redis.opsForValue().increment(redisPrefix + COUNTER_NAME);
        String counter = redis.opsForValue().get(redisPrefix + COUNTER_NAME);
        //查询时查询整个字段，所以存储方式是json
        redis.opsForValue().set(redisPrefix + counter,
                JSON.toJSONString(schoolCardForm));
        log.info("写入Redis成功");
        //TODO 可返回信息
        return Result.ok();
    }

}
