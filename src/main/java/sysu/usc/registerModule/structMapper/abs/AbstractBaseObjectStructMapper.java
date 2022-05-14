package sysu.usc.registerModule.structMapper.abs;

import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import sysu.usc.registerModule.domain.template.BaseObject;
import sysu.usc.registerModule.dto.PersistenceDTO;

/**
 * <p>
 *      PersistenceDTO转换为BaseObject的抽象接口
 * </p>
 * @author littlepenguin
 * @since 2022/05/14 9:53
 */
public interface AbstractBaseObjectStructMapper<T extends BaseObject> extends AbstractStructMapper<T, PersistenceDTO> {
    @Override
    T toTarget(PersistenceDTO source);
}
