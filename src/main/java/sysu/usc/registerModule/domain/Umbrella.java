package sysu.usc.registerModule.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;
import sysu.usc.registerModule.domain.template.BaseObject;

/**
 * @author little-penguin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Umbrella extends BaseObject {
    /**
     * 归还时间
     */
    Long returnTimeStamp;
    /**
     * 数量
     */
    Long quantity;
}
