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
public class PriceTypeEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_price_type")
    private long idPriceType;
    @Basic
    @Column(name = "price_type")
    private String priceType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PriceTypeEntity that = (PriceTypeEntity) o;

        if (idPriceType != that.idPriceType) return false;
        if (priceType != null ? !priceType.equals(that.priceType) : that.priceType != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (idPriceType ^ (idPriceType >>> 32));
        result = 31 * result + (priceType != null ? priceType.hashCode() : 0);
        return result;
    }
}
