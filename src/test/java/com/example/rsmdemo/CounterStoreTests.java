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

	@Test void decrementByDelta() {
		long delta = randomLong();
		StepVerifier.create(subject.take(2))
		            .then(() -> counterStore.decrement(delta))
		            .expectNext(initial, initial - delta)
		            .verifyComplete();
	}

	@Test void incrementAndDecrement() {
		long delta1 = randomLong();
		long delta2 = randomLong();
		long delta3 = randomLong();
		long delta4 = randomLong();
		StepVerifier.create(subject.take(5))
		            .then(() -> {
			            counterStore.decrement(delta1);
			            counterStore.increment(delta2);
			            counterStore.increment(delta3);
			            counterStore.decrement(delta4);
		            })
		            .expectNext(initial, initial - delta1, initial - delta1 + delta2, initial - delta1 + delta2 + delta3,
		                        initial - delta1 + delta2 + delta3 - delta4)
		            .verifyComplete();
	}

	@Test void dispatchIncrementAction() {
		long delta = randomLong();
		StepVerifier.create(subject.take(2))
		            .then(() -> counterStore.dispatch(new CounterIncrementAction(delta)))
		            .expectNext(initial, initial + delta)
		            .verifyComplete();
	}
}