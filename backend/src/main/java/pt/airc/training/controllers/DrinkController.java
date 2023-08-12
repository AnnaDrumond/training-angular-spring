package pt.airc.training.controllers;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import pt.airc.training.dtos.DrinkDTO;
import pt.airc.training.enums.TypeDrink;
import pt.airc.training.services.DrinkService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("drink")
@RequiredArgsConstructor
public class DrinkController {

    private final DrinkService drinkService;

    @ApiOperation(value = "Buscar todas as Drinks paginadas",
            response = DrinkDTO.class, notes = "Drinks devem ter todas deletedAt = null")
    @Transactional(readOnly = true)
    @GetMapping(value = "/list/active")
    @ResponseStatus(code = HttpStatus.OK)
    public Page<DrinkDTO> listAllIsDeletedAtNull(@RequestParam(name = "search", defaultValue = "_") String filter,
                                                 @PageableDefault(value = 5, page = 0,
                                                         sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(this.drinkService.listAllIsDeletedAtNull(pageable, filter)).getBody();
    }

    @ApiOperation(value = "Buscar todas as Drinks",
            response = DrinkDTO.class, notes = "Drinks devem ter todas deletedAt = null")
    @Transactional(readOnly = true)
    @GetMapping(value = "/all/simple")
    @ResponseStatus(code = HttpStatus.OK)
    public List <DrinkDTO> listAllIsDeletedAtNullSimple(){
        return ResponseEntity.ok(this.drinkService.listAllIsDeletedAtNullSimple()).getBody();

    }

    @ApiOperation(value = "Buscar todos os tipos de Drinks", response = TypeDrink.class)
    @Transactional(readOnly = true)
    @GetMapping(value = "/enums/list")
    @ResponseStatus(code = HttpStatus.OK)
    public List<TypeDrink> listAllEnums() {
        return ResponseEntity.ok().body(this.drinkService.findAllEnums()).getBody();
    }

    @ApiOperation(value = "Criar Drink",
            response = DrinkDTO.class, notes = "Deve existir a brand à qual a bebida será associada")
    @PostMapping()
    @Transactional(rollbackFor = Throwable.class)
    public ResponseEntity<DrinkDTO> create(@RequestBody @Valid DrinkDTO drinkDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.drinkService.create(drinkDTO));
    }

    @ApiOperation(value = "Buscar Drink por ID",
            response = DrinkDTO.class, notes = "Deve existir Drink para o id informado")
    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<DrinkDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok().body(this.drinkService.getById(id));
    }

    @ApiOperation(value = "Buscar Drinks por BrandID",
            response = DrinkDTO.class, notes = "Deve existir Brand para o id informado")
    @GetMapping("/byBrand/{id}")
    @Transactional(readOnly = true)
    public List<DrinkDTO> getDrinksByBrandId(@PathVariable Long id) {
        return ResponseEntity.ok(this.drinkService.getDrinksByBrandId(id)).getBody();
    }

    @ApiOperation(value = "Editar Drink",
            response = DrinkDTO.class, notes = "Deve existir Drink para o id informado")
    @PutMapping("/{id}")
    @Transactional(rollbackFor = Throwable.class)
    public ResponseEntity<DrinkDTO> edit(@RequestBody @Valid DrinkDTO drinkDTO, @PathVariable Long id) {
        return ResponseEntity.ok().body(this.drinkService.edit(drinkDTO, id));
    }

    @ApiOperation(value = "Apagar Drink",
            notes = "Deve existir Drink para o id informado")
    @DeleteMapping("/{id}")
    @Transactional(rollbackFor = Throwable.class)
    public ResponseEntity<Void> doSoftdelete(@PathVariable Long id) {
        this.drinkService.doSoftdelete(id);
        return ResponseEntity.ok().build();
    }
}
