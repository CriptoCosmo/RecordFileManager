package it.marbola.record.reader;

import it.marbola.record.annotation.Definition;
import it.marbola.record.annotation.Filler;
import it.marbola.record.util.OrderedProperties;
import it.marbola.record.util.PropsUtil;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public abstract class AbstractFileReader implements FileReader {

	protected Properties schema;

	protected List<Map<String,String>> elements ;

	protected Charset charset = Charset.defaultCharset();

	public AbstractFileReader(String filePath) throws IOException {
		this(filePath,"input-schema.properties");
	}

	public AbstractFileReader(String filePath, String shemaResurceFileName) throws IOException {
		this(filePath, PropsUtil.get(shemaResurceFileName));
	}

	public AbstractFileReader(String filePath, Properties schema) throws IOException {
		inizialize(filePath,schema);
	}

	public AbstractFileReader(String filePath,Class<?> clazz) throws IOException {

		Properties schema = new OrderedProperties();

		int countFiller = 0;

		for(Field field : clazz.getDeclaredFields()){
			Class type = field.getType();

			Definition definition = field.getAnnotation(Definition.class);

			String definitionName = definition.name().trim();
			String name = definitionName.equals("") ? field.getName() : definitionName ;

			if(type == Filler.class){
				name = "FILLER_" + countFiller++;
			}

			schema.put(name, definition.size());

		}

		inizialize(filePath,schema);
	}

	public void setCharset(Charset charset){
		this.charset = charset;
	}

	public List<Map<String,String>> getElements(){
		return elements ;
	}
}
