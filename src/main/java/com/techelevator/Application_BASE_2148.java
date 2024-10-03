package com.techelevator;

import java.math.BigDecimal;
import java.util.Scanner;

public class Application {

	public static void main(String[] args) {

		Application app = new Application();
		app.run();

	}

	public void run() {
		String activeMenu = "main";
		int menuOption;
		BigDecimal currentBalance = new BigDecimal("0.00");
		VendingMachine vm = new VendingMachine();
		boolean exitProgram = false;

		while(!exitProgram) {
			menuOption = ConsoleServices.inputMenuOption(activeMenu, currentBalance);
			if (activeMenu.equals("main")) {
				switch (menuOption) {
					case 1:
						vm.displayInventory();
						break;
					case 2:
						activeMenu = "purchase";
						break;
					case 3:
						exitProgram = true;
						break;
					case 4:
						System.out.println("Display sales report here.");
						//Call display sales report method from here
						break;
				}

			} else {
				switch (menuOption) {
					case 1:
						currentBalance = vm.feedMoney(currentBalance);
						break;
					case 2:
						System.out.println("Select product.");
						//Call select product method from here
						break;
					case 3:
						System.out.println("Complete transaction.");
						activeMenu = "main";
						//Call complete transaction method from here
						break;
				}
			}
		}
	}
}
