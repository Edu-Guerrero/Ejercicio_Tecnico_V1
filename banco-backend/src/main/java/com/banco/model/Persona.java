package com.banco.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "persona")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@NoArgsConstructor
@AllArgsConstructor

public abstract class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false, length = 20)
    private String genero;

    @Column(nullable = false, length = 11, unique = true)
    private String identificacion;

    @Column(nullable = false, length = 100)
    private String direccion;

    @Column(nullable = false, length = 15)
    private String telefono;
}
