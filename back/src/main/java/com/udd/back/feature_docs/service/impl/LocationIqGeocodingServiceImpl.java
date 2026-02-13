package com.udd.back.feature_docs.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.udd.back.feature_docs.service.interf.GeocodingService;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Service
public class LocationIqGeocodingServiceImpl implements GeocodingService {

    @Autowired private RestTemplate restTemplate;
    @Autowired private ObjectMapper objectMapper;

    @Value("${geocoding.locationiq.base-url}")
    private String baseUrl;

    @Value("${geocoding.locationiq.api-key}")
    private String apiKey;

    @Override
    public Optional<GeoPoint> geocode(String address) {
        if (address == null || address.isBlank()) return Optional.empty();

        URI uri = UriComponentsBuilder.fromHttpUrl(baseUrl + "/search")
                .queryParam("key", apiKey)
                .queryParam("q", address)
                .queryParam("format", "json")
                .queryParam("limit", 1)
                .build()
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, "application/json");

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    uri,
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    String.class
            );

            String body = response.getBody();
            if (body == null || body.isBlank()) return Optional.empty();

            JsonNode root = objectMapper.readTree(body);
            if (!root.isArray() || root.isEmpty()) return Optional.empty();

            JsonNode first = root.get(0);
            JsonNode latNode = first.get("lat");
            JsonNode lonNode = first.get("lon");
            if (latNode == null || lonNode == null) return Optional.empty();

            return Optional.of(new GeoPoint(
                    Double.parseDouble(latNode.asText()),
                    Double.parseDouble(lonNode.asText())
            ));
        } catch (Exception e) {
            return Optional.empty();
        }

    }

}
