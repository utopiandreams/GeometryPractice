package practice.Geomquery.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import practice.Geomquery.dto.CreateItemRequest;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Item {
    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    @Column
    private String name;

    @Column
    @OneToMany(mappedBy = "item")
    private List<StorageItem> storageItems = new ArrayList<>();

    public void changeName(String newName) {
        this.name = newName;
    }

    public Item(CreateItemRequest requestDto) {
        this.name = requestDto.getName();
    }

    public Item(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                '}';
    }
}
