package cn.alone.Ioc.lifecycle;

import cn.alone.Ioc.common.User;
import cn.alone.Ioc.common.UserFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Spring Bean Lifecycle
 */
public class BeanLifecycleDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.scan("cn.alone.Ioc.common");
        applicationContext.refresh();

        UserFactory userFactory = applicationContext.getBean(UserFactory.class);
        User user = userFactory.getUser();
        System.out.println(user);

        applicationContext.close();
    }

}
