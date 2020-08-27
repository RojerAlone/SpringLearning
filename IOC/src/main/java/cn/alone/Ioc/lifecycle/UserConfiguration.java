package cn.alone.Ioc.lifecycle;

import cn.alone.Ioc.common.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class UserConfiguration {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(ProxyUserConfiguration.class);
        applicationContext.register(NonProxyUserConfiguration.class);
        applicationContext.refresh();

        User proxyUser = applicationContext.getBean("proxyUser", User.class);
        User nonProxyUser = applicationContext.getBean("nonProxyUser", User.class);
        System.out.println("proxy User: " + proxyUser + ", hashcode: " + Objects.hashCode(proxyUser));
        System.out.println("non-proxy User: " + nonProxyUser + ", hashcode: " + Objects.hashCode(nonProxyUser));

        ProxyUserConfiguration proxyUserConfiguration = applicationContext.getBean(ProxyUserConfiguration.class);
        NonProxyUserConfiguration nonProxyUserConfiguration = applicationContext.getBean(NonProxyUserConfiguration.class);
        // 两个不同的 Configuration，一个配置了 proxyBeanMethod 一个没有，没配置的 Bean 由于没有代理，所以每次都会原样执行方法，返回一个新创建的 bean
        // 所以 @PostConstruct 这样的注解也不会执行
        System.out.println("get again, new proxy user hash code: " + Objects.hashCode(proxyUserConfiguration.proxyUser()));
        System.out.println("get again, new non-proxy user hash code: " + Objects.hashCode(nonProxyUserConfiguration.nonProxyUser()));
        // 重新从 ApplicationContext 中获取 bean，未进行代理新创建的 Bean 是不存在的
        proxyUser = applicationContext.getBean("proxyUser", User.class);
        nonProxyUser = applicationContext.getBean("nonProxyUser", User.class);
        System.out.println("proxy User: " + proxyUser + ", hashcode: " + Objects.hashCode(proxyUser));
        System.out.println("non-proxy User: " + nonProxyUser + ", hashcode: " + Objects.hashCode(nonProxyUser));

        applicationContext.close();
    }
}
