package sysu.usc.registerModule.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一接受所有表单
 * @author little-penguin
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormDTO {
    /**
     * 学工号
     */
    String id;
    /**
     * 手机号
     */
    String phoneNumber;
    /**
     * 签名图片base64
     */
    @JSONField(serialize = false)
    String signatureBase64;
    /**
     * 类型
     */
    String type;
    /**
     * 数量
     */
    Long quantity;
    /**
     * 时间戳
     */
    Long timeStamp;
}
