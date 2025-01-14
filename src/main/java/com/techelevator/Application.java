package com.techelevator;

public class Application {

	public static void main(String[] args) {

		Application app = new Application();
		app.run();

	}

	public void run() {
		VendingMachine vm = new VendingMachine();
		int menuOption;
		boolean exitProgram = false;

		while(!exitProgram) {
			menuOption = ConsoleServices.inputMenuOption(vm);
			if (vm.getActiveMenu().equals("main")) {
				switch (menuOption) {
					case 1:
						vm.displayInventory();
						break;
					case 2:
						vm.setActiveMenu("purchase");
						break;
					case 3:
						exitProgram = true;
						break;
					case 4:
						vm.generateSalesReport();
						System.out.println("The sales report has been generated.");
						break;
				}

			} else {
				switch (menuOption) {
					case 1:
						ConsoleServices.inputMoney(vm);
						break;
					case 2:
						vm.displayInventory();
						ConsoleServices.inputProductSelection(vm);
						break;
					case 3:
						System.out.println("Thank you for using the Vendo-Matic 800.");
						vm.dispenseChange();
						vm.setActiveMenu("main");
						break;
				}
			}
		}
	}

}
