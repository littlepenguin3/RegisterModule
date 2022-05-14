package sysu.usc.registerModule.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 *      接收前端表单接口数据类，以json形式接收
 * </p>
 * @author littlepenguin
 * @since 2022/05/14 9:23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormDTO {
    /**
     * 学工号
     */
    String userId;
    /**
     * 手机号
     */
    String phoneNumber;
    /**
     * 类型
     */
    String type;
    /**
     * 数量
     */
    Long quantity;

    /**
     * 签名图片base64
     */
    String signatureBase64;
}
