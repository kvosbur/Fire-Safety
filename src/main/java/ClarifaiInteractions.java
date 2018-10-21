import clarifai2.dto.prediction.Concept;

import java.util.List;

abstract class ClarifaiInteractions
{
    abstract List<Concept> getPredictionResult(String url);
    abstract void train(String url, double threshold, Concept... concepts);
    abstract void introduceTrainingData(String url, String concept);
}