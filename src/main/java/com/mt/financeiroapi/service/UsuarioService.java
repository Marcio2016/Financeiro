package com.mt.financeiroapi.service;


import com.mt.financeiroapi.model.Usuario;

public interface UsuarioService {

    Usuario autenticar(String email, String senha);

    Usuario salvar(Usuario usuario);

    void validarEmail(String email);

}
