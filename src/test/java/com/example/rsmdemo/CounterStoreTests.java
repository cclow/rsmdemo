package com.example.rsmdemo;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class CounterStoreTests {

	@Test void initialValue() {
		long initial = new Random().nextLong();
		CounterStore counterStore = new CounterStore(initial);
		Flux<Long> subject = counterStore.value$();

		StepVerifier.create(subject)
		            .expectNext(initial)
		            .verifyComplete();
	}
}