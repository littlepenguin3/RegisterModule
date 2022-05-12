package sysu.usc.registerModule.service;

import com.baomidou.mybatisplus.extension.service.IService;
import sysu.usc.registerModule.domain.SchoolCard;
import sysu.usc.registerModule.dto.FormDTO;
import sysu.usc.registerModule.dto.Result;

/**
 * <p>
 *
 * </p>
 * @author littlepenguin
 * @since 2022/05/12 7:47
 */
public interface ISchoolCardService extends IService<SchoolCard> {

    Result saveRecord(FormDTO schoolCardForm);
}
