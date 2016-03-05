package org.fiveware.test.servicesbean;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.fiveware.test.model.entities.Pessoa;
import org.fiveware.test.repositories.PessoaRepository;
import org.fiveware.test.services.PessoaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("pessoaService")
@Transactional
public class PessoaServiceBean implements PessoaService {

	@Autowired
	private PessoaRepository repository;
	
	@Override
	public Pessoa findById(int id) {
		return repository.findById(id);
	}

	@Override
	public void salvarPessoa(Pessoa pessoa) {
		repository.save(pessoa);
	}

	@Override
	public void atualizarPessoa(Pessoa pessoa) {
		Pessoa entidade = repository.findById(pessoa.getId());
		if(entidade!=null){
			entidade.setNome(pessoa.getNome());
			entidade.setStatuscivil(pessoa.getStatuscivil());
			entidade.setDeficiente(pessoa.isDeficiente());
			entidade.setSexo(pessoa.getSexo());
		}
	}

	@Override
	public void apagarPessoa(int id) {
		repository.deleteById(id);
	}

	@Override
	public List<Pessoa> findAll() {
		return repository.findAll();
	}

}
