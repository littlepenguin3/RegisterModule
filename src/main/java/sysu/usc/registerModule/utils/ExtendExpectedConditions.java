package sysu.usc.registerModule.utils;

import cn.hutool.core.util.StrUtil;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class ExtendExpectedConditions {
    public static ExpectedCondition<Boolean> textIsNotBlankInElementValue(final By locator) {
        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    String elementText = driver.findElement(locator).getAttribute("value");
                    return StrUtil.isNotBlank(elementText);
                } catch (StaleElementReferenceException e) {
                    return false;
                }
            }
        };
    }
    public static ExpectedCondition<Boolean> handleAlert(Boolean handle) {
        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                Alert alert = null;
                try {
                    alert = driver.switchTo().alert();
                } catch (NoAlertPresentException e) {
                    return true;
                }
                if(alert != null){
                    if(Boolean.TRUE.equals(handle)) {
                        alert.accept();
                    }else {
                        alert.dismiss();
                    }
                }
                return true;
            }

            @Override
            public String toString() {
                return "alert to be present";
            }
        };
    }
}
