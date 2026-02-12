package com.udd.back.feature_docs.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.udd.back.feature_docs.service.interf.GeocodingService;
import org.elasticsearch.common.geo.GeoPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
public class NominatimGeocodingServiceImpl implements GeocodingService {

    @Autowired RestTemplate restTemplate;
    @Autowired ObjectMapper objectMapper;

    @Value("${geocoding.nominatim.base-url:https://nominatim.openstreetmap.org}")
    private String baseUrl;

    @Value("${geocoding.nominatim.user-agent}")
    private String userAgent;

    @Override
    public Optional<GeoPoint> geocode(String address) {
        if (address == null || address.isBlank()) return Optional.empty();

        String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .path("/search")
                .queryParam("format", "jsonv2")
                .queryParam("limit", 1)
                .queryParam("addressdetails", 0)
                .queryParam("q", address)
                .encode(StandardCharsets.UTF_8)
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.USER_AGENT, userAgent);
        headers.set(HttpHeaders.ACCEPT, "application/json");

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                String.class
        );

        String body = response.getBody();
        if (body == null || body.isBlank()) return Optional.empty();

        try {
            JsonNode root = objectMapper.readTree(body);
            if (!root.isArray() || root.isEmpty()) return Optional.empty();

            JsonNode first = root.get(0);
            JsonNode latNode = first.get("lat");
            JsonNode lonNode = first.get("lon");

            if (latNode == null || lonNode == null) return Optional.empty();

            double lat = Double.parseDouble(latNode.asText());
            double lon = Double.parseDouble(lonNode.asText());

            return Optional.of(new GeoPoint(lat, lon));
        } catch (Exception e) {
            return Optional.empty();
        }

    }

}
