package sysu.usc.registerModule.domain.template;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author little-penguin
 * @date 2022-05-12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseObject {
    /**
     * 学工号
     */
    String id;
    /**
     * 手机号
     */
    String phoneNumber;
    /**
     * 时间戳
     */
    long timeStamp;
    /**
     * 职能部门
     */
    String department;
    /**
     * 姓名
     */
    String name;
    /**
     * 签名图片地址
     */
    String signaturePath;
}
