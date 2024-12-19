package ca.jrvs.apps.jdbc.dto;

public class Position {

  private String ticker; //id
  private int numOfShares;
  private double valuePaid; //total amount paid for shares

  public String getTicker() {
    return ticker;
  }

  public void setTicker(String ticker) {
    this.ticker = ticker;
  }

  public int getNumOfShares() {
    return numOfShares;
  }

  public void setNumOfShares(int numOfShares) {
    this.numOfShares = numOfShares;
  }

  public double getValuePaid() {
    return valuePaid;
  }

  public void setValuePaid(double valuePaid) {
    this.valuePaid = valuePaid;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    sb.append("Position Details:\n");
    sb.append("-------------------------------------\n");
    sb.append(String.format("Symbol: %s%n", ticker));
    sb.append(String.format("Number of Shares: %d%n", numOfShares));
    sb.append(String.format("Value Paid: %.2f%n", valuePaid));
    sb.append("-------------------------------------\n");
    return sb.toString();
  }



}
