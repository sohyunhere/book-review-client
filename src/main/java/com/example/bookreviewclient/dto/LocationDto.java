package com.example.bookreviewclient.dto;

import com.example.bookreviewclient.model.Location;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class LocationDto {
//    Long locationId;
    BigDecimal lat;
    BigDecimal lng;

    public Location toEntity(){
        return Location.builder()
                .lat(lat)
                .lng(lng)
                .build();
    }

}
