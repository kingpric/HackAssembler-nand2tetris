package com.hack;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HackAssembler {

	public static void main(String[] fileNames) throws IOException {

		if (fileNames.length == 0) {
			System.out.println("Enter complete filename as argument");
			System.exit(0);
			// fileNames = new String[] { "g:\\Automation Journey\\nand2tetris\\projects\\06\\pong\\PongL.asm" };
		}

		Parser parser = new Parser();

		for (int i = 0; i < fileNames.length; i++) {

			System.out.println("Processing file:" + fileNames[i]);

			File file = new File(fileNames[i]);

			if (file.exists() && file.isFile()) {

				FileReader fread = new FileReader(file);
				BufferedReader bfread = new BufferedReader(fread);

				String txtLine;
				List<String> procASM = new ArrayList<String>();
				int lineNum = 0;
				while ((txtLine = bfread.readLine()) != null) {

					char instType = parser.Parse(txtLine);
					String instVal = parser.getInstructionValue();
					System.out.println(lineNum + "\t" + instType + "\t" + instVal + "\t" + txtLine);
					if (instType == 'L') {
						SymStructure.set(instVal, String.valueOf(lineNum));
					} else if (instType == 'A' || instType == 'C') {
						lineNum++;
						procASM.add(instVal);
					}

				}

				bfread.close();
				fread.close();

				int totalLines = lineNum;

				lineNum = 0;
				String outFileName = fileNames[i].substring(0, fileNames[i].length() - 3);
				FileWriter fw = new FileWriter(outFileName + "hack");
				BufferedWriter bufwr = new BufferedWriter(fw);

				for (String asmStr : procASM) {
					String bin = parser.AsmToBin(asmStr);
					System.out.println(lineNum + "\t" + bin);
					bufwr.append(bin);

					if (++lineNum < totalLines) {
						bufwr.newLine();
					}
					bufwr.flush();
				}

				bufwr.close();
				fw.close();

				for (Map.Entry<String, String> entry : SymStructure.symTable.entrySet())
					System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());

			} else {
				System.err.println("Error in processing: " + fileNames[i]);
			}

		}

	}

}
