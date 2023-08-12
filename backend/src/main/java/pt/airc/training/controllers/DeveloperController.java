package pt.airc.training.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pt.airc.training.models.Developer;
import pt.airc.training.repository.DeveloperRepository;
import pt.airc.training.services.DeveloperService;

import java.util.List;

@RestController
@RequestMapping("developer")
public class DeveloperController {

    private final DeveloperService developerService;

    @Autowired
    public DeveloperController(DeveloperService developerService) {
        this.developerService = developerService;
    }

    @GetMapping
    public List<Developer> listAll(){
        return this.developerService.findAll();
    }

    @PostMapping
    public Developer save(@RequestBody Developer developer){
        return this.developerService.create(developer);
    }

}
