package com.workintech.zoo.controller;

import com.workintech.zoo.entity.Koala;
import com.workintech.zoo.exceptions.ZooException;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/koalas")
public class KoalaController {
    private Map<Long, Koala> koalas;

    @PostConstruct
    public void init() {
        koalas = new HashMap<>();
    }

    @GetMapping
    public List<Koala> getAll() {
        return new ArrayList<>(koalas.values());
    }

    @GetMapping("/{id}")
    public Koala getById(@PathVariable Long id) {
        Koala k = koalas.get(id);
        if (k == null) {
            throw new ZooException("Koala not found: id=" + id, HttpStatus.NOT_FOUND);
        }
        return k;
    }

    @PostMapping
    public Koala add(@RequestBody Koala koala) {
        if (koala == null || koala.getId() <= 0) {
            throw new ZooException("Invalid koala payload", HttpStatus.BAD_REQUEST);
        }
        if (koalas.containsKey(koala.getId())) {
            throw new ZooException("Koala already exists: id=" + koala.getId(), HttpStatus.CONFLICT);
        }
        koalas.put(koala.getId(), koala);
        return koala;
    }

    @PutMapping("/{id}")
    public Koala update(@PathVariable Long id, @RequestBody Koala updated) {
        if (!koalas.containsKey(id)) {
            throw new ZooException("Koala not found for update: id=" + id, HttpStatus.NOT_FOUND);
        }
        if (updated == null) {
            throw new ZooException("Invalid koala payload", HttpStatus.BAD_REQUEST);
        }
        updated.setId(id);
        koalas.put(id, updated);
        return updated;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        Koala removed = koalas.remove(id);
        if (removed == null) {
            throw new ZooException("Koala not found for delete: id=" + id, HttpStatus.NOT_FOUND);
        }
    }

}
