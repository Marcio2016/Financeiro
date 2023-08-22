package com.mt.financeiroapi.repository;

import com.mt.financeiroapi.model.Usuario;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;


@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UsuarioRepositoryTest {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    TestEntityManager testEntityManager;

    @Test
    public void deveVerificarEmail() {
        /*Cenário*/
        Usuario usuario = getUsuario();
        testEntityManager.persist(usuario);

        /*Ação*/
        boolean result = usuarioRepository.existsByEmail("usuario@email.com");

        /*Resultado*/
        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void deveRetornarFalsoParaVerificarEmail() {
        /*Cenário*/

        /*Ação*/
        boolean result = usuarioRepository.existsByEmail("usuario@email.com");

        /*Resultado*/
        Assertions.assertThat(result).isFalse();
    }

    @Test
    public void devePersistirUsuario(){
        Usuario usuario = getUsuario();

        usuarioRepository.save(usuario);

        Assertions.assertThat(usuario.getId()).isNotNull();

    }

    @Test
    public void deveretornarVazioBuscaDeUsuario() {


        Optional<Usuario> result = usuarioRepository.findByEmail("usuario@email.com");

        Assertions.assertThat(result.isPresent()).isFalse();
    }

    @Test
    public void deveBuscarUmUsuario() {
        Usuario usuario = getUsuario();
        testEntityManager.persist(usuario);

        Optional<Usuario> result = usuarioRepository.findByEmail("usuario@email.com");

        Assertions.assertThat(result.isPresent()).isTrue();
    }

    private Usuario getUsuario() {
        Usuario usuario = Usuario.builder()
                .nome("usuario")
                .email("usuario@email.com")
                .senha("123456")
                .build();
        return usuario;
    }
}
