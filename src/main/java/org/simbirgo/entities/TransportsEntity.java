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
public class TransportsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_transport")
    private long idTransport;
    @Basic
    @Column(name = "can_be_rented")
    private boolean canBeRented;
    @Basic
    @Column(name = "id_model")
    private long idModel;
    @Basic
    @Column(name = "id_color")
    private long idColor;
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
    @Column(name = "minutePrice")
    private Double minutePrice;
    @Basic
    @Column(name = "dayPrice")
    private Double dayPrice;
    @Basic
    @Column(name = "id_owner")
    private long idOwner;
    @Basic
    @Column(name = "id_transport_type")
    private long idTransportType;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TransportsEntity that = (TransportsEntity) o;

        if (idTransport != that.idTransport) return false;
        if (canBeRented != that.canBeRented) return false;
        if (idModel != that.idModel) return false;
        if (idColor != that.idColor) return false;
        if (Double.compare(latitude, that.latitude) != 0) return false;
        if (Double.compare(longitude, that.longitude) != 0) return false;
        if (idOwner != that.idOwner) return false;
        if (idTransportType != that.idTransportType) return false;
        if (identifier != null ? !identifier.equals(that.identifier) : that.identifier != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (minutePrice != null ? !minutePrice.equals(that.minutePrice) : that.minutePrice != null) return false;
        if (dayPrice != null ? !dayPrice.equals(that.dayPrice) : that.dayPrice != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) (idTransport ^ (idTransport >>> 32));
        result = 31 * result + (canBeRented ? 1 : 0);
        result = 31 * result + (int) (idModel ^ (idModel >>> 32));
        result = 31 * result + (int) (idColor ^ (idColor >>> 32));
        result = 31 * result + (identifier != null ? identifier.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        temp = Double.doubleToLongBits(latitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (minutePrice != null ? minutePrice.hashCode() : 0);
        result = 31 * result + (dayPrice != null ? dayPrice.hashCode() : 0);
        result = 31 * result + (int) (idOwner ^ (idOwner >>> 32));
        result = 31 * result + (int) (idTransportType ^ (idTransportType >>> 32));
        return result;
    }
}
