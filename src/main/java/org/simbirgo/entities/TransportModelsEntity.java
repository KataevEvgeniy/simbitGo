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
@Table(name = "transport_models", schema = "public", catalog = "simbir_go")
public class TransportModelsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_transport_model")
    private long idTransportModel;
    @Basic
    @Column(name = "model")
    private String model;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TransportModelsEntity that = (TransportModelsEntity) o;

        if (idTransportModel != that.idTransportModel) return false;
        if (model != null ? !model.equals(that.model) : that.model != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (idTransportModel ^ (idTransportModel >>> 32));
        result = 31 * result + (model != null ? model.hashCode() : 0);
        return result;
    }
}
