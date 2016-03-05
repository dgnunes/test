package org.fiveware.test.repositoriesbean;

import java.util.List;

import org.fiveware.test.model.entities.Pessoa;
import org.fiveware.test.repositories.PessoaRepository;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository("pessoaRepository")
public class PessoaRepositoryBean extends AbstractRepository<Integer, Pessoa> implements PessoaRepository {

	@Override
	public Pessoa findById(int id) {
		Pessoa pessoa = getByKey(id);
		return pessoa;
	}

	@Override
	public void save(Pessoa pessoa) {
		persist(pessoa);
	}

	@Override
	public void deleteById(int id ) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("id", id));
		Pessoa pessoa = (Pessoa) crit.uniqueResult();
		delete(pessoa);
	}

	
	@Override
	@SuppressWarnings("unchecked")
	public List<Pessoa> findAll() {
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("nome"));
		List<Pessoa> pessoas = (List<Pessoa>) criteria.list();
		
		return pessoas;
	}

}
