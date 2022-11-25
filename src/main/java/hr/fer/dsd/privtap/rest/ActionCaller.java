package hr.fer.dsd.privtap.rest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class ActionCaller {

    private String url;

    public void setUrl(String url){
        this.url = url;
    }
    @RequestMapping(value = "{url}", method = RequestMethod.POST,
            headers = "Accept=application/json")
    public String dostuff() throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("Sample request body"))
                .version(HttpClient.Version.HTTP_2)
                .build();
        System.out.println("son qui");
        System.out.println(request.uri());
        HttpClient client = HttpClient.newBuilder().build();
        System.out.println("qui?");
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(" oppure qui?");
        //String responseString = response.thenApply(HttpResponse::body).get(5, TimeUnit.SECONDS);
        //lo status della risposta ce l'hai solo se il tipo è HttpResponse
        return "seeActionHere";
    }

    public void otherTrial(String thisurl) throws IOException {
        URL url = new URL(thisurl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        System.out.println("prima");
        con.setRequestMethod("POST");
        System.out.println("dopo");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);
        /*
        String jsonInputString = "{\"name\": \"Upendra\", \"job\": \"Programmer\"}";
        try(OutputStream os = con.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        */


        int status = con.getResponseCode();
        System.out.println("status:" + status);
        /*
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        System.out.println("content:" + content); */
        con.disconnect();

    }

}
