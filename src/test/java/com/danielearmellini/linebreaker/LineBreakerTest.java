package com.danielearmellini.linebreaker;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;



/**
 * Unit test for simple App.
 */
public class LineBreakerTest
{
	private List<String[]> elements = new ArrayList<String[]>();
	
	@Before
	public void setUp(){
		elements.add(new String[]{"John", "Smith", "Manager", "SectorA"});
		elements.add(new String[]{"Frank", "Powell", "Employee", "SectorA"});
		elements.add(new String[]{"Arthur", "Herbert", "Employee", "SectorA"});
		elements.add(new String[]{"John", "Doe", "Manager", "SectorB"});
		elements.add(new String[]{"Michael", "Smith", "Employee", "SectorB"});
		elements.add(new String[]{"Michael", "Herbert", "Manager", "SectorC"});
	}

    /**
     * Rigourous Test :-)
     */
	@Test
    public void testApp()
    {
		
		Map<String, List<String[]>> sectorsMap = new HashMap<String, List<String[]>>();
		
		BreakRule<String[]> rule = new SectorsBreakAction(sectorsMap);
		
		LineBreaker<String[]> breaker = new LineBreaker<String[]>();
		breaker.addRule(rule);
		breaker.execute(elements);
		
		assertEquals(3, sectorsMap.size());
		
		List<String[]> people = sectorsMap.get("SectorA");
		assertNotNull(people);
		assertEquals(3, people.size());
		for(Iterator<String[]> it = people.iterator(); it.hasNext(); ){
			String[] elem = it.next();
			assertEquals("SectorA", elem[3]);
		}
		
		people = sectorsMap.get("SectorB");
		assertNotNull(people);
		assertEquals(2, people.size());
		for(Iterator<String[]> it = people.iterator(); it.hasNext(); ){
			String[] elem = it.next();
			assertEquals("SectorB", elem[3]);
		}
		
		people = sectorsMap.get("SectorC");
		assertNotNull(people);
		assertEquals(1, people.size());
		for(Iterator<String[]> it = people.iterator(); it.hasNext(); ){
			String[] elem = it.next();
			assertEquals("SectorC", elem[3]);
		}
    }
	
	public static void main(String[] args) {
		
		LineBreakerTest test = new LineBreakerTest();
		test.setUp();
		
		BreakRule<String[]> rule = new BreakRule<String[]>() {
			
			int numElem = 0;
			
			public int compare(String[] str1, String[] str2) {
				return str1[3].compareTo(str2[3]);
			}
			
			public void doFirst(String[] firstElement) {
				numElem=0;
				System.out.println("Sector: "+firstElement[3]);
				System.out.println("Name\tSurname\tRole\t");
				System.out.println("--------------------------------------------------");
				
			}
			
			public void doCurrent(String[] element) {
				System.out.println(element[0] + "\t" + element[1] + "\t" + element[2] + "\t");
				numElem++;
			}
			
			public void doLast(String[] lastElement) {
				System.out.println("Total: "+numElem);
				System.out.println();
			}
			
		};
		
		LineBreaker<String[]> breaker = new LineBreaker<String[]>();
		breaker.addRule(rule);
		breaker.execute(test.elements);
		
		
	}
}

class SectorsBreakAction implements BreakRule<String[]>{

	private Map<String, List<String[]>> output;
	private List<String[]> prevList;
	
	public SectorsBreakAction(Map<String, List<String[]>> sectorsMap){
		output = sectorsMap;
	}
	
	public void doFirst(String[] firstElement) {
		prevList = new ArrayList<String[]>();
	}

	public void doCurrent(String[] element) {
		prevList.add(element);
	}

	public void doLast(String[] lastElement) {
		output.put(lastElement[3], prevList);
	}

	public int compare(String[] str1, String[] str2) {
		return str1[3].compareTo(str2[3]);
	}
	
	public Map<String, List<String[]>> getOutputList(){
		return output;
	}
	
}
