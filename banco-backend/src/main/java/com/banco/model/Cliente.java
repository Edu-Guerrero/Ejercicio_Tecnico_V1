package com.banco.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.*;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyJoinColumn(name = "id") // clave foránea hacia persona.id
public class Cliente extends Persona {

    @Column(nullable = false, length = 255)
    private String contrasena;

    @Column(nullable = false)
    private Boolean estado = true;
}
