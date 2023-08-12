package pt.airc.training.controllers;


import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import pt.airc.training.dtos.StorageDTO;
import pt.airc.training.dtos.StorageDrinkDTO;
import pt.airc.training.services.StorageDrinkService;

import javax.validation.Valid;

import org.springframework.data.domain.Pageable;


@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/storageDrink")
@RequiredArgsConstructor
public class StorageDrinkController {

    private final StorageDrinkService storageDrinkService;

    //Paginada
    //Ordenar por nome bebida(drink.name), quantidade(quantity), nome marca (drink.brand.name)
    //http://localhost:8080/api/training/storageDrink/byStorage/1?page=0&sort=drink.brand.name,asc&search=
    //pesquisar/spec nome bebida e nome marca - name = "search", é por causa da url acima
    @ApiOperation(value = "Buscar dados de uma certa Storage", response = StorageDTO.class)
    @Transactional(readOnly = true)
    @GetMapping("/byStorage/{id}")
    public Page<StorageDrinkDTO> getDataByStorageId(@PathVariable Long id,
                                                    @RequestParam(name = "search", defaultValue = "_") String filter,
                                                    @PageableDefault(value = 3, page = 0, sort = "drink.name",
                                                            direction = Sort.Direction.ASC) Pageable pageable) {
        log.info("FILTRO " + filter);
        return ResponseEntity.ok(this.storageDrinkService.getDataByStorageId(pageable, id, filter)).getBody();
    }

    @PostMapping()
    @ApiOperation(value = "Adicionar bebida em storage",
            response = StorageDrinkDTO.class, notes = "Deve existir a Storage e deve existir a bebida")
    @Transactional(rollbackFor = Throwable.class)
    public ResponseEntity<StorageDrinkDTO> addDrinkInStorage(@RequestBody @Valid StorageDrinkDTO storageDrinkDTO) {
        this.storageDrinkService.addDrinkInStorage(storageDrinkDTO);
        return ResponseEntity.ok().build();
    }

}
