package org.vinalynn.voicectrl.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * User: caiwm
 * Date: 13-5-11
 * Time: AM 2:16
 */
public class AppContextAwaredFactory implements ApplicationContextAware{
    private static ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
         AppContextAwaredFactory.applicationContext = applicationContext;
    }


    public static <T> T getBean(Class<T> requiredType) throws BeansException {
        return applicationContext.getBean(requiredType);
    }
}
