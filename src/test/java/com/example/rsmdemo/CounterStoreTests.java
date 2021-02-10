package com.example.rsmdemo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Random;

class CounterStoreTests {
		long initial;
		CounterStore counterStore;
		Flux<Long> subject;

	@BeforeEach void setUp() {
		initial = randomLong();
		counterStore = new CounterStore(initial);
		subject = counterStore.value$();
	}

	private long randomLong() {
		return new Random().nextLong();
	}

	@Test void initialValue() {
		StepVerifier.create(subject.take(1))
		            .expectNext(initial)
		            .verifyComplete();
	}

	@Test void incrementByDelta() {
		long delta = randomLong();
		StepVerifier.create(subject.take(2))
		            .then(() -> counterStore.increment(delta))
		            .expectNext(initial, initial + delta)
		            .verifyComplete();
	}
}