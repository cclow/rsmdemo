package com.example.rsmdemo;

import reactor.core.publisher.Flux;

public class CounterStore {
	private Long state;

	public CounterStore(long initial) {
		this.state = initial;
	}

	public Flux<Long> value$() {
		return Flux.just(this.state);
	}
}
