package sysu.usc.registerModule.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * <p>
 *      上下文工具类
 * </p>
 * @author littlepenguin
 * @since 2022/05/12 22:43
 */
@Component
public class ApplicationContextUtil implements ApplicationContextAware {
    /**
     * 上下文对象实例
     */
    private static ApplicationContext applicationContext = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (ApplicationContextUtil.applicationContext == null) {
            ApplicationContextUtil.applicationContext = applicationContext;
        }
    }

    /**
     * 获取applicationContext
     *
     * @return
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 通过name获取 Bean.
     *
     * @param name
     * @return
     */
    public static Object getBean(String name) {
        ApplicationContext applicationContext = getApplicationContext();
        if (applicationContext == null) {
            return null;
        }
        return applicationContext.getBean(name);
    }

    /**
     * 获取type所有 beans
     *
     * @param type
     * @return
     */
    public static Collection getBeansOfType(Class type) {
        ApplicationContext applicationContext = getApplicationContext();
        if (applicationContext == null) {
            return null;
        }
        return applicationContext.getBeansOfType(type).values();
    }

    /**
     * 通过class获取Bean.
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> clazz) {
        ApplicationContext applicationContext = getApplicationContext();
        if (applicationContext == null) {
            return null;
        }
        return applicationContext.getBean(clazz);
    }

    /**
     * 通过name,以及Clazz返回指定的Bean
     *
     * @param name
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        ApplicationContext applicationContext = getApplicationContext();
        if (applicationContext == null) {
            return null;
        }
        return applicationContext.getBean(name, clazz);
    }
}

