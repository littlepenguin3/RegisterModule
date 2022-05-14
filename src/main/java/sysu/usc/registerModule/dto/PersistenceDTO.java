package sysu.usc.registerModule.dto;

import lombok.Data;

import java.sql.Timestamp;

/**
 * <p>
 *    持久化到Mysql的数据格式
 *    一定内含姓名，部门和签名存储地址
 *    可能含有Type，quantity
 * </p>
 * @author littlepenguin
 * @since 2022/05/12 15:41
 */
@Data
public class PersistenceDTO {
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
    /**
     * 姓名，Bmc查询得到
     */
    String name;
    /**
     * 部门，Bmc查询得到
     */
    String department;
    /**
     * 时间戳
     */
    Long timeStamp;
    public PersistenceDTO(){
        name = "未查到";
        department = "未查到";
    }
}
