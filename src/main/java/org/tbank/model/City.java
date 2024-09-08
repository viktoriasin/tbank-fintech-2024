package org.tbank.model;

import lombok.*;

@Data
public class City {
    private String slug;
    private Coordinate coords;
}

@Data
class Coordinate {
    private double lat;
    private double lon;
}
