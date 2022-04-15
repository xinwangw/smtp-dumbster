package local.smtp.model;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.validator.internal.util.Version;

public class CustomBlobSerializer extends JsonSerializer<Blob>{
	private static final Logger log = LoggerFactory.getLogger(CustomBlobSerializer.class);

	@Override
	public void serialize(Blob value, JsonGenerator gen, SerializerProvider serializers)
			throws IOException, JsonProcessingException {
		try {
			gen.writeString(new String(value.getBytes(0, (int) value.length())));
		} catch (SQLException e) {
			log.error("Error when serialize Blob object.",e);
		}
	}


}
