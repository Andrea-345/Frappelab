package com.frostmood.controller;

import com.frostmood.model.Categoria;
import com.frostmood.repository.CategoriaRepository;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaRepository repo;

    public CategoriaController(CategoriaRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("categorias", repo.findAll());
        return "categorias/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("categoria", new Categoria());
        return "categorias/form";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("categoria") Categoria categoria, BindingResult result) {
        if (result.hasErrors()) {
            return "categorias/form";
        }
        repo.save(categoria);
        return "redirect:/categorias";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        return repo.findById(id).map(c -> {
            model.addAttribute("categoria", c);
            return "categorias/form";
        }).orElse("redirect:/categorias");
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("categoria") Categoria categoria,
                         BindingResult result) {
        categoria.setId(id);
        if (result.hasErrors()) {
            return "categorias/form";
        }
        repo.save(categoria);
        return "redirect:/categorias";
    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable Long id, Model model) {
        return repo.findById(id).map(c -> {
            model.addAttribute("categoria", c);
            return "categorias/details";
        }).orElse("redirect:/categorias");
    }

    @GetMapping("/delete/{id}")
    public String confirmDelete(@PathVariable Long id, Model model) {
        return repo.findById(id).map(c -> {
            model.addAttribute("categoria", c);
            return "categorias/delete";
        }).orElse("redirect:/categorias");
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirect) {
        try {
            repo.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            redirect.addFlashAttribute("error",
                    "No se puede eliminar: la categoría tiene frappés asociados. Elimínalos o cámbialos de categoría primero.");
        }
        return "redirect:/categorias";
    }
}
