import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import clarifai2.api.request.model.Action;
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
    List<Concept> getPredictionResult(String url)
    {
        Model<Concept> generalModel = client.getModelByID("fire_safety").executeSync().get().asConceptModel();

        PredictRequest<Concept> request = generalModel.predict().withInputs(ClarifaiInput.forImage(url));

        List<ClarifaiOutput<Concept>> result = request.executeSync().get();

        return result.get(0).data();
    }

    @Override
    void train(String url, double threshold, Concept... concepts)
    {
        List<Concept> list = new ArrayList<>();

        for (Concept c : concepts)
        {
            if (c.name() != null)
            {
                list.add(Concept.forID(c.name()).withValue(c.value() > threshold));
            }
        }

        client.addInputs().plus(ClarifaiInput.forImage(url).withConcepts(list)).executeSync();

        client.trainModel("fire_safety").executeSync();
    }

    void introduceTrainingData(String url, String concept)
    {
        client.modifyModel("fire_safety").withConcepts(Action.MERGE, Concept.forID(concept)).executeSync();

        client.addInputs().plus(ClarifaiInput.forImage(url).withConcepts(Concept.forID(concept))).executeSync();

        client.trainModel("fire_safety").executeSync();
    }
}