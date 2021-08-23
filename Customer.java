package shopping.cart;

public class Customer extends Person {
	
	ShoppingCart cart = new ShoppingCart();

	public Customer(String name) {
		super(name);
	}
	
	public String getName() {
		return this.getName();
	}
	
	public void setName(String name) {
		this.setName(name);
	}
	
	public void addToCart(Item item) {
		
		cart.addItem(item);
		
	}
	
	public void removeFromCart(Item item) {
		
		cart.removeItem(item);
		
	}
}
