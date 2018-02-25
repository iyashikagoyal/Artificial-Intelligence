package decision.tree;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

class Data{
    
    LinkedList<Example> examples = new LinkedList<Example>();
  
    //Calculate the size of dataSet
    int size_of_data_set() {
     return examples.size();
    }

    //Get the set of examples for each value of Attribute
    Data get_dataset_for_each_value(String v, String A) {
        Data ds = new Data();
        for(Example e : examples){
                if(e.get_attribute_value(A).equals(v)){
                    ds.add(e);
                }
            }
        return ds;
    }

    //Add example to DataSet
    private void add(Example e) {
       examples.add(e);
    }

    //Get Example
    Example getExample(int i) {
      return examples.get(i);
    }

    //Iterator
    Iterator<Example> iterator() {
       return examples.iterator();
    }

    //Calcualte the gain
    double calculateGain(String attribute) {
        HashMap<String, Data> splitOnAttributeValues = new HashMap<>();
        double remainder = 0.0;
        //for loop generates a HashMap<String, DataSet> where String is the attributeValue
        //and DataSet is the set of examples for the corrosponding String
        for(Example e : examples){
            String val = e.get_attribute_value(attribute);
            if(splitOnAttributeValues.containsKey(val)){
                splitOnAttributeValues.get(val).add(e);
            }
            else
            {
                Data dataset = new Data();
                dataset.add(e);
                splitOnAttributeValues.put(val,dataset );
            }
        }
        
        //for loop calculates the remainder for each pf the value of an attribute
        for(String value : splitOnAttributeValues.keySet()){
            double b = splitOnAttributeValues.get(value).calculateB();
            int totalSize = examples.size();
            int size = splitOnAttributeValues.get(value).examples.size();
            remainder = remainder + (size/totalSize)*b;
        }
        //return the Gain
        return (calculateB() - remainder);
    }

     double log2(double v){
        return (Math.log10(v) / Math.log10(2));
    }
     
    //Calculate B 
    private double calculateB() { 
        HashMap<String, Integer> PositiveNegativeValuesCount= new HashMap<>();
        for(Example e : examples){
                String targetValue = e.getTargetValue();
                if(PositiveNegativeValuesCount.containsKey(targetValue)){
                    PositiveNegativeValuesCount.put(targetValue, PositiveNegativeValuesCount.get(targetValue) + 1);
                }
                else{
                    PositiveNegativeValuesCount.put(targetValue, 1);
                }
            }
        int totalSize = examples.size();
        int sizeOfPositiveValues = PositiveNegativeValuesCount.get("Yes");
        double q = sizeOfPositiveValues/totalSize;
        double b = (-(q*log2(q) + (1-q)*log2(1-q) ));
        return b;
    }
}


class Example{
    
    HashMap<String, Attribute> attributes = new HashMap<>();
    Attribute targetattribute ; 

    //Get VAlue for Attribute A
    String get_attribute_value(String A) {
       return attributes.get(A).getAttributeValue();
    }
    //Get VAlue of Target Attribute
    String getTargetValue() {
      return targetattribute.getAttributeValue() ;
    }
}

class Attribute{
    
    String attributename;
    String attributevalue;
    
    HashMap<String, LinkedList<String>> PossibleAttributeValues = new HashMap<>(); 
    
    //Get all values of Attribute
    LinkedList<String> getvalueofattributes(String Attribute){
        return PossibleAttributeValues.get(Attribute);
    }

    //Get AttributeVAlue
    String getAttributeValue() {
       return attributevalue;
    }
}

class DecisionTree{
    
    String attributeName;
    HashMap<String, DecisionTree> nodes = new HashMap<>();
    
    DecisionTree(String attrName){
        this.attributeName = attrName;
        nodes = new HashMap<String,DecisionTree>();
    }
    void addNode(String attrName, DecisionTree dt){
        nodes.put(attrName, dt);
    }
}
public class DecisionTreeLearning {
   
   DecisionTree dtl(Data examples, LinkedList<String> attributenames, DecisionTree defaultTree){
    if(examples.size_of_data_set()==0){
        return defaultTree;
    }
    else if(if_all_examples_have_same_classification(examples)){
        return new DecisionTree(examples.getExample(0).getTargetValue());
    }
    else if(attributenames.isEmpty()){
        return defaultTree;
    }
    else{
        String ChosenAttribute = ChooseAttribute(attributenames, examples);
        Attribute at = new Attribute();
        DecisionTree tree = new DecisionTree(ChosenAttribute);
        LinkedList<String> values = at.getvalueofattributes(ChosenAttribute);
         attributenames.remove(ChosenAttribute);
        for(String v : values){
            Data exs = examples.get_dataset_for_each_value(v, ChosenAttribute);
            DecisionTree subtree = dtl(exs, attributenames ,tree);
            tree.addNode(v, subtree);
        }
         return tree;
    }
     }   

   //Return true if the examples have same classification
    private boolean if_all_examples_have_same_classification(Data dataset) {
        
        String classification = dataset.getExample(0).getTargetValue();
        Iterator<Example> it = dataset.iterator();
        while(it.hasNext()){
            Example ex = it.next();
            if(!(ex.getTargetValue().equals(classification)))
                    {
                        return false;
                    }
        }
        return true;
        }
          
    

    //Return the most Important attribute which provides maximum information
    private String ChooseAttribute(LinkedList<String> attributenames, Data dataset) {
       
       double GreatestGain = 0.0;
       String attrWithGreatestGain = attributenames.get(0);
       for(String a : attributenames){
           double gain = dataset.calculateGain(a);
           if(gain > GreatestGain){
               GreatestGain = gain;
               attrWithGreatestGain = a;
           }
       }
       return attrWithGreatestGain;
    }
}
