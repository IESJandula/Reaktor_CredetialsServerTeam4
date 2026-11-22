package com.example.demo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "credenciales")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CredentialPassEntity
{

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String usuario;
    private String descripcion;

    private String ubicacion;
    private String url;

    private String password;

    private Boolean funciona;

}
