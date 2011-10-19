package fr.irit.smac.may.lib.components.distribution;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import fr.irit.smac.may.lib.interfaces.MapGet;

public class JSONTransformerImpl<Msg> extends JSONTransformer<Msg> {

	private final static ObjectMapper mapper = new ObjectMapper();
	private TypeReference<Msg> clazz;

	public JSONTransformerImpl(TypeReference<Msg> clazz) {
		this.clazz = clazz;
		mapper.enableDefaultTyping();
	}

	@Override
	protected MapGet<Msg, String> serializer() {
		return new MapGet<Msg, String>() {
			public String get(Msg message) {
				String m = null;
				try {
					m = mapper.writeValueAsString(message);
				} catch (JsonGenerationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JsonMappingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return m;
			}
		};
	}

	@Override
	protected MapGet<String, Msg> deserializer() {
		return new MapGet<String, Msg>() {
			public Msg get(String thing) {
				Msg message = null;
				try {
					message = mapper.readValue(thing, clazz);
				} catch (JsonParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JsonMappingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return message;
			}
		};
	}

}
