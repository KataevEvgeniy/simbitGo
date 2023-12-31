package org.simbirgo.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "rents", schema = "public", catalog = "simbir_go")
public class RentEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_rent")
    private long idRent;
    @Basic
    @Column(name = "time_start")
    private Date timeStart;
    @Basic
    @Column(name = "time_end")
    private Date timeEnd;
    @Basic
    @Column(name = "id_transport")
    @JsonProperty("transportId")
    private long idTransport;
    @Basic
    @Column(name = "id_user")
    @JsonProperty("userId")
    private long idUser;
    @Basic
    @Column(name = "price_of_unit")
    private Double priceOfUnit;
    @Basic
    @Column(name = "id_price_type")
    private long idPriceType;
    @Basic
    @Column(name = "final_price")
    private Double finalPrice;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RentEntity that = (RentEntity) o;

        if (idRent != that.idRent) return false;
        if (idTransport != that.idTransport) return false;
        if (idUser != that.idUser) return false;
        if (idPriceType != that.idPriceType) return false;
        if (timeStart != null ? !timeStart.equals(that.timeStart) : that.timeStart != null) return false;
        if (timeEnd != null ? !timeEnd.equals(that.timeEnd) : that.timeEnd != null) return false;
        if (priceOfUnit != null ? !priceOfUnit.equals(that.priceOfUnit) : that.priceOfUnit != null) return false;
        if (finalPrice != null ? !finalPrice.equals(that.finalPrice) : that.finalPrice != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (idRent ^ (idRent >>> 32));
        result = 31 * result + (timeStart != null ? timeStart.hashCode() : 0);
        result = 31 * result + (timeEnd != null ? timeEnd.hashCode() : 0);
        result = 31 * result + (int) (idTransport ^ (idTransport >>> 32));
        result = 31 * result + (int) (idUser ^ (idUser >>> 32));
        result = 31 * result + (priceOfUnit != null ? priceOfUnit.hashCode() : 0);
        result = 31 * result + (int) (idPriceType ^ (idPriceType >>> 32));
        result = 31 * result + (finalPrice != null ? finalPrice.hashCode() : 0);
        return result;
    }
}
