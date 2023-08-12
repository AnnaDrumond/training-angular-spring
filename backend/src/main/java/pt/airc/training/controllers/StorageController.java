package pt.airc.training.controllers;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pt.airc.training.dtos.DrinkDTO;
import pt.airc.training.dtos.StorageDTO;
import pt.airc.training.services.StorageService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/storage")
@RequiredArgsConstructor
public class StorageController {

    private final StorageService storageService;

    @ApiOperation(value = "Buscar todas as Storages",response = StorageDTO.class)
    @Transactional(readOnly = true)
    @GetMapping("/list")
    public List<StorageDTO> listAllOrderByLocationAndCode() {
        return ResponseEntity.ok(this.storageService.listAllOrderByLocationAndCode()).getBody();
    }
}
