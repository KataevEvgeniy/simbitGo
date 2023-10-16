package org.simbirgo.entities.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RentEndData {

    @JsonProperty("lat")
    private double latitude;
    @JsonProperty("long")
    private double longitude;

}
