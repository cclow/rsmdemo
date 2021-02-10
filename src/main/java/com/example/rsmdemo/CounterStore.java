package com.example.rsmdemo;

import reactor.core.publisher.Flux;

public class CounterStore {
	public CounterStore(long initial) {

	}

	public Flux<Long> value$() {
		return Flux.just(10L);
	}
}
