package ca.jrvs.apps.jdbc.helpers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

import ca.jrvs.apps.jdbc.dto.Quote;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class QuoteHttpHelperTest {

  @InjectMocks
  private QuoteHttpHelper quoteHttpHelper; // Inject mocks to QuoteHttpHelper
  @Mock
  private OkHttpClient mockClient;
  @Mock
  private Call mockCall;
  @Mock
  private Response mockResponse;
  @Mock
  private ResponseBody mockResponseBody;

  @Test
  void fetchQuoteInfo() throws IOException {

    String mockJsonResponse = "{\n" +
        "  \"Global Quote\": {\n" +
        "    \"01. symbol\": \"MSFT\",\n" +
        "    \"02. open\": \"174.00\",\n" +
        "    \"03. high\": \"175.00\",\n" +
        "    \"04. low\": \"173.00\",\n" +
        "    \"05. price\": \"174.50\",\n" +
        "    \"06. volume\": \"2000000\",\n" +
        "    \"07. latest trading day\": \"2024-11-28\",\n" +
        "    \"08. previous close\": \"173.50\",\n" +
        "    \"09. change\": \"1.00\",\n" +
        "    \"10. change percent\": \"0.58%\"\n" +
        "  }\n" +
        "}";

    lenient().when(mockClient.newCall(any(Request.class))).thenReturn(mockCall);
    lenient().when(mockCall.execute()).thenReturn(mockResponse);
    lenient().when(mockResponse.isSuccessful()).thenReturn(true);
    lenient().when(mockResponse.body()).thenReturn(mockResponseBody);
    lenient().when(mockResponseBody.string()).thenReturn(mockJsonResponse);

    Quote quote = quoteHttpHelper.fetchQuoteInfo("MSFT");

    assertNotNull(quote);
    assertEquals("MSFT", quote.getTicker());
    assertEquals(174.00, quote.getOpen(), 0.01);
    assertEquals(175.00, quote.getHigh(), 0.01);
    assertEquals(173.00, quote.getLow(), 0.01);
    assertEquals(174.50, quote.getPrice(), 0.01);
    assertEquals(2000000, quote.getVolume());
  }

}