package practice.Geomquery.dto;

import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
public class StorageNearbyDto {
    private String name;
    private int distance;

    public StorageNearbyDto(String name, double distance) {
        this.name = name;
        this.distance = (int) Math.round(distance);
    }
}
