package sysu.usc.registerModule.utils;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.data.redis.core.StringRedisTemplate;
import sysu.usc.registerModule.dto.CacheDTO;
import sysu.usc.registerModule.dto.FormDTO;
import sysu.usc.registerModule.structMapper.CacheDtoMapper;

import static sysu.usc.registerModule.constants.RedisConstants.COUNTER_NAME;
import static sysu.usc.registerModule.constants.RedisConstants.REGISTER_SCHOOL_CARD_KEY;

/**
 * <p>
 *      生成CacheDTO的工具类
 * </p>
 * @author littlepenguin
 * @since 2022/05/14 9:42
 */
@Slf4j
public class CacheUtil {
    private static final CacheDtoMapper cacheDtoMapper = Mappers.getMapper(CacheDtoMapper.class);
    public static CacheDTO generateCacheDto(FormDTO formDTO,SnowFlakeUtil snowFlakeUtil){
        long id = snowFlakeUtil.nextId();
        CacheDTO cacheDTO = cacheDtoMapper.toTarget(formDTO);
        cacheDTO.setId(id);
        return cacheDTO;
    }

    public static void saveCacheDtoToRedis(CacheDTO cacheDTO, StringRedisTemplate redis){
        //得到学号 作为key的一部分，保存填写表单的内容和表单id，若调取签名则根据path查询文件服务器

        //名字不能直接保存在Redis中(查询慢) 而是在后续要单独跟学号进行联系，永久保存
        String userId = cacheDTO.getUserId();
        String redisPrefix = REGISTER_SCHOOL_CARD_KEY + userId + ":";
        redis.opsForValue().increment(redisPrefix + COUNTER_NAME);
        String counter = redis.opsForValue().get(redisPrefix + COUNTER_NAME);
        //查询时查询整个字段，所以存储方式是json，自动忽略空值
        redis.opsForValue().set(redisPrefix + counter,
                JSON.toJSONString(cacheDTO));
        log.info("写入Redis成功");
    }
}
