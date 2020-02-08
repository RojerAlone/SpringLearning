package cn.alone.Ioc.common;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 生命周期
 * 1. Bean 实例化，即调用构造方法
 * 2. Bean 属性赋值，即注入 Spring 相关注解的标注的变量，如 @Value / @Autowired 等
 * 3. Bean 初始化，即按照 @PostConstruct -> InitializingBean.afterPropertiesSet -> init-method 的优先级调用各个方法
 * 4. 销毁，依次按照 @PreDestroy -> DisposableBean.destroy -> destroy-method 的优先级调用各个方法
 */
@Component
public class DefaultUserFactory implements UserFactory {

    public DefaultUserFactory() {
        System.out.println("UserFactory Constructor"); // 生命周期 1
    }

    @Autowired
    public void setBeanFactory(BeanFactory beanFactory) {
        System.out.println("UserFactory setBeanFactory"); // 生命周期 2
    }

    @PostConstruct
    public void init() {
        System.out.println("UserFactory @PostConstruct"); // 生命周期 3
    }

    @PreDestroy
    public void destroy() {
        System.out.println("UserFactory @PreDestroy"); // 生命周期 4
    }

    @Override
    public User getUser() {
        return new User("RojerAlone", 20);
    }

}
