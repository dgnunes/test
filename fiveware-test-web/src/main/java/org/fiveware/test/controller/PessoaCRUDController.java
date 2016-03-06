package org.fiveware.test.controller;

import org.fiveware.test.model.entities.Pessoa;
import org.fiveware.test.services.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;

/*Controller para projeto sem utilizar AJAX, utilizando pessoaCRUD.jsp
 * e todos os componentes do MVC
 * MAppings estão comentados para não haver conflito com aplicação Vaadin
*/
@Controller
public class PessoaCRUDController {

	private PessoaService pessoaService; 
	
	@Autowired(required=true)
	@Qualifier(value="pessoaService")
	public void setPessoaService(PessoaService pessoaService){
		this.pessoaService = pessoaService;
	}
	
	@RequestMapping(value = {"/mvc","/mvc/pessoas"}, method = RequestMethod.GET)
	public String listaPessoas(Model model){
		model.addAttribute("pessoa", new Pessoa());
		model.addAttribute("listaPessoas",  this.pessoaService.findAll());
		return "pessoaCRUD";
		
	}
	
	@RequestMapping(value = "/mvc/pessoa/add", method = RequestMethod.POST)
	public String adicionarPessoa(@ModelAttribute("pessoa") Pessoa pessoa){
		if(pessoa.getId()==null || pessoa.getId() == 0){
			this.pessoaService.salvarPessoa(pessoa);
		} else {
			this.pessoaService.atualizarPessoa(pessoa);
		}
		
		return "redirect:/mvc/pessoas";
	}
	
	@RequestMapping("/mvc/edit/{id}")
	public String editarPEssoa (@PathVariable("id") int id, Model model){
		model.addAttribute("pessoa",  this.pessoaService.findById(id));
		model.addAttribute("listaPessoas", this.pessoaService.findAll());
		
		return "pessoaCRUD";
	}
	
	@RequestMapping("/mvc/remove/{id}")
	public String removePessoa(@PathVariable("id") int id, Model model){
		this.pessoaService.apagarPessoa(id);
		return "redirect:/mvc/pessoas";
	}
	
}
