package tacRPG.u_interface;

import java.util.ArrayList;

import tacRPG.resource.element.Element;

public class U_Interface {
	private ArrayList<Element> elementList;

	public U_Interface() {
		elementList = new ArrayList<Element>();

		this.init();
	}

	public ArrayList<Element> getElements() {
		return this.elementList;
	}
	
	private void init() {
		
	}
}
