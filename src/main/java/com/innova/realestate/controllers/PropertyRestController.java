package com.innova.realestate.controllers;

import com.innova.realestate.models.Property;
import com.innova.realestate.services.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/properties")
public class PropertyRestController {

    @Autowired
    private PropertyService propertyService;

    @GetMapping
    public List<Property> getAll() {
        return propertyService.getAllProperties();
    }

    @PostMapping
    public Property create(@RequestBody Property property) {
        return propertyService.save(property);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        propertyService.deleteProperty(id);
    }
}