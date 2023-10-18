package org.simbirgo.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "price_type", schema = "public", catalog = "simbir_go")
public class RentTypeEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_price_type")
    private long idPriceType;
    @Basic
    @Column(name = "price_type")
    private String rentType;

}
