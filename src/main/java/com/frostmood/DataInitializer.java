package com.frostmood;

import com.frostmood.model.Categoria;
import com.frostmood.model.Frappe;
import com.frostmood.repository.CategoriaRepository;
import com.frostmood.repository.FrappeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


@Component
public class DataInitializer implements CommandLineRunner {


    private final CategoriaRepository categorias;
    private final FrappeRepository frappes;

    public DataInitializer(CategoriaRepository categorias, FrappeRepository frappes) {
        this.categorias = categorias;
        this.frappes = frappes;
    }

    @Override
    public void run(String... args) {
        if (categorias.count() > 0) {
            return;
        }
        Categoria clasicos = nuevaCategoria("Clásicos", "Los sabores de siempre, cremosos y helados.");
        Categoria frutales = nuevaCategoria("Frutales", "Frescos, ácidos y llenos de color.");
        Categoria cafe = nuevaCategoria("Cafeteros", "Para los que necesitan cafeína con estilo.");

        nuevoFrappe("Oreo Cloud", "Chocolate", "Galleta triturada", "Grande", "4.50", true, clasicos);
        nuevoFrappe("Vainilla Glacé", "Vainilla", "Caramelo", "Mediano", "3.75", true, clasicos);
        nuevoFrappe("Mango Frost", "Fruta", "Trozos de mango", "Mediano", "4.00", false, frutales);
        nuevoFrappe("Fresa Dream", "Fruta", "Crema batida", "Grande", "4.25", true, frutales);
        nuevoFrappe("Mocha Moon", "Café", "Chispas de chocolate", "Grande", "4.75", true, cafe);
    }

    private Categoria nuevaCategoria(String nombre, String descripcion) {
        Categoria c = new Categoria();
        c.setNombre(nombre);
        c.setDescripcion(descripcion);
        return categorias.save(c);
    }

    private void nuevoFrappe(String nombre, String base, String topping, String tamano,
                             String precio, boolean conCrema, Categoria categoria) {
        Frappe f = new Frappe();
        f.setNombre(nombre);
        f.setBase(base);
        f.setTopping(topping);
        f.setTamano(tamano);
        f.setPrecio(new BigDecimal(precio));
        f.setConCrema(conCrema);
        f.setCategoria(categoria);
        frappes.save(f);
    }
}
