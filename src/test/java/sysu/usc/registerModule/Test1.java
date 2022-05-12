package sysu.usc.registerModule;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sysu.usc.registerModule.VO.Selenium;
import sysu.usc.registerModule.dto.BmcDTO;
import sysu.usc.registerModule.exception.WebDriverNotMatchException;
import sysu.usc.registerModule.utils.SeleniumUtil;
import sysu.usc.registerModule.utils.WebDriverFactory;

@SpringBootTest
public class Test1 {
    @Autowired
    Selenium selenium;
    @Autowired
    WebDriverFactory webDriverFactory;
    WebDriver webDriver;
    WebDriverWait serviceWait;
    WebDriverWait exceptionWait;
    String id = "19335067";
    @Test
    public void test2() throws WebDriverNotMatchException {
        webDriver = webDriverFactory.buildWebDriver();
        serviceWait = webDriverFactory.buildWebDriverWait(webDriver,selenium.waitService.amount,selenium.waitService.unit);
        exceptionWait = webDriverFactory.buildWebDriverWait(webDriver,selenium.waitException.amount,selenium.waitException.unit);
        //TODO bmc上身份不完全
        long begin = System.currentTimeMillis();
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
        //点击新事件
        SeleniumUtil.safeClickByXpath(selenium.web.xpaths.newEvent);
        //点击学工号框
        SeleniumUtil.safeClickByXpath(selenium.web.xpaths.userId);
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
        System.out.println(bmcDTO);
        //清除记录
        SeleniumUtil.safeClickByXpath(selenium.web.xpaths.clear);
        //退出BMC
        SeleniumUtil.safeClickByXpath(selenium.web.xpaths.close);
        //进入退出的frame中
        SeleniumUtil.safeSwitchToFrameByXpath(selenium.web.xpaths.closeFrame);
        SeleniumUtil.safeClickByXpath(selenium.web.xpaths.closeButton);
        long end = System.currentTimeMillis();
        System.out.println(end-begin + "ms");
    }
}
