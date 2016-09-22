//Splitta il testo in base alla pipe
package it.uniroma3.gta.utils;

import java.util.HashMap;
import java.util.regex.Pattern;

public class CommandHelper {
	private final static String pipeREGEX = "\\|";
	
	public CommandHelper() {}
	
	public boolean matchPipePattern(String text) {
		if (text.contains("|"))
			return true;
		return false;
	}
	
	public HashMap<Integer,String> splitText(String text) {
		Pattern pattern = Pattern.compile(pipeREGEX);
		HashMap<Integer,String> substrings = new HashMap<Integer,String>();
		int index = 0;
		if (matchPipePattern(text)) {
			for (String s: pattern.split(text)) {
				substrings.put(index,s);
				index++;
			}
			return substrings;
		}
		return substrings;
	}
}
