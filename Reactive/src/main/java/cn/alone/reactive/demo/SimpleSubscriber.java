package cn.alone.reactive.demo;

import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;

/**
 * Created by zhangrenjie on 2018-12-05
 */
public class SimpleSubscriber extends BaseSubscriber<Integer> {

    @Override
    protected void hookOnSubscribe(Subscription subscription) {
        // 所谓的背压，设置自己还能处理多少个请求
        subscription.request(1);
        // 或者也可以使用 request(long n) 方法
//        request(4);
    }

    // 通过 request() 可以进行限制处理速度或其他情况（比如状态或请求个数）
    @Override
    protected void hookOnNext(Integer value) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("value is " + value);
        request(1);
    }

}
