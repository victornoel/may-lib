package fr.irit.smac.may.lib.components.distribution;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectMapper.DefaultTyping;
import org.codehaus.jackson.type.TypeReference;

import fr.irit.smac.may.lib.components.distribution.interfaces.Transform;

public class JSONTransformerImpl<Msg> extends JSONTransformer<Msg> {
	
	private final static ObjectMapper mapper = new ObjectMapper();
	private TypeReference<Msg> clazz;

	public JSONTransformerImpl(TypeReference<Msg> clazz) {
		this.clazz = clazz;
		mapper.enableDefaultTypingAsProperty(DefaultTyping.NON_FINAL, "@class");
	}

	@Override
	protected Transform<Msg, String> make_serializer() {
		return new Transform<Msg, String>() {
			public String transform(Msg message) {
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
	protected Transform<String, Msg> make_deserializer() {
		return new Transform<String, Msg>() {
			public Msg transform(String thing) {
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
