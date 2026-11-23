
package com.example.demo.repositories;

import com.example.demo.dtos.CredencialDTO;
import com.example.demo.models.CredentialPassEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CredentialRepository extends JpaRepository<CredentialPassEntity,Long>
{
    boolean existByUbicacion(String ubi);
    boolean existByUrl(String url);
    CredentialPassEntity findByUbicacion(String ubi);
    CredentialPassEntity findByUrl(String url);


    @Query("""
    SELECT new com.example.demo.dtos.CredencialDto(
        c.usuario,
        c.description,
        c.ubicacion,
        c.url
    ) FROM CredencialPassEntity c
""")
    List<CredencialDTO> obtenerCredenciales();


    @Query("""
    SELECT c.password
    FROM CredencialPassEntity c
    where c.usuario = :usuario and
    (c.ubicacion = :ubicacion OR
    c.url = :url)
""")
    String obtenerPassword(String usuario, String ubicacion, String url);
}
