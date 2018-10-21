import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.omg.CORBA.NameValuePair;
//import org.apache.http.NameValuePair;
//import org.apache.http.client.utils.URLEncodedUtils;

import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
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
    */

    FireSafetyServer()
    {
        port(30000);

        //adds https capability to server

        addroutes();
    }

    public void addroutes()
    {

        post("/image/check", (request, response) ->
        {

            //List<NameValuePair> pairs = URLEncodedUtils.parse(request.body(),Charset.defaultCharset());
            //Map<String, String> params = toMap(pairs);
            //String[] b = params.get("images").split(",");
            ArrayList<String> urls = new ArrayList<>();
            //for(int i = 0; i < b.length; i++){
            //    urls.add(URLDecoder.decode(b[i]));
            //}

            DataHandler dh = new DataHandler();
            DatabaseAPI dapi = new DatabaseAPI();
            ArrayList<Problem> problems = dapi.getProblems();

            return dh.checkUserImages(problems, urls);
        });

        post("/concept/add", "application/json", ((request, response) ->{

            JsonParser parser = new JsonParser();
            JsonElement s = parser.parse(request.body());
            JsonObject dict = s.getAsJsonObject();
            String response_str = dict.get("response").getAsString();
            JsonObject urls = dict.get("urls").getAsJsonObject();
            JsonArray conditions = dict.getAsJsonArray("conditions");

            ArrayList<Condition> conditionsArr = new ArrayList<>();
            int type = 0;
            for(int i = 0; i < conditions.size(); i++){
                int status = conditions.get(i).getAsJsonArray().get(0).getAsInt();
                String concept = conditions.get(i).getAsJsonArray().get(1).getAsString();
                conditionsArr.add(new Condition(concept,status));
                if(status == 1) {
                    type = 1;
                }
                System.out.println(conditions.get(i).getAsJsonArray().get(1));
            }
            Problem prob = new Problem(response_str, type);
            prob.conditions = conditionsArr;
            System.out.println(prob);

            ClarifaiHandler ch = new ClarifaiHandler();
            DatabaseAPI dapi = new DatabaseAPI();

            for(Condition c: prob.conditions){
                if(urls.get(c.concept) != null && !(dapi.conceptExists(c.concept))){
                    JsonArray a = urls.get(c.concept).getAsJsonArray();
                    for(int i = 0; i < a.size(); i++){
                        //call train method
                        ch.introduceTrainingData(a.get(i).getAsString(),c.concept);
                    }
                }
            }

            return "";
        }));

    }

    private static Map<String, String> toMap(List<NameValuePair> pairs){
        Map<String, String> map = new HashMap<>();
        for(int i=0; i<pairs.size(); i++){
            NameValuePair pair = pairs.get(i);
            //map.put(pair.getName(), pair.getValue());
        }
        return map;
    }

    public static void main(String[]args){

        FireSafetyServer fss = new FireSafetyServer();

    }

    /**
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
     **/


}



