package com.mt.financeiroapi.service.service.impl;

import com.mt.financeiroapi.exception.ErroAutenticacao;
import com.mt.financeiroapi.exception.RegraNegocioException;
import com.mt.financeiroapi.model.Usuario;
import com.mt.financeiroapi.repository.UsuarioRepository;
import com.mt.financeiroapi.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Usuario autenticar(String email, String senha) {

        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);

        if(!usuario.isPresent()){
            throw new ErroAutenticacao("Usuário não encontrado!");
        }

        if(!usuario.get().getSenha().equals(senha)){
            throw new ErroAutenticacao("Senha inválida!");
        }

        return usuario.get();
    }

    @Override
    public Usuario salvar(Usuario usuario) {
        validarEmail(usuario.getEmail());
        return usuarioRepository.save(usuario);
    }

    @Override
    public void validarEmail(String email) {
        boolean usuarioEmail = usuarioRepository.existsByEmail(email);

        if(usuarioEmail) {
            throw new RegraNegocioException("E-mail já utilizado");
        }
    }
}
