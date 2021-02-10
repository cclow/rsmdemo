package com.example.rsmdemo;

public class CounterIncrementAction {
	private final long delta;

	public CounterIncrementAction(long delta) {
		this.delta = delta;
	}

	public long getDelta() {
		return delta;
	}
}
