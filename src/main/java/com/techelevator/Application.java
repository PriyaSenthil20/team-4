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
		boolean exitProgram = false;

		while(!exitProgram) {
			menuOption = ConsoleServices.inputMenuOption(activeMenu, currentBalance);
			if (activeMenu.equals("main")) {
				switch (menuOption) {
					case 1:
						System.out.println("Display inventory here.");
						//Call display inventory method from here
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
						currentBalance = feedMoney(currentBalance);
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

	public BigDecimal feedMoney(BigDecimal currentBalance) {
		Scanner userInput = new Scanner(System.in);
		boolean isFeedingMoney = true;


		System.out.println("Hello World!");
		VendingMachine vm=new VendingMachine();
		vm.displayInventory();

		while(isFeedingMoney) {
			System.out.println("Please enter the number of dollars you would like to add as a whole number: ");
			String moneyAddedAsStr = userInput.nextLine();
			if (moneyAddedAsStr.matches("\\d+")) {
				BigDecimal moneyAdded = new BigDecimal(moneyAddedAsStr);
				currentBalance = currentBalance.add(moneyAdded);
			} else {
				System.out.println("Invalid input. Please enter a whole number.");
				continue;
			}
			System.out.println("Thank you! Please press (1) to continue feeding money or (2) to return to the Purchase menu.");
			String continueOption = userInput.nextLine();
			while(isFeedingMoney) {
				if (continueOption.equals("1")) {
					break;
				} else if (continueOption.equals("2")) {
					isFeedingMoney = false;
				} else {
					System.out.println("Invalid input. Please press (1) to continue feeding money or (2) to return to the Purchase menu.");
					continueOption = userInput.nextLine();
				}
			}
		}


		return currentBalance;

	}
}
