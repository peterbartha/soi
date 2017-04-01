package websocket.message;

import java.util.HashMap;
import java.util.Map;

public class WebSocketMessage {
	
	private String type;
	private Map<String, Object> properties;
	
	
	public WebSocketMessage() {
		this.properties = new HashMap<String, Object>();
	}
	
	public String getType() {
		return this.type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public HashMap<String, Object> getProperties() {
		return (HashMap<String, Object>) this.properties;
	}
	
	public void addProperty(String name, Object value) {
		this.properties.put(name, value);
	}
	
}
