package it.marbola.record.demo;

import it.marbola.record.reader.RecordFileReader;
import it.marbola.record.writer.RecorFileWriter;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Example {

	public static void main(String...args) throws IOException {

		// Read
		String inputFile = "C:\\Users\\User1\\Desktop\\input.txt";

		RecordFileReader recorFileReader = new RecordFileReader(inputFile, ModelMap.class);

		for (Map element: recorFileReader.getElements()) {
			System.out.print(element);
		}

		// Write
		String outputFile = "C:\\Users\\User1\\Desktop\\asdasdd.txt";

		Map<String,String> elements = new HashMap<>();

		elements.put("id","id");
		elements.put("codiceFiscale","codiceFiscale");
		elements.put("sequence","sequence");
		elements.put("number","number");
		elements.put("date","date");
		elements.put("note","note");

		RecorFileWriter recorFileWriter = new RecorFileWriter(outputFile, ModelMap.class);
		recorFileWriter.write(Arrays.asList(elements));

	}
}
