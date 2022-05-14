package sysu.usc.registerModule.constants;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 38122
 */
@Component
@NoArgsConstructor
@Data
@ConfigurationProperties(prefix = "selenium")
public class Selenium {
    public Boolean isHeadless;
    public BrowserDriver chromeDriver;
    public Web web;
    public Wait waitService;
    public Wait waitException;
}
