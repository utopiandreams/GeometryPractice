package practice.Geomquery.repository;


import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import practice.Geomquery.dto.StorageNearbyDto;
import practice.Geomquery.entity.Storage;
import practice.Geomquery.entity.User;

import java.util.List;
import java.util.Optional;

public interface StorageRepository extends JpaRepository<Storage, Long>, StorageCustomRepository {
    List<Storage> findByUser(User user);

    Optional<Storage> findByName(String name);

    List<Storage> findByUserId(Long id);
    @Query(value = "select s from Storage s where ST_DWithin(:center, s.location, :radius, false) = true")
    List<Storage> findNearbyStorage(@Param("center") Point point, @Param("radius") int radius);

    @Query(value = "select new practice.Geomquery.dto.StorageNearbyDto(s.name, round(cast(ST_DistanceSphere(:point, s.location) as double)) as distance) from Storage s order by distance asc")
    List<StorageNearbyDto> findStorageNearby(@Param("point") Point point);

    @Query(value = "select distinct s from Storage s join fetch s.storageItems si join fetch si.item i where ST_DWithin(:center, s.location, :radius, false) = true")
    List<Storage> findStorageNearbyWithItems(@Param("center") Point center, @Param("radius") int radius);

    @Query(value = "select s from Storage s join s.storageItems si join si.item i where ST_DWithin(:center, s.location, :radius, false) = true")
    List<Storage> findStorageNearbyWithoutItems(@Param("center") Point center, @Param("radius") int radius);
}
