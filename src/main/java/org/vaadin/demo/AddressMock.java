package org.vaadin.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class AddressMock {

	
	public static List<String> countries = Arrays.asList("United States", "Germany", "Austria");
	
	
	public static Map<String, List<String>> states= new HashMap<>();
	
	public static List<Address> addresses =  Collections.synchronizedList(new ArrayList<>());
	
	
	
	private static String[] names = new String[] {"Blue", "Red", "Purple", "Black", "Orange", "White", "Gray"};
	
	private static String[] address = new String[] {"Blue's Home", "Red's Work", "Purple's Place", "Black's Street", "Everybody's Welcome", "Strange Home", "Child Playground"};
	
	static {
		states.put("United States", Arrays.asList("Alabama", "California", "Florida", "Georgia", "Texas"));
		
		states.put("Germany", Arrays.asList("Berlin", "Hamburg", "Bavaria", "Saxony", "Hesse"));
		
		
		states.put("Austria",  Arrays.asList("Viena", "Tyrol", "Salzburg",  "Bugerland","Styria"));
		
		
		Random rand = new Random();
		
		for(int i = 0; i< 30; i++) {
			int nameAddressIndex = rand.nextInt(7);
			int addressIndex = rand.nextInt(7);
			int countryIndex = rand.nextInt(3);
			int stateIndex = rand.nextInt(5);
			
			Address bean = new Address(i, countries.get(countryIndex), states.get(countries.get(countryIndex)).get(stateIndex), names[nameAddressIndex], address[addressIndex]);
			addresses.add(bean);
			
		}
		
 		
	}
	
	
	public static Collection<String> getStatesOf(String country) {
		if(country == null) return Collections.emptyList();
		List<String> list = AddressMock.states.get(country);
		return list!= null ? list : Collections.emptyList();
	}
	
	public static void save(Address address) {
		if(address.isNew()) {
			int nextId = addresses.get(addresses.size() - 1).getId() + 1;
			address.setId(nextId);
			addresses.add(address);
		}else {
			Address existing = addresses.stream().filter(x -> x.getId() == address.getId()).findFirst().orElse(null);
			if(existing != null) {
				addresses.set(addresses.indexOf(existing), address);
			}else {
				addresses.add(address);
			}
		}
	}

	
	public static void  delete(Address address) {
		if(address != null && !address.isNew()) {
			addresses.remove(address);
		}
	}
	
}
