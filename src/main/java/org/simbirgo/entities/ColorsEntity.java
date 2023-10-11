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
@Table(name = "colors", schema = "public", catalog = "simbir_go")
public class ColorsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_color")
    private long idColor;
    @Basic
    @Column(name = "color")
    private String color;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ColorsEntity that = (ColorsEntity) o;

        if (idColor != that.idColor) return false;
        if (color != null ? !color.equals(that.color) : that.color != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (idColor ^ (idColor >>> 32));
        result = 31 * result + (color != null ? color.hashCode() : 0);
        return result;
    }
}
