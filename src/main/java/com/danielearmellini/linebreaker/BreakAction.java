package com.danielearmellini.linebreaker;

/**
 * Class that implements the action to be applied to the equivalence class computed by
 * line breaker
 * @author Daniele Armellini
 *
 * @param <T>
 */
public interface BreakAction<T> {

	/**
	 * Method executed on the first element of every equivalence class
	 * @param firstElement
	 */
	public void doFirst(T firstElement);
	
	/**
	 * Method executed on every element of the equivalence class (including the first
	 * and the last element)
	 * @param element
	 */
	public void doCurrent(T element);
	
	/**
	 * Method executed on the last element of every equivalence class
	 * @param lastElement
	 */
	public void doLast(T lastElement);
	
}
