import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import clarifai2.api.request.model.PredictRequest;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.model.Model;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.prediction.Concept;

import java.util.ArrayList;
import java.util.List;

public class ClarifaiHandler extends ClarifaiInteractions
{
    final String apiKey = "f4d59c866d7447cd8b4d93cca544f6d7";

    final ClarifaiClient client = new ClarifaiBuilder(apiKey).buildSync();

    @Override
    List<Concept> getPredictionResult(String url) {
        Model<Concept> generalModel = client.getModelByID("smoke detector").executeSync().get().asConceptModel();

        PredictRequest<Concept> request = generalModel.predict().withInputs(ClarifaiInput.forImage(url));

        List<ClarifaiOutput<Concept>> result = request.executeSync().get();

        return result.get(0).data();
    }

    @Override
    void train(String url, boolean positive, String... contexts) {

    }

    public static void main(String[] args)
    {
        ClarifaiHandler ch = new ClarifaiHandler();
        List<Concept> list = ch.getPredictionResult("https://media.rs-online.com/t_large/F7404434-01.jpg");
        for (Concept c : list)
        {
            System.out.println(c.name() + " " + c.value());
        }
    }
}