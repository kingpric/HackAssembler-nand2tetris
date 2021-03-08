package com.hack;

import java.util.HashMap;
import java.util.Map;

public class SymStructure {

	public static Map<String, String> symTable = new HashMap<String, String>();
	private static int currentIdx = 16;

	static {
		reset();
	}

	public static void reset() {
		symTable.putIfAbsent("R0", "0");
		symTable.putIfAbsent("R1", "1");
		symTable.putIfAbsent("R2", "2");
		symTable.putIfAbsent("R3", "3");
		symTable.putIfAbsent("R4", "4");
		symTable.putIfAbsent("R5", "5");
		symTable.putIfAbsent("R6", "6");
		symTable.putIfAbsent("R7", "7");
		symTable.putIfAbsent("R8", "8");
		symTable.putIfAbsent("R9", "9");
		symTable.putIfAbsent("R10", "10");
		symTable.putIfAbsent("R11", "11");
		symTable.putIfAbsent("R12", "12");
		symTable.putIfAbsent("R13", "13");
		symTable.putIfAbsent("R14", "14");
		symTable.putIfAbsent("R15", "15");

		symTable.putIfAbsent("SCREEN", "16384");
		symTable.putIfAbsent("KBD", "24576");

		symTable.putIfAbsent("SP", "0");
		symTable.putIfAbsent("LCL", "1");
		symTable.putIfAbsent("ARG", "2");
		symTable.putIfAbsent("THIS", "3");
		symTable.putIfAbsent("THAT", "4");
		symTable.putIfAbsent("TEMP", "5");
		

	}

	public static String get(String key) {

		if (symTable.get(key) == null)

			symTable.computeIfAbsent(key, k -> String.valueOf(currentIdx++));
		return symTable.get(key);
	}

	public static void set(String key, String value) {
		symTable.putIfAbsent(key, value);
	}

}
