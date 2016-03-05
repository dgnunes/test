package org.fiveware.test.services;

import java.util.List;

import org.fiveware.test.model.entities.Pessoa;

public interface PessoaService {
	Pessoa findById(int id);
	void salvarPessoa(Pessoa pessoa);
	void atualizarPessoa(Pessoa pessoa);
	void apagarPessoa(int id);
	
	List<Pessoa> findAll();
}
