package com.example.rsmdemo.reactivestore;

public interface Action<T> {
	T apply(T counter);
}
