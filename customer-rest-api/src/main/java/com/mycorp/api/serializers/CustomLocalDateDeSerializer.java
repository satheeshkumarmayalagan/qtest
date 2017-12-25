package com.mycorp.api.serializers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class CustomLocalDateDeSerializer extends StdDeserializer<LocalDate> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 814842766501160738L;
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

	public CustomLocalDateDeSerializer() {
		this(null);
	}

	public CustomLocalDateDeSerializer(Class<LocalDate> t) {
		super(t);
	}

	@Override
    public LocalDate deserialize(JsonParser jsonparser, DeserializationContext context)
      throws IOException, JsonProcessingException {
        String date = jsonparser.getText();
        return LocalDate.parse(date, formatter);
    }
}