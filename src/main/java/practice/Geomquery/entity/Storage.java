package practice.Geomquery.entity;

import lombok.*;
import org.locationtech.jts.geom.Point;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Storage {
    @Id
    @GeneratedValue
    @Column(name = "stroage_id")
    private Long id;

    @Column
    private String name;

    @Column(columnDefinition = "Geometry(Point, 4326)")
    private Point location;

    @Override
    public String toString() {
        return "Storage{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location=" + location +
                '}';
    }
}
