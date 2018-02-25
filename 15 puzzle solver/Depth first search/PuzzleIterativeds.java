
import java.util.LinkedList;
import java.util.Scanner;

public class PuzzleIterativeds {

   


    private static String goalState = "123456789ABCDEF0";  //goal state
    
 
    
    private static String currentState; //current state
    private static String newState; //new State after branching
    
    private static int cutoff = 0; //To check if the solution exists in the depth
    private static int i; //To store index of blank
    private static int depth = 0; //To store the depth of the search
   
   
    private static  LinkedList<String> pathList = new LinkedList(); //list to store the path 
    
    
    
    
    public static void main(String[] args) {
          
        long startTime = System.currentTimeMillis();  //start time of the program
        
        System.out.println("Enter the input in Hexadecimal"); 
        
        //scanning the input. Input should be in hexadecimal.
        Scanner sc = new Scanner(System.in);
        String initialState = sc.next();
       
      int totaldepth = 0; //to store the depth of the solution
      
      //Run the depth search on initial state with increasing depths until you reach defined depthlimit or find a solution.
      while(cutoff == 0){
          long startTimevsdepth = System.currentTimeMillis();  //start time of the program while in depth
         totaldepth = depth;
        
        String result = search(initialState,depth);
        depth++;
        
        long stopTimevsdepth = System.currentTimeMillis();    
        long elapsedTime1 = stopTimevsdepth - startTimevsdepth;    
        System.out.println("");
        System.out.println("Time to run in depth "+ (depth-1) + " is " + elapsedTime1);
      }
      
      //print the solution
      if(cutoff==1){
          printSolution();
          System.out.println("The depth of the solution : " + totaldepth);
      }
      else {
           System.out.println("Failure! Can not find the solution in this depthlimit");
      }
        
        //checking for the memory usage
        Runtime runtime = Runtime.getRuntime();
        runtime.gc();    // Run the garbage collector
        long usedmemory = runtime.totalMemory() - runtime.freeMemory();  // Calculate the used memory
        System.out.println("Memory Usage in bytes : " + usedmemory);
        
        //Total Time taken
        long stopTime = System.currentTimeMillis();    
        long elapsedTime = stopTime - startTime;    
        System.out.println("");
        System.out.println("Total Time to run IDS in milliseconds : " + elapsedTime);
    }


    private static String search(String currentState, int depth) {
 
        
        //checking if the current state is goal state
            if(currentState.equals(goalState)){
               cutoff = 1;
               pathList.add(currentState);
               return currentState;
              }
            
            //failure after depthlimit =100
            else if (depth==100){
                return "failure";
            }
            
            //to stop the explore at the depth
            else if(depth==0){
                return "cutoff";
            }
            
            
            else{
              i = currentState.indexOf("0");  //finding the location of blank = 0
             
              //move left
              while(i!=0 && i!=4 && i!=8 && i!=12){
                  newState = currentState.substring(0, i-1) + "0" + currentState.charAt(i-1) 
                          + currentState.substring(i+1); //replacement with new state
               
               //Recursive method search   
               String solution = search(newState,depth-1);
               
                if(solution.equals("cutoff")){
                    cutoff = 0;
                }
                else if(!solution.equals("failure")){
                    pathList.add(currentState);
                    return solution;
                } 
                
                break;
              }
              
              //move right 
              i = currentState.indexOf("0");  //finding the location of blank = 0 (calculated everytime)
             
              while(i!=3 && i!=7 && i!=11 && i!=15){
                   newState = currentState.substring(0, i) + currentState.charAt(i+1)+ "0"
                          + currentState.substring(i+2); //replacement with new state
                
                String solution = search(newState,depth-1);
                
                if(solution.equals("cutoff")){
                    cutoff = 0;
                }
                
                else if(!solution.equals("failure"))
                {
                    pathList.add(currentState);
                    
                    return solution;
                }
                
                break;
              }
              
              
              //move up
              
              i = currentState.indexOf("0");  //finding the location of blank = 0
              
              while(i!=0 && i!=1 && i!=2 && i!=3){
                  newState = currentState.substring(0, i-4) + "0" 
                          + currentState.substring(i-3, i) + currentState.charAt(i-4) + currentState.substring(i+1); //replacement with new state
              
                  String solution = search(newState,depth-1);
                  
                if(solution.equals("cutoff")){
                    cutoff = 0;
                }
                
                else if(!solution.equals("failure"))
                {
                    pathList.add(currentState);
                    
                    return solution;
                }
                
                break;
              }
              
               //move down
              i = currentState.indexOf("0");  //finding the location of blank = 0
             
              while(i!=12 && i!=13 && i!=14 && i!=15){
                  newState = currentState.substring(0, i) + currentState.charAt(i+4)
                          + currentState.substring(i+1, i+4) + "0" + currentState.substring(i+5); //replacement with new state
                          
                 
               String solution = search(newState,depth-1);
               
               if(solution.equals("cutoff")){
                    cutoff = 0;
                }
               
                else if(!solution.equals("failure"))
                {
                    pathList.add(currentState);
                    
                    return solution;
                }  
               
                break;
              }
              
            
            }

           
            if(cutoff == 0) {
                return "cutoff" ;
            }
            else
                return "failure";

   }

    
     //printing the solution
       private static void printSolution() {
        System.out.println("");
        System.out.println("Path to be followed");
        System.out.println("");
       
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
    