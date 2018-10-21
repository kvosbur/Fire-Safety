import clarifai2.api.ClarifaiBuilder;
import clarifai2.api.ClarifaiClient;
import clarifai2.api.ClarifaiResponse;
import clarifai2.api.request.model.Action;
import clarifai2.api.request.model.PredictRequest;
import clarifai2.dto.input.ClarifaiInput;
import clarifai2.dto.model.ConceptModel;
import clarifai2.dto.model.Model;
import clarifai2.dto.model.ModelVersion;
import clarifai2.dto.model.output.ClarifaiOutput;
import clarifai2.dto.prediction.Concept;
import clarifai2.dto.prediction.Prediction;

import java.util.ArrayList;
import java.util.List;

public class ClarifaiHandler extends ClarifaiInteractions
{
    final String apiKey = "b5535747c23e4c04a6f22826a8ac501b";

    final ClarifaiClient client = new ClarifaiBuilder(apiKey).buildSync();

    final String modelID = "fire_safety";

    @Override
    List<Concept> getPredictionResult(String url)
    {
        Model<Concept> generalModel = client.getModelByID(modelID).executeSync().get().asConceptModel();

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

        client.trainModel(modelID).executeSync();
    }

    void introduceTrainingData(String url, String concept)
    {
        client.modifyModel(modelID).withConcepts(Action.MERGE, Concept.forID(concept)).executeSync();

        client.addInputs().plus(ClarifaiInput.forImage(url).withConcepts(Concept.forID(concept))).executeSync();

        client.trainModel(modelID).executeSync();
    }
}