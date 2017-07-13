package com.ekocbiyik.javafx.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created by seyyah on 29.10.2016.
 */
@Component
public class UtilsForSpring implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        UtilsForSpring.applicationContext = applicationContext;
    }

    public static <T> T getSingleBeanOfType(Class<T> beanClass){
        return applicationContext.getBeansOfType(beanClass).values().iterator().next();
    }
}
