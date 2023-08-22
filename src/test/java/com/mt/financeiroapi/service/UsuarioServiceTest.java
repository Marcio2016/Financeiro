package com.mt.financeiroapi.service;

import com.mt.financeiroapi.exception.ErroAutenticacao;
import com.mt.financeiroapi.exception.RegraNegocioException;
import com.mt.financeiroapi.model.Usuario;
import com.mt.financeiroapi.repository.UsuarioRepository;
import com.mt.financeiroapi.service.service.impl.UsuarioServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;


@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UsuarioServiceTest {

    @MockBean
    private UsuarioRepository usuarioRepository;
    @SpyBean
    private UsuarioServiceImpl usuarioService;


    @Test(expected = Test.None.class)
    public void deveValidarEmail(){
        /*Cenário*/
        Mockito.when(usuarioRepository.existsByEmail(Mockito.anyString())).thenReturn(false);

        /*Ação*/
        usuarioService.validarEmail("email@email.com");
    }

    @Test(expected = RegraNegocioException.class)
    public void deveLancarErroAoValidarEmailCadastrado(){
        /*Cenário*/
        Mockito.when(usuarioRepository.existsByEmail(Mockito.anyString())).thenReturn(true);

        /*Ação*/
        usuarioService.validarEmail("usuario@email.com");
    }
    @Test(expected = Test.None.class)
    public void deveAutenticaUsuario(){
        //Cenário
        String email = "email@email.com";
        String senha = "senha";

        Usuario usuario = Usuario.builder().email(email).senha(senha).id(1l).build();
        Mockito.when(usuarioRepository.findByEmail(email)).thenReturn(Optional.of(usuario));

        //Acão
        Usuario result = usuarioService.autenticar(email,senha);

        //Verificação
        Assertions.assertThat(result).isNotNull();

    }
    @Test(expected = ErroAutenticacao.class)
    public void deveLancaErroUsuarioComMesmoEmail(){

        Mockito.when(usuarioRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());

        //Acão
        Usuario result = usuarioService.autenticar("email","senha");

    }

    @Test
    public void deveLancaErroUsuarioComSenhaErrada(){

        //Cenário
        String email = "email@email.com";
        String senha = "senha";

        Usuario usuario = Usuario.builder().email(email).senha(senha).id(1l).build();
        Mockito.when(usuarioRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(usuario));

        //Acão
        Throwable exception = Assertions.catchThrowable( () ->  usuarioService.autenticar(email,"123"));
        Assertions.assertThat(exception).isInstanceOf(ErroAutenticacao.class).hasMessage("Senha inválida!");

    }
    @Test
    public void deveSalvarUsuario(){
        //Cenario
        Mockito.doNothing().when(usuarioService).validarEmail(Mockito.anyString());
        Usuario usuario = Usuario.builder()
                .nome("Marcio")
                .email("email@email.com")
                .senha("1223").build();
        Mockito.when(usuarioRepository.save(Mockito.any(Usuario.class))).thenReturn(usuario);

        //Ação
        Usuario usuarioSalvo = usuarioService.salvar(new Usuario());

        //Verificação
        Assertions.assertThat(usuarioSalvo).isNotNull();
    }

    @Test(expected = RegraNegocioException.class)
    public void naoDeveSalvarUsuarioComEmailCadastrado(){
        //Cenario
        String email = "email@email.com";
        Usuario usuario = Usuario.builder()
                .email(email)
                .build();
        Mockito.doThrow(RegraNegocioException.class).when(usuarioService).validarEmail(email);

        //Ação
        usuarioService.salvar(usuario);

        //Verificação
        Mockito.verify(usuarioRepository, Mockito.never()).save(usuario);
    }
}
