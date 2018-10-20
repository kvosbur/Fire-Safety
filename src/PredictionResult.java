public class PredictionResult
{
    private String context;
    private double result;

    public PredictionResult(String context, double result)
    {
        this.context = context;

        this.result = result;
    }

    public String getContext()
    {
        return context;
    }

    public double getResult()
    {
        return result;
    }
}