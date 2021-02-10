package com.example.rsmdemo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

public class CounterStore {
	private Long state;
	private Sinks.Many<Long> valueSink = Sinks.many()
	                                          .multicast()
	                                          .onBackpressureBuffer();

	public CounterStore(long initial) {
		this.state = initial;
		this.valueSink.tryEmitNext(this.state);
	}

	public Flux<Long> value$() {
		return this.valueSink.asFlux();
	}

	public void increment(long delta) {
		this.state += delta;
		// emit new state into value$
		this.valueSink.tryEmitNext(this.state);
	}

	public void decrement(long delta) {
		this.state -= delta;
		this.valueSink.tryEmitNext(this.state);
	}
}
