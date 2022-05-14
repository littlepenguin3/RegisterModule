package sysu.usc.registerModule.structMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import sysu.usc.registerModule.dto.CacheDTO;
import sysu.usc.registerModule.dto.PersistenceDTO;
import sysu.usc.registerModule.structMapper.abs.AbstractStructMapper;

/**
 * <p>
 *      CacheDTO转换为PersistenceDTO
 * </p>
 * @author littlepenguin
 * @since 2022/05/14 10:03
 */
@Mapper
public interface PersistenceDtoMapper extends AbstractStructMapper<PersistenceDTO, CacheDTO> {
    @Override
    @Mappings({@Mapping(target="timeStamp",
            expression = "java( sysu.usc.registerModule.utils.SnowFlakeUtil.parseStampFromId(source.getId()) )")
    })
    PersistenceDTO toTarget(CacheDTO source);
}
