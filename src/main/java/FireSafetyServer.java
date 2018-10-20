package main.java;

/**import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;

import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static spark.Spark.port;
import static spark.Spark.post;


public class FireSafetyServer
{
    final String apiKey = "f4d59c866d7447cd8b4d93cca544f6d7";

    //final ClarifaiClient client = new ClarifaiBuilder(apiKey).buildSync();

    /*
    spark documentation
    http://sparkjava.com/documentation
    localhost:4567/hello


    FireSafetyServer()
    {
        port(20000);

        //adds https capability to server

        addroutes();
    }

    //Not exactly sure why this is here
    FireSafetyServer(int useless)
    {
    }

    public void addroutes()
    {

        post("/image/check", (request, response) ->
        {

            List<NameValuePair> pairs = URLEncodedUtils.parse(request.body(),Charset.defaultCharset());
            Map<String, String> params = toMap(pairs);
            String[] b = params.get("images").split(",");
            for(int i = 0; i < b.length; i++){
                b[i] = URLDecoder.decode(b[i]);
            }


            return "Hope this works";

        });

    }

    private static Map<String, String> toMap(List<NameValuePair> pairs){
        Map<String, String> map = new HashMap<>();
        for(int i=0; i<pairs.size(); i++){
            NameValuePair pair = pairs.get(i);
            map.put(pair.getName(), pair.getValue());
        }
        return map;
    }

    public static void main(String[]args){

        FireSafetyServer fss = new FireSafetyServer();

    }

     public class FireSafetyServer
     {
     final String apiKey = "f4d59c866d7447cd8b4d93cca544f6d7";

     final ClarifaiClient client = new ClarifaiBuilder(apiKey).buildSync();

     public List<ClarifaiOutput<Concept>> getPredictions(String imageURL)
     {
     Model<Concept> generalModel = client.getDefaultModels().generalModel();

     PredictRequest<Concept> request = generalModel.predict().withInputs
     (
     ClarifaiInput.forImage(imageURL)
     );

     List<ClarifaiOutput<Concept>> result = request.executeSync().get();
     }

     ConceptModel model = client.getDefaultModels().generalModel();
     ModelVersion modelVersion = model.getVersionByID("the-version").executeSync().get();

     ClarifaiResponse<List<ClarifaiOutput<Prediction>>> response = client.predict(model.id())
     .withInputs(ClarifaiInput.forImage("https://samples.clarifai.com/metro-north.jpg"))
     .withVersion("aa7f35c01e0642fda5cf400f543e7c40")
     .executeSync();

     }


}

**/

public class FireSafetyServer
        {

        }