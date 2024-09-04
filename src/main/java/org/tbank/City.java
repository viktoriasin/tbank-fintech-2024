package org.tbank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@ToString
@Getter
@Setter
public class City {
    private String slug;
    private Coordinate coords;
}

@NoArgsConstructor
@ToString
@Getter
@Setter
class Coordinate {
    private double lat;
    private double lon;
}
