import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Scraper {

    private static String api_key = "AIzaSyBlWZ18lP4Wbp7dpO-u0tU1VpSfqrN_bsM";

    public static ArrayList<String> scrape_all() throws Exception{
        ArrayList<String> images = new ArrayList<>();
        for(int i = 0; i < 6; i++){
            images.addAll(scrape(i * 10 + 1));
        }

        return images;
    }

    public static ArrayList<String> scrape(int start) throws Exception{
        URL url = new URL("https://www.googleapis.com/customsearch/v1?q=smoke+detector&safe=active&num=10&cx=010710749101572951682:tdhodzxai2i&searchType=image&key=" + api_key + "&start=" + start);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setConnectTimeout(5000);
        con.setReadTimeout(5000);

        int status = con.getResponseCode();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();

        ArrayList<String> images = new ArrayList<>();

        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(content.toString());
        JsonArray items = element.getAsJsonObject().get("items").getAsJsonArray();
        for(int i =0; i < items.size(); i++){
            String s = items.get(i).getAsJsonObject().get("link").getAsString();
            images.add(s);
        }

        return images;
    }

    public static void main(String[] args) {
        try {
            ArrayList<String> images = Scraper.scrape_all();
            for(int i = 0; i < images.size(); i++){
                System.out.println(images.get(i));
            }
            System.out.println(images.size());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
