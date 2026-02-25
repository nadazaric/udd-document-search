package com.udd.back.feature_docs.service.interf;

import com.udd.back.feature_docs.model.GeocodeResult;

import java.util.Optional;

public interface GeocodingService {

    Optional<GeocodeResult> geocode(String address);

}
