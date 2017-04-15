package providers;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import com.google.protobuf.Message;
import com.google.protobuf.util.JsonFormat;
import com.google.protobuf.GeneratedMessageV3.Builder;


@Provider
@Consumes({"application/json", "application/x-protobuf"})
@Produces({"application/json", "application/x-protobuf"})
public class ProtoBufProvider implements MessageBodyReader<Message>, MessageBodyWriter<Message> {
	@Override
	public boolean isReadable(Class cls, Type type, Annotation[] annots, MediaType mediaType) {
		return true;
	}

	@Override
	public Message readFrom(Class cls, Type type, Annotation[] annots, MediaType mediaType, MultivaluedMap map,
			InputStream stream) throws IOException, WebApplicationException {
		try {
			if (MediaType.APPLICATION_JSON_TYPE.isCompatible(mediaType)) {
	            InputStreamReader streamReader = new InputStreamReader(stream);
                Method newBuilderMethod = cls.getMethod("newBuilder");
                Builder builder = (Builder) newBuilderMethod.invoke(null);
	            JsonFormat.parser().ignoringUnknownFields().merge(streamReader, builder);
	            return builder.build();
			} else {
                Method parseFromMethod = cls.getMethod("parseFrom", InputStream.class);
                return (Message) parseFromMethod.invoke(null, stream);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public long getSize(Message message, Class cls, Type type, Annotation[] annots, MediaType mediaType) {
		return -1;
	}

	@Override
	public boolean isWriteable(Class cls, Type type, Annotation[] annots, MediaType mediaType) {
		return true;
	}

	@Override
	public void writeTo(Message message, Class cls, Type type, Annotation[] annots, MediaType mediaType,
			MultivaluedMap map, OutputStream stream) throws IOException, WebApplicationException {
		if (MediaType.APPLICATION_JSON_TYPE.isCompatible(mediaType)) {
            String json = JsonFormat.printer().includingDefaultValueFields().print(message);
            OutputStreamWriter streamWriter = new OutputStreamWriter(stream);
            streamWriter.write(json);
            streamWriter.flush();
		} else {
			message.writeTo(stream);
		}
	}
}
