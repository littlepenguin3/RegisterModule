package sysu.usc.registerModule.constants;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
@Data
public class BrowserDriver {
    public String name;
    public String path;
}
