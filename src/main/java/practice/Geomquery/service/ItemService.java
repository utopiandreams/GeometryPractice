package practice.Geomquery.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import practice.Geomquery.dto.CreateItemRequest;
import practice.Geomquery.entity.Item;
import practice.Geomquery.entity.Storage;
import practice.Geomquery.entity.StorageItem;
import practice.Geomquery.repository.ItemRepository;
import practice.Geomquery.repository.StorageRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    @PersistenceContext
    EntityManager em;
    private final StorageRepository storageRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public void createItem(CreateItemRequest requestDto) {
        List<Long> storageIdList = requestDto.getStorageIdList();
        Item item = new Item(requestDto);
        itemRepository.save(item);
        for (Long storageId : storageIdList) {
            Storage storage = storageRepository.findById(storageId).orElseThrow(IllegalArgumentException::new);
            StorageItem storageItem = new StorageItem(storage, item);
            em.persist(storageItem);
            storage.getStorageItems().add(storageItem);
            item.getStorageItems().add(storageItem);
        }
    }

    @Transactional
    public void modifyItem(Item item, String newName) {
        item.changeName(newName);
    }

}
