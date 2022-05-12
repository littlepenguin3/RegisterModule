package sysu.usc.registerModule.selenium;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;
import sysu.usc.registerModule.VO.Selenium;
import sysu.usc.registerModule.dto.BmcDTO;
import sysu.usc.registerModule.exception.WebDriverNotMatchException;
import sysu.usc.registerModule.utils.SeleniumUtil;
import sysu.usc.registerModule.utils.WebDriverFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * <p>
 *      多实例Selenium
 * </p>
 * @author littlepenguin
 * @since 2022/05/12 11:54
 */
@Component
@Slf4j
public class SingletonInfoSelenium implements InfoSelenium {

    private final Selenium selenium;
    private final WebDriverFactory webDriverFactory;
    WebDriver webDriver;
    WebDriverWait serviceWait;
    WebDriverWait exceptionWait;

    public SingletonInfoSelenium(Selenium selenium,
                                 WebDriverFactory webDriverFactory,
                                 ThreadPoolExecutor threadPool) throws WebDriverNotMatchException {
        this.selenium = selenium;
        this.webDriverFactory = webDriverFactory;
    }

    /**
     * 初始化BMC连接
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("SingletonInfoSelenium初始化完毕");
    }

    @Override
    public <T> void fillBean(String id, T bean) {
        try {
            webDriver = webDriverFactory.buildWebDriver();
        } catch (WebDriverNotMatchException e) {
            e.printStackTrace();
        }
        try{
            serviceWait = webDriverFactory.buildWebDriverWait(webDriver,selenium.waitService.amount,selenium.waitService.unit);
            exceptionWait = webDriverFactory.buildWebDriverWait(webDriver,selenium.waitException.amount,selenium.waitException.unit);
            //TODO bmc上身份不完全
            webDriver.get(selenium.web.url);
            SeleniumUtil.setDriverAndWait(webDriver,serviceWait);
            //输入账号
            SeleniumUtil.safeSendKeysByXpath(selenium.web.xpaths.username,selenium.web.username);
            //输入密码
            SeleniumUtil.safeSendKeysByXpath(selenium.web.xpaths.password,selenium.web.password);
            //点击登录按钮
            SeleniumUtil.safeClickByXpath(selenium.web.xpaths.loginButton);
            //点击应用程序
            SeleniumUtil.safeClickByXpath(selenium.web.xpaths.application);
            //点击事件管理
            SeleniumUtil.safeClickByXpath(selenium.web.xpaths.eventManager);
            //点击搜索事件
            SeleniumUtil.safeClickByXpath(selenium.web.xpaths.searchEvent);
            //输入学工号
            SeleniumUtil.safeSendKeysByXpath(selenium.web.xpaths.userId,id);
            //点击弹出的tab
            SeleniumUtil.safeClickByXpath(selenium.web.xpaths.tab);
            //获取姓名
            String name = SeleniumUtil.safeGetTextAreaByXpath(selenium.web.xpaths.name);
            //获取部门
            String department = SeleniumUtil.safeGetTextAreaByXpath(selenium.web.xpaths.department);
            BmcDTO bmcDTO = new BmcDTO();
            bmcDTO.setName(name);
            bmcDTO.setDepartment(department);
            //退出BMC
            SeleniumUtil.safeClickByXpath(selenium.web.xpaths.close);
            SeleniumUtil.safeClickByXpath(selenium.web.xpaths.closeButton);
        }finally {
            webDriver.close();
        }
    }


}
