package test;

import static org.junit.Assert.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class RegexTests {
	private static final String point = "\\s*\\d+\\s+\\d+\\s*";
	private static final String points = "\\s*(\\d+\\s+\\d+\\s+)*(\\d+\\s+\\d+\\s*)?";
	//private Pattern r = Pattern.compile(point);
	
	private String[] twoNumbers = {"321 32", "0932 9329 ", "98432 8493     ", "234    984", "0001    1   ", " 1 2", "   30  12   "};
	private String[] threeNumbers = {"1 2 3", "12   22   2  ", "  032 132   832", "8932   382 888", " 2837 873   8723   "};
	private String[] fiveNumbers = {"1 2 3 4 5", "12 321  99  78  9  ", "   23  0  12  3289 91 "};
	private String[] multiplePoints = {"1 2", "1 2 3 4", "1 2 3 4 5 6", "12 321  03 999  ", "  32 9 99   99 9  9", " 1928 382   83  8  8 8 88 92 98 12  "};
	
	
	/*
	String str3 = "89348  4394  ";
	String str4 = "  8832   32321  ";
	String str5 = "  2993 932 21 ";
	
	String str6 = "8493 9384 932";
	String str7 = " 89234  88995";
	
	String str8 = "";
	*/
	@Test
	public void pointCanMatchSinglePoint() {
		assertTrueRegexStrs(point,twoNumbers);
	}
	
	@Test
	public void pointFailsOnMoreThanTwoNumbers() {
		assertFalseRegexStrs(point,threeNumbers);
		assertFalseRegexStrs(point,fiveNumbers);
	}
	
	@Test
	public void pointsCanMatchSinglePoint() {
		assertTrueRegexStrs(points, twoNumbers);
	}
	
	@Test
	public void pointsCanMatchMultiplePoints() {
		assertTrueRegexStrs(points, multiplePoints);
	}
	
	@Test
	public void pointsFailsOnUnevenNumbers() {
		//assertFalse(threeNumbers[1].matches(points));
		assertFalseRegexStrs(points, threeNumbers);
		assertFalseRegexStrs(points, fiveNumbers);
	}

	@Test
	public void matchResultTest() {
		Pattern pattern = Pattern.compile("(?<first>(a?)(b?)(c?))|(?<second>(d?)(e?)(f?))|(?<third>(?:g?)(h?)(i?))");
		Matcher matcher = pattern.matcher("ef");
		
		if (matcher.matches()) {
			//System.out.println(matcher.group());
			//findGroupNumber(matcher);
			System.out.println("second: "+matcher.group("second"));
			System.out.println("The matched group is: "+findGroupNumber(matcher));
		}
		//assertTrue(matcher.matches());
	}
	
	private int findGroupNumber(Matcher m) {
		int val = -1;
		if (m.matches()) {
			for (int i=1; i<=m.groupCount(); i++) {
				System.out.println(""+i+": "+m.group(i));
				if (m.group(i) != null) {
					val = i;
				}
					//return i;
			}
			//return 0;
		}
		return val;
	}
	
	private void assertTrueRegexStrs(String regex, String[] strs) {
		for (String str : strs) {
			assertTrue(str.matches(regex));
		}
	}
	
	private void assertFalseRegexStrs(String regex, String[] strs) {
		for (String str : strs) {
			assertFalse(str.matches(regex));
		}
	}
}
