package fr.unistra.l2.poo.tp4;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FactRequester {
    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        FactRequester fr = new FactRequester();
//        System.out.println(fr.getFact());
//        System.out.println(fr.getCategories());
        fr.parListe();
    }

    String getFact() throws URISyntaxException, IOException, InterruptedException {
        HttpResponse<String> response = getStringHttpResponse("https://api.chucknorris.io/jokes/random");
        return parseFact(response);
    }

    private String parseFact(HttpResponse<String> response) {
        checkApi(response);
        Pattern p = Pattern.compile("value\":\"([^\"]*)");
        Matcher m = p.matcher(response.body());
        if (m.find()) {
            return m.group(1);
        }
        return "on n'a rien trouvé";
    }

    private void checkApi(HttpResponse<String> response) {
        if (response.statusCode() != 200) {
            throw new RuntimeException("L'api n'a pu etre accedée");
        }
    }

    String[] getCategories() throws URISyntaxException, IOException, InterruptedException {
        HttpResponse<String> response = getStringHttpResponse("https://api.chucknorris.io/jokes/categories");
        return parseCategories(response);
    }

    private HttpResponse<String> getStringHttpResponse(String str) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder(new URI(str)).GET().build();
        HttpClient client = HttpClient.newHttpClient();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    private String[] parseCategories(HttpResponse<String> response) {
        checkApi(response);
        return response.body().replaceAll("[\\[\\]\"]", "").split(",");
    }

    public void parListe() throws URISyntaxException, IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        String[] categories = getCategories();
        int max = categories.length;
        printCategories(categories);
        boolean continuer = true;
        do {
            String input = scanner.next();
            if ("x".equals(input)) {
                continuer = false;
            } else if ("h".equals(input)) {
                printCategories(categories);
            } else {
                try {
                    int i = Integer.parseInt(input);
                    if (i > 0 && i <= max) {
                        System.out.println(getJoke(categories[i]));
                    }
                } catch (NumberFormatException nfe) {
                }
            }
        } while (continuer);
    }

    private String getJoke(String category) throws URISyntaxException, IOException, InterruptedException {
        HttpResponse<String> response = getStringHttpResponse("https://api.chucknorris.io/jokes/random?category="+category);
        return parseFact(response);
    }

    private void printCategories(String[] categories) {
        System.out.println("Choix de la categorie");
        int index = 0;
        for (String category : categories) {
            System.out.printf("%d : %s%n", index++, category);
        }
        System.out.printf("Entrez%n -le numero de la categorie demandée %n -h pour reafficher les categories %n -x pour quitter%n");

    }
}
