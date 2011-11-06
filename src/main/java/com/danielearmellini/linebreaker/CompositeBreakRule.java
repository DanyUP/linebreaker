package com.danielearmellini.linebreaker;

import java.util.Comparator;

public class CompositeBreakRule<T> implements BreakRule<T> {

	private Comparator<T> comparator;
	private BreakAction<T> action;
	
	public CompositeBreakRule(Comparator<T> comparator, BreakAction<T> action){
		this.comparator = comparator;
		this.action = action;
	}
	

	public void doFirst(T firstElement) {
		action.doFirst(firstElement);
	}



	public void doCurrent(T element) {
		action.doCurrent(element);
	}



	public void doLast(T lastElement) {
		action.doLast(lastElement);
	}



	public int compare(T o1, T o2) {
		return comparator.compare(o1, o2);
	}

}
