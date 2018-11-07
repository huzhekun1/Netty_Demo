package com.chat.common.core.utils;


import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;

public class MapperBeanNameGenerator implements BeanNameGenerator {

    @Override
    public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
        String beanClass = definition.getBeanClassName();
        int w = beanClass.indexOf(".write.");

        int r = beanClass.indexOf(".read.");
        return (w > 0) ? beanClass.substring(w + 1) : beanClass.substring(r + 1);
    }
}
