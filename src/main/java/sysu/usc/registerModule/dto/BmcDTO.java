package sysu.usc.registerModule.dto;

import lombok.Data;

/**
 * <p>
 *      bmc查询信息类
 * </p>
 * @author littlepenguin
 * @since 2022/05/12 15:41
 */
@Data
public class BmcDTO {
    String name;
    String department;
    public BmcDTO(){
        name = "未查到";
        department = "未查到";
    }
}
