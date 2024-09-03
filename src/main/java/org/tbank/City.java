package org.tbank;

import lombok.*;

//@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class City {
    private String slug;
    private Coordinate coords;
}

//@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
class Coordinate {
    private double lat;
    private double lon;
}
