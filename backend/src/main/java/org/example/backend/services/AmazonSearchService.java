package org.example.backend.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.backend.models.entities.BookOffer;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AmazonSearchService {

    private static final int SUCCESS_STATUS_CODE = 200;
    private final String apiKey = "69a00f63442e5b27cc553da253551edb497df8b6";

    /**
     * This method searches for book offers on Amazon.
     * It uses the unwrangle API to search for book offers.
     *
     * @param bookTitle book title
     * @return list of book offers
     */
    public List<BookOffer> searchForBook(String bookTitle) {
        List<BookOffer> bookOffers = List.of();

        try {
            String encodedTitle = URLEncoder.encode(bookTitle, StandardCharsets.UTF_8);
            String url = String.format(
                    "https://data.unwrangle.com/api/getter/?platform=amazon_search"
                            + "&search=%s&country_code=us&api_key=%s",
                     encodedTitle, apiKey
            );

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == SUCCESS_STATUS_CODE) {
                bookOffers = parseJSON(response.body());
            }
        }
        catch (IOException | InterruptedException ex) {
            throw new RuntimeException("Failed to search for book offers:" + ex.getMessage());
        }

        return bookOffers;
    }

    /**
     * This method parses the JSON response from the unwrangle API.
     * It extracts the url and the price from the search result
     * from the JSON response.
     *
     * @param response JSON response
     * @return list of book offers
     */
    private List<BookOffer> parseJSON(String response) throws JsonProcessingException {
        List<BookOffer> bookOffers = new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response);

        JsonNode results = root.get("results");

        if (results != null && results.isArray()) {
            for (JsonNode result : results) {
                String url = result.get("url").asText();
                double price = result.get("price").asDouble();
                if (price == 0) {
                    // this indicates that the price is not available, so we skip this offer
                    continue;
                }
                BookOffer bookOffer = BookOffer.builder()
                        .url(url)
                        .price(price)
                        .date(LocalDate.now())
                        .build();
                bookOffers.add(bookOffer);
            }
        }

        return bookOffers;
    }
}
