package cn.alone.Ioc.lifecycle;

import cn.alone.Ioc.common.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
public class NonProxyUserConfiguration {

    @Bean
    public User nonProxyUser() {
        return new User("non-proxy user", 24);
    }
}
