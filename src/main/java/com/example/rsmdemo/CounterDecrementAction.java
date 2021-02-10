package com.example.rsmdemo;

public class CounterDecrementAction {
	private final long delta;

	public CounterDecrementAction(long delta) {
		this.delta = delta;
	}

	public long getDelta() {
		return delta;
	}
}
