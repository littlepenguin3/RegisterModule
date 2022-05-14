package sysu.usc.registerModule.domain.template;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @author little-penguin
 * @date 2022-05-12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseObject {
    @TableId(type= IdType.INPUT)
    /**
     * 主键
     */
    Long id;
    /**
     * 学工号
     */
    String userId;
    /**
     * 手机号
     */
    @TableField("phone_number")
    String phoneNumber;
    /**
     * mysql时间戳
     */
    @TableField("create_time")
    Timestamp createTime;
    /**
     * 职能部门
     */
    String department;
    /**
     * 姓名
     */
    String name;

}
