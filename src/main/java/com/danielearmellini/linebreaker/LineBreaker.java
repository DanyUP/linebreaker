package com.danielearmellini.linebreaker;

import java.util.Comparator;
import java.util.List;

/**
 * Class that manage line breaking of a list of objects.
 * To implement a breaking strategy you need to use the appropriate BreakAction or
 * to create your own.
 * @author Daniele Armellini
 *
 * @param <T>
 */
public class LineBreaker<T> {

	private BreakAction<T> action;
	
	/**
	 * Creates a new LineBreaker object with the action passed as parameter.
	 * @param action applied for line breaking
	 */
	public LineBreaker(BreakAction<T> action){
		this.action = action;
	}
	
	/**
	 * Executes the line breaking strategy in the list passed as argument. The comparator
	 * is used to compute the equivalence classes to which break action is applied.
	 * @param list of objects to be broken
	 * @param comparator used for determine the equivalence class
	 */
	public void execute(List<T> list, Comparator<T> comparator){
		T oldObject = null;
		for(T element : list){
			if(element == null){
				throw new NullPointerException("Elements to be broken cannot be null.");
			}
			
			if((oldObject == null) || (comparator.compare(element, oldObject) != 0)){
				if(oldObject != null){
					this.action.doLast(oldObject);
				}
				oldObject = element;
				this.action.doFirst(element);
			}
			this.action.doCurrent(element);
		}
		
		if(list.size() > 0){
			this.action.doLast(oldObject);
		}
	}
}
