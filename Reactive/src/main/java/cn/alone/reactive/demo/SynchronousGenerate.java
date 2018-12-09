package cn.alone.reactive.demo;

import reactor.core.publisher.Flux;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by RojerAlone on 2018-12-09
 *
 * https://projectreactor.io/docs/core/release/reference/#producing
 */
public class SynchronousGenerate {

    public static void main(String[] args) {
        Flux<String> flux = Flux.generate(
                () -> 0,
                (state, sink) -> {
                    sink.next("3 * " + state + " = " + 3 * state);
                    if (state == 10) sink.complete();
                    return  state + 1;
                }
        );
        flux.subscribe(System.out::println);

        System.out.println();

        flux = flux.generate(
                AtomicLong::new,
                (state, sink) -> {
                    long stat = state.getAndIncrement();
                    sink.next("3 * " + state + " = " + 3 * stat);
                    if (stat == 10) {
                        sink.complete();
                    }
                    return state;
                });
        flux.subscribe(System.out::println);
    }

}
