package cn.alone.ioc.postprocessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class PrintBeanPostProcessor implements BeanPostProcessor {

    public PrintBeanPostProcessor() {
        System.out.println("--- constructor: " + this.getClass().getName());
    }

    @PostConstruct
    public void init() {
        System.out.println("--- PostConstruct: " + this.getClass().getName());
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("--- postProcessBeforeInitialization, bean: " + bean);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("--- postProcessAfterInitialization, bean: " + bean);
        return bean;
    }
}
