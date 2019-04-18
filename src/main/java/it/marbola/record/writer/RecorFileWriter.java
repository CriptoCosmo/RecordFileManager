package it.marbola.record.writer;

import it.marbola.record.util.PropsUtil;
import lombok.Cleanup;

import java.io.*;
import java.text.MessageFormat;
import java.util.*;

import static org.apache.commons.lang3.StringUtils.leftPad;

public class RecorFileWriter {

	private String outputFilePath;
	private Properties schema;

	public RecorFileWriter(String filePath) throws IOException {
		this(filePath, getSchema("output-schema.properties","input-schema" +
				".properties"));
	}

	public RecorFileWriter(String outputFilePath, String shemaResurceFileName) throws IOException {
		this(outputFilePath, PropsUtil.get(shemaResurceFileName));
	}

	public RecorFileWriter(String outputFilePath, Properties schema) {
		this.schema = schema;
		this.outputFilePath = outputFilePath;
	}

	private static Properties getSchema(String shemaResurceFileName) throws IOException {
		return PropsUtil.get(shemaResurceFileName);
	}

	private static Properties getSchema(String shemaResurceFileName, String rollbackShema) throws FileNotFoundException {
		try {
			return getSchema(shemaResurceFileName);
		} catch (IOException e) {
			try {
				return getSchema(rollbackShema);
			} catch (IOException e1) {

				String message = MessageFormat.format("file {0} or {1} not found",
						shemaResurceFileName, rollbackShema);
				throw new FileNotFoundException(message);
			}
		}
	}

	public void write(Collection<? extends Map<String,String>> records) throws IOException {

		File file = new File(outputFilePath);
		file.createNewFile(); // create if not exist

		@Cleanup FileWriter fileWriter = new FileWriter(file);
		@Cleanup PrintWriter printWriter = new PrintWriter(fileWriter);

		for (Map<String,String> record:records) {
			printWriter.print(format(record));
		}

	}

	private String format(Map<String, String> record){

		StringBuilder line = new StringBuilder();

		for (Enumeration e = schema.propertyNames(); e.hasMoreElements();) {
			String key = e.nextElement().toString();
			int size = Integer.parseInt(schema.getProperty(key));

			if(key.contains("FILLER_")){
				line.append( leftPad(" ", size) );
				continue;
			}

			line.append( leftPad(record.get(key), size) );
		}

		line.append(System.getProperty("line.separator"));

		return line.toString();
	}
}
