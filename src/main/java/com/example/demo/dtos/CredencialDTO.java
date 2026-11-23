package com.example.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CredencialDTO
{
    private String usuario;
    private String descripcion;
    private String ubicacion;
    private String url;

}
