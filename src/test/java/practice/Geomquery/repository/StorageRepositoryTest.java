package practice.Geomquery.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Commit;
import practice.Geomquery.dto.StorageNearbyDto;
import practice.Geomquery.entity.Storage;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Commit
class StorageRepositoryTest {
    @PersistenceContext
    EntityManager em;
    @Autowired
    StorageRepository storageRepository;
    JPAQueryFactory queryFactory;
    GeometryFactory geometryFactory;

    @BeforeEach
    void before() {
        geometryFactory = new GeometryFactory();
        queryFactory = new JPAQueryFactory(em);

        Point 남산타워 = geometryFactory.createPoint(new Coordinate(126.98820, 37.55126));
        Point 경복궁 = geometryFactory.createPoint(new Coordinate(126.97689, 37.57760));
        Point 이순신 = geometryFactory.createPoint(new Coordinate(126.97700, 37.57098));
        Storage testStorage1 = Storage.builder().name("남산타워").location(남산타워).build();
        Storage testStorage2 = Storage.builder().name("경복궁").location(경복궁).build();
        Storage testStorage3 = Storage.builder().name("이순신").location(이순신).build();

        em.persist(testStorage1);
        em.persist(testStorage2);
        em.persist(testStorage3);
    }

    @Test
    void test1() {
        Point 남산타워 = geometryFactory.createPoint(new Coordinate(126.98820, 37.55126));
        List<Storage> nearbyStorage = storageRepository.findNearbyStorage(남산타워, 5000);
        for (Storage storage : nearbyStorage) {
            System.out.println("storage = " + storage);
        }
    }

    @Test
    void test2() {
        Point 남산타워 = geometryFactory.createPoint(new Coordinate(126.98820, 37.55126));
        List<StorageNearbyDto> storageNames = storageRepository.findStorageNames(남산타워);
        for (StorageNearbyDto storageName : storageNames) {
            System.out.println("storageName = " + storageName);
        }
    }
}