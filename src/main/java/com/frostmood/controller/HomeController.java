package com.frostmood.controller;

import com.frostmood.repository.CategoriaRepository;
import com.frostmood.repository.FrappeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final CategoriaRepository categorias;
    private final FrappeRepository frappes;

    public HomeController(CategoriaRepository categorias, FrappeRepository frappes) {
        this.categorias = categorias;
        this.frappes = frappes;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("totalCategorias", categorias.count());
        model.addAttribute("totalFrappes", frappes.count());
        return "index";
    }
}
