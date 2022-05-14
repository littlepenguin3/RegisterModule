package sysu.usc.registerModule.utils;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumUtil {
    /**
     * 安全根据Xpath输入内容
     * @param driver web驱动
     * @param wait webWait对象
     * @param xpath 元素xpath
     * @param str 输入内容
     */
    public static WebDriver driver;
    public static WebDriverWait wait;

    public static void setWebDriver(WebDriver webDriver) {
        SeleniumUtil.driver = webDriver;
    }

    public static void setWebDriverWait(WebDriverWait webDriverWait) {
        SeleniumUtil.wait = webDriverWait;
    }

    public static void setDriverAndWait(WebDriver webDriver,WebDriverWait webDriverWait){
        SeleniumUtil.driver = webDriver;
        SeleniumUtil.wait = webDriverWait;
    }

    public static void safeSendKeysByXpath(WebDriver driver, WebDriverWait wait, String xpath, String str){
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
        WebElement safeElement = driver.findElement(By.xpath(xpath));
        safeElement.sendKeys(str);
    }

    public static WebElement safeFindElementByXpath(WebDriver driver,WebDriverWait wait, String xpath) {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
        return driver.findElement(By.xpath(xpath));
    }

    public static void safeClickByXpath(WebDriver driver,WebDriverWait wait, String xpath) {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
        WebElement safeElement = driver.findElement(By.xpath(xpath));
        safeElement.click();
    }

    public static void elementToBeClickableByXpath(WebDriverWait wait, String xpath) {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
    }

    public static void safeSendKeysByXpath(String xpath, String str){
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
        WebElement safeElement = driver.findElement(By.xpath(xpath));
        safeElement.sendKeys(str);
    }

    public static WebElement safeFindElementByXpath(String xpath) {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
        return driver.findElement(By.xpath(xpath));
    }

    public static void safeClickByXpath(String xpath) {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
        WebElement safeElement = driver.findElement(By.xpath(xpath));
        safeElement.click();
    }

    public static void elementToBeClickableByXpath(String xpath) {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
    }

    public static String safeGetTextAreaByXpath(String xpath) {
        wait.until(ExtendExpectedConditions.textIsNotBlankInElementValue(By.xpath(xpath)));
        WebElement description = driver.findElement(By.xpath(xpath));
        return description.getAttribute("value");
    }

    public static String safeGetTextAreaByXpath(String xpath,String checkText) {
        wait.until(ExpectedConditions.textToBePresentInElementValue(By.xpath(xpath),checkText));
        WebElement description = driver.findElement(By.xpath(xpath));
        return description.getAttribute("value");
    }

    public static String safeGetTextAreaByXpath(WebDriver driver, WebDriverWait wait,String xpath,String checkText) {
        wait.until(ExpectedConditions.textToBePresentInElementValue(By.xpath(xpath),""));
        WebElement description = driver.findElement(By.xpath(xpath));
        return description.getAttribute("value");
    }

    /**
     * 点击某些不能够被检测出点击的DOM,后来发现是因为没有切换到iframe 因此废除
     * @param xpath
     */
    @Deprecated
    public static void safeClickDOMByXpath(String xpath) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        WebElement safeElement = driver.findElement(By.xpath(xpath));
        safeElement.click();
    }
    /**
     * 点击某些不能够被检测出点击的DOM,后来发现是因为没有切换到iframe 因此废除
     * @param xpath
     */
    @Deprecated
    public static void safeClickDOMByXpath(WebDriver driver, WebDriverWait wait, String xpath) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        WebElement safeElement = driver.findElement(By.xpath(xpath));
        safeElement.click();
    }

    public static void safeSwitchToFrameByXpath(String xpath){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        driver.switchTo().frame(driver.findElement(By.xpath(xpath)));
    }

    public static void switchToFrameByXpath(WebDriver driver, WebDriverWait wait, String xpath){
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        driver.switchTo().frame(driver.findElement(By.xpath(xpath)));
    }

    public static void handlePrompt(WebDriver driver, WebDriverWait wait,Boolean handle){
        wait.until(ExtendExpectedConditions.handleAlert(handle));
    }
    public static void acceptPrompt(WebDriver driver, WebDriverWait wait){
        handlePrompt(driver,wait,true);
    }
    public static void dismissPrompt(WebDriver driver, WebDriverWait wait){
        handlePrompt(driver,wait,false);
    }
    public static void safeCheckTextInElementLocatedByXpath(String xpath,String checkText){
        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath(xpath),checkText));
    }
}
