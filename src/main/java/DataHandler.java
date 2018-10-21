import clarifai2.dto.prediction.Concept;

import java.util.ArrayList;
import java.util.List;

public class DataHandler
{
    public String checkUserImages(List<Problem> problems, List<String> urls)
    {
        ClarifaiHandler ch = new ClarifaiHandler();

        List<List<Concept>> results = new ArrayList<>();

        for (String url : urls)
        {
            results.add(ch.getPredictionResult(url));
        }

        String output = "";

        for (Problem p : problems)
        {
            boolean isProblem;

            if (p.type == 0)
            {
                isProblem = checkNegatives(results, p);
            }
            else
            {
                isProblem = checkPositives(results, p);
            }

            if (isProblem)
            {
                output += p.response + "\n";
            }
        }

        return output;
    }

    public boolean checkNegatives(List<List<Concept>> results, Problem p)
    {
        //Check every instance of a concept found in any of the images to see if they ever match one of the problem conditions
        //If any of the problem conditions are in one of the images, then there is no problem
        for (List<Concept> concepts : results)
        {
            for (Concept imageConcept : concepts)
            {
                for (Condition c : p.conditions)
                {
                    if (imageConcept.name().equals(c.concept) && imageConcept.value() > threshold())
                    {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public boolean checkPositives(List<List<Concept>> results, Problem p)
    {
        //Check every image to see if the necessary conditions in the problem are ever met
        for (List<Concept> concepts : results)
        {
            boolean isProblem = true;

            for (Condition c : p.conditions)
            {
                for (Concept imageConcept : concepts)
                {
                    if (imageConcept.name().equals(c.concept) && c.status == 0 && imageConcept.value() > threshold())
                    {
                        isProblem = false;

                        break;
                    }

                    if (imageConcept.name().equals(c.concept) && c.status == 1 && imageConcept.value() < threshold())
                    {
                        isProblem = false;

                        break;
                    }
                }
            }

            if (isProblem)
            {
                return true;
            }
        }

        return false;
    }

    public double threshold()
    {
        return 0.5;
    }
}
