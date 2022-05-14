package sysu.usc.registerModule.utils;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import sysu.usc.registerModule.constants.Selenium;
import sysu.usc.registerModule.dto.PersistenceDTO;
import sysu.usc.registerModule.dto.CacheDTO;
import sysu.usc.registerModule.exception.WebDriverNotMatchException;

/**
 * <p>
 *      获取信息工具类
 * </p>
 * @author littlepenguin
 * @since 2022/05/12 10:08
 */
@Component
@Scope("prototype")
@Slf4j
public class BmcUtil {

    /**
     * <p>
     *      从DTO中得到BmcDTO
     *      为了转换时的方便，BmcDTO中含有
     * </p>
     * @author littlepenguin
     * @since 2022/05/12 10:30
     */
    public static WebDriverFactory webDriverFactory;
    public static Selenium selenium;
    static {
        webDriverFactory = ApplicationContextUtil.getBean(WebDriverFactory.class);
        selenium = ApplicationContextUtil.getBean(Selenium.class);
        assert webDriverFactory != null;
        assert selenium != null;
    }

    public static void FillPersistenceDtoWithBmcInfo(PersistenceDTO persistenceDTO) throws InstantiationException, IllegalAccessException {
        try {
            queryFromBmc(persistenceDTO);
        } catch (WebDriverNotMatchException e) {
            e.printStackTrace();
        }
     }
     private static void queryFromBmc(PersistenceDTO persistenceDTO) throws WebDriverNotMatchException {
        String userId = persistenceDTO.getUserId();
         //webDriverFactory一定能够被Spring获取
         WebDriver webDriver = webDriverFactory.buildWebDriver();
         long begin = System.currentTimeMillis();
         try {
             WebDriverWait serviceWait = webDriverFactory.buildWebDriverWait(webDriver, selenium.waitService.amount, selenium.waitService.unit);
             WebDriverWait exceptionWait = webDriverFactory.buildWebDriverWait(webDriver, selenium.waitException.amount, selenium.waitException.unit);
             //TODO bmc上身份不完全
             webDriver.get(selenium.web.url);
             SeleniumUtil.setDriverAndWait(webDriver, serviceWait);
             //输入账号
             SeleniumUtil.safeSendKeysByXpath(selenium.web.xpaths.username, selenium.web.username);
             //输入密码
             SeleniumUtil.safeSendKeysByXpath(selenium.web.xpaths.password, selenium.web.password);
             //点击登录按钮
             SeleniumUtil.safeClickByXpath(selenium.web.xpaths.loginButton);
             try {
                 SeleniumUtil.acceptPrompt(webDriver, exceptionWait);
             } finally {
                 log.info("prompt异常控制结束");
             }
             //点击应用程序
             SeleniumUtil.safeClickByXpath(selenium.web.xpaths.application);
             //点击事件管理
             SeleniumUtil.safeClickByXpath(selenium.web.xpaths.eventManager);
             //点击新事件
             SeleniumUtil.safeClickByXpath(selenium.web.xpaths.newEvent);
             //点击学工号框
             SeleniumUtil.safeClickByXpath(selenium.web.xpaths.userId);
             //输入学工号
             SeleniumUtil.safeSendKeysByXpath(selenium.web.xpaths.userId, userId);
             //点击弹出的tab
             SeleniumUtil.safeClickByXpath(selenium.web.xpaths.tab);
             //获取姓名
             String name = SeleniumUtil.safeGetTextAreaByXpath(selenium.web.xpaths.name);
             //获取部门
             String department = SeleniumUtil.safeGetTextAreaByXpath(selenium.web.xpaths.department);
             persistenceDTO.setName(name);
             persistenceDTO.setDepartment(department);
             //清除记录
             SeleniumUtil.safeClickByXpath(selenium.web.xpaths.clear);
         }finally {
             //退出BMC
             SeleniumUtil.safeClickByXpath(selenium.web.xpaths.close);
             //进入退出的frame中
             SeleniumUtil.safeSwitchToFrameByXpath(selenium.web.xpaths.closeFrame);
             SeleniumUtil.safeClickByXpath(selenium.web.xpaths.closeButton);
             long end = System.currentTimeMillis();
             log.info("此次访问bmc查询姓名和部门共耗时" + (end-begin) + "ms");
             //确认BMC已经注销才能退出
             SeleniumUtil.safeCheckTextInElementLocatedByXpath(selenium.web.xpaths.quitConfirm,"您已成功注销");
             log.info("BMC已成功注销!");
             webDriver.close();
         }
     }
}
