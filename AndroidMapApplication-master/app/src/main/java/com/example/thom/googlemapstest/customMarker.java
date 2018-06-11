package com.example.thom.googlemapstest;

/**
 * Created by Thom on 4/19/2015.
 */
public class customMarker {


    private Double lat;
    private Double lng;

    public customMarker( Double lat, Double lng) {

        this.lat = lat;
        this.lng = lng;
    }
    public Double getLat() {
        return lat;
    }
    public Double getLng() {
        return lng;
    }

}
