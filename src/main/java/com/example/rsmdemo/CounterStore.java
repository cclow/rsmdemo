package com.example.rsmdemo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.concurrent.atomic.AtomicReference;

public class CounterStore {
	private AtomicReference<Counter> state;
	private Sinks.Many<Counter> valueSink = Sinks.many()
	                                             .multicast()
	                                             .onBackpressureBuffer();

	public CounterStore(long initial) {
		this.state = new AtomicReference<>(new Counter(initial));
		this.valueSink.tryEmitNext(this.state.get());
	}

	public Flux<Counter> value$() {
		return this.valueSink.asFlux();
	}

	public void dispatch(CounterAction counterAction) {
		// emit new state into value$
		this.valueSink.tryEmitNext(this.state.updateAndGet(counterAction::apply));
	}
}
