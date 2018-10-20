import java.util.List;

public abstract class ClarifaiInteractions
{
    abstract List<PredictionResult> getPredictionResult(String url);
    abstract void train(String url, boolean positive, String... contexts);
}