package com.example.rsmdemo;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.concurrent.atomic.AtomicLong;

public class CounterStore {
	private AtomicLong state;
	private Sinks.Many<Long> valueSink = Sinks.many()
	                                          .multicast()
	                                          .onBackpressureBuffer();

	public CounterStore(long initial) {
		this.state = new AtomicLong(initial);
		this.valueSink.tryEmitNext(this.state.get());
	}

	public Flux<Long> value$() {
		return this.valueSink.asFlux();
	}

	public void dispatch(CounterIncrementAction counterIncrementAction) {
		// emit new state into value$
		this.valueSink.tryEmitNext(this.state.addAndGet(counterIncrementAction.getDelta()));
	}

	public void dispatch(CounterDecrementAction counterDecrementAction) {
		this.valueSink.tryEmitNext(this.state.addAndGet(-counterDecrementAction.getDelta()));
	}
}
