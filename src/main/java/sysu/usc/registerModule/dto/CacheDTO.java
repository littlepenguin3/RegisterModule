package sysu.usc.registerModule.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 缓存到Redis的数据格式，以json形式转换，也是传向前端的数据格式
 * @author little-penguin
 */
@Data
@AllArgsConstructor
public class CacheDTO {
    /**
     * 自动生成的雪花算法表单id
     */
    Long id;
    /**
     * 学工号
     */
    String userId;
    /**
     * 手机号
     */
    String phoneNumber;
    /**
     * 类型 密封类型
     */
    String type;
    /**
     * 数量，密封份数有数量
     */
    Long quantity;
    /**
     * 归还时间,归还不通过表单,-1表示未归还，1表示已经归还
     */
    Long returnTimeStamp;

    public CacheDTO(){
        returnTimeStamp = -1L;
    }

}
