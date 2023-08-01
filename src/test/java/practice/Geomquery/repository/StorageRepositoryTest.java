package practice.Geomquery.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import practice.Geomquery.dto.CreateItemRequest;
import practice.Geomquery.dto.StorageNearbyDto;
import practice.Geomquery.entity.Item;
import practice.Geomquery.entity.Storage;
import practice.Geomquery.entity.User;
import practice.Geomquery.service.ItemService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@SpringBootTest
@Transactional
@Commit
class StorageRepositoryTest {
    @PersistenceContext
    EntityManager em;
    @Autowired
    StorageRepository storageRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ItemService itemService;
    @Autowired
    ItemRepository itemRepository;
    JPAQueryFactory queryFactory;
    GeometryFactory geometryFactory;


    @BeforeEach
    @Transactional
    void before() {
        geometryFactory = new GeometryFactory();
        queryFactory = new JPAQueryFactory(em);

        Point 남산타워 = geometryFactory.createPoint(new Coordinate(126.98820, 37.55126));
        Point 경복궁 = geometryFactory.createPoint(new Coordinate(126.97689, 37.57760));
        Point 이순신 = geometryFactory.createPoint(new Coordinate(126.97700, 37.57098));

        Storage testStorage1 = Storage.builder().name("남산타워").location(남산타워).storageItems(new ArrayList<>()).build();
        Storage testStorage2 = Storage.builder().name("경복궁").location(경복궁).storageItems(new ArrayList<>()).build();
        Storage testStorage3 = Storage.builder().name("이순신").location(이순신).storageItems(new ArrayList<>()).build();

        em.persist(testStorage1);
        em.persist(testStorage2);
        em.persist(testStorage3);

        System.out.println("testStorage3.getStorageItems() = " + testStorage3.getStorageItems());

        CreateItemRequest 연필 = CreateItemRequest.builder().name("연필").StorageIdList(new ArrayList<>(Arrays.asList(testStorage1.getId(), testStorage2.getId()))).build();
        CreateItemRequest 휴지 = CreateItemRequest.builder().name("휴지").StorageIdList(new ArrayList<>(Arrays.asList(testStorage2.getId(), testStorage3.getId()))).build();
        CreateItemRequest 물 = CreateItemRequest.builder().name("물").StorageIdList(new ArrayList<>(Arrays.asList(testStorage3.getId()))).build();
        CreateItemRequest 핸드폰케이스 = CreateItemRequest.builder().name("핸드폰케이스").StorageIdList(new ArrayList<>(Arrays.asList(testStorage2.getId()))).build();
        CreateItemRequest 커피 = CreateItemRequest.builder().name("커피").StorageIdList(new ArrayList<>(Arrays.asList(testStorage1.getId(), testStorage3.getId()))).build();
        CreateItemRequest 모니터 = CreateItemRequest.builder().name("모니터").StorageIdList(new ArrayList<>(Arrays.asList(testStorage2.getId(), testStorage3.getId()))).build();

        itemService.createItem(연필);
        itemService.createItem(휴지);
        itemService.createItem(물);
        itemService.createItem(핸드폰케이스);
        itemService.createItem(커피);
        itemService.createItem(모니터);

        em.flush();
        em.clear();
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
    void persistTest() {
        Item 연필 = itemRepository.findByName("연필").orElseThrow(() -> new IllegalArgumentException());
        em.flush();
        em.clear();
        itemService.modifyItem(연필, "변경");
        Item result = itemRepository.findByName("변경").orElseGet(() -> new Item("병신"));
        System.out.println("result = " + result);
    }

    @Test
    void findTest() {
        User user = User.builder().name("김기홍").build();
        userRepository.save(user);
        Long id = user.getId();
        Storage 남산타워 = storageRepository.findByName("남산타워").orElseThrow(IllegalArgumentException::new);
        남산타워.allocateUser(user);
        em.flush(); em.clear();
        List<Storage> result = storageRepository.findByUserId(id);
        System.out.println("result.size() = " + result.size());
    }

    @Test
    void test2() {
        Point 남산타워 = geometryFactory.createPoint(new Coordinate(126.98820, 37.55126));
        List<StorageNearbyDto> storageNames = storageRepository.findStorageNearby(남산타워);
        for (StorageNearbyDto storageName : storageNames) {
            System.out.println("storageName = " + storageName);
        }
    }

    @Test
    void test3_1() {
        Point 남산타워 = geometryFactory.createPoint(new Coordinate(126.98820, 37.55126));
        List<Storage> result = storageRepository.findStorageNearbyWithItems(남산타워, 5000);
        System.out.println("result.size() = " + result.size());
        for (Storage storage : result) {
            System.out.println("storage = " + storage.getName());
            storage.getStorageItems().forEach(storageItem -> {
                System.out.println("storageItem = " + storageItem.getItem());
            });
        }
    }

    @Test
    void test3_2() {
        Point 남산타워 = geometryFactory.createPoint(new Coordinate(126.98820, 37.55126));
        List<Storage> result = storageRepository.findStorageNearbyWithoutItems(남산타워, 5000);
        for (Storage storage : result) {
            storage.getStorageItems().forEach(storageItem -> System.out.println(storage.getName() + " = " + storageItem.getItem()));
        }
    }
}