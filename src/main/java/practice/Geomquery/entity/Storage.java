package practice.Geomquery.entity;

import lombok.*;
import org.locationtech.jts.geom.Point;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "storage")
    private List<StorageItem> storageItems = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void allocateUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Storage{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location=" + location +
                '}';
    }
}
