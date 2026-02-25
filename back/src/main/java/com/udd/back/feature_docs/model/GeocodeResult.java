package com.udd.back.feature_docs.model;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

public class GeocodeResult {
    private GeoPoint geoPoint;
    private String city;

    public GeocodeResult() {

    }

    public GeocodeResult(GeoPoint geoPoint, String city) {
        this.geoPoint = geoPoint;
        this.city = city;
    }

    public GeoPoint getGeoPoint() {
        return geoPoint;
    }

    public void setGeoPoint(GeoPoint geoPoint) {
        this.geoPoint = geoPoint;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

}
