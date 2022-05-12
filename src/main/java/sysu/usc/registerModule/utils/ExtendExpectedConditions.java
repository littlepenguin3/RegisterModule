package sysu.usc.registerModule.utils;

import cn.hutool.core.util.StrUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
}
