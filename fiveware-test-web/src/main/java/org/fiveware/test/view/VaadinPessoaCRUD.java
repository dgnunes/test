package org.fiveware.test.view;

import java.util.List;

import javax.servlet.annotation.WebListener;
import javax.servlet.annotation.WebServlet;

import org.fiveware.test.config.SpringContextHelper;
import org.fiveware.test.enumerations.SexoEnum;
import org.fiveware.test.enumerations.StatusCivilEnum;
import org.fiveware.test.model.entities.Pessoa;
import org.fiveware.test.services.PessoaService;

import org.springframework.context.annotation.Configuration;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.spring.annotation.EnableVaadin;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.server.SpringVaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

@SpringUI
@Theme("reindeer")
public class VaadinPessoaCRUD extends UI{
	

	private PessoaService pessoaService;
	private VaadinPessoaEditor pessoaEditor;
	
	private Grid grid;
	private Button addNewBtn;
	
	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = VaadinPessoaCRUD.class)
    public static class Servlet extends SpringVaadinServlet {
    }

  //  @WebListener
   // public static class MyContextLoaderListener extends ContextLoaderListener {
    //}

    @Configuration
    @EnableVaadin
    public static class MyConfiguration {
    }
    
	public VaadinPessoaCRUD() {
		SpringContextHelper helper = new SpringContextHelper(VaadinServlet.getCurrent().getServletContext());
		this.pessoaService = (PessoaService) helper.getBean("pessoaService");
		this.pessoaEditor = new VaadinPessoaEditor(pessoaService);
		this.grid = new Grid();
		this.addNewBtn = new Button("Adicionar Pessoa", FontAwesome.PLUS);
    }

	
//	public VaadinPessoaCRUD(PessoaService pessoaService, VaadinPessoaEditor pessoaEditor) {
//		SpringContextHelper helper = new SpringContextHelper(VaadinServlet.getCurrent().getServletContext());
//		this.pessoaService = pessoaService = (PessoaService) helper.getBean("pessoaService");
//		this.pessoaEditor = pessoaEditor;
//		this.grid = new Grid();
//		this.addNewBtn = new Button("Adicionar Pessoa", FontAwesome.PLUS);
//	}
	
	@Override
	protected void init(VaadinRequest request) {
		 
		 //build layout
		HorizontalLayout actions = new HorizontalLayout(addNewBtn);
		VerticalLayout mainLayout = new VerticalLayout(grid, actions, pessoaEditor);
		setContent(mainLayout);

		// Configure layouts and components
		actions.setSpacing(true);
		mainLayout.setMargin(true);
		mainLayout.setSpacing(true);

		grid.setHeight(300, Unit.PIXELS);
		
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
	
	// tag::listarPessoa[]
	private void listarPessoas() {

		grid.setContainerDataSource(
				new BeanItemContainer<Pessoa>(Pessoa.class, pessoaService.findAll()));
		
		//		List<Pessoa> lista = pessoaService.findAll();
//
//		for(Pessoa p : lista){
//			grid.addRow(p.getId(),p.getNome(),p.getStatuscivil(),p.isDeficiente(),p.getSexo());
//		}
		
	}
	// end::listarPessoa[]

	private Pessoa initPessoa(){
		Pessoa result = new Pessoa();
		
		result.setNome("");
		result.setStatuscivil(StatusCivilEnum.SOLTEIRO);
		result.setDeficiente(false);
		result.setSexo(SexoEnum.MASCULINO);
		return result;
	}
	
	
}
