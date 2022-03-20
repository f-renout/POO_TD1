package fr.unistra.l2.poo.tp4;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FactRequester {
    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        FactRequester fr = new FactRequester();
        System.out.println(fr.getFact());
        System.out.println(fr.getCategories());
    }

    String getFact() throws URISyntaxException, IOException, InterruptedException {
        HttpResponse<String> response = getStringHttpResponse("https://api.chucknorris.io/jokes/random");
        return parseFact(response);
    }

    private String parseFact(HttpResponse<String> response) {
        checkApi(response);
        Pattern p = Pattern.compile(".*value\":\"(.*)\".*");
        Matcher m = p.matcher(response.body());
        if (m.matches()) {
            return m.group(1);
        }
        return "on n'a rien trouvé";
    }

    private void checkApi(HttpResponse<String> response) {
        if (response.statusCode() != 200) {
            throw new RuntimeException("L'api n'a pu etre accedée");
        }
    }

    List<String> getCategories() throws URISyntaxException, IOException, InterruptedException {
        HttpResponse<String> response = getStringHttpResponse("https://api.chucknorris.io/jokes/categories");
        return parseCategories(response);
    }

    private HttpResponse<String> getStringHttpResponse(String str) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder(new URI(str)).GET().build();
        HttpClient client = HttpClient.newHttpClient();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private List<String> parseCategories(HttpResponse<String> response) {
        checkApi(response);
        String[] split = response.body().replaceAll("[\\[\\]\"]", "").split(",");
        return Arrays.asList(split);
    }
}
