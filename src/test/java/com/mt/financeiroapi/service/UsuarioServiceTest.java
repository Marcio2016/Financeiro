package com.mt.financeiroapi.service;

import com.mt.financeiroapi.exception.RegraNegocioException;
import com.mt.financeiroapi.model.Usuario;
import com.mt.financeiroapi.repository.UsuarioRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureDataJpa()
public class UsuarioServiceTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Test(expected = Test.None.class)
    public void deveValidarEmail(){
        /*Cenário*/
        usuarioRepository.deleteAll();
        /*Ação*/
        usuarioService.validarEmail("email@email.com");
    }

    @Test(expected = RegraNegocioException.class)
    public void deveLancarErroAoValidarEmailCadastrado(){
        /*Cenário*/
        Usuario usuario = Usuario.builder().nome("usuario").email("usuario@email.com").build();
        usuarioRepository.save(usuario);

        /*Ação*/
        usuarioService.validarEmail("usuario@email.com");
    }
}
