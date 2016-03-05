package org.fiveware.test.repositories;

import java.util.List;

import org.fiveware.test.model.entities.Pessoa;

public interface PessoaRepository {
	Pessoa findById(int id);
	void save(Pessoa pessoa);
	void deleteById(int id);
	List<Pessoa> findAll();
}
