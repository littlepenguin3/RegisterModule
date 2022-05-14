package sysu.usc.registerModule.constants;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@NoArgsConstructor
@Data
/**
 * <p>
 *
 * </p>
 * @author littlepenguin
 * @since 2022/05/12 14:18
 */
public class Web {
    public String url;
    public String username;
    public String password;
    public Xpaths xpaths;
}
