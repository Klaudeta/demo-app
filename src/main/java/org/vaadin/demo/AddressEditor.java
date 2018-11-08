package org.vaadin.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog.CancelEvent;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog.ConfirmEvent;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.templatemodel.TemplateModel;

/**
 * A Designer generated component for the address-editor.html template.
 *
 * Designer will add and remove fields with @Id mappings but
 * does not overwrite or otherwise change this file.
 */
@Tag("address-editor")
@HtmlImport("src/address-editor.html")
public class AddressEditor extends PolymerTemplate<AddressEditor.AddressEditorModel> implements Editor{

    @Id("newButton")
	private Button newButton;
	@Id("countryCombo")
	private ComboBox<String> countryCombo;
	@Id("stateCombo")
	private ComboBox<String> stateCombo;
	@Id("yesCheckbox")
	private Checkbox yesCheckbox;
	@Id("noCheckbox")
	private Checkbox noCheckbox;
	@Id("nameTextField")
	private TextField nameTextField;
	@Id("addressTextField")
	private TextField addressTextField;
	@Id("saveButton")
	private Button saveButton;
	@Id("deleteButton")
	private Button deleteButton;
	@Id("headerToolbar")
	private HorizontalLayout headerToolbar;
	@Id("footerToolbar")
	private HorizontalLayout footerToolbar;
	/**
     * Creates a new AddressEditor.
     */
	
	
	Binder<Address> binder = new Binder<>();
	
	
	private Optional<Runnable> addressChangeHandler = Optional.empty();
	private ConfirmDialog deleteDialog;
	private Address originalAddress;
	

	
    public AddressEditor() {
    	this.headerToolbar.setJustifyContentMode(JustifyContentMode.END);
    	
    	newButton.addClickListener(e -> newButtonClicked());
    	saveButton.addClickListener(e -> saveButtonClicked());
    	deleteButton.addClickListener(e -> deleteButtonClicked());
    	
    	List<String> items = new ArrayList<>();
    	ListDataProvider<String> dataProvider = 
    	        new ListDataProvider<>(items);
    	
    	stateCombo.setDataProvider(dataProvider);
    	countryCombo.setItems(AddressMock.countries);
    	
    	countryCombo.addValueChangeListener(e -> {
    		items.clear();
    		items.addAll(AddressMock.getStatesOf(e.getValue()));
            dataProvider.refreshAll();
    	});
    	
    	yesCheckbox.addValueChangeListener(e -> {
    		addressTextField.setVisible(!e.getValue());
    		nameTextField.setReadOnly(e.getValue());
    	});
    	
    	noCheckbox.addValueChangeListener(e -> {
    		nameTextField.setVisible(!e.getValue());
    		addressTextField.setReadOnly(e.getValue());
			
    	});
    	
    	bindFields();
    	
    	deleteDialog = new ConfirmDialog("Confirm delete",
		        "Are you sure you want to delete the item?",
		        "Delete", this::onDelete, "Cancel", this::onCancel);
		
		
		deleteDialog.setConfirmButtonTheme("error primary");
		
		setAddress(new Address());
		
    }
    
    public void bindFields() {
    	binder.forField(countryCombo).asRequired("Country is mandatory!").bind(Address::getCountry, Address::setCountry);
    	binder.forField(stateCombo).bind(Address::getState, Address::setState);
    	
    	binder.bind(nameTextField, Address::getName, Address::setName);
    	binder.bind(addressTextField, Address::getAddress, Address::setAddress);
    	
    }
    
    public void newButtonClicked() {
    	this.setAddress(new Address());
	}
	
	public void saveButtonClicked() {
		binder.validate();
		if(binder.isValid()) {
			Address bean = new Address();
			binder.writeBeanIfValid(bean);
			bean.setId(originalAddress.getId());
			AddressMock.save(bean);
			addressChangeHandler.ifPresent(Runnable::run);
			setAddress(new Address());
			
			Notification.show("Address Saved!");
		}
		else {
			new ConfirmDialog("Invalid Address",
			        "The address you want to save is not valid", "OK", e -> { }).open();
			
		}
		
	}
	
	public void deleteButtonClicked() {
		final Address bean = new Address();
		binder.writeBeanIfValid(bean);
		
		if(bean != null && !originalAddress.isNew())
			deleteDialog.open();
		else
			this.setAddress(new Address());
	}
	
	private void onDelete(ConfirmEvent e) {
		AddressMock.delete(originalAddress);
		addressChangeHandler.ifPresent(Runnable::run);
		setAddress(new Address());
		
		Notification.show("Address Deleted!");
	}
	
	private void onCancel(CancelEvent e) {
		
	}
    /**
     * This model binds properties between AddressEditor and address-editor.html
     */
    public interface AddressEditorModel extends TemplateModel {
    }


	public void withAddressChangeHandler(Runnable action) {
		this.addressChangeHandler = Optional.ofNullable(action);
		
	}

	public void setAddress(Address address) {
		originalAddress = address != null ? address : new Address();
		
		binder.readBean(originalAddress);
	}

	public void setStatesOf(String country) {
		stateCombo.setItems(AddressMock.getStatesOf(country));
		
	}
    

}
