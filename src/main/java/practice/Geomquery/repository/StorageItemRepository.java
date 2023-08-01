package practice.Geomquery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import practice.Geomquery.entity.StorageItem;

@Repository
public interface StorageItemRepository extends JpaRepository<StorageItem, Long> {
}
