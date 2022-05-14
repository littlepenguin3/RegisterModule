package sysu.usc.registerModule.structMapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import sysu.usc.registerModule.dto.CacheDTO;
import sysu.usc.registerModule.dto.FormDTO;
import sysu.usc.registerModule.structMapper.abs.AbstractStructMapper;

/**
 * <p>
 *      FormDTO到CacheDTO
 * </p>
 * @author littlepenguin
 * @since 2022/05/14 9:50
 */
@Mapper
public interface CacheDtoMapper extends AbstractStructMapper<CacheDTO, FormDTO> {
    @Override
    CacheDTO toTarget(FormDTO source);
}
