package com.example.rsmdemo;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

class CounterStoreTests {

	@Test void initialValue() {
		CounterStore counterStore = new CounterStore(10L);
		Flux<Long> subject = counterStore.value$();

		StepVerifier.create(subject)
		            .expectNext(10L)
		            .verifyComplete();
	}
}