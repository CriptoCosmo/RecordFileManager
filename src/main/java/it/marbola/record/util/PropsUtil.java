package it.marbola.record.reader.util;

import lombok.Cleanup;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropsUtil {

	public static Properties get(String resourceName) throws IOException {

		ClassLoader loader = Thread.currentThread().getContextClassLoader();

		Properties props = new OrderedProperties();

		try {
			@Cleanup
			InputStream resourceStream = loader.getResourceAsStream(resourceName);
			props.load(resourceStream);
		} catch (IOException e) {
			throw new IOException("File not found exception");
		}

		return props;
	}

}
