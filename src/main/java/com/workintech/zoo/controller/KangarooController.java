package com.workintech.zoo.controller;

import com.workintech.zoo.entity.Kangaroo;
import com.workintech.zoo.exceptions.ZooException;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/kangaroos")
public class KangarooController {
    private Map<Long, Kangaroo> kangaroos;

    @PostConstruct
    public void init() {
        kangaroos = new HashMap<>();
    }

    @GetMapping
    public List<Kangaroo> getAll() {
        return new ArrayList<>(kangaroos.values());
    }

    @GetMapping("/{id}")
    public Kangaroo getById(@PathVariable Long id) {
        Kangaroo k = kangaroos.get(id);
        if (k == null) {
            throw new ZooException("Kangaroo not found: id=" + id, HttpStatus.NOT_FOUND);
        }
        return k;
    }

    @PostMapping
    public Kangaroo add(@RequestBody Kangaroo kangaroo) {
        if (kangaroo == null || kangaroo.getId() <= 0) {
            throw new ZooException("Invalid kangaroo payload", HttpStatus.BAD_REQUEST);
        }
        if (kangaroos.containsKey(kangaroo.getId())) {
            throw new ZooException("Kangaroo already exists: id=" + kangaroo.getId(), HttpStatus.CONFLICT);
        }
        kangaroos.put(kangaroo.getId(), kangaroo);
        return kangaroo;
    }

    @PutMapping("/{id}")
    public Kangaroo update(@PathVariable Long id, @RequestBody Kangaroo updated) {
        if (!kangaroos.containsKey(id)) {
            throw new ZooException("Kangaroo not found for update: id=" + id, HttpStatus.NOT_FOUND);
        }
        if (updated == null) {
            throw new ZooException("Invalid kangaroo payload", HttpStatus.BAD_REQUEST);
        }
        // id sabit tut
        updated.setId(id);
        kangaroos.put(id, updated);
        return updated;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Kangaroo> delete(@PathVariable Long id) {
        Kangaroo removed = kangaroos.remove(id);
        if (removed == null) {
            throw new ZooException("Kangaroo not found for delete: id=" + id, HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(removed);
    }

}
