package practice.Geomquery.repository;


import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import practice.Geomquery.dto.StorageNearbyDto;
import practice.Geomquery.entity.Storage;

import java.util.List;

public interface StorageRepository extends JpaRepository<Storage, Long>, StorageCustomRepository {
    @Query(value = "select s from Storage s where ST_DWithin(:center, s.location, :radius, false) = true")
    List<Storage> findNearbyStorage(@Param("center") Point point, @Param("radius") int radius);

    @Query(value = "select new practice.Geomquery.dto.StorageNearbyDto(s.name, round(cast(ST_DistanceSphere(:point, s.location) as double)) as distance) from Storage s order by distance asc")
    List<StorageNearbyDto> findStorageNames(@Param("point") Point point);
}
