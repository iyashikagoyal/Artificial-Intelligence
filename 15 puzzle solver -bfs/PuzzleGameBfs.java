import java.util.HashMap;
import java.util.LinkedList;
import java.util.Scanner;

public class PuzzleGameBfs {

    private static String goalState = "123456789ABCDEF0";  //goal state
    
    private static  LinkedList<String> openList = new LinkedList(); //frontier
    private static String currentState; //current state
    private static String newState; //new State after branching
    
    private static int i; //To store index of blank
    private static int levelvalue; //counter for storing level depth
    
    private static HashMap<String,Integer> stateLevel = new HashMap(); //to store cuurentState and level
    private static HashMap<String,String> stateHistory  = new HashMap(); //to store currentState and parent
    
    private static  LinkedList<String> pathList = new LinkedList(); //list to store the path 
    
    
    
    
    public static void main(String[] args) {
          
        long startTime = System.currentTimeMillis();  //start time of the program
        
        System.out.println("Enter the input in Hexadecimal"); 
        
        //scanning the input. Input should be in hexadecimal.
        Scanner sc = new Scanner(System.in);
        String initialState = sc.next();
       
        //adding the states to the frontier
        addToOpenList(initialState, null);
   
        //performing the search
        search();
 
        //chcking for the memory usage
        Runtime runtime = Runtime.getRuntime();
        runtime.gc();    // Run the garbage collector
        long usedmemory = runtime.totalMemory() - runtime.freeMemory();  // Calculate the used memory
        System.out.println("Memory Usage in bytes : " + usedmemory);
        
        //Time taken
        long stopTime = System.currentTimeMillis();    
        long elapsedTime = stopTime - startTime;    
        System.out.println("");
        System.out.println("Time in milliseconds : " + elapsedTime);
    }


    private static void search() {
   
        while(!openList.isEmpty()){
            currentState=openList.removeFirst();
            
            //checking if the current state is goal state
            if(currentState.equals(goalState)){
                printSolution(currentState);
                break;
            }
            
            else{
              i = currentState.indexOf("0");  //finding the location of blank = 0
             
              //move left
              while(i!=0 && i!=4 && i!=8 && i!=12){
                  newState = currentState.substring(0, i-1) + "0" + currentState.charAt(i-1) 
                          + currentState.substring(i+1); //replacement with new state
                  addToOpenList(newState, currentState);    //add new state to frontier
                  break;
              }
              
              //move right
              while(i!=3 && i!=7 && i!=11 && i!=15){
                  newState = currentState.substring(0, i) + currentState.charAt(i+1)+ "0" 
                          + currentState.substring(i+2); //replacement with new state
                  addToOpenList(newState, currentState); //add new state to frontier
                  break;
              }
              
              //move up
              while(i!=0 && i!=1 && i!=2 && i!=3){
                  newState = currentState.substring(0, i-4) + "0" + currentState.substring(i-3, i) 
                          + currentState.charAt(i-4) + currentState.substring(i+1); //replacement with new state
                  addToOpenList(newState, currentState); //add new state to frontier
                  break;
              }
              
              //move down
              while(i!=12 && i!=13 && i!=14 && i!=15){
                  newState = currentState.substring(0, i) + currentState.charAt(i+4)
                          + currentState.substring(i+1, i+4) + "0" + currentState.substring(i+5); //replacement with new state
                  addToOpenList(newState, currentState); //add new state to frontier
                  break;
              }
              
                
             }
            
            }
        if(openList.isEmpty()) {
            System.out.println("No solution found");
        } 
        
    }

    
    private static void addToOpenList(String newState, String oldState) {
        
        if(!stateLevel.containsKey(newState)){   //checking for the repeated states
            
           levelvalue = oldState==null ? 0 : stateLevel.get(oldState)+1;   //compute the level
           
            stateLevel.put(newState, levelvalue);         //enter the level of state into map
            
            openList.add(newState);        //addition to the frontier
            
            stateHistory.put(newState, oldState);    //parent-child addition for the path
         
        }
      
    }

    private static void printSolution(String currentState) {
        System.out.println("");
        System.out.println("Path to be followed");
        System.out.println("");
        
        //to get the path 
        pathList.add(currentState);   //add the states to a path List
        
        String parentState = stateHistory.get(currentState);   //get the parent states of current state
        
        while(parentState != null){ //when reached root node
            
            pathList.add(parentState); //add the states to a path List
             parentState = stateHistory.get(parentState); //get the parent states of parent state
        }
        
        //printing the path
        while(!pathList.isEmpty()){
            String pathState = pathList.removeLast();
            System.out.println(pathState.substring(0, 4));
            System.out.println(pathState.substring(4, 8));
            System.out.println(pathState.substring(8, 12));
            System.out.println(pathState.substring(12, 16));
          
            System.out.println("");
        }
    }
   
  }
    