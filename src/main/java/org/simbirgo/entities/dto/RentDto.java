package org.simbirgo.entities.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RentDto {

    private Long transportId;
    private Long userId;
    private Date timeStart;
    private Date timeEnd;
    private Double priceOfUnit;
    private String priceType;
    private Double finalPrice;



}
