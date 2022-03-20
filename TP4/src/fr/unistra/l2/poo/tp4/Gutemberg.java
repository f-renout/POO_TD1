package fr.unistra.l2.poo.tp4;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Gutemberg {

    public static final String TITLE_AND_AUTHOR_EBOOK_NO = "TITLE and AUTHOR                                                     EBOOK NO.";

    private String getLastBookUrl() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder(URI.create("https://www.gutenberg.org/dirs/GUTINDEX.ALL")).build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<Stream<String>> response = client.send(request, HttpResponse.BodyHandlers.ofLines());
        Optional<String> first = response.body()
                .dropWhile(Predicate.not(TITLE_AND_AUTHOR_EBOOK_NO::equals)) //on attend d'avoir la ligne qui va annoncer les livres
                .skip(2) //on saute cette signe et la ligne suivante qui est blanche
                .findFirst(); //ensuite la suivante est le 1er ouvrage dispo
        String x = first.get();
        //on va remplacer tous les espaces simples par du vide (sauf si c'est suivi d'un chiffre)
        x= x.replaceAll("\\s(\\D)", "$1");
        //on remplace x espaces par un seul
        x=x.replaceAll("\\s\\s+"," ");
        String[] s = x.split(" ");
        x= s[s.length-1];
        return String.format("https://www.gutenberg.org/files/%1$s/%1$s-h/%1$s-h.htm",x);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Gutemberg g = new Gutemberg();
        String lastBookUrl = g.getLastBookUrl();
        g.saveBook(lastBookUrl,"d:/develop/testEbook.html");
    }

    private void saveBook(String lastBookUrl, String s) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder(URI.create(lastBookUrl)).build();
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<Path> response = client.send(request, HttpResponse.BodyHandlers.ofFile(Paths.get(s)));
        System.out.println(response.body());
    }
}
