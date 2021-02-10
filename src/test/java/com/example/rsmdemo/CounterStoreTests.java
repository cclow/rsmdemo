package com.example.rsmdemo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Random;

class CounterStoreTests {
	long initial;
	Store<Counter> counterStore;
	Flux<Long> subject;

	@BeforeEach void setUp() {
		initial = randomLong();
		counterStore = new Store<>(new Counter(initial));
		subject = counterStore.value$()
		                      .map(Counter::getValue);
	}

	private long randomLong() {
		return new Random().nextLong();
	}

	@Test void initialValue() {
		StepVerifier.create(subject.take(1))
		            .expectNext(initial)
		            .verifyComplete();
	}

	@Test void dispatchIncrementAction() {
		long delta = randomLong();
		StepVerifier.create(subject.take(2))
		            .then(() -> counterStore.dispatch(new CounterIncrementAction(delta)))
		            .expectNext(initial, initial + delta)
		            .verifyComplete();
	}

	@Test void dispatchDecrementAction() {
		long delta = randomLong();
		StepVerifier.create(subject.take(2))
		            .then(() -> counterStore.dispatch(new CounterDecrementAction(delta)))
		            .expectNext(initial, initial - delta)
		            .verifyComplete();
	}

	@Test void dispatchIncrementAndDecrementActions() {
		long delta1 = randomLong();
		long delta2 = randomLong();
		long delta3 = randomLong();
		long delta4 = randomLong();
		StepVerifier.create(subject.take(5))
		            .then(() -> {
			            counterStore.dispatch(new CounterDecrementAction(delta1));
			            counterStore.dispatch(new CounterIncrementAction(delta2));
			            counterStore.dispatch(new CounterIncrementAction(delta3));
			            counterStore.dispatch(new CounterDecrementAction(delta4));
		            })
		            .expectNext(initial, initial - delta1, initial - delta1 + delta2, initial - delta1 + delta2 + delta3,
		                        initial - delta1 + delta2 + delta3 - delta4)
		            .verifyComplete();
	}
}