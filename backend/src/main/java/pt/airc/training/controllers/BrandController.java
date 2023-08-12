package pt.airc.training.controllers;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pt.airc.training.dtos.BrandDTO;

import pt.airc.training.services.BrandService;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/brand")
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;

    @ApiOperation(value = "Buscar todas as Brands paginadas e ordenadas",
            response = BrandDTO.class, notes = "Brands devem ter todas deletedAt = null")
    @Transactional(readOnly = true)
    @GetMapping("/paginator")
    public Page<BrandDTO> listAllIsDeletedAtNull(@RequestParam(name = "search", defaultValue = "_") String filter,
                                                 @PageableDefault(value = 5, page = 0,
                                                         sort = "name", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(this.brandService.listAllIsDeletedAtNull(pageable, filter)).getBody();
    }

    @ApiOperation(value = "Buscar todas as Brands",
            response = BrandDTO.class, notes = "Brands devem ter todas deletedAt = null")
    @Transactional(readOnly = true)
    @GetMapping("/list")
    public List<BrandDTO> listAllIsDeletedAtNull() {
        return ResponseEntity.ok(this.brandService.listAllIsDeletedAtNull()).getBody();
    }

    @ApiOperation(value = "Apagar Brand",
            notes = "Brand não pode ter bebidas com deletedAt != null e deve existir Brand para o id informado")
    @DeleteMapping("/{id}")
    @Transactional(rollbackFor = Throwable.class)
    public ResponseEntity<Void> doSoftDelete(@PathVariable Long id) {
        this.brandService.doSoftDelete(id);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Criar Brand", response = BrandDTO.class)
    @PostMapping()
    @Transactional(rollbackFor = Throwable.class)
    public ResponseEntity<BrandDTO> create(@RequestBody @Valid BrandDTO brandDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.brandService.create(brandDTO));
    }

}
