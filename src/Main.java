import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //Fuzzy_Logic fuzzy_logic = new Fuzzy_Logic();

        ArrayList<Variable> variables = new ArrayList<>();
        ArrayList<Rule> rules = new ArrayList<>();

        while(true){

            /* Get The User Choice */
            System.out.println("1-Start Logic \n2-Exit\n");
            System.out.print("Enter Choice: ");
            int choice = new Scanner(System.in).nextInt();

            /* Invalid Input From User */
            if(choice > 2 || choice < 1) {
                System.out.println("Invalid Input\n");
                continue;
            }

            /*If User Wants To Shut Down The System. */
            if(choice == 2)
                break;


            int n = 0; //Number Of Variables.
            int k = 0; //Number Of Rules.

            /*
            * After Starting System, There is A group of Variables and Rules.
            * */

            while(true){
                int listChoice; // Variable To Control List

                int variableSetSize = 0;
                System.out.println("1- Add Variable \n2- Add Rule \n3- End\n");
                listChoice = new Scanner(System.in).nextInt();

                /* Invalid Input */
                if(listChoice > 3 || listChoice < 1)
                {
                    System.out.println("Invalid Input");
                    continue;
                }


                else if (listChoice == 1){
                    Variable var = new Variable();

                    /* Get Variable Name From User */
                    System.out.print("Enter Variable Name: ");
                    var.name = new Scanner(System.in).nextLine();//.toUpperCase();


                    System.out.print("Enter Number Of Degrees: ");
                    int d = new Scanner(System.in).nextInt();

                    for(int j = 0; j < d; j++){
                        System.out.print("Enter Degree Set: ");
                        String degree = new Scanner(System.in).nextLine();//.toUpperCase();

                        while (true) {
                            /* Get Set From User (Size - Value) */
                            System.out.print("Enter Set Size: (Triangle/trapezoidal) ->");
                            variableSetSize = new Scanner(System.in).nextInt();
                            if (variableSetSize == 3 || variableSetSize == 4)
                                break;
                            else
                                System.out.println("Invalid Input...(Should Be Either 3 or 4\n");
                        }

                        System.out.println("Enter Set Values: ");
                        ArrayList<Integer> tmp = new ArrayList<>();
                        for(int i = 0; i < variableSetSize; i++){
                            int x = new Scanner(System.in).nextInt();
                            tmp.add(x);
                        }
                        set s = new set();
                        s.name=degree;
                        s.setValues=tmp;
                        var.sets.add(s);
                    }
                    variables.add(var);
                    n++;
                }

                /* Adding Rule To Preform The Logic */

                else if(listChoice == 2){

                    System.out.println("Rule has Formula: (User Or || And Between n of Variables and Output After Then Word)");
                    String rule = new Scanner(System.in).nextLine().toLowerCase();
                    Rule rule1 = new Rule();

                    /* Refine Rule To Use It */
                    String [] tmp = rule.split(" then ");

                    /*
                     *    Input Refining
                    */
                    //Remove and - or
                    String [] smallRules = tmp[0].split(" and | or ");

                    /* Removing If */
                    for(int m = 0; m < smallRules.length; m++){
                        String target = smallRules[m].copyValueOf("if ".toCharArray());
                        smallRules[m] = smallRules[m].replace(target, "");
                    }

                    /* Removing is and Set Each Key With Value For Inputs */
                    for(int c = 0; c < smallRules.length; c++){
                        String[] inputTemp = smallRules[c].split(" is ");
                        rule1.ruleValues.put(inputTemp[0], inputTemp[1]);
                    }

                    /* Work on Operations and set Then on List Arranged */
                    int operationsSize = 0;

                    //if Multiple Operation
                    if(smallRules.length >=2){
                        operationsSize = smallRules.length - 1;
                        int sz = 0;
                        while(true){
                            if(sz == operationsSize)
                                break;
                            else{
                                String [] words = tmp[0].split(" ");
                                for(int b = 0; b < words.length; b++){
                                    if(words[b].equals("or")) {
                                        rule1.operations.add("or");
                                        sz++;
                                    }
                                    else if(words[b].equals("and")) {
                                        rule1.operations.add("and");
                                        sz++;
                                    }
                                }
                            }
                        }
                    }
                    // If Single Operation
                    else{
                        operationsSize = 1;
                        if(tmp[0].contains(" or ")){
                            rule1.operations.add("or");
                        }else rule1.operations.add("and");
                    }

                    /*
                    * Output Refining
                    * */

                    String str = tmp[tmp.length - 1];
                    int outputOperations = 0;

                    // If Multiple Output Variable
                    if(str.contains(" or ") || str.contains(" and ")) {
                        String [] strTemp = str.split(" and | or ");
                        outputOperations = strTemp.length - 1;
                        for(int p = 0; p < strTemp.length; p++){
                            String [] fOut = strTemp[p].split(" is ");
                            rule1.ruleValuesOutput.put(fOut[0], fOut[1]);
                        }

                        /* Work For Operations */
                        if(outputOperations > 1){
                            int sz = 0;
                            while(true){
                                if(sz == outputOperations)
                                    break;
                                String [] words = str.split(" ");
                                for(int b = 0; b < words.length; b++){
                                    if(words[b].equals("or")) {
                                        rule1.operationsOutput.add("or");
                                        sz++;
                                    }
                                    else if(words[b].equals("and")) {
                                        rule1.operationsOutput.add("and");
                                        sz++;
                                    }
                                }
                            }
                        }
                        else{
                            if(str.contains(" or "))
                                rule1.operationsOutput.add("or");
                            else
                                rule1.operationsOutput.add("and");
                        }
                    }
                    else{
                        outputOperations = 1;
                        String [] outputTemp = str.split(" is ");
                        rule1.ruleValuesOutput.put(outputTemp[0], outputTemp[1]);
                    }
                    k++;
                    rules.add(rule1);
                }else break;

                for(int u = 0; u < variables.size(); u++){
                    System.out.print("Enter " + variables.get(u).name + " Crisp Value: ");
                    variables.get(u).crispValue = new Scanner(System.in).nextDouble();
                }
            }
        }
        Fuzzy_Logic s= new Fuzzy_Logic();
        s.fuzzification(variables);
        s.inference(variables,rules);
    }
}
