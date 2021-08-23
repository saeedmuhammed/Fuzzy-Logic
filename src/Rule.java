import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Rule {
    Map<String, String> ruleValues;
    Map<String, String> ruleValuesOutput;
    ArrayList<String> operations;
    ArrayList<String> operationsOutput;

    public Rule(){
        ruleValues = new HashMap<>();
        ruleValuesOutput = new HashMap<>();
        operations = new ArrayList<>();
        operationsOutput = new ArrayList<>();
    }
}
