package com.br.service;

import java.util.Set;
import com.br.domain.ContaBancaria;
import com.br.repository.ContaBancariaRepository;


public class ContaBancariaService {

    private ContaBancariaRepository repository;

    public ContaBancariaService(ContaBancariaRepository repository) {
        this.repository = repository;
    }


    public void adicionarUsuario(ContaBancaria usuario) {

        repository.adicionarUsuario(usuario);
    }


    public ContaBancaria buscarUsuarioPelaID(Long id) {

        return repository.buscarUsuarioPelaID(id);
    }


    public Set<ContaBancaria> buscarTodosUsuarios() {

        return repository.buscarTodosUsuarios();
    }


}
