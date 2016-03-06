package org.fiveware.test.jsonview;

import java.util.List;

import org.fiveware.test.model.entities.Pessoa;

import com.fasterxml.jackson.annotation.JsonView;

public class AjaxResponseBody {

	@JsonView(Views.Public.class)
	String msg;
	
	@JsonView(Views.Public.class)
	String code;
	
	@JsonView(Views.Public.class)
	List<Pessoa> result;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<Pessoa> getResult() {
		return result;
	}

	public void setResult(List<Pessoa> result) {
		this.result = result;
	}


}