package org.vaadin.demo;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.Route;

/**
 * The main view contains a button and a click listener.
 */
@Route("")
public class MainView extends HorizontalLayout {

	
	private AddressGrid addressGrid;
	
	ListDataProvider<Address> dataProvider = new ListDataProvider<>(
		        AddressMock.addresses);
     
	 
	
    public MainView() {
    	
    	this.setMargin(true);
    	
    	
        AddressEditor addressEditor = new AddressEditor();
        
        addressEditor.withAddressChangeHandler(() -> {
        	dataProvider.refreshAll();
        });
        
        this.add(addressEditor);
        
        addressGrid = new AddressGrid(dataProvider);
  
        this.add(this.addressGrid.getGrid());
        
        this.setFlexGrow(1, this.addressGrid.getGrid());
        
        
        addressGrid.getGrid().addSelectionListener(e -> {
        	Address address = e.getFirstSelectedItem().orElse(null);
        
        	
			addressEditor.setAddress(address);
        });
    }
}
