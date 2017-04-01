package websocket.message;

import java.io.StringReader;
import java.util.Map.Entry;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class WebSocketMessageDecoder implements Decoder.Text<WebSocketMessage> {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public void init(EndpointConfig config) {
		// TODO Auto-generated method stub
	}

	@Override
	public WebSocketMessage decode(String message) throws DecodeException {
		StringReader stringReader = new StringReader(message);
		JsonReader reader = Json.createReader(stringReader);
		JsonObject jsonObject = reader.readObject();
		reader.close();
		
		WebSocketMessage resultMessage = new WebSocketMessage();
		resultMessage.setType(jsonObject.getString("type"));
		
		for (Entry<String, JsonValue> property: jsonObject.entrySet()) {
			if (!property.getKey().equals("type")) {
				resultMessage.addProperty(property.getKey(), property.getValue());
			}
		}
		
		return resultMessage;
	}

	@Override
	public boolean willDecode(String message) {
		return true;
	}

}
