package ca.jrvs.apps.jdbc.controllers;

import ca.jrvs.apps.jdbc.dto.Quote;
import ca.jrvs.apps.jdbc.services.PositionService;
import ca.jrvs.apps.jdbc.services.QuoteService;
import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;

public class StockQuoteController {

  private QuoteService quoteService;
  private PositionService positionService;

  public StockQuoteController(QuoteService quoteService, PositionService positionService) {
    this.quoteService = quoteService;
    this.positionService = positionService;
  }

  /**
   * User interface for our application
   */
  public void initClient() {
    Scanner scanner = new Scanner(System.in);

    while (true) {
      try {
        System.out.println("Welcome to the Stock Quote App!");
        System.out.println("Please choose an option by entering the corresponding number:");
        System.out.println("1. View stock quote");
        System.out.println("2. Buy shares");
        System.out.println("3. Sell shares");
        System.out.println("4. View position");
        System.out.println("5. Exit");

        int choice = scanner.nextInt();

        switch (choice) {
          case 1:
            viewQuote(scanner);
            break;
          case 2:
            buyShares(scanner);
            break;
          case 3:
            sellShares(scanner);
            break;
          case 4:
            viewPosition(scanner);
            break;
          case 5:
            System.out.println("Exiting application...");
            return;
          default:
            System.out.println("Invalid choice. Please try again.");
        }
      } catch (InputMismatchException e) {
        System.out.println("Invalid input. Please enter a number.");
        scanner.nextLine();
      } catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
      }
    }
  }

  private void viewQuote(Scanner scanner) {
    System.out.print("Enter stock ticker: ");
    String ticker = scanner.next();
    Optional<Quote> quote = quoteService.fetchQuoteDataFromAPI(ticker);
    if (quote.isEmpty()) {
      System.out.println("That is an invalid quote. Please try again.");
    } else {
      System.out.println(quote.get());
    }

  }

  private void buyShares(Scanner scanner) {
    System.out.print("Enter stock ticker: ");
    String ticker = scanner.next();

    System.out.print("Enter number of shares: ");
    int numberOfShares = scanner.nextInt();

    System.out.print("Enter price per share: ");
    double price = scanner.nextDouble();

    try {
//      Position position = positionService.buy(ticker, numberOfShares, price);
//      System.out.println("Position updated: " + position);
    } catch (Exception e) {
      System.out.println("Error while buying shares: " + e.getMessage());
    }
  }

  public void sellShares(Scanner scanner){

  }

  public void viewPosition(Scanner scanner){

  }

}
