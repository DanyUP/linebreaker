package com.danielearmellini.linebreaker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
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
	
	private List<BreakRule<T>> rules = new ArrayList<BreakRule<T>>();
	
	/**
	 * Creates a new LineBreaker object with the action passed as parameter.
	 * @param action applied for line breaking
	 */
	public LineBreaker(){ }
	
	/**
	 * Adds a new rule to the rule chain of this line breaker. Multiple rules
	 * are used for nested line breaks.
	 * @param rule to be added
	 */
	public void addRule(BreakRule<T> rule){
		rules.add(rule);
	}
	
	/**
	 * Executes the line breaking strategy through the list passed as argument.
	 * The rules added to this object are used for line breaking.
	 * @param collection list of objects to be passed to the line breaker
	 */
	public void execute(Collection<T> collection){
		execute(collection.iterator());
	}
	
	
	/**
	 * Executes the line breaking strategy through the iterator passed as argument.
	 * The rules added to this object are used for line breaking.
	 * @param it iterator to the items to be passed to the line breaker
	 */
	public void execute(Iterator<T> it){
		T oldObject = null;
		while(it.hasNext()){
			T element = it.next();
			if(element == null){
				throw new NullPointerException("Elements to be broken cannot be null.");
			}
			boolean firstElementRaised = false;
			
			for(BreakRule<T> rule : rules){
				if(!firstElementRaised){
					firstElementRaised = checkFirstElement(rule, element, oldObject);
				}
				executeRule(rule, element, oldObject, firstElementRaised);
			}
			
			oldObject = element;
		}
		
		if(oldObject != null){
			for(BreakRule<T> rule : rules){
				rule.doLast(oldObject);
			}
		}
	}
	
	private boolean checkFirstElement(BreakRule<T> rule, T element, T oldObject){
		if((oldObject == null) || (rule.compare(element, oldObject) != 0)){
			return true;
		} else {
			return false;
		}
	}
	
	private void executeRule(BreakRule<T> rule, T element, T oldObject, boolean firstElement){
		if(firstElement){
			if(oldObject != null){
				rule.doLast(oldObject);
			}
			rule.doFirst(element);
		}
		rule.doCurrent(element);
	}
}
