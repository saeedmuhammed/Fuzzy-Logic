import java.util.ArrayList;

public class Variable {
    public String name;
    //public Map<String, ArrayList<Integer>> sets;
    public ArrayList<set> sets;
    //public Map<Map<String, ArrayList<Integer>>, Double> output;
    public double crispValue;

    public Variable(){
        name = new String();
        sets = new ArrayList<>();
      //  output = new HashMap<>();
        crispValue = 0;
    }

    /*
    public double getOutputValue(Map<String, ArrayList<Integer>> key){
        return (output.get(key));
    }
*/

public double getSetByName(String name){
    for (int i =0 ;i <sets.size();i++){
        if(sets.get(i).name.equals(name)){
            return sets.get(i).output;
        }


    }


   return 0.0;

}

}
