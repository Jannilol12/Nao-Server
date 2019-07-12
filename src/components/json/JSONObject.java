package components.json;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JSONObject extends abstractJSON{
	private Map<String, abstractJSON> map;
	
	public JSONObject(Map<?, ?> map) {
		this.map = new HashMap<>();
	}
	
	public JSONObject() {
		map = new HashMap<>();
	}

	public void add(String key, Object value) {
		map.put(key, JSONValue.getabstractJSON(value));
	}
	
	public abstractJSON get(String key) {
		return map.get(key);
	}
	
	public void clear() {
		map.clear();
	}
	
	@Override
	protected void write(JSONWriter writer, int height, boolean shouldUseSpace) throws IOException {
		writer.write("{", height);
		writer.newLine();
		
		boolean first = true;
		
		for(String key : map.keySet()) {
			if(first)first = false;
			else {
				writer.write(",", 0);
				writer.newLine();
			}
			
			writer.write("\"" + key + "\"", height+1);
			writer.write(':', 0);
			map.get(key).write(writer, height+1, false);
		}
		
		writer.newLine();
		writer.write("}", height);
	}

	@Override
	protected StringBuffer toJSONString(StringBuffer buffer) {
		buffer.append("{");
		
		boolean first = true;
		
		for(String key : map.keySet()) {
			if(first)first = false;
			else {
				buffer.append(",");
			}
			
			buffer.append("\"" + key + "\":");
			map.get(key).toJSONString(buffer);
		}
		
		buffer.append("}");
		return buffer;
	}
	
	
}
