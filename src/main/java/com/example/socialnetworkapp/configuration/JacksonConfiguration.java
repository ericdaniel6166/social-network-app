package com.example.socialnetworkapp.configuration;

import com.example.socialnetworkapp.utils.Constants;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

@Configuration
@RequiredArgsConstructor
public class JacksonConfiguration {

    private final AppConfiguration appConfiguration;

    @Bean
    public Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder() {
        Jackson2ObjectMapperBuilder mapperBuilder = new Jackson2ObjectMapperBuilder();
        mapperBuilder.serializers(
                        new LocalDateSerializer(DateTimeFormatter.ofPattern(Constants.DATE_FORMAT)),
                        new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(Constants.DATE_TIME_FORMAT)
                                .withZone(ZoneId.of(appConfiguration.getTimeZoneId())))
                )
                .deserializers(
                        new LocalDateDeserializer(DateTimeFormatter.ofPattern(Constants.DATE_FORMAT)),
                        new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(Constants.DATE_TIME_FORMAT)
                                .withZone(ZoneId.of(appConfiguration.getTimeZoneId())))
                )
                .visibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .timeZone(TimeZone.getTimeZone(appConfiguration.getTimeZoneId()))
                .dateFormat(new SimpleDateFormat(Constants.DATE_TIME_FORMAT))
                .serializationInclusion(JsonInclude.Include.NON_NULL);
        return mapperBuilder;
    }


}
