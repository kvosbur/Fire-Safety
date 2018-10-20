public class Condition {

    public String concept;
    public int status;

    Condition(String concept, int status){
        this.concept = concept;
        this.status = status;
    }

    public String toString(){
        return "status:" + status + " concept:" + this.concept;
    }
}
