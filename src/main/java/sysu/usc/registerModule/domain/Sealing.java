package sysu.usc.registerModule.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import sysu.usc.registerModule.domain.template.BaseObject;

/**
 * @author little-penguin
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Sealing extends BaseObject {
    /**
     * 枚举类
     */
    int type;
    /**
     * 数量
     */
    int quantity;
}
