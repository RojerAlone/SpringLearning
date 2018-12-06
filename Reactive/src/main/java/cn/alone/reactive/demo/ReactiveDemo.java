package cn.alone.reactive.demo;

import reactor.core.publisher.Flux;

/**
 * Created by zhangrenjie on 2018-12-05
 */
public class ReactiveDemo {

    public static void main(String[] args) {
        SimpleSubscriber subscriber = new SimpleSubscriber();
//        Flux<Integer> seq = Flux.range(1, 5).map(i -> {
//            if (i >= 5) throw new RuntimeException("Value is bigger than 4");
//            else return i;});
        Flux<Integer> seq = Flux.range(1, 5);
//        seq.subscribe(System.out::println,
//                System.out::println,
//                () -> System.out.println("Done")).dispose();
        seq.subscribe(subscriber);
    }

    }
