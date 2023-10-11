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
@Table(name = "transport_types", schema = "public", catalog = "simbir_go")
public class TransportTypesEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_transport_type")
    private long idTransportType;
    @Basic
    @Column(name = "transport_type")
    private String transportType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TransportTypesEntity that = (TransportTypesEntity) o;

        if (idTransportType != that.idTransportType) return false;
        if (transportType != null ? !transportType.equals(that.transportType) : that.transportType != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (idTransportType ^ (idTransportType >>> 32));
        result = 31 * result + (transportType != null ? transportType.hashCode() : 0);
        return result;
    }
}
