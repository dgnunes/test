package org.fiveware.test.controller;


import java.util.List;

import org.fiveware.test.jsonview.AjaxResponseBody;
import org.fiveware.test.jsonview.Views;
import org.fiveware.test.model.entities.Pessoa;
import org.fiveware.test.model.entities.bean.PessoaBean;
import org.fiveware.test.services.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;



//@RestController
public class AjaxController {

	private PessoaService pessoaService;
	
	//@Autowired(required=true)
	//@Qualifier(value="pessoaService")
	public void setPessoaService(PessoaService pessoaService){
		this.pessoaService = pessoaService;
	}
	
	// @ResponseBody, not necessary, since class is annotated with @RestController
	// @RequestBody - Convert the json data into object (SearchCriteria) mapped by field name.
	// @JsonView(Views.Public.class) - Optional, filters json data to display.
	@JsonView(Views.Public.class)
	//@RequestMapping(value = {"/pessoas"})
	public AjaxResponseBody getAllPessoas(@RequestBody PessoaBean pessoaBean) {
		
		AjaxResponseBody result = new AjaxResponseBody();
		List<Pessoa> pessoas = pessoaService.findAll(); 

		if (pessoas.size() > 0) {
			result.setCode("200");
			result.setMsg("");
			result.setResult(pessoas);
		} else {
			result.setCode("204");
			result.setMsg("No user!");
		}

		//AjaxResponseBody will be converted into json format and send back to the request.
		return result;
	}
	
	@JsonView(Views.Public.class)
	//@RequestMapping(value = "/add")
	public AjaxResponseBody addPessoa(@RequestBody PessoaBean pessoaBean) {
		
		AjaxResponseBody result = new AjaxResponseBody();
		String errorMsg = ValidPessoa(pessoaBean); 
		
		
		if (errorMsg == "") {
			Pessoa pessoa = pessoaService.findById(pessoaBean.getId()); 

			if (pessoa != null) {
				result.setCode("200");
				result.setMsg("");
				//result.setResult(pessoa);
			} else {
				result.setCode("204");
				result.setMsg("Não foi possível encontrar essa Pessoa.");
			}

		}else if(errorMsg == "BLABLA") {
			result.setCode("204");
			result.setMsg("errorMsg");
		}

		//AjaxResponseBody will be converted into json format and send back to the request.
		return result;
	}

	private String ValidPessoa(PessoaBean pessoaBean) {
		// TODO Auto-generated method stub
		return "";
	}
}