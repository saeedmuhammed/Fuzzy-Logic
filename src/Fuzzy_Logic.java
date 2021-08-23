import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class Fuzzy_Logic {

    /*------------------------------------------------------------------------------------*/
    public Fuzzy_Logic(){ }

    /*------------------------------------------------------------------------------------*/
    public void fuzzification(ArrayList<Variable> variables){
        for (int i = 0; i < variables.size(); i++) {
            int finalI = i;
            for(int j=0 ;j <variables.get(i).sets.size();j++){
                ArrayList<Point> points = new ArrayList<>();
                if(variables.get(i).sets.get(j).setValues.size() == 3){
                    points = chooseTriangular(variables.get(finalI).crispValue, variables.get(i).sets.get(j).setValues);
                }else{
                    points = chooseTrapezoidal(variables.get(finalI).crispValue, variables.get(i).sets.get(j).setValues);
                }
                double lineSlope = calcSlope(points);
                double b = (points.get(0).y) - (lineSlope * points.get(0).x);
                double output = ((lineSlope * variables.get(finalI).crispValue) + b);
                variables.get(i).sets.get(j).output=output;
                //variables.get(finalI).output.put(variables.get(finalI).sets, output);
            }
        }
    }
    /*------------------------------------------------------------------------------------*/
    public void inference(ArrayList<Variable> variables, ArrayList<Rule> rules){
        Variable variable = new Variable();

        for(int i = 0; i < rules.size(); i++){

            for(int y = 0; y < rules.get(i).ruleValues.size(); y++){
                ArrayList<Double> multiOutput = new ArrayList<>();
                if(rules.get(i).operations.size() > 1) {
                    Iterator<Map.Entry<String, String>> itr = rules.get(i).ruleValues.entrySet().iterator();

                    while(itr.hasNext())
                    {
                        Map.Entry<String, String> entry = itr.next();
                        Variable s= getVriable(variables,entry.getKey());
                        Double setOutput=s.getSetByName(entry.getValue());
                        multiOutput.add(setOutput);

                    }
                    Double lasOutput=0.0;
                    if (rules.get(i).operations.get(0).equals("or")) {
                        lasOutput = Math.max(multiOutput.get(0), multiOutput.get( 1));
                    }
                    else{
                        lasOutput = Math.min(multiOutput.get(0), multiOutput.get( 1));

                    }

                        for(int j = 1; j < rules.get(i).operations.size(); j++) {
                        for (int k = 2; k < multiOutput.size(); k++) {
                            if (rules.get(i).operations.get(j).equals("or")) {
                                lasOutput=Math.max(multiOutput.get(k),lasOutput);
                            } else {
                                lasOutput=Math.min(multiOutput.get(k),lasOutput);


                            }
                        }
                    }
                }
                else
                    if(rules.get(i).operations.get(0).equals("or")){
                        Iterator< Map.Entry<String, String>> itr = rules.get(i).ruleValues.entrySet().iterator();

                            Map.Entry<String, String> entry = itr.next();
                           Variable s= getVriable(variables,entry.getKey());
                           Double setOutput=s.getSetByName(entry.getValue());

                        Map.Entry<String, String> entry2 = itr.next();
                        Variable s2= getVriable(variables,entry2.getKey());
                        Double setOutput2=s.getSetByName(entry2.getValue());
                        System.out.println(Math.max(setOutput,setOutput2));
                        y++;



                    }else{
                        Iterator< Map.Entry<String, String>> itr = rules.get(i).ruleValues.entrySet().iterator();

                        Map.Entry<String, String> entry = itr.next();
                        Variable s= getVriable(variables,entry.getKey());
                        Double setOutput=s.getSetByName(entry.getValue());

                        Map.Entry<String, String> entry2 = itr.next();
                        Variable s2= getVriable(variables,entry2.getKey());
                        Double setOutput2=s2.getSetByName(entry2.getValue());
                        System.out.println(Math.min(setOutput,setOutput2));
                        y++;


                    }
            }
        }
    }
    /*------------------------------------------------------------------------------------*/
    public ArrayList<Point> chooseTriangular(double crisp, ArrayList<Integer> set){
        ArrayList<Point> points = new ArrayList<>();

        if ((crisp >= set.get(0)) && (crisp <= set.get(1))){
            Point p1 = new Point(set.get(0), 0);
            Point p2 = new Point(set.get(1), 1);
            points.add(p1);
            points.add(p2);
        } else if ((crisp >= set.get(1)) && (crisp <= set.get(2))){
            Point p1 = new Point(set.get(1), 1);
            Point p2 = new Point(set.get(2), 0);
            points.add(p1);
            points.add(p2);
        }
        return points;
    }
    /*------------------------------------------------------------------------------------*/
    public ArrayList<Point> chooseTrapezoidal(double crisp, ArrayList<Integer> set){
        ArrayList<Point> points = new ArrayList<>();

        if(crisp >= set.get(0) && crisp <= set.get(1)){
            Point p1 = new Point(set.get(0), 0);
            Point p2 = new Point(set.get(1), 1);
            points.add(p1);
            points.add(p2);
        }
        else if(crisp >= set.get(1) && crisp <= set.get(2)){
            Point p1 = new Point(set.get(1), 1);
            Point p2 = new Point(set.get(2), 1);
            points.add(p1);
            points.add(p2);
        }
        else if(crisp >= set.get(2) && crisp <= set.get(3)){
            Point p1 = new Point(set.get(2), 1);
            Point p2 = new Point(set.get(3), 0);
            points.add(p1);
            points.add(p2);
        }
        return points;
    }
    /*------------------------------------------------------------------------------------*/
    public double calcSlope(ArrayList<Point> points){
       Double slope = (Double.valueOf(points.get(1).y) - Double.valueOf(points.get(0).y))/(Double.valueOf(points.get(1).x) - Double.valueOf(points.get(0).x));
        return slope;
    }
    public Variable getVriable(ArrayList<Variable> variables,String name){

        for(int i =0;i<variables.size();i++){
            if(variables.get(i).name.equals(name)){ return variables.get(i); }


        }
        return null;


    }
}
