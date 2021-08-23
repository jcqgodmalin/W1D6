package shopping.cart;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Store {
	
	//cashier and customer instance
	//added a dummy parameters (just to satisfy the UML requirement) since it will be instantiated in the main class.
	Cashier cashier = new Cashier("",LocalTime.of(0, 0),LocalTime.of(0, 0));
	Customer customer = new Customer("");

	//storeItems
	List<Item> storeItems = new ArrayList<>();

	//constructor	
	public Store(Customer customer, Cashier cashier) {
		this.customer = customer;
		this.cashier = cashier;
	}
	
/*
 * The main flow of the program is defined in this method. It must have the scanner to get inputs from user 
 */
	public void shop() {
		//Constant variables here...
		final String USER_COMMANDS = "Press [1] Add Item [2] Remove Item [3] Checkout [4] Exit";
		final String PRINT_LINES = "-----------------------------------------------------------------------------------------";
		final String INVALID_MESSAGE = "Invalid Input";
		
		Scanner scanner = new Scanner(System.in);
		
		//userInput will be the variable that will hold the user selected command.
		int userInput = 0;
		
		
		//while loop to allow shop operation until user enters 4
		while(userInput != 4) {
			
			//print the header first
			System.out.println(PRINT_LINES + "\n" + USER_COMMANDS + "\n" + PRINT_LINES);
			userInput = scanner.nextInt();
			
			//let's check if the user entered appropriate command.
			switch (userInput) {
			
				case 1: //Add Item
				
					//show the items on the list
					System.out.println("\nStore Items\n" + PRINT_LINES);
					readStoreItemsFromFile("src/shopping/cart/storefiles/store-items.csv");
					System.out.println(PRINT_LINES);
					
					//ask the user what to add to cart.
					System.out.println("[0] - [" + (storeItems.size() - 1) + "] to select items");
					System.out.print("Select item to add: ");
					
					int selectedItem;
					selectedItem = scanner.nextInt();
					
					//check if the user selected the appropriate commands
					if((selectedItem >= 0) && (selectedItem <= (storeItems.size() - 1))) {
						
						//i-addtocart mo na yan!
						customer.addToCart(storeItems.get(selectedItem));
						
						//display customer's cart
						System.out.println(PRINT_LINES + "\nCart Items\n" + PRINT_LINES);
						for(Item item : customer.cart.getItems()) {
							System.out.println("[" + customer.cart.getItems().indexOf(item) + "] " + item.getName() + "\t" + item.getPrice());
						}
						
						
						break;
						
					}else { //not within the specified commands
						
						System.out.println(INVALID_MESSAGE);
						break;
					
					}
					
				case 2: //Remove Item
					
					//if customer's cart is empty, show a message that the cart is empty and there's nothing to remove
					if(customer.cart.getItems().size() == 0) {
						System.out.println("\nCart is empty. No items to remove.");
						break;
					}
					
					//if the cart is not empty, print the customers cart
					System.out.println("\n\n" + PRINT_LINES);
					System.out.println("Cart Items");
					System.out.println(PRINT_LINES);
					for(Item item : customer.cart.getItems()) {
						System.out.println("[" + customer.cart.getItems().indexOf(item) + "] " + item.getName() + "\t" + item.getPrice());
					}
					
					//ask for the item the customer wish to remove
					System.out.print("Select the item to remove: ");
					
					int itemselected;
					itemselected = scanner.nextInt();
					
					//check if the user selected the appropriate commands
					if((itemselected >= 0) && (itemselected <= (customer.cart.getItems().size() -1))) {
						
						//remove the selected item from the cart
						customer.removeFromCart(customer.cart.items.get(itemselected));
						
						//if customer's cart is empty, show a message that the cart is empty and there's nothing to remove
						if(customer.cart.getItems().size() == 0) {
							break;
						}
						
						//reprint the customer's cart
						System.out.println("\n\n" + PRINT_LINES);
						System.out.println("Cart Items");
						System.out.println(PRINT_LINES);
						for(Item item : customer.cart.getItems()) {
							System.out.println("[" + customer.cart.getItems().indexOf(item) + "] " + item.getName() + "\t" + item.getPrice());
						}
						
						break;
						
					}else { //not within the specified commands
						
						System.out.println(INVALID_MESSAGE);
						break;
						
					}
					
				case 3: //Checkout
					
					//if customer's cart is empty, show a message that the cart is empty and there's nothing to checkout.
					if(customer.cart.getItems().size() == 0) {
						System.out.println("\nCart is empty. No items to checkout.");
						break;
					}
					
					//if the cart is not empty, generate and print the receipt
					System.out.print("\n");
					saveReceiptToFile("src/shopping/cart/storefiles/receipt.txt");
					
					//cleaning...
					scanner.close();
					scanner = null;
					customer = null;
					cashier = null;
					
					//goodbye
					System.exit(0);
					
				case 4: //Exit
					
					break;
					
				default://not within the specified commands
					
					System.out.println(INVALID_MESSAGE);
					break;
					
			}
		}
		
		//cleaning
		scanner.close();
		scanner = null;
		customer = null;
		cashier = null;
		
		//goodbye
		System.exit(0);
	}
	
	/*
	 * This method retrieves the store items data from store-items.csv 
	 */
	public void readStoreItemsFromFile(String filename) {
		
		try {
			
			File file = new File(filename);
			Scanner scanner = new Scanner(file);
			
			//this will be used for user command later.
			int counter = 0; 
			
			//clear the storeItems list first to avoid duplicates
			storeItems.clear();
			
			while(scanner.hasNextLine()) {
				
				//assign this into an Item object so that we can add this to the list
				String[] temporaryItemHolder = scanner.nextLine().split(",");
				Item item = new Item(temporaryItemHolder[0],Double.parseDouble(temporaryItemHolder[1]));
				
				//add this item to our global List storeItems
				storeItems.add(item);
				
				//Print the items..
				System.out.println("[" + counter + "] " + item.getName() + "\t" + item.getPrice());
				
				//increment the counter
				counter++;
				
			}
			
			//cleaning...
			scanner.close();
			scanner = null;
			file = null;
			
		}catch(FileNotFoundException e) {
			
			//You should already know why. If you don't, Google: "FileNotFoundException". Thank you.
			System.out.println("Cannot find the file!");
			
		}
	}
	
	
	/*
	 * Prints the receipt with System.out.print() on the screen 
	 */
	public void printReceipt() {
		
		//let's read the receipt.txt file so we don't repeat ourselves.
		try {
			
			File file = new File("src/shopping/cart/storefiles/receipt.txt");
			Scanner scanner = new Scanner(file);
			
			while(scanner.hasNextLine()) {
				System.out.println(scanner.nextLine());
			}
			
			//cleaning...
			scanner.close();
			scanner = null;
			file = null;
			
		}catch(Exception e) {
			//do nothing for now
		}

	}
	
	
	/*
	 * Saves the receipt to receipt.txt file 
	 */
	public void saveReceiptToFile(String filename){
		
		
		String lines = "-----------------------------------------------------------------------------------------";
		String receipt = "";
		
		//print the receipt header first
		receipt += lines + "\n\t\t\t\t\tRECEIPT\n" + lines + "\n";
		receipt += "Cashier: " + cashier.getName() + "\tShift: " + cashier.getStartOfShift() + " - " + cashier.getEndOfShift() + "\n";
		receipt += "Date: " + LocalDate.now() + "\n" + lines + "\n";
		receipt += "Items:\n" + "\tItem name\t\t\tQty\t\tPrice\t\tTotal Price" + "\n";
		
		
		
		//created Map to remove duplicates and add quantities for each product
		Map<String,String> itemListForReceipt = new HashMap<>();
		
		//eliminate duplicates and assigning quantities for each item
		for(Item i : customer.cart.getItems()) {	
			
			if(itemListForReceipt.containsKey(i.getName())) {
				
				//get the value first
				String val = itemListForReceipt.get(i.getName());
				
				//assign qty and price
				int qty = Integer.parseInt(val.split(",")[0]) + 1;
				double price = Double.parseDouble(val.split(",")[1]);
				
				//create a new value
				String newVal = qty + "," + price;
				
				//replace the old value to the new value
				itemListForReceipt.replace(i.getName(), itemListForReceipt.get(i.getName()), newVal);
				
			} else { //if there's no duplicate
				
				//add the item to map
				itemListForReceipt.put(i.getName(), ("1," + i.getPrice()));	
			
			}
			
		}
		
		
		//add the map to the receipt string
		for(String key : itemListForReceipt.keySet()) {
			
			//get the value first and store it in array = {qty, price}
			String[] itemInfo = itemListForReceipt.get(key).split(",");
			
			int itemQty = Integer.parseInt(itemInfo[0]);
			double itemPrice = Double.parseDouble(itemInfo[1]);
			
			//add the item info to the receipt file
			receipt += "\t" + key + "\t\t" + itemQty + "\t\t" + itemPrice + "\t\t" + (itemPrice * itemQty) + "\n";
			
		}
		
		
		//add the total price to the receipt string
		receipt += lines + "\nTOTAL: " + customer.cart.computeTotalPrice() + "\n" + lines;
		
		
		//write the receipt string to the file...
		try {
			
			File file = new File(filename);
			
			//check if file exists
			if(file.exists()) {
				
				//if it does, delete it.
				file.delete();
				
			}
			
			//create a new file
			if(file.createNewFile()) {
				
				FileWriter writer = new FileWriter(filename);
				
				//write the receipt string to the file
				writer.write(receipt);
					
				//cleaning...
				writer.close();
				
			}
			
			//cleaning...
			file = null;
			
		}catch(IOException e) {
			
			//do nothing for now
			
		}
		
		//display the receipt in the screen
		printReceipt();
		
	}
	
	
}
