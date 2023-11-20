package com.skn.practice;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuple3;

@SpringBootTest
class SpringPracticeApplicationTests {

  @Test
  void contextLoads() {}

  @Test
  @Disabled("Not Needed. Just For Practicing")
  @DisplayName("Basic Mono Data & Error")
  void workingWithMonoBasic() {
    Mono<String> error = Mono.error(new RuntimeException("Error Occured!!!"));
    Mono<String> m1 = Mono.just("Hello SKN!").log().then(error);

    m1.subscribe(data -> System.out.printf("Data: %s\n", data));
    error.subscribe(err -> System.out.printf("Output: %s\n", err));
  }

  @Test
  @Disabled
  @DisplayName("Combined Mono")
  void workingWithMonoZip() {
    Mono<String> m1 = Mono.just("Hello!!").log();
    Mono<String> m2 = Mono.just("I am SKN!!!").log();
    Mono<String> m3 = Mono.just("I am also Wang So!!!").log();

    Mono<Tuple2<String, String>> combinedMono2 = Mono.zip(m1, m2);

    combinedMono2.subscribe(data -> {
      System.out.printf("Data1: %s\n", data.getT1());
      System.out.printf("Data1: %s\n", data.getT2());
    });

    System.out.println("Process Completed!");

    Mono<Tuple3<String, String, String>> combinedMono3 = Mono.zip(m1, m2, m3);

    combinedMono3.subscribe(data -> {
      System.out.printf("Data1: %s\n", data.getT1());
      System.out.printf("Data2: %s\n", data.getT2());
      System.out.printf("Data3: %s\n", data.getT3());
    });

    System.out.println("Process Completed!");

    Mono<Tuple2<String, String>> combinedM2M3 = m2.zipWith(m3);

    combinedM2M3.subscribe(data -> {
      System.out.printf("Data1: %s\n", data.getT1());
      System.out.printf("Data2: %s\n", data.getT2());
    });

    System.out.println("Process Completed!");
  }

  @Test
  @Disabled
  @DisplayName("Mono Map")
  void workingWithMonoMap() {
    //* Mono Map Works On Value, Changes It Internally & Returns Another Mono
    Mono<String> m1 = Mono.just("This Is Mono Instance1 Data");
    Mono<String> m2 = Mono.just("This Is Mono Instance2 Data");
    Mono<String> m3 = Mono.just("This Is Mono Instance3 Data");

    Mono<String> map1 = m1.map(value -> value.toUpperCase()).log();
    Mono<String> map2 = m2.map(value -> value.toLowerCase()).log();
    Mono<String[]> map3 = m3.map(value -> value.split(" ")).log();

    map1.subscribe(data -> System.out.printf("Map1 Data: %s\n", data));
    map2.subscribe(data -> System.out.printf("Map2 Data: %s\n", data));
    map3.subscribe(data -> {
      for (String s : data) {
        System.out.printf("Map3 Data Array Element: %s\n", s);
      }
    });
  }

  @Test
  @DisplayName("Mono Flat Map")
  void workingWithMonoFlatMap() {
    //* Mono Flat Map Works On Value, Creates A New Mono With That Value & Returns The New Mono
    Mono<String> m1 = Mono.just("This Is Mono Flat Map Data1");
    Mono<String> m2 = Mono.just("This Is Mono Flat Map Data2");

    Mono<String> flatMap1 = m1
      .flatMap(value -> Mono.just(value.toUpperCase()))
      .log();
    Mono<String[]> flatMap2 = m2
      .flatMap(value -> Mono.just(value.split(" ")))
      .log();

    flatMap1.subscribe(data -> System.out.printf("Flat Map 1: %s\n", data));
    flatMap2.subscribe(data -> {
      for (String s : data) {
        System.out.printf("Flat Map 2: %s\n", s);
      }
    });
  }

  @Test
  @DisplayName("Mono Flat Map Many")
  void workingWithMonoFlatMapMany() {
    //* Mono Flat Map Many Works On Value, Creates A Flux & Returns That Flux
    Mono<String> m1 = Mono.just("This is Mono Flat Map Many Data1");
    Mono<String> m2 = Mono.just("This is Mono Flat Map Many Data2");

    Flux<String> flatMapMany1 = m1
      .flatMapMany(value -> Flux.just(value.toUpperCase()))
      .log();
    Flux<String> flatMapMany2 = m2
      .flatMapMany(value -> Flux.just(value.split(" ")))
      .log();

    flatMapMany1.subscribe(data ->
      System.out.printf("Flat Map Many Data1: %s\n", data)
    );

    flatMapMany2.subscribe(data ->
      System.out.printf("Flat Map Many Data 2: %s\n", data)
    );
  }
}
