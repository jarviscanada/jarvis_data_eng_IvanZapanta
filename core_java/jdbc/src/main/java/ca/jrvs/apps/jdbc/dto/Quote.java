package ca.jrvs.apps.jdbc.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import java.sql.Date;
import java.sql.Timestamp;

@JsonInclude(Include.NON_NULL)
@JsonRootName("Global Quote")
@JsonPropertyOrder({
    "01. symbol",
    "02. open",
    "03. high",
    "04. low",
    "05. price",
    "06. volume",
    "07. latest trading day",
    "08. previous close",
    "09. change",
    "10. change percent"
})

public class Quote {

  @JsonProperty("01. symbol")
  private String ticker; //id
  @JsonProperty("02. open")
  private double open;
  @JsonProperty("03. high")
  private double high;
  @JsonProperty("04. low")
  private double low;
  @JsonProperty("05. price")
  private double price;
  @JsonProperty("06. volume")
  private int volume;
  @JsonProperty("07. latest trading day")
  private Date latestTradingDay;
  @JsonProperty("08. previous close")
  private double previousClose;
  @JsonProperty("09. change")
  private double change;
  @JsonProperty("10. change percent")
  private String changePercent;

  private Timestamp timestamp; //time when the info was pulled

  public String getTicker() {
    return ticker;
  }

  public void setTicker(String ticker) {
    this.ticker = ticker;
  }

  public double getOpen() {
    return open;
  }

  public void setOpen(double open) {
    this.open = open;
  }

  public double getHigh() {
    return high;
  }

  public void setHigh(double high) {
    this.high = high;
  }

  public double getLow() {
    return low;
  }

  public void setLow(double low) {
    this.low = low;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public int getVolume() {
    return volume;
  }

  public void setVolume(int volume) {
    this.volume = volume;
  }

  public Date getLatestTradingDay() {
    return latestTradingDay;
  }

  public void setLatestTradingDay(Date latestTradingDay) {
    this.latestTradingDay = latestTradingDay;
  }

  public double getPreviousClose() {
    return previousClose;
  }

  public void setPreviousClose(double previousClose) {
    this.previousClose = previousClose;
  }

  public double getChange() {
    return change;
  }

  public void setChange(double change) {
    this.change = change;
  }

  public String getChangePercent() {
    return changePercent;
  }

  public void setChangePercent(String changePercent) {
    this.changePercent = changePercent;
  }

  public Timestamp getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Timestamp timestamp) {
    this.timestamp = timestamp;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Quote{");
    sb.append("ticker='").append(ticker).append('\'');
    sb.append(", open=").append(open);
    sb.append(", high=").append(high);
    sb.append(", low=").append(low);
    sb.append(", price=").append(price);
    sb.append(", volume=").append(volume);
    sb.append(", latestTradingDay=").append(latestTradingDay);
    sb.append(", previousClose=").append(previousClose);
    sb.append(", change=").append(change);
    sb.append(", changePercent='").append(changePercent).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
