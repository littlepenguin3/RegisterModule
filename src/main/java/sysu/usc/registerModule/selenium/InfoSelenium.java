package sysu.usc.registerModule.selenium;

import org.springframework.beans.factory.InitializingBean;
import sysu.usc.registerModule.domain.template.BaseObject;

public interface InfoSelenium extends InitializingBean {
    <T> void fillBean(String id, T bean);
}
