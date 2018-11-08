package org.vaadin.demo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.function.ValueProvider;

public class AddressGrid {

	private Grid<Address> grid = new Grid<>();
	
	
	public AddressGrid(ListDataProvider<Address> dataProvider) {
		
		
		grid.setHeightByRows(true);
		grid.setDataProvider(dataProvider);
		
		grid.addColumn(Address::getId).setHeader("ID");
		grid.addColumn(Address::getCountry).setHeader("Country").setSortable(true);
		grid.addColumn(Address::getState).setHeader("State").setSortable(true);
		grid.addColumn(Address::getName).setHeader("Name").setSortable(true);
		grid.addColumn(Address::getAddress).setHeader("Address").setSortable(true);
		grid.setMultiSort(true);
		
		
		List<ValueProvider<Address, String>> valueProviders = new ArrayList<>();
		valueProviders.add(Address::getCountry);
		valueProviders.add(Address::getState);
		valueProviders.add(Address::getName);
		valueProviders.add(Address::getAddress);
		
	
		HeaderRow filterRow = grid.appendHeaderRow();

		Iterator<ValueProvider<Address, String>> iterator = valueProviders
		        .iterator();

		grid.getColumns().stream().skip(1).forEach(column -> {
		    TextField field = new TextField();
		    ValueProvider<Address, String> valueProvider = iterator.next();

		    field.addValueChangeListener(event -> dataProvider
		            .addFilter(person -> StringUtils.containsIgnoreCase(
		                    valueProvider.apply(person), field.getValue())));

		    field.setValueChangeMode(ValueChangeMode.EAGER);

		    filterRow.getCell(column).setComponent(field);
		    field.setSizeFull();
		    field.setPlaceholder("Filter");
		});
		
		
		
	}
	
	public Grid<Address> getGrid() {
		return grid;
	}
}
