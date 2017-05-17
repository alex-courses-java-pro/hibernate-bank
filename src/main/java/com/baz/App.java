package com.baz;

import com.baz.dao.PersistenceManager;
import com.baz.enums.CurrencyType;
import com.baz.service.ClientService;

import java.math.BigDecimal;
import java.util.Scanner;

/**
 * Hello world!
 */
public class App {

    private static ClientService clientService = new ClientService();

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("0: exit");
                System.out.println("1: replenish account");
                System.out.println("2: money transfer");
                System.out.println("3: currency exchange");
                System.out.println("4: withdraw all money in UAH");
                System.out.print("-> ");
                int menuOption = Integer.parseInt(scanner.nextLine());
                handleInput(menuOption, scanner);
            }
        }
    }

    private static void handleInput(int menuOption, Scanner scanner) {
        switch (menuOption) {
            case 0:
                exitApp();
                break;
            case 1:
                replenishAcc(scanner);
                break;
            case 2:
                transferMoney(scanner);
                break;
            case 3:
                exchangeCurrency(scanner);
                break;
            case 4:
                withdrawAll(scanner);
                break;

        }
    }

    private static void withdrawAll(Scanner scanner) {
        System.out.print("client id: ");
        long clientId = Long.parseLong(scanner.nextLine());

        clientService.withdrawAllInUAH(clientId);
    }

    private static void exchangeCurrency(Scanner scanner) {
        System.out.print("client id: ");
        long clientId = Long.parseLong(scanner.nextLine());
        System.out.print("from account: ");
        CurrencyType typeFrom = CurrencyType.valueOf(scanner.nextLine());
        System.out.print("to account: ");
        CurrencyType typeTo = CurrencyType.valueOf(scanner.nextLine());
        System.out.print("amount: ");
        BigDecimal amount = new BigDecimal(scanner.nextLine());

        clientService.exchange(clientId, typeFrom, typeTo, amount);
    }

    private static void transferMoney(Scanner scanner) {
        System.out.println("*FROM*");
        System.out.print("client id: ");
        long clientIdFrom = Long.parseLong(scanner.nextLine());
        System.out.print("account type: ");
        CurrencyType type = CurrencyType.valueOf(scanner.nextLine());
        System.out.print("amount: ");
        BigDecimal amount = new BigDecimal(scanner.nextLine());

        System.out.println("*TO*");
        System.out.print("client id: ");
        long clientIdTo = Long.parseLong(scanner.nextLine());

        clientService.transfer(clientIdFrom, clientIdTo, type, amount);
    }

    private static void replenishAcc(Scanner scanner) {
        System.out.print("client id: ");
        long clientId = Long.parseLong(scanner.nextLine());
        System.out.print("account type: ");
        CurrencyType type = CurrencyType.valueOf(scanner.nextLine());
        System.out.print("amount: ");
        BigDecimal amount = new BigDecimal(scanner.nextLine());

        clientService.replenishAccount(clientId, type, amount);
    }

    private static void exitApp() {
        PersistenceManager.INSTANCE.close();
        System.exit(0);
    }
}
