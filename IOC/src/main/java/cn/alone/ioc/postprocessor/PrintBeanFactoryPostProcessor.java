package cn.alone.ioc.postprocessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class PrintBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    public PrintBeanFactoryPostProcessor() {
        System.out.println("--- constructor: " + this.getClass().getName());
    }

    @PostConstruct
    public void init() {
        System.out.println("--- PostConstruct: " + this.getClass().getName());
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        System.out.println("--- BeanFactoryPostProcessor invoked");
    }
}
