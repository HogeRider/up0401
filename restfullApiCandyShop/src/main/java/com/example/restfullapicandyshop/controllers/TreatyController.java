package com.example.restfullapicandyshop.controllers;

import com.example.restfullapicandyshop.models.Treaty;
import com.example.restfullapicandyshop.repositories.TreatyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/treaties")
public class TreatyController {

    @Autowired
    private TreatyRepository treatyRepository;

    @GetMapping
    public List<Treaty> getAllTreaties() {
        return treatyRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Treaty> getTreatyById(@PathVariable(value = "id") Integer id) {
        Optional<Treaty> treatyOptional = treatyRepository.findById(id);
        return treatyOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public Treaty createTreaty(@Valid @RequestBody Treaty treaty) {
        return treatyRepository.save(treaty);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Treaty> updateTreaty(@PathVariable(value = "id") Integer id,
                                               @Valid @RequestBody Treaty updatedTreaty) {
        Optional<Treaty> existingTreatyOptional = treatyRepository.findById(id);
        if (existingTreatyOptional.isPresent()) {
            Treaty existingTreaty = existingTreatyOptional.get();
            existingTreaty.setTreatyNumber(updatedTreaty.getTreatyNumber());
            existingTreaty.setDateOfConclusion(updatedTreaty.getDateOfConclusion());
            existingTreaty.setAmount(updatedTreaty.getAmount());
            existingTreaty.setTreatyContent(updatedTreaty.getTreatyContent());
            existingTreaty.setUser(updatedTreaty.getUser());
            Treaty savedTreaty = treatyRepository.save(existingTreaty);
            return ResponseEntity.ok(savedTreaty);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTreaty(@PathVariable(value = "id") Integer id) {
        Optional<Treaty> treatyOptional = treatyRepository.findById(id);
        if (treatyOptional.isPresent()) {
            treatyRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
