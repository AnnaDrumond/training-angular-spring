package pt.airc.training.mappers;

import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import pt.airc.training.repository.StorageDrinkRepository;
import pt.airc.training.repository.StorageRepository;

@Component
@RequiredArgsConstructor
public class StorageMapperUtil {

    private final StorageDrinkRepository storageDrinkRepository;
    private final StorageRepository storageRepository;

    @Named("countDrinksByStorageId")
    public Long countDrinksByStorageId(Long id) {
        Long total = this.storageDrinkRepository.getTotalDrinksByStorageId(id);
        if (total != null) {
            return total;
        }
        return 0L;
    }


    @Named("calculateRemainingCapacity")
    public Integer calculateRemainingCapacity(Long id) {
        Long total = this.storageDrinkRepository.getTotalDrinksByStorageId(id);
        Integer capacity = this.storageRepository.findCapacityById(id);
        if (total != null) {
            return Math.toIntExact(capacity - total);
        }
        return capacity;
    }


}
