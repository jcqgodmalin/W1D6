package shopping.cart;
import java.util.ArrayList;
import java.util.List;
public class ShoppingCart {
	
	List<Item> items = new ArrayList<>();
	
	
	public ShoppingCart() {}
	
	public List<Item> getItems() {
		
		return items;
		
	}
	
	public void addItem(Item item) {
		
		items.add(item);
		
	}
	
	public void removeItem(Item item) {
		
		items.remove(item);
	
	}
	
	public double computeTotalPrice() {
		if(items.size() > 0) {
			//compute
			int sum = 0;
			for(Item i : items) {
				sum += i.getPrice();
			}
			return sum;
		}
		return 0.0;
	}

}
