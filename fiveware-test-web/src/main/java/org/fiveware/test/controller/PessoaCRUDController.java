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

@Controller
public class PessoaCRUDController {

	private PessoaService pessoaService; 
	
	@Autowired(required=true)
	@Qualifier(value="pessoaService")
	public void setPessoaService(PessoaService pessoaService){
		this.pessoaService = pessoaService;
	}
	
	@RequestMapping(value = {"/", "/pessoas"}, method = RequestMethod.GET)
	public String listaPessoas(Model model){
		model.addAttribute("pessoa", new Pessoa());
		model.addAttribute("listaPessoas",  this.pessoaService.findAll());
		return "pessoa";
		
	}
	
	@RequestMapping(value = "/pessoa/add", method = RequestMethod.POST)
	public String adicionarPessoa(@ModelAttribute("pessoa") Pessoa pessoa){
		if(pessoa.getId() == 0){
			this.pessoaService.salvarPessoa(pessoa);
		} else {
			this.pessoaService.atualizarPessoa(pessoa);
		}
		
		return "redirect:/pessoas";
	}
	
	@RequestMapping("/edit/{id}")
	public String editarPEssoa (@PathVariable("id") int id, Model model){
		model.addAttribute("pessoa",  this.pessoaService.findById(id));
		model.addAttribute("listaPessoas", this.pessoaService.findAll());
		
		return "pessoa";
	}
	
	@RequestMapping("/remove{id}")
	public String removePessoa(@PathVariable("id") int id, Model model){
		this.pessoaService.apagarPessoa(id);
		return "redirect:/pessoas";
	}
	
}
