package cn.alone.Ioc.ch1;

import org.springframework.stereotype.Component;

/**
 * Created by RojerAlone on 2017/7/1.
 */
@Component
public class HelloWorldImpl_2 implements HelloWorld {
    public void sayHello() {
        System.out.println("\tAnother Hello World");
    }
}
