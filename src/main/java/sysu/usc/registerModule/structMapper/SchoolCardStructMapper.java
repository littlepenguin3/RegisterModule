package sysu.usc.registerModule.structMapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import sysu.usc.registerModule.domain.SchoolCard;
import sysu.usc.registerModule.dto.PersistenceDTO;
import sysu.usc.registerModule.structMapper.abs.AbstractBaseObjectStructMapper;

/**
 * <p>
 *         bmcDTO使用此Mapper Impl转换为其他类型
 * </p>
 * @author littlepenguin
 * @since 2022/05/13 6:01
 */
@Mapper
public interface SchoolCardStructMapper extends AbstractBaseObjectStructMapper<SchoolCard> {
    @Override
    @Mappings({@Mapping(target="createTime",
            expression = "java( new java.sql.Timestamp(source.getTimeStamp()) )")
    })
    SchoolCard toTarget(PersistenceDTO source);
}
