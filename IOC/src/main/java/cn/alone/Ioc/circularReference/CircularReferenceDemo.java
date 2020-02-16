package cn.alone.Ioc.circularReference;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Spring Bean 循环依赖
 */
public class CircularReferenceDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.scan(CircularReferenceDemo.class.getPackage().getName());
        applicationContext.refresh();

        /*
         * 循环依赖之 Autowire 依赖，BeanA 和 BeanB 互相依赖
         * BeanA 实例化完了，开始初始化，填充属性，遇到依赖了 BeanB，从 BeanFactory 中获取 beanB，但是没有，所以需要 createBean
         * BeanB 实例化完了，开始初始化，遇到了依赖 BeanA，这时候的初始化是在 BeanA 的初始化中的，
         * 而 BeanA 在之前已经把自己做了提前暴露处理了，因此这时候 BeanB 从 BeanFactory 中是可以获取到 BeanA 的，
         * 通过 singletonFactories 中获取到了 BeanFactory，再通过 BeanFactory.getObject 获取到了 BeanA 的实例
         *
         * 原理细节:
         * 为了解决循环依赖，Spring 在 DefaultSingletonBeanRegistry （AbstractBeanFactory 的父类）中设置了 3 个辅助变量：
         *
         * Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);
         * Map<String, ObjectFactory<?>> singletonFactories = new HashMap<>(16);
         * Map<String, Object> earlySingletonObjects = new HashMap<>(16);
         *
         * singletonObjects 是已经完成初始化后的 bean
         * singletonFactories 是未完成初始化的 ObjectFactory
         * earlySingletonObjects 是为了解决循环依赖，允许未初始化完成的 bean 先对外暴露
         *
         * 在实例化完了以后，判断是否允许提前暴露，如果允许，放到 singletonFactories 中，在这之后才执行属性注入和初始化方法。
         * 完成整个 bean 的处理后，会调用 addSingleton 方法，
         * 这个方法添加 bean 到 singletonObjects 并且清理 singletonFactories 和 earlySingletonObjects
         *
         * 循环依赖的处理主要在 getSingleton 方法中完成：
         * 获取循环依赖 bean 的时候，先从 singletonObjects 中找，如果找不到，可能 bean 还没初始化，判断是否允许获取提前暴露的bean，
         * getSingleton 方法默认是允许的，因此从 singletonFactories，如果有对应 bean 的 ObjectFactory，就调用 getObject 方法获取，
         * 再将获取到的 bean 放入到 earlySingletonObjects 中，因为这个 bean 只是实例化了，还没有执行初始化，只是提前暴露出来了，
         * 这样就获取到了还未初始化完成的 bean
         *
         * 这里有个疑问，为什么不是直接把未初始化的 bean 放到 earlySingletonObjects 中？
         * 个人猜测是为了统一设计，因为 bean 不仅有单例的还有 prototype 的，因此都放到 ObjectFactory 中
         */
        applicationContext.getBean(BeanA.class);
        /*
         * BeanC 和 BeanD 是构造器循环依赖，这个是无法解决的，因此会抛出异常 BeanCurrentlyInCreationException
         * BeanC 在实例化的时候，发现只有一个需要 BeanD 的构造器，那么就先去获取 BeanD，同样的情况，BeanD 在实例化之前先去获取 BeanC。
         * 在实例化 bean 之前，会调用 beforeSingletonCreation 方法，在这个方法中检测当前 bean 是不是正在创建中，
         * 而之前已经设置了 BeanC 和 BeanD 都已经是正在创建中的状态了，如果一个 bean 正在创建，并且又一次要实例化 bean，
         * 那么就说明发生了循环依赖，进而抛出异常 BeanCurrentlyInCreationException
         *
         * 上面的步骤就是
         *
         * getBean                      获取 beanC
         * getSingleton                 调用 getSingleton 获取 beanC，没有获取到，因此 createBean 创建 beanC
         * createBean
         *     beforeSingletonCreation  检测 beanC 是不是正在创建，并且把 beanC 添加到正在创建的 bean name 集合中
         *     doCreateBean             进行真正的创建，实例化 beanC，发现依赖了 beanD
         *     doResolveDependency      调用 doResolveDependency 方法解决依赖 beanD
         *         getBean              获取 beanD
         *         ...                  同上步骤，调用 getSingleton - createBean... 等操作，直到再次调用 getBean("beanC")
         *     beforeSingletonCreation  检测，发现 beanC 正在创建，抛出异常
         */
        applicationContext.getBean(BeanC.class);
    }

}
