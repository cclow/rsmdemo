package com.example.rsmdemo;

public interface Action<T> {
	T apply(T counter);
}
