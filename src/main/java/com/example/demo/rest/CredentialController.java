package com.example.demo.rest;


import com.example.demo.dtos.CredencialDTO;
import com.example.demo.dtos.CredencialPassDto;
import com.example.demo.services.CredentialService;
import com.example.demo.utils.Constants;
import com.example.demo.utils.CredencialError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping(value = "/credencial")
public class CredentialController
{
    @Autowired
    private CredentialService credentialService;

    @PostMapping("/crear")
    public ResponseEntity<?> crearCredencial(@RequestBody CredencialPassDto credencial) {
        // Lógica para registrar la credencial validando orden: usuario, descripcion, ubicacion, url, password
        // Y validación de mayúscula, minúscula, dígito y carácter especial

        try
        {
            credentialService.crearCredencial(credencial);

            log.info("credencial creada correctamente");

            return  ResponseEntity.ok().build();
        }
        catch (CredencialError credencialError)
        {
            if (credencialError.getCode().equals(Constants.ERROR_INSTANCE_NULL)){
                return ResponseEntity.status(400).body(credencialError.getBodyExceptionMessage());
            }
            if (credencialError.getCode().equals(Constants.ERROR_USER_NULL_OR_EMPTY)){
                return ResponseEntity.status(401).body(credencialError.getBodyExceptionMessage());
            }
            if (credencialError.getCode().equals(Constants.ERROR_DESCRIPTION_NULL_OR_EMPTY)){
                return ResponseEntity.status(402).body(credencialError.getBodyExceptionMessage());
            }
            if (credencialError.getCode().equals(Constants.ERROR_UBICACION_OR_URL_NULL_OR_EMPTY)){
                return ResponseEntity.status(403).body(credencialError.getBodyExceptionMessage());
            }
            if (credencialError.getCode().equals(Constants.ERROR_UBICATION_NOT_EXIST)){
                return ResponseEntity.status(404).body(credencialError.getBodyExceptionMessage());
            }
            if (credencialError.getCode().equals(Constants.ERROR_PASSWORD_NULL_OR_EMPTY)){
                return ResponseEntity.status(405).body(credencialError.getBodyExceptionMessage());
            }
            if (credencialError.getCode().equals(Constants.ERROR_PASSWORD_VERY_SORT)){
                return ResponseEntity.status(406).body(credencialError.getBodyExceptionMessage());
            }
            return ResponseEntity.badRequest().build();

        }
        catch (Exception exception)
        {
            String error = "Error generico";
            log.error(error,exception);
            CredencialError credencialError = new CredencialError(Constants.ERROR_GENERIC,error,exception);
            return ResponseEntity.status(500).body(credencialError.getBodyExceptionMessage());
        }
    }

    @GetMapping("/credenciales")
    public ResponseEntity<?> listarCredenciales() {
        // Lógica para devolver el listado de credenciales o array vacío

        try
        {
            List<CredencialDTO> credencialDTOList = this.credentialService.listarCredenciales();
            return ResponseEntity.ok().body(credencialDTOList);
        }
        catch (CredencialError credencialError)
        {
            if (credencialError.getCode().equals(Constants.ERROR_LIST_OF_CREDENTIAL_NULL)){
                return ResponseEntity.badRequest().body(credencialError.getBodyExceptionMessage());
            }
            return ResponseEntity.badRequest().build();
        }
        catch (Exception exception)
        {
            String error = "Error generico";

            log.error(error,exception);

            CredencialError credencialError = new CredencialError(Constants.ERROR_GENERIC,error,exception);

            return ResponseEntity.status(500).body(credencialError.getBodyExceptionMessage());
        }

    }

    @DeleteMapping("/credenciales")
    public ResponseEntity<?> eliminarCredenciales(@RequestBody CredencialDTO credencialDto) {
        // Lógica para eliminar registros donde va relleno usuario y al menos ubicación o URL
        try
        {
            credentialService.eliminarCredenciales(credencialDto);
            return ResponseEntity.ok().build();
        }
        catch (CredencialError credencialError)
        {
            if (credencialError.getCode().equals(Constants.ERROR_INSTANCE_NULL)){
                return ResponseEntity.status(400).body(credencialError.getBodyExceptionMessage());
            }
            if (credencialError.getCode().equals(Constants.ERROR_USER_NULL_OR_EMPTY)){
                return ResponseEntity.status(401).body(credencialError.getBodyExceptionMessage());
            }
            if (credencialError.getCode().equals(Constants.ERROR_UBICACION_OR_URL_NULL_OR_EMPTY)){
                return ResponseEntity.status(403).body(credencialError.getBodyExceptionMessage());
            }
            if (credencialError.getCode().equals(Constants.ERROR_UBICATION_NOT_EXIST)){
                return ResponseEntity.status(404).body(credencialError.getBodyExceptionMessage());
            }
            return ResponseEntity.badRequest().build();
        }
        catch (Exception exception)
        {
            String error = "Error generico";
            CredencialError credencialError = new CredencialError(Constants.ERROR_GENERIC,error,exception);
            return ResponseEntity.status(500).body(credencialError.getBodyExceptionMessage());
        }
    }

    @PutMapping("/credenciales")
    public ResponseEntity<?> actualizarFuncionamiento(
            @RequestBody CredencialDTO credencialDto,
            @RequestHeader("funciona") Boolean funciona) {
        // Lógica para actualizar el campo 'funciona' según el filtro y la cabecera
        try
        {

        }
        catch (CredencialError credencialError)
        {

        }
        catch (Exception exception)
        {
            String error = "Error generico";
            CredencialError credencialError = new CredencialError(Constants.ERROR_GENERIC,error,exception);
            return ResponseEntity.status(500).body(credencialError.getBodyExceptionMessage());
        }


    }

    @GetMapping("/password")
    public ResponseEntity<?> obtenerPassword(@RequestBody CredencialDTO credencialDto) {
        // Lógica para obtener la contraseña como cadena JSON (String)
        try
        {

        }
        catch (CredencialError credencialError)
        {

        }
        catch (Exception exception)
        {
            String error = "Error generico";
            CredencialError credencialError = new CredencialError(Constants.ERROR_GENERIC,error,exception);
            return ResponseEntity.status(500).body(credencialError.getBodyExceptionMessage());
        }


    }

}
