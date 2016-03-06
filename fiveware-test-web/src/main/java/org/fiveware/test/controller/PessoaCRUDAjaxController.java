package org.fiveware.test.controller;

import java.util.List;

import org.fiveware.test.model.entities.Pessoa;
import org.fiveware.test.services.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

/* Controller para respostas em JSON. 
 * 
 * 
 * MAppings estão comentados para não haver conflito.
 */

//@RestController
public class PessoaCRUDAjaxController {

	private PessoaService pessoaService; 
	
	//@Autowired(required=true)
	//@Qualifier(value="pessoaService")
	public void setPessoaService(PessoaService pessoaService){
		this.pessoaService = pessoaService;
	}
	
	//@RequestMapping(value = {"/pessoas/"}, method = RequestMethod.GET)
    public ResponseEntity<List<Pessoa>> listAllPessoas() {
        List<Pessoa> pessoas = this.pessoaService.findAll();
        if(pessoas.isEmpty()){
            return new ResponseEntity<List<Pessoa>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Pessoa>>(pessoas, HttpStatus.OK);
    }
	

//	@RequestMapping(value = "/pessoa/", method = RequestMethod.POST)
    public ResponseEntity<Void> adicionarPessoa(@RequestBody Pessoa pessoa,    UriComponentsBuilder ucBuilder) {
        System.out.println("Creating Pessoa " + pessoa.getNome());
 
        pessoaService.salvarPessoa(pessoa);
 
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/pessoa/{id}").buildAndExpand(pessoa.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }
	
	 //@RequestMapping(value = "/pessoa/{id}", method = RequestMethod.PUT)
	    public ResponseEntity<Pessoa> updatePessoa(@PathVariable("id") int id, @RequestBody Pessoa pessoa) {
	        System.out.println("Updating Pessoa " + id);
	         
	        Pessoa currentPessoa = pessoaService.findById(id);
	         
	        if (currentPessoa==null) {
	            System.out.println("Pessoa with id " + id + " not found");
	            return new ResponseEntity<Pessoa>(HttpStatus.NOT_FOUND);
	        }
	 
	        currentPessoa.setNome(pessoa.getNome());
	
	        pessoaService.atualizarPessoa(currentPessoa);
	        return new ResponseEntity<Pessoa>(currentPessoa, HttpStatus.OK);
	    }
	
    //@RequestMapping(value = "/pessoa/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Pessoa> deletePessoa(@PathVariable("id") int id) {
        System.out.println("Fetching & Deleting Pessoa with id " + id);
 
        Pessoa pessoa = pessoaService.findById(id);
        if (pessoa == null) {
            System.out.println("Unable to delete. User with id " + id + " not found");
            return new ResponseEntity<Pessoa>(HttpStatus.NOT_FOUND);
        }
 
        pessoaService.apagarPessoa(id);
        return new ResponseEntity<Pessoa>(HttpStatus.NO_CONTENT);
	    }	
}
