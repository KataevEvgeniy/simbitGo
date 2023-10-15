package org.simbirgo.entities.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RentFindData {

    @JsonProperty("lat")
    private Double latitude;
    @JsonProperty("long")
    private Double longitude;
    private Double radius;
    @JsonProperty("type")
    private String transportType;

}
