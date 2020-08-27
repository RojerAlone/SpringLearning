package cn.alone.Ioc.lifecycle;

import cn.alone.Ioc.common.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProxyUserConfiguration {

    @Bean
    public User proxyUser() {
        return new User("proxy user", 24);
    }
}
