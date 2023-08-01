package practice.Geomquery.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateItemRequest {
    private String name;
    private List<Long> StorageIdList;
    private String category;
}
