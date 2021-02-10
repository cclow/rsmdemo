package com.example.rsmdemo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.concurrent.atomic.AtomicReference;

public class Store<T> {
	private AtomicReference<T> state;
	private Sinks.Many<T> valueSink = Sinks.many()
	                                       .multicast()
	                                       .onBackpressureBuffer();

	public Store(T initial) {
		this.state = new AtomicReference<>(initial);
		this.valueSink.tryEmitNext(this.state.get());
	}

	public Flux<T> value$() {
		return this.valueSink.asFlux();
	}

	public void dispatch(Action<T> counterAction) {
		// emit new state into value$
		this.valueSink.tryEmitNext(this.state.updateAndGet(counterAction::apply));
	}
}
