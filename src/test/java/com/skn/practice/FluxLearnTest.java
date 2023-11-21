package com.skn.practice;

import com.skn.practice.services.FluxLearnService;
import java.time.Duration;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.util.function.Tuple2;

@SpringBootTest
public class FluxLearnTest {

  @Autowired
  private FluxLearnService fluxLearnService;

  @Test
  @Disabled
  @DisplayName("Flux Basic From List")
  void myHeart() {
    this.fluxLearnService.getMyHeartFlux()
      .subscribe(data -> System.out.printf("Basic Data: %s\n", data));
  }

  @Test
  @DisplayName("Flux Map")
  @Disabled
  void myHeartMap() {
    //* Map Is Syncronous
    this.fluxLearnService.getMyHeartFluxMap()
      .subscribe(data -> System.out.printf("Map Data: %s\n", data));
  }

  @Test
  @DisplayName("Flux Map Equality Check")
  @Disabled
  void checkMyHearMapCharacteristice() {
    this.fluxLearnService.checkMyHeartFluxCharacteristics()
      .subscribe(data -> System.out.printf("Data Map Check: %s\n", data));
  }

  @Test
  @DisplayName("Flux Map Step Verifier Test")
  @Disabled
  void expectMyHeartFluxTest() {
    Flux<String> myHeartFluxMap = this.fluxLearnService.getMyHeartFluxMap();

    StepVerifier.create(myHeartFluxMap).expectNextCount(2).verifyComplete();
    //* To Much StepVerifier Chaining Of Multiple Expect Can Cause Test Error!!!
    StepVerifier
      .create(myHeartFluxMap)
      .expectNext("Logno".toUpperCase(), "Atoshi".toUpperCase())
      .verifyComplete();
  }

  @Test
  @DisplayName("Flux Flat Map")
  @Disabled
  void myHeartFlatMap() {
    //* Flat Map Is Asyncronous
    this.fluxLearnService.getMyHeartFluxFlatMap()
      .subscribe(data -> System.out.printf("Flat Map Data: %s\n", data));

    Flux<String> myHeartFluxFlatMap =
      this.fluxLearnService.getMyHeartFluxFlatMap();

    StepVerifier
      .create(myHeartFluxFlatMap)
      .expectNextCount(11)
      .verifyComplete();
  }

  @Test
  @DisplayName("Flux Flat Map Delay")
  @Disabled
  void myHeartFlatMapDelay() throws InterruptedException {
    Flux<String> myHeartFluxFlatMapDelay =
      this.fluxLearnService.getMyHeartFluxFlatMapDelay();

    //* If Step Verifier Is Used Then Thread.sleep() Is Not Needed To Use When Using .delayElements()
    StepVerifier
      .create(myHeartFluxFlatMapDelay)
      .expectNextCount(11)
      .verifyComplete();
  }

  @Test
  @DisplayName("Flux Transform")
  @Disabled
  void tranformStringToIntegerFlux() {
    //* Transform Is used to Transform Flux Of One Type To Flux Of Other Type
    // this.fluxLearnService.transformStringToIntegerFlux()
    //   .subscribe(data -> System.out.printf("Transformed Data: %d\n", data));
    Flux<Integer> transformStringToIntegerFlux =
      this.fluxLearnService.transformStringToIntegerFlux()
        .delayElements(Duration.ofMillis(2000));

    StepVerifier
      .create(transformStringToIntegerFlux)
      .expectNextCount(4)
      .verifyComplete();
  }

  @Test
  @DisplayName("Flux Filter")
  @Disabled
  void fluxFilter() {
    Flux<String> fluxFilter = this.fluxLearnService.getFluxFilter(7);

    StepVerifier.create(fluxFilter).expectNextCount(1).verifyComplete();

    System.out.println("---------");

    Flux<String> fluxFilterSwitchEmpty =
      this.fluxLearnService.getFluxFilterSwitchEmpty(7);

    StepVerifier
      .create(fluxFilterSwitchEmpty)
      .expectNextCount(2)
      .verifyComplete();
  }

  @Test
  @DisplayName("Flux Concat")
  @Disabled
  void fluxConcat() {
    //* .concat()[static] & .concatWith() [instance] are syncronous
    Flux<String> fluxConcat = this.fluxLearnService.getFluxConcat();

    StepVerifier.create(fluxConcat).expectNextCount(4).verifyComplete();
  }

  @Test
  @DisplayName("Flux Merge")
  @Disabled
  void fluxMerge() {
    //* .merge()[static] & .mergeWith()[instance] are asyncronous
    Flux<String> fluxMerge = this.fluxLearnService.getFluxMerge();

    StepVerifier.create(fluxMerge).expectNextCount(4).verifyComplete();
  }

  @Test
  @DisplayName("Flux Zip Basic")
  void fluxZipBasic() {
    //* Tuple Of Corresponding Elements Of Two Flux
    Flux<Tuple2<String, String>> fluxZipBasic =
      this.fluxLearnService.getFluxZipBasic();

    StepVerifier.create(fluxZipBasic).expectNextCount(2).verifyComplete();
  }

  @Test
  @DisplayName("Flux Zip Hetero Types")
  void fluxZipHetero() {
    Flux<Tuple2<String, Integer>> fluxZipHeteroType =
      this.fluxLearnService.getFluxZipHeteroType();

    StepVerifier.create(fluxZipHeteroType).expectNextCount(2).verifyComplete();
  }

  @Test
  @DisplayName("Flux Zip Custom CallBack")
  void fluxZipCustomCallBack() {
    Flux<String> fluxZipCustomCallBack =
      this.fluxLearnService.getFluxZipCustomCallBack();

    StepVerifier
      .create(fluxZipCustomCallBack)
      .expectNextCount(2)
      .verifyComplete();
  }

  @Test
  @DisplayName("Flux Side Effect Meythod")
  void fluxSideEffectMethod() {
    Flux<String> fluxSideEffectMethodExample =
      this.fluxLearnService.getFluxSideEffectMethodExample();

    StepVerifier
      .create(fluxSideEffectMethodExample)
      .expectNextCount(2)
      .verifyComplete();
  }
}
