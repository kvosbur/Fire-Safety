import java.util.ArrayList;

public class Problem {

    public String response;
    public ArrayList<Condition> conditions;
    public int type;

    Problem(String response, int type){
        this.response = response;
        this.conditions = new ArrayList<>();
        this.type = type;
    }

    public void addCondition(Condition c){
        this.conditions.add(c);
    }

    public String toString(){
        return "response: " + this.response + "\ntype: " + this.type + "\nconditions:\n" + conditionString();
    }

    public String conditionString(){
        String a = "";
        for(int i = 0; i < conditions.size(); i++){
            a += conditions.get(i).toString() + "\n";
        }
        return a;
    }
}
