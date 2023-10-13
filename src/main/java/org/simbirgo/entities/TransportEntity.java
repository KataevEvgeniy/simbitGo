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
@Table(name = "transports", schema = "public", catalog = "simbir_go")
public class TransportEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_transport")
    private long idTransport;
    @Basic
    @Column(name = "can_be_rented")
    private boolean canBeRented;
    @Basic
    @Column(name = "id_model")
    private Long idModel;
    @Basic
    @Column(name = "id_color")
    private Long idColor;
    @Basic
    @Column(name = "identifier")
    private String identifier;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "latitude")
    private double latitude;
    @Basic
    @Column(name = "longitude")
    private double longitude;
    @Basic
    @Column(name = "minute_price")
    private Double minutePrice;
    @Basic
    @Column(name = "day_price")
    private Double dayPrice;
    @Basic
    @Column(name = "id_owner")
    private long idOwner;
    @Basic
    @Column(name="id_transport_type")
    private Long idTransportType;


}
