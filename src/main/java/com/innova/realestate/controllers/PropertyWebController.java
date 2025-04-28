package com.innova.realestate.controllers;

import com.innova.realestate.models.Property;
import com.innova.realestate.services.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/properties")
public class PropertyWebController {

    @Autowired
    private PropertyService propertyService;

    @GetMapping
    public String viewProperties(Model model) {
        model.addAttribute("properties", propertyService.getAllProperties());
        return "properties"; // Renders properties.html
    }

    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("property", new Property());
        return "add-property"; // Renders add-property.html
    }

    @PostMapping
    public String saveProperty(@ModelAttribute Property property) {
        propertyService.save(property);
        return "redirect:/properties"; // Redirects to properties list
    }
}