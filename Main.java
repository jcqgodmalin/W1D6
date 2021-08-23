package shopping.cart;

import java.time.LocalTime;

public class Main {
	
	public static void main(String[] args) {
		Customer cust = new Customer("Chrizel");
		Cashier cashier = new Cashier("Jhon",LocalTime.of(8,0,0),LocalTime.of(18,0,0));
		Store store = new Store(cust,cashier);
		store.shop();
	}

}