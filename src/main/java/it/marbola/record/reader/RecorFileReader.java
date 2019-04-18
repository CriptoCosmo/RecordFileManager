package it.marbola.record.reader;
import it.marbola.record.reader.util.PropsUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class RecorFileReader {

	private Charset charset = Charset.defaultCharset();

	private List<Map<String,String>> elements ;

	public RecorFileReader(String filePath) throws IOException {
		this(filePath,"input-schema.properties");
	}

	public RecorFileReader(String filePath, String shemaResurceFileName) throws IOException {
		this(filePath, PropsUtil.get(shemaResurceFileName));
	}

	public RecorFileReader(String filePath, Properties schema) {

		elements = new ArrayList<>();

		try (BufferedReader reader =
					 Files.newBufferedReader(Paths.get(filePath), charset)) {

			String line;
			while ((line = reader.readLine()) != null) {
				Map<String, String> map = map(line, schema);
				if (!map.isEmpty()) {
					elements.add(map);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Map<String,String> map(String line, Properties schema) {

		Map<String,String> map = new HashMap<>();

		SequenceReader sequenceReader = new SequenceReader(line);

		for (Enumeration e = schema.propertyNames(); e.hasMoreElements();) {

			String key = e.nextElement().toString();

			int size = Integer.parseInt(schema.getProperty(key));

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

	public void setCharset(Charset charset){
		this.charset = charset;
	}

	public List<Map<String,String>> getElements(){
		return elements ;
	}

}
