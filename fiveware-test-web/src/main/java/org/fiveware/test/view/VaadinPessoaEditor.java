package org.fiveware.test.view;

import java.util.EnumSet;

import org.fiveware.test.enumerations.StatusCivilEnum;
import org.fiveware.test.config.SpringContextHelper;
import org.fiveware.test.enumerations.SexoEnum;
import org.fiveware.test.model.entities.Pessoa;
import org.fiveware.test.services.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinServlet;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

@SpringComponent
@UIScope
public class VaadinPessoaEditor extends HorizontalLayout{

	private final PessoaService pessoaService;

	private Pessoa pessoa;

	/* Fields */
	TextField nome = new TextField("Nome");
	ComboBox statuscivil = new ComboBox("Status Civil",EnumSet.allOf(StatusCivilEnum.class));
	CheckBox deficiente = new CheckBox("Deficiente?");
	OptionGroup sexo = new OptionGroup("Sexo",EnumSet.allOf(SexoEnum.class));
	
	/* Action buttons */
	Button save = new Button("Salvar", FontAwesome.SAVE);
	Button cancel = new Button("Cancelar");
	Button delete = new Button("Deletar", FontAwesome.TRASH_O);
	CssLayout actions = new CssLayout(save, cancel, delete);

	@Autowired
	public VaadinPessoaEditor(PessoaService pessoaService) {
		SpringContextHelper helper = new SpringContextHelper(VaadinServlet.getCurrent().getServletContext());
		this.pessoaService = (PessoaService) helper.getBean("pessoaService");

		addComponents(nome,statuscivil,deficiente,sexo, actions);

		// Configure and style components
		setSpacing(true);
		actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

		// wire action buttons to save, delete and reset
		save.addClickListener(e -> pessoaService.atualizarPessoa(pessoa));
		delete.addClickListener(e -> pessoaService.apagarPessoa(pessoa.getId()));
		cancel.addClickListener(e -> editPessoa(pessoa));
		setVisible(false);
	}

	public interface ChangeHandler {

		void onChange();
	}

	public final void editPessoa(Pessoa p) {
		final boolean persisted = p.getId() != null;
		if (persisted) {
			// Find fresh entity for editing
			pessoa = pessoaService.findById(p.getId());
		}
		else {
			pessoa = p;
		}
		cancel.setVisible(persisted);

		// Bind customer properties to similarly named fields
		// Could also use annotation or "manual binding" or programmatically
		// moving values from fields to entities before saving
		BeanFieldGroup.bindFieldsUnbuffered(pessoa, this);

		setVisible(true);

		// A hack to ensure the whole form is visible
		save.focus();
		// Select all text in firstName field automatically
		nome.selectAll();
	}

	public void setChangeHandler(ChangeHandler h) {
		// ChangeHandler is notified when either save or delete
		// is clicked
		save.addClickListener(e -> h.onChange());
		delete.addClickListener(e -> h.onChange());
	}
}
