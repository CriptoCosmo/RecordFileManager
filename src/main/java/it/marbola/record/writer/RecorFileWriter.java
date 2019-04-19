package it.marbola.record.writer;

import it.marbola.record.reader.AbstractFileReader;
import it.marbola.record.util.PropsUtil;
import lombok.Cleanup;

import java.io.*;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

import static org.apache.commons.lang3.StringUtils.leftPad;

public class RecorFileWriter extends AbstractFileReader {

	private String filePath;

	public RecorFileWriter(String filePath) throws IOException {
		super(filePath,getSchema("output-schema.properties", "input-schema" +
				".properties"));
	}

	public RecorFileWriter(String filePath, String shemaResurceFileName) throws IOException {
		super(filePath, shemaResurceFileName);
	}

	public RecorFileWriter(String filePath, Properties schema) throws IOException {
		super(filePath, schema);
	}

	public RecorFileWriter(String filePath, Class<?> clazz) throws IOException {
		super(filePath, clazz);
	}

	@Override
	public void inizialize(String filePath, Properties schema) {
		this.schema = schema;
		this.filePath = filePath;
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

		File file = new File(filePath);
		file.createNewFile(); // create if not exist

		@Cleanup FileWriter fileWriter = new FileWriter(file);
		@Cleanup PrintWriter printWriter = new PrintWriter(fileWriter);

		for (Map<String,String> record:records) {
			printWriter.print(serialize(record));
		}

	}

	private String serialize(Map<String, String> record){

		StringBuilder line = new StringBuilder();

		for (Enumeration e = schema.propertyNames(); e.hasMoreElements();) {
			String key = e.nextElement().toString();
			int size = Integer.parseInt(schema.get(key).toString());

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
