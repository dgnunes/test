package org.fiveware.test.view;

import javax.servlet.annotation.WebListener;
import javax.servlet.annotation.WebServlet;

import org.fiveware.test.enumerations.SexoEnum;
import org.fiveware.test.enumerations.StatusCivilEnum;
import org.fiveware.test.model.entities.Pessoa;
import org.fiveware.test.services.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.util.StringUtils;
import org.springframework.web.context.ContextLoaderListener;

import com.vaadin.annotations.Theme;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.EnableVaadin;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.server.SpringVaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;

@SpringUI
@Theme("valo")
@Scope("prototype")
public class VaadinPessoaCRUD extends UI{
	
	private final PessoaService pessoaService;
	private final VaadinPessoaEditor pessoaEditor;
	
	private final Grid grid;
	private final Button addNewBtn;
	
	@WebServlet(value = "/*", asyncSupported = true)
    public static class Servlet extends SpringVaadinServlet {
    }

    @WebListener
    public static class MyContextLoaderListener extends ContextLoaderListener {
    }

    @Configuration
    @EnableVaadin
    public static class MyConfiguration {
    }
	
	
	@Autowired
	public VaadinPessoaCRUD(PessoaService pessoaService, VaadinPessoaEditor pessoaEditor) {
		this.pessoaService = pessoaService;
		this.pessoaEditor = pessoaEditor;
		this.grid = new Grid();
		this.addNewBtn = new Button("Adicionar Pessoa", FontAwesome.PLUS);
	}
	
	@Override
	protected void init(VaadinRequest request) {
		// build layout
		HorizontalLayout actions = new HorizontalLayout(addNewBtn);
		HorizontalLayout mainLayout = new HorizontalLayout(actions, grid, pessoaEditor);
		setContent(mainLayout);

		// Configure layouts and components
		actions.setSpacing(true);
		mainLayout.setMargin(true);
		mainLayout.setSpacing(true);

		grid.setHeight(300, Unit.PIXELS);
		grid.setColumnOrder("id", "nome", "statuscivil", "deficiente", "sexo");

		// Connect selected Customer to editor or hide if none is selected
		grid.addSelectionListener(e -> {
			if (e.getSelected().isEmpty()) {
				pessoaEditor.setVisible(false);
			}
			else {
				pessoaEditor.editPessoa((Pessoa) e.getSelected().iterator().next());
			}
		});

		// Instantiate and edit new Customer the new button is clicked
		addNewBtn.addClickListener(e -> pessoaEditor.editPessoa(initPessoa()));

		// Listen changes made by the editor, refresh data from backend
		pessoaEditor.setChangeHandler(() -> {
			pessoaEditor.setVisible(false);
			listarPessoas();
		});

		// Initialize listing
		listarPessoas();
	}
	
	// tag::listCustomers[]
	private void listarPessoas() {
		grid.setContainerDataSource(
				new BeanItemContainer(Pessoa.class, pessoaService.findAll()));
	}
	// end::listCustomers[]

	private Pessoa initPessoa(){
		Pessoa result = new Pessoa();
		
		result.setNome("");
		result.setStatuscivil(StatusCivilEnum.SOLTEIRO);
		result.setDeficiente(false);
		result.setSexo(SexoEnum.MASCULINO);
		return result;
	}
	
	
}
