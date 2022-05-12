package sysu.usc.registerModule.utils;

import cn.hutool.core.util.StrUtil;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.AbstractDriverOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;
import sysu.usc.registerModule.VO.Selenium;
import sysu.usc.registerModule.exception.WebDriverNotMatchException;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.temporal.TemporalUnit;

import static sysu.usc.registerModule.constants.WebDriverConstants.CHROME;
import static sysu.usc.registerModule.constants.WebDriverConstants.HEADLESS;


/**
 * <p>
 *      webDriver工厂
 * </p>
 * @author littlepenguin
 * @since 2022/05/12 14:06
 */
@Component
public class WebDriverFactory {
    @Resource
    Selenium selenium;
    public WebDriver buildWebDriver() throws WebDriverNotMatchException {
        //chrome启动逻辑
        if(StrUtil.containsIgnoreCase(selenium.chromeDriver.name, CHROME)){
            //创建配置
            System.setProperty(selenium.chromeDriver.name,selenium.chromeDriver.path);
            ChromeOptions chromeOptions=new ChromeOptions();
            if(selenium.isHeadless) {chromeOptions.addArguments(HEADLESS);}
            //返回WebDriver
            return new ChromeDriver(chromeOptions);
        }
        throw new WebDriverNotMatchException();
    }
    public WebDriverWait buildWebDriverWait(WebDriver webDriver, long amount, TemporalUnit unit){
        return new WebDriverWait(webDriver, Duration.of(amount,unit));
    }
}
