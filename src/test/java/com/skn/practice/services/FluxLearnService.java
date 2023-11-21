package com.skn.practice.services;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

@Service
public class FluxLearnService {

  private List<String> myHeartList = List.of("Logno", "Atoshi");
  private List<String> myIdList = List.of(
    "Wang So",
    "Lee Joon Gi",
    "Lord Saya"
  );
  private List<Integer> myNumList = List.of(7, 3, 1);

  public Flux<String> getMyHeartFlux() {
    return Flux.fromIterable(myHeartList).log();
  }

  public Flux<String> getMyHeartFluxMap() {
    //* Map Is Syncronous
    return Flux
      .fromIterable(myHeartList)
      .map(value -> value.toUpperCase())
      .log();
  }

  public Flux<String> checkMyHeartFluxCharacteristics() {
    Flux<String> f1 = Flux.fromIterable(myHeartList).log();
    Flux<String> map1 = f1.map(value -> value.toLowerCase()).log();

    if (f1.equals(map1)) {
      System.out.printf("They Are Equal\n");
    } else {
      System.out.printf("They Are Not Equal\n");
    }

    return map1;
  }

  public Flux<String> getMyHeartFluxFlatMap() {
    //* Flat Map Is Asyncronous
    return Flux
      .fromIterable(myHeartList)
      .flatMap(value -> Flux.just(value.split("")))
      .log();
  }

  public Flux<String> getMyHeartFluxFlatMapDelay() {
    return Flux
      .fromIterable(myHeartList)
      .flatMap(value -> Flux.just(value.split("")))
      .delayElements(Duration.ofMillis(2000))
      .log();
  }

  public Flux<Integer> transformStringToIntegerFlux() {
    //* Transform Is used to Transform Flux Of One Type To Flux Of Other Type
    Function<Flux<String>, Flux<Integer>> transformer = stringFlux ->
      stringFlux.flatMap(value -> Flux.just(Integer.valueOf(value)));

    return Flux.just("7", "1", "0", "3").transform(transformer).log();
  }

  public Flux<String> getFluxFilter(int length) {
    return Flux
      .fromIterable(myHeartList)
      .filter(value -> value.length() >= length)
      .defaultIfEmpty("No Data Found!")
      .log();
  }

  public Flux<String> getFluxFilterSwitchEmpty(int length) {
    //* switchIfEmpty() works if the main Flux returns empty. Then switchEmpty() will work on the given new Flux inside it
    return Flux
      .fromIterable(myHeartList)
      .filter(value -> value.length() >= length)
      .switchIfEmpty(Flux.fromIterable(myIdList))
      .log();
  }

  public Flux<String> getFluxConcat() {
    //* .concat()[static] & .concatWith()[instance] are syncronous
    Flux<String> f1 = Flux.fromIterable(myHeartList);
    Flux<String> f2 = Flux.fromIterable(myIdList);

    return Flux
      .concat(
        f1.delayElements(Duration.ofMillis(1000)),
        f2.delayElements(Duration.ofMillis(2000))
      )
      .log();
  }

  public Flux<String> getFluxMerge() {
    //* .merge()[static] & .mergeWith()[instance] are asyncronous
    Flux<String> f1 = Flux.fromIterable(myHeartList);
    Flux<String> f2 = Flux.fromIterable(myIdList);

    return Flux
      .merge(
        f1.delayElements(Duration.ofMillis(1000)),
        f2.delayElements(Duration.ofMillis(2000))
      )
      .log();
  }

  public Flux<Tuple2<String, String>> getFluxZipBasic() {
    //* Basic Zip That Returns A Tuple
    Flux<String> f1 = Flux.fromIterable(myHeartList);
    Flux<String> f2 = Flux.fromIterable(myIdList);

    return Flux.zip(f1, f2).log();
  }

  public Flux<Tuple2<String, Integer>> getFluxZipHeteroType() {
    //* Zip That Returns A Tuple Of Different Types
    Flux<String> f1 = Flux.fromIterable(myHeartList);
    Flux<Integer> f2 = Flux.fromIterable(myNumList);

    return Flux.zip(f1, f2).log();
  }

  public Flux<String> getFluxZipCustomCallBack() {
    //* Zip That Doesn't Return A Tuple But Instead A Normal Flux According To The CallBack Function Provided
    Flux<String> f1 = Flux.fromIterable(myHeartList);
    Flux<Integer> f2 = Flux.fromIterable(myNumList);

    return Flux
      .zip(f1, f2, (first, second) -> String.format("%s : %d\n", first, second))
      .log();
  }

  public Flux<String> getFluxSideEffectMethodExample() {
    //* Flux Event Based Side Effect Methods
    //* These Methods Will Run Before Each Event Triggers
    return Flux
      .fromIterable(this.myHeartList)
      .doOnSubscribe(data -> System.out.printf("onSubscribe Data: %s\n", data))
      .doOnRequest(data -> System.out.printf("request Data: %s\n", data))
      .doOnNext(data -> System.out.printf("onNext Data: %s\n", data))
      .doOnComplete(() -> System.out.println("Process Completed Successfully!"))
      .doOnEach(data -> System.out.printf("onEach Data: %s\n", data))
      .log();
  }
}
