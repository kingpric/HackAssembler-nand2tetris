package com.hack;

import java.util.HashMap;
import java.util.Map;

public class Parser {

	String inline;
	char instructionType;
	String instruction;
	Map<String, String> compMap = new HashMap<String, String>();
	Map<String, String> destMap = new HashMap<String, String>();
	Map<String, String> jmpMap = new HashMap<String, String>();

	public Parser() {
		compMapReset();
		destMapReset();
		jmpMapReset();
	}

	public char Parse(String line) {
		String instructValue = "";
		line = line.strip();
		int cmtcnt = 0;
		for (char ch : line.toCharArray()) {
			if (ch == '/') {
				++cmtcnt;
				if (cmtcnt == 1)
					continue;
				else if (cmtcnt == 2) {
					if (instructValue != "")
						break;
					return 'W';
				}
			}
			cmtcnt = 0;
			instructValue = instructValue + String.valueOf(ch);
		}
		instructValue.replaceAll("\\s+", "");

		if (instructValue.length() == 0)
			return 'W';

		if (instructValue.startsWith("@")) {
			instructionType = 'A';
			instruction = instructValue;
			// instruction = instructValue.substring(1);
			// return AInstructionsEcoder(instructValue);
		}

		else if (instructValue.startsWith("(")) {
			instructionType = 'L';
			instruction = instructValue.substring(1, instructValue.length() - 1);
		}

		else {
			instructionType = 'C';
			instruction = instructValue;
		}

		return instructionType;
	}

	public char getInstructionType() {
		return instructionType;
	}

	public String getInstructionValue() {
		return instruction;
	}

	public String AsmToBin(String line) {
		switch (Parse(line)) {
		case 'A':
			return AInstructionsEcoder();

		case 'C':
			return CInstructionEncoder();
		default:
			return null;
		}

	}

	public String AInstructionsEcoder() {

		if (instruction.startsWith("@")) {
			instruction = instruction.substring(1);
		}

		int locValue;
		try {
			locValue = Integer.parseInt(instruction);
		} catch (Exception ex) {
			locValue = Integer.parseInt(SymStructure.get(instruction));
		}

		String binValue = Integer.toBinaryString(locValue);
		return ("0000000000000000" + binValue).substring(binValue.length());
	}

	public String CInstructionEncoder() {

		String destValue = "", compValue = "", jmpValue = "";
		String[] eqSplt = instruction.split("=");

		String sptVal = eqSplt[0];
		if (eqSplt.length > 1) {
			destValue = eqSplt[0];
			sptVal = eqSplt[1];
		}

		String[] scSplt = sptVal.split(";");
		compValue = scSplt[0];
		if (scSplt.length > 1) {
			jmpValue = scSplt[1];
		}

		return "111" + compMap.get(compValue) + destMap.get(destValue) + jmpMap.get(jmpValue);

	}

	private void compMapReset() {
		compMap.put("0", "0101010");
		compMap.put("1", "0111111");
		compMap.put("-1", "0111010");
		compMap.put("D", "0001100");
		compMap.put("A", "0110000");
		compMap.put("M", "1110000");
		compMap.put("!D", "0001101");
		compMap.put("!A", "0110001");
		compMap.put("!M", "1110001");
		compMap.put("-D", "0001111");
		compMap.put("-A", "0110011");
		compMap.put("-M", "1110011");
		compMap.put("D+1", "0011111");
		compMap.put("A+1", "0110111");
		compMap.put("M+1", "1110111");
		compMap.put("D-1", "0001110");
		compMap.put("A-1", "0110010");
		compMap.put("M-1", "1110010");
		compMap.put("D+A", "0000010");
		compMap.put("D+M", "1000010");
		compMap.put("D-A", "0010011");
		compMap.put("D-M", "1010011");
		compMap.put("A-D", "0000111");
		compMap.put("M-D", "1000111");
		compMap.put("D&A", "0000000");
		compMap.put("D&M", "1000000");
		compMap.put("D|A", "0010101");
		compMap.put("D|M", "1010101");

	}

	private void destMapReset() {
		destMap.put("", "000");
		destMap.put("M", "001");
		destMap.put("D", "010");
		destMap.put("A", "100");
		destMap.put("MD", "011");
		destMap.put("AD", "110");
		destMap.put("AM", "101");
		destMap.put("ADM", "111");
	}

	private void jmpMapReset() {
		jmpMap.put("", "000");
		jmpMap.put("JGT", "001");
		jmpMap.put("JEQ", "010");
		jmpMap.put("JGE", "011");
		jmpMap.put("JLT", "100");
		jmpMap.put("JNE", "101");
		jmpMap.put("JLE", "110");
		jmpMap.put("JMP", "111");
	}

}
