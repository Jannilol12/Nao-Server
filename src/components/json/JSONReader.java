package components.json;

import components.classes.Files;
import components.json.parser.JSONParser;

import java.io.File;

public class JSONReader {
	public static abstractJSON read(File file) {
		if(!file.exists()) return null;

		String content = Files.readFile(file.getAbsolutePath());
		if(content == null || content.isEmpty()) return null;
		return JSONParser.parse(content);
	}
}