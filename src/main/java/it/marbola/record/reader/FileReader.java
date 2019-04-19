package it.marbola.record.reader;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Properties;

public interface FileReader {

	void setCharset(Charset charset);

	void inizialize(String filePath, Properties schema) throws IOException;

}
