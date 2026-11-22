package com.example.demo.services;

import com.example.demo.dtos.CredencialDTO;
import com.example.demo.dtos.CredencialPassDto;
import com.example.demo.models.CredentialPassEntity;
import com.example.demo.repositories.CredentialRepository;
import com.example.demo.utils.Constants;
import com.example.demo.utils.CredencialError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CredentialService
{

    @Autowired
    private CredentialRepository credentialRepository;
    @Autowired
    private PasswordEncoder passwordEncoder; // inyecta la interfaz, Spring te da tu Bean



    public void crearCredencial(CredencialPassDto credencialPassDto) throws CredencialError {
        if (credencialPassDto == null)
        {
            String error = "El objeto es null";
            log.error(error);
            throw new CredencialError(Constants.ERROR_INSTANCE_NULL,error);
        }
        if (credencialPassDto.getUsuario()==null || credencialPassDto.getUsuario().isEmpty())
        {
            String error = "El usuario esta vacio o es null";
            log.error(error);
            throw new CredencialError(Constants.ERROR_USER_NULL_OR_EMPTY,error);
        }
        if (credencialPassDto.getDescripcion()==null || credencialPassDto.getDescripcion().isEmpty())
        {
            String error = "La descripcion esta vacia o no es valida";

            throw new CredencialError(Constants.ERROR_DESCRIPTION_NULL_OR_EMPTY,error);
        }
        if (credencialPassDto.getUbicacion()==null && credencialPassDto.getUrl()==null
                || credencialPassDto.getUrl().isEmpty() && credencialPassDto.getUbicacion().isEmpty())
        {
            String error = "La ubicacion y la url estan vacios o no son validas";
            log.error(error);
            throw new CredencialError(Constants.ERROR_UBICACION_OR_URL_NULL_OR_EMPTY,error);
        }
        if (!credentialRepository.existByUbicacion(credencialPassDto.getUbicacion()) )
        {
            String error = "La ubicacion no existe";
            log.error(error);
            throw new CredencialError(Constants.ERROR_UBICATION_NOT_EXIST,error);
        }
        if (credencialPassDto.getPassword()==null || credencialPassDto.getPassword().isEmpty())
        {
            String error = "La contraseña es null o esta vacia";
            log.error(error);
            throw new CredencialError(Constants.ERROR_PASSWORD_NULL_OR_EMPTY,error);
        }
        if (credencialPassDto.getPassword().length() < 8)
        {
            String error = "La contraseña debe ser mas larga de 8 caracteres";
            log.error(error);
            throw new CredencialError(Constants.ERROR_PASSWORD_VERY_SORT,error);
        }

        CredentialPassEntity credentialPassEntity = new CredentialPassEntity();
        credentialPassEntity.setUbicacion(credencialPassDto.getUbicacion());
        credentialPassEntity.setUrl(credencialPassDto.getUrl());
        credentialPassEntity.setDescripcion(credencialPassDto.getDescripcion());
        credentialPassEntity.setPassword(passwordEncoder.encode(credencialPassDto.getPassword()));
        credentialPassEntity.setUsuario(credencialPassDto.getUsuario());

        credentialRepository.saveAndFlush(credentialPassEntity);

        String info = "Creada con exito";
        log.info(info);

    }

    public List<CredencialDTO> listarCredenciales()throws CredencialError{
        List<CredencialDTO> credencialDTOList = this.credentialRepository.obtenerCredenciales();
        if (credencialDTOList == null)
        {
            String errorString = "lista nula o vacia";
            log.error(errorString);

            throw new CredencialError(Constants.ERROR_LIST_OF_CREDENTIAL_NULL, errorString);
        }
        return credencialDTOList;
    }


    public void eliminarCredenciales(CredencialDTO credencialDTO) throws CredencialError {

        if (credencialDTO.getUsuario()==null || credencialDTO.getUsuario().isEmpty())
        {
            String error = "El usuario esta vacio o es null";
            log.error(error);
            throw new CredencialError(Constants.ERROR_USER_NULL_OR_EMPTY,error);
        }
        if (credencialDTO.getUbicacion()==null && credencialDTO.getUrl()==null
                || credencialDTO.getUrl().isEmpty() && credencialDTO.getUbicacion().isEmpty())
        {
            String error = "La ubicacion y la url estan vacios o no son validas";
            log.error(error);
            throw new CredencialError(Constants.ERROR_UBICACION_OR_URL_NULL_OR_EMPTY,error);
        }
        if (!credentialRepository.existByUbicacion(credencialDTO.getUbicacion()))
        {
            String error = "La ubicacion no existe";
            log.error(error);
            throw new CredencialError(Constants.ERROR_UBICATION_NOT_EXIST,error);
        }

        CredentialPassEntity credentialPassEntity = new CredentialPassEntity();
        if (credencialDTO.getUbicacion() == null)
        {
            credentialPassEntity = credentialRepository.findByUrl(credencialDTO.getUrl());

        }
        else
        {
            credentialPassEntity = credentialRepository.findByUbicacion(credencialDTO.getUbicacion());
        }

        if (credentialPassEntity != null)
        {
            credentialRepository.deleteById(credentialPassEntity.getId());
        }

    }


    public void actualizarFuncionamiento(CredencialDTO credencialDTO,boolean funciona) throws CredencialError
    {
        if (credencialDTO.getUsuario()==null || credencialDTO.getUsuario().isEmpty())
        {
            String error = "El usuario esta vacio o es null";
            log.error(error);
            throw new CredencialError(Constants.ERROR_USER_NULL_OR_EMPTY,error);
        }
        if (credencialDTO.getUbicacion()==null && credencialDTO.getUrl()==null
                || credencialDTO.getUrl().isEmpty() && credencialDTO.getUbicacion().isEmpty())
        {
            String error = "La ubicacion y la url estan vacios o no son validas";
            log.error(error);
            throw new CredencialError(Constants.ERROR_UBICACION_OR_URL_NULL_OR_EMPTY,error);
        }
        if (!credentialRepository.existByUbicacion(credencialDTO.getUbicacion()))
        {
            String error = "La ubicacion no existe";
            log.error(error);
            throw new CredencialError(Constants.ERROR_UBICATION_NOT_EXIST,error);
        }
        CredentialPassEntity credentialPassEntity;
        if (credencialDTO.getUbicacion() == null)
        {
            credentialPassEntity = credentialRepository.findByUrl(credencialDTO.getUrl());

        }
        else
        {
            credentialPassEntity = credentialRepository.findByUbicacion(credencialDTO.getUbicacion());
        }
        if (credentialPassEntity != null)
        {
            credentialPassEntity.setUsuario(credencialDTO.getUsuario());
            credentialPassEntity.setDescripcion(credencialDTO.getDescripcion());
            credentialPassEntity.setUbicacion(credencialDTO.getUbicacion());
            credentialPassEntity.setUrl(credencialDTO.getUrl());
            credentialPassEntity.setFunciona(funciona);
        }
    }


}
