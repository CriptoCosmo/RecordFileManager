package it.marbola.record.reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class RecordFileReader extends AbstractFileReader {

	public RecordFileReader(String inputFile, Class<?> modelMapClass) throws IOException {
		super(inputFile, modelMapClass);
	}

	public RecordFileReader(String filePath) throws IOException {
		super(filePath);
	}

	public RecordFileReader(String filePath, String shemaResurceFileName) throws IOException {
		super(filePath, shemaResurceFileName);
	}

	public RecordFileReader(String filePath, Properties schema) throws IOException {
		super(filePath, schema);
	}

	public void inizialize(String filePath, Properties schema) throws IOException {
		elements = new ArrayList<>();

		try (BufferedReader reader =
					 Files.newBufferedReader(Paths.get(filePath), charset)) {

			String line;
			while ((line = reader.readLine()) != null) {
				try{
					Map<String, String> map = map(line, schema);
					if (!map.isEmpty()) {
						elements.add(map);
					}
				}catch (Exception e){
					e.printStackTrace();
					System.out.println("error on line " + line );
				}
			}

		} catch (IOException e) {
			throw new IOException("errore nella letture dello schema",e);
		}
	}

	public Map<String,String> map(String line, Properties schema) {

		Map<String,String> map = new HashMap<>();

		SequenceReader sequenceReader = new SequenceReader(line);

		for (Enumeration e = schema.propertyNames(); e.hasMoreElements();) {

			String key = e.nextElement().toString();

			int size = Integer.parseInt(schema.get(key).toString());

			if(key.contains("FILLER_")){
				sequenceReader.jump(size);
				continue;
			}

			String next = sequenceReader.next(size, true);

			if(next != null){
				map.put(key,next);
			}
		}

		return map;
	}
}
