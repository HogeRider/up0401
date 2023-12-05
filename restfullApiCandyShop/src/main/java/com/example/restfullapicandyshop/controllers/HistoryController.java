package com.example.restfullapicandyshop.controllers;

import com.example.restfullapicandyshop.models.History;
import com.example.restfullapicandyshop.repositories.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/history")
public class HistoryController {

    @Autowired
    private HistoryRepository historyRepository;

    @GetMapping
    public List<History> getAllHistory() {
        return historyRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<History> getHistoryById(@PathVariable(value = "id") Integer id) {
        Optional<History> historyOptional = historyRepository.findById(id);
        return historyOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public History createHistory(@Valid @RequestBody History history) {
        return historyRepository.save(history);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<History> updateHistory(@PathVariable(value = "id") Integer id,
                                                 @Valid @RequestBody History updatedHistory) {
        Optional<History> existingHistoryOptional = historyRepository.findById(id);
        if (existingHistoryOptional.isPresent()) {
            History existingHistory = existingHistoryOptional.get();
            existingHistory.setTreaty(updatedHistory.getTreaty());
            existingHistory.setProduct(updatedHistory.getProduct());
            existingHistory.setProductCountHistory(updatedHistory.getProductCountHistory());
            History savedHistory = historyRepository.save(existingHistory);
            return ResponseEntity.ok(savedHistory);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteHistory(@PathVariable(value = "id") Integer id) {
        Optional<History> historyOptional = historyRepository.findById(id);
        if (historyOptional.isPresent()) {
            historyRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
