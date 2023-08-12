package pt.airc.training.services;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pt.airc.training.dtos.DrinkDTO;
import pt.airc.training.dtos.StorageDTO;
import pt.airc.training.mappers.StorageMapper;
import pt.airc.training.repository.StorageRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StorageService {

    private final StorageRepository storageRepository;
    private final StorageMapper storageMapper;

    public List<StorageDTO> listAllOrderByLocationAndCode() {
        //https://www.bezkoder.com/spring-data-sort-multiple-columns/
        return this.storageRepository.findAll(Sort.by("location")
                        .and(Sort.by("code"))).stream()
                .map(this.storageMapper::toDTO).collect(Collectors.toList());
    }


}
