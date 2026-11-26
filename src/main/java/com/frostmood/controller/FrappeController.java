package com.frostmood.controller;

import com.frostmood.model.Categoria;
import com.frostmood.model.Frappe;
import com.frostmood.repository.CategoriaRepository;
import com.frostmood.repository.FrappeRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/frappes")
public class FrappeController {


    private final FrappeRepository repo;
    private final CategoriaRepository categoriaRepo;

    public FrappeController(FrappeRepository repo, CategoriaRepository categoriaRepo) {
        this.repo = repo;
        this.categoriaRepo = categoriaRepo;
    }

    // Listas que usa el formulario (se cargan en cada petición)
    @ModelAttribute("categorias")
    public List<Categoria> categorias() {
        return categoriaRepo.findAll();
    }

    @ModelAttribute("bases")
    public List<String> bases() {
        return List.of("Café", "Chocolate", "Fruta", "Té matcha", "Vainilla");
    }

    @ModelAttribute("tamanos")
    public List<String> tamanos() {
        return List.of("Pequeño", "Mediano", "Grande");
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("frappes", repo.findAll());
        return "frappes/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        Frappe frappe = new Frappe();
        frappe.setCategoria(new Categoria());
        model.addAttribute("frappe", frappe);
        return "frappes/form";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("frappe") Frappe frappe, BindingResult result) {
        validarCategoria(frappe, result);
        if (result.hasErrors()) {
            return "frappes/form";
        }
        repo.save(frappe);
        return "redirect:/frappes";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        return repo.findById(id).map(f -> {
            model.addAttribute("frappe", f);
            return "frappes/form";
        }).orElse("redirect:/frappes");
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("frappe") Frappe frappe,
                         BindingResult result) {
        frappe.setId(id);
        validarCategoria(frappe, result);
        if (result.hasErrors()) {
            return "frappes/form";
        }
        repo.save(frappe);
        return "redirect:/frappes";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable Long id, Model model) {
        return repo.findById(id).map(f -> {
            model.addAttribute("frappe", f);
            return "frappes/details";
        }).orElse("redirect:/frappes");
    }

    @GetMapping("/delete/{id}")
    public String confirmDelete(@PathVariable Long id, Model model) {
        return repo.findById(id).map(f -> {
            model.addAttribute("frappe", f);
            return "frappes/delete";
        }).orElse("redirect:/frappes");
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        repo.deleteById(id);
        return "redirect:/frappes";
    }

    private void validarCategoria(Frappe frappe, BindingResult result) {
        if (frappe.getCategoria() == null || frappe.getCategoria().getId() == null) {
            result.rejectValue("categoria.id", "required", "Selecciona una categoría");
        }
    }
}
