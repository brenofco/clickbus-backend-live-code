package com.clickbus.challenge.controller;

import com.clickbus.challenge.dto.PlaceDTO;
import com.clickbus.challenge.entity.Place;
import com.clickbus.challenge.service.PlaceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/places")
public class PlaceController {
    private PlaceService service;

    @PostMapping
    public ResponseEntity create(@RequestBody @Valid PlaceDTO dto) {
        return new ResponseEntity(service.save(dto.buildPlace()).convertToDTO(), HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity findById(@PathVariable Long id) {
        return service.findById(id)
                .map(place -> ResponseEntity.ok(place.convertToDTO()))
                .orElseThrow(() -> new PlaceNotFoundException(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity findAll() {
        Iterable<PlaceDTO> places = PlaceDTO.convertToList(service.findAll());
        return ResponseEntity.ok(places);
    }

    @PutMapping("/{id}")
    public ResponseEntity alter(@PathVariable Long id, @RequestBody @Valid PlaceDTO placeDTO) {
        Place place = service.findById(id).orElseThrow(null);
        return new ResponseEntity(service.alter(place, placeDTO).convertToDTO(), HttpStatus.OK);
    }
}
