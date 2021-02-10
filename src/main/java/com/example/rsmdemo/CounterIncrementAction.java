package com.example.rsmdemo;

public class CounterIncrementAction implements Action<Counter> {
	private final long delta;

	public CounterIncrementAction(long delta) {
		this.delta = delta;
	}

	public Counter apply(Counter counter) {
		return new Counter(counter.getValue() + this.delta);
	}
}
