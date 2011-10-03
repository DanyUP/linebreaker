package com.danielearmellini.linebreaker;

public interface BreakAction<T> {

	public void doFirst(T firstElement);
	
	public void doCurrent(T element);
	
	public void doLast(T lastElement);
	
}
