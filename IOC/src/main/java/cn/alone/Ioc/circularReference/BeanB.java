package cn.alone.Ioc.circularReference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BeanB {

    @Autowired
    private BeanA beanA;

}
