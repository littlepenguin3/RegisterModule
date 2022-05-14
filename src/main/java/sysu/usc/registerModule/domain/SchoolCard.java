package sysu.usc.registerModule.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import sysu.usc.registerModule.domain.template.BaseObject;

import java.sql.Timestamp;

/**
 * @author little-penguin
 */
@Data
@TableName(value = "tb_school_card")
public class SchoolCard extends BaseObject {

}
