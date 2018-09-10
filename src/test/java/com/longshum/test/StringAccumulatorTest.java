package com.longshum.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.longshum.test.StringAccumulator;

public class StringAccumulatorTest {

	@Test
	public void testAddSimpleCase() throws Exception {
		StringAccumulator tester = new StringAccumulator();
		assertEquals(tester.add(""),0);
		assertEquals(tester.add(null),0);
		assertEquals(tester.add("2"),2);
		assertEquals(tester.add("1,2,3,4,5"),15);
	}

	@Test
	public void testAddMultipleLine() throws Exception {
		StringAccumulator tester = new StringAccumulator();
		assertEquals(tester.add("1\n2,3"),6);
		assertEquals(tester.add("1,2\n3,4\n5,6,7,8\n9,10"),55);
	}
	
	@Test
	public void testDelimiters() throws Exception {
		StringAccumulator tester = new StringAccumulator();
		assertEquals(tester.add("//;\n1;2;3;4;5"),15);
		assertEquals(tester.add("//***\n 1***2***3***4***5"),15);
		assertEquals(tester.add("//abc\n 1 abc 2\n3abc4\n5abc6abc7abc8\n9abc10"),55);
		
		assertEquals(tester.add("//***|,|;\n 1;2;3;4"),10);
		assertEquals(tester.add("//***|,|;\n 1;2,3***4;5"),15);
		assertEquals(tester.add("//***|,|;\n 1***2***3,4;5\n6;7***8,9\n10"),55);
				
	}
	
	@Test
	public void testLargeThanThousand() throws Exception {
		StringAccumulator tester = new StringAccumulator();
		assertEquals(tester.add("//;\n1;2;3;4;5;1001,20000"),15);
		assertEquals(tester.add("//;\n1000;1;2;3;4;5"),1015);
	}
	
	
	
	@Test(expected = Exception.class)
	public void testInvalidInput() throws Exception{
		StringAccumulator tester = new StringAccumulator();
		try {
			assertEquals(tester.add("1,\n"),1);
		}
		catch(Exception e)
		{
			System.out.println("testInvalidInput: " + e.getMessage());
			throw e;
		}
	}
	
	@Test
	public void testAddNegativeNumber() throws Exception{
		StringAccumulator tester = new StringAccumulator();
		String errorMessage=null;
		try {
			int result=tester.add("1,2,3,4,-5, 1000");
		}
		catch(Exception e)
		{
			errorMessage = e.getMessage();
		}
		
		assertNotNull(errorMessage);
		assertTrue(errorMessage.indexOf("negatives not allowed")>=0);
		
		assertTrue(errorMessage.indexOf("1")<0);
		assertTrue(errorMessage.indexOf("2")<0);
		assertTrue(errorMessage.indexOf("3")<0);
		assertTrue(errorMessage.indexOf("4")<0);
		
		assertTrue(errorMessage.indexOf("-5")>0);
		
		
	}
	
	@Test
	public void testAddNegativeNumberWithMultipleDelimiters() throws Exception{
		StringAccumulator tester = new StringAccumulator();
		String errorMessage =null;
		try {
			int result=tester.add("//;|***|,\n1,-2,3,4***-5, -1000");
		}
		catch(Exception e)
		{
			errorMessage = e.getMessage();
		}
		
		assertNotNull(errorMessage);
		assertTrue(errorMessage.indexOf("negatives not allowed")>=0);
		
		assertTrue(errorMessage.indexOf("3")<0);
		assertTrue(errorMessage.indexOf("4")<0);
		
		assertTrue(errorMessage.indexOf("-2")>0);
		assertTrue(errorMessage.indexOf("-5")>0);
		assertTrue(errorMessage.indexOf("-1000")>0);
		
	}

}
