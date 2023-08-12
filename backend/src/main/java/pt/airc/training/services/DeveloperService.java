package pt.airc.training.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.airc.training.models.Developer;
import pt.airc.training.repository.DeveloperRepository;

import java.util.List;

@Service
public class DeveloperService {

    private final DeveloperRepository developerRepository;

    @Autowired
    public DeveloperService(DeveloperRepository developerRepository) {
        this.developerRepository = developerRepository;
    }

    public List<Developer> findAll(){
        return this.developerRepository.findAll();
    }

    public Developer create(Developer developer){
        return this.developerRepository.save(developer);
    }
}
