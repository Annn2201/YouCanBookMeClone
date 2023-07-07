package com.hive.ycbm.services.impl;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Events;
import com.hive.ycbm.dto.EventDto;
import com.hive.ycbm.dto.UserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.time.format.DateTimeFormatter;

@Service
public class GoogleCalendarService {
    private static final String APPLICATION_NAME = "ycbm-clone";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private static final String CALENDAR_ID = "primary";
    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;
    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;
    private final String redirectUri = "http://localhost:8080/info/oauth2/google";

    public String createUri() {
        return "https://accounts.google.com/o/oauth2/v2/auth" +
                "?redirect_uri=" + URLEncoder.encode(redirectUri, StandardCharsets.UTF_8) +
                "&prompt=consent" +
                "&response_type=code" +
                "&client_id=" + clientId +
                "&scope=https://www.googleapis.com/auth/calendar%20https://www.googleapis.com/auth/calendar.events" +
                "&access_type=offline" +
                "&service=lso&o2v=2&flowName=GeneralOAuthFlow";
    }

    public String exchangeCodeForAccessToken(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("code", code);
        requestBody.add("client_id", clientId);
        requestBody.add("client_secret", clientSecret);
        requestBody.add("redirect_uri", redirectUri);
        requestBody.add("grant_type", "authorization_code");
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);
        RestTemplate restTemplate = new RestTemplate();
        String tokenUrl = "https://www.googleapis.com/oauth2/v4/token";
        ResponseEntity<Map<String, String>> responseEntity = restTemplate.exchange(
                tokenUrl,
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<>() {
                }
        );
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            Map<String, String> responseMap = responseEntity.getBody();
            return responseMap.get("access_token");
        } else {
            throw new RuntimeException("Failed to exchange code for access token");
        }
    }

    public void createEvent(UserDto userDto, EventDto eventDto) {
        GoogleCredential credential = new GoogleCredential().setAccessToken(userDto.getAccessToken());
        Calendar calendarService = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
        Event event = new Event()
                .setSummary(eventDto.getBooker().getFirstName() + " and " + userDto.getFirstName())
                .setDescription("From ycbm-clone with love <3")
                .setStart(changeFormat(eventDto.getStart()))
                .setEnd(changeFormat(eventDto.getEnd()));
        EventAttendee[] attendees = new EventAttendee[]{
                new EventAttendee().setEmail(userDto.getMainEmail()),
                new EventAttendee().setEmail(eventDto.getBooker().getEmail()),
        };
        event.setAttendees(Arrays.asList(attendees));
        try {
            calendarService.events().insert(CALENDAR_ID, event).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<EventDto> getEvents(UserDto userDto) {
        GoogleCredential credential = new GoogleCredential().setAccessToken(userDto.getAccessToken());
        Calendar calendarService = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
        Events events;
        try {
            events = calendarService.events().list("primary").execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<Event> googleEvents = events.getItems();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        List<EventDto> eventDtos = new ArrayList<>();
        for (Event googleEvent : googleEvents) {
            EventDto eventDto = new EventDto();
            eventDto.setEventTitle(googleEvent.getSummary());
            if (googleEvent.getStart().getDateTime() != null) {
                String startDateTimeString = googleEvent.getStart().getDateTime().toString();
                LocalDateTime startDateTime = LocalDateTime.parse(startDateTimeString, formatter);
                eventDto.setStart(startDateTime);
            }
            if (googleEvent.getEnd().getDateTime() != null) {
                String endDateTimeString = googleEvent.getEnd().getDateTime().toString();
                LocalDateTime endDateTime = LocalDateTime.parse(endDateTimeString, formatter);
                eventDto.setEnd(endDateTime);
            }
            eventDtos.add(eventDto);
        }
        return eventDtos;
    }

    private EventDateTime changeFormat(LocalDateTime time) {
        DateTime dateTime = DateTime.parseRfc3339(time.toInstant(ZoneOffset.UTC).toString());
        return new EventDateTime()
                .setDateTime(dateTime)
                .setTimeZone("GMT");
    }
}
