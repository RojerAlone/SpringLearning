package cn.alone.Ioc.ch1;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

/**
 * Created by RojerAlone on 2017/7/1.
 * 在配置文件中定义bean，从applicationContext中获取bean
 */
public class Main {

    HelloWorld helloWorld;

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext_ch1.xml");
        Main main = new Main();

        main.helloWorld = (HelloWorld) applicationContext.getBean("hello");
        System.out.println("getBeanByNameAndCast:" + main.helloWorld);
        main.helloWorld.sayHello();

        // 在 applicationContext中如果配置了两个继承自 HelloWorld的类，那么根据接口获取类会报错
        // expected single matching bean but found 2
        main.helloWorld = applicationContext.getBean(HelloWorldImpl.class);
        System.out.println("getBeanByClass:" + main.helloWorld);
        main.helloWorld.sayHello();

        main.helloWorld = applicationContext.getBean("hello", HelloWorld.class);
        System.out.println("getBeanByNameAndClass:" + main.helloWorld);
        main.helloWorld.sayHello();

        Map<String, HelloWorld> map = applicationContext.getBeansOfType(HelloWorld.class);
        System.out.println("getBeanOfType:");
        for (Map.Entry entry : map.entrySet()) {
            System.out.println("\t" + map.get(entry.getKey()));
        }

    }

}
