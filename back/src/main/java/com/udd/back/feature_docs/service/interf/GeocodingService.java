package com.udd.back.feature_docs.service.interf;

import org.elasticsearch.common.geo.GeoPoint;

import java.util.Optional;

public interface GeocodingService {

    public Optional<GeoPoint> geocode(String address);

}
