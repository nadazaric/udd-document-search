package com.udd.back.feature_docs.service.interf;

import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import java.util.Optional;

public interface GeocodingService {

    Optional<GeoPoint> geocode(String address);

}
