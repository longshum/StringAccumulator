package com.longshum.test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class StringAccumulator {
	
	private ArrayList<Integer> negativeNumberList;
	private int result;
	
	
	private ArrayList<String> getDelimiterArray(String input)
	{
		String[] delimiterArray = input.split(Pattern.quote("|"));
		ArrayList<String> delimiterList = new ArrayList<String>();
		for(int i=0; i<Array.getLength(delimiterArray); i++)
		{
			delimiterList.add(delimiterArray[i].trim());
		}
		return delimiterList;
	}
	
	public int add(String numbers) throws Exception
	{
		
		result=0;
		ArrayList<String> delimiterArray = new ArrayList<String>();
		
		negativeNumberList = new ArrayList<Integer>();
		
		if(numbers == null || "".equals(numbers))
		{
			return result;
		}
		
		String[] lineArray = numbers.split("\n");
		ArrayList<String> lineList = new ArrayList<String>();


		int startIndex = 0;
		
		if(lineArray[0].startsWith("//"))
		{
			delimiterArray = getDelimiterArray(lineArray[0].substring(2));
			startIndex++;
		}
		else
		{
			delimiterArray.add(",");
		}
		
		for(int i=startIndex; i<lineArray.length; i++)
		{
			String lastChar = lineArray[i].substring(lineArray[i].length()-1);
			try {
				Integer.parseInt(lastChar);
			}
			catch(Exception e)
			{
				throw new Exception("Unsupported end of line: " + lastChar);
			}
				
			lineList.add(lineArray[i].trim());
		}
		
		for(String delimiter : delimiterArray)
		{
			ArrayList<String> remainingStr = new ArrayList<String>();
			for(String line : lineList)
			{
				remainingStr.addAll(addLine(line, delimiter));
			}
			lineList = remainingStr;
		}
		
		if(!negativeNumberList.isEmpty())
		{
			StringBuffer message = new StringBuffer();
			for(Integer num : negativeNumberList) {
				if(message.length()==0)
					message.append(num.toString());
				else
					message.append("," + num.toString());
			}
			throw new Exception("negatives not allowed : " + message.toString());
		}
		return result;
	}
	
	public ArrayList<String> addLine(String numbers, String delimiter) throws Exception
	{
		ArrayList<String> strList = new ArrayList<String>();;


		if(numbers == null || "".equals(numbers))
		{
			return strList;
		}

		String[] numberArray = numbers.split(Pattern.quote(delimiter));
		
		for(String str : numberArray)
		{
			int num=0;
				try{
					num = Integer.parseInt(str.trim());
					if(num<=1000)
					{
						result += num;
					}
					if(num<0)
					{
						negativeNumberList.add(num);
					}
				}
				catch(Exception e)
				{
					strList.add(str);
				}
		}
		return strList;
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		StringAccumulator stringAccumulator = new StringAccumulator();

		try {
			String testString = null;

			if(stringAccumulator.add(testString)==0)
			{
				System.out.println("null. Passed");
			}

			if(stringAccumulator.add("")==0)
			{
				System.out.println("0. Passed");
			}

			if(stringAccumulator.add("2")==2)
			{
				System.out.println("1. Passed");
			}

			if(stringAccumulator.add("2,3")==5)
			{
				System.out.println("2. Passed");
			}


			if(stringAccumulator.add("1,2,3")==6)
			{
				System.out.println("3. Passed");
			}

			if(stringAccumulator.add("1,2,3,4,5,6,7,8,9,10")==55)
			{
				System.out.println("4. Passed");
			}
			
			if(stringAccumulator.add("1\n2,3")==6)
			{
				System.out.println("5. Passed");
			}
			
			if(stringAccumulator.add("//;\n1;2")==3)
			{
				System.out.println("6. Passed");
			}
			
			try {
				if(stringAccumulator.add("//;\n1;2;-1;-3;10;-7")==3)
				{
					System.out.println("7. Failed");
				}
			}
			catch(Exception e)
			{
				System.out.println("7. Passed. " + e.getMessage());
			}
			
			
			if(stringAccumulator.add("//;\n1;2;1000;1001;1002")==1003)
			{
				System.out.println("8. Passed");
			}
			
			if(stringAccumulator.add("//abc\n1abc2abc1000abc1001abc1002")==1003)
			{
				System.out.println("9. Passed");
			}
			
			
			if(stringAccumulator.add("//abc|;\n1abc2;1000abc1001abc1002")==1003)
			{
				System.out.println("10. Passed");
			}
			
			if(stringAccumulator.add("//abc|;|,|xyz\n1abc2;3;4xyz5;6;7,8abc9,10\n11xyz12abc13")==91)
			{
				System.out.println("11. Passed");
			}
			
			if(stringAccumulator.add("//abc|;|,|xyz\n1,2")==3)
			{
				System.out.println("12. Passed");
			}
			
			System.out.println(stringAccumulator.add("//***|,|;\n 1***2***3,4;5\n6;7***8,9\n10"));
		
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
