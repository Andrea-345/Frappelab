package com.frostmood.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

@Entity
public class Frappe {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 80, message = "Máximo 80 caracteres")
    private String nombre;

    @NotBlank(message = "Elige una base")
    private String base;

    @Size(max = 60, message = "Máximo 60 caracteres")
    private String topping;

    @NotBlank(message = "Elige un tamaño")
    private String tamano;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor que 0")
    @Digits(integer = 6, fraction = 2, message = "Máximo 2 decimales")
    @Column(precision = 8, scale = 2)
    private BigDecimal precio;

    private boolean conCrema;

    @ManyToOne(optional = false)
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getBase() { return base; }
    public void setBase(String base) { this.base = base; }

    public String getTopping() { return topping; }
    public void setTopping(String topping) { this.topping = topping; }

    public String getTamano() { return tamano; }
    public void setTamano(String tamano) { this.tamano = tamano; }

    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }

    public boolean isConCrema() { return conCrema; }
    public void setConCrema(boolean conCrema) { this.conCrema = conCrema; }

    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }
}
