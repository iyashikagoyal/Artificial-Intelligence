package puzzlea;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Scanner;



public class PuzzleA {

   
    private static String goalState = "123456789ABCDEF0";  //goal state
    private static LinkedList<String> list = new LinkedList();
    private static int depth = 0;
    
    public static void main(String[] args) {
          
        long startTime = System.currentTimeMillis();  //start time of the program
        
        System.out.println("Enter the input in Hexadecimal"); 
        
        //scanning the input. Input should be in hexadecimal.
        Scanner sc = new Scanner(System.in);
        String initialState1 = sc.next();
        String initialState = initialState1.toUpperCase();
        
        searchWithManhattan(initialState);
       
        searchWithMisplacedTiles(initialState);
  
        //chcking for the memory usage
        Runtime runtime = Runtime.getRuntime();
        runtime.gc();    // Run the garbage collector
        long usedmemory = runtime.totalMemory() - runtime.freeMemory();  // Calculate the used memory
        System.out.println("Memory Usage in bytes : " + usedmemory);
        
        
        
        //Time taken
        long stopTime = System.currentTimeMillis();    
        long elapsedTime = stopTime - startTime;    
        System.out.println("");
        System.out.println("Time in milliseconds for Manhattan : " + elapsedTime);
        
     
        
        
        
    }


    private static void searchWithManhattan(String initialState) {
        
     int h = 0, g = 0 , f = 0 ;
     
     LinkedList<String> openList = new LinkedList(); //frontier
     
     LinkedList<String> closedList  = new LinkedList<String>(); //to store explored states( currentState and parent)
    
     LinkedList<String> pathList = new LinkedList(); //list to store the path 
         
         // to compare values as stated in comparator class
        NodeComparator comparator = new NodeComparator();
        PriorityQueue<Node> prq = new PriorityQueue<Node>(4, comparator); // to compare f(x)
        
        h = manhattan(initialState);
        f=g+h ;
        Node initnode = new Node(initialState, g, f );
      
        openList.add(initialState);
        pathList.add(initialState);
        
        while(!openList.isEmpty()){
            
           String cstate =  openList.remove();
        //checking if the current state is goal state
            if(cstate.equals(goalState)){
                printSolution(pathList);
                break;
            }
            else{
             
            successor(cstate);
            g= g+1;
            
            while(!list.isEmpty()){
              String state = list.remove(); //list contains the successor of each state
             
              h = manhattan(state);
              
              
              f = g + h;
              Node node = new Node(state , g, f );
                
              prq.add(node);
                
               }
            
            Node bestnode = prq.poll();
            
            for(int j=0; j<4; j++){
                
            // if node already explored
            if(closedList.contains(bestnode.node)){
              bestnode = prq.poll(); 
               continue;
            }
            else{
              openList.add(bestnode.node);
              depth++;
            closedList.add(bestnode.node);
            pathList.add(bestnode.node);
             prq.clear();
            break;
              
            
               }
           
            }
            
        }
    
     }
    }
    
  
    
    private static void searchWithMisplacedTiles(String initialState) {
        
     int h = 0, g = 0 , f = 0 ;
     
     LinkedList<String> openList = new LinkedList(); //frontier
     String currentState; //current state
     String newState; //new State after branching
    
     LinkedList<String> closedList  = new LinkedList<String>(); //to store explored states( currentState and parent)
     
     LinkedList<String> pathList = new LinkedList(); //list to store the path 
     
        NodeComparator comparator = new NodeComparator();
        PriorityQueue<Node> prq = new PriorityQueue<Node>(4, comparator);
        h = misplacedTiles(initialState);
        f=g+h ;
        Node initnode = new Node(initialState, g, f );
      
        openList.add(initialState);
        pathList.add(initialState);
        
        while(!openList.isEmpty()){
            
           String cstate =  openList.remove();
        //checking if the current state is goal state
            if(cstate.equals(goalState)){
                printSolution(pathList);
                break;
            }
            else{
             
            successor(cstate);
            g= g+1;
            
            while(!list.isEmpty()){
              String state = list.remove();
             
              h = misplacedTiles(state);
              
                f = g + h;
              Node node = new Node(state , g, f );
                
              prq.add(node);
                
               }
            
            
            for(int j=0; j<4; j++){
                               
              Node bestnode = prq.poll();
            

                if(!closedList.contains(bestnode.node)){
              openList.add(bestnode.node);
            closedList.add(bestnode.node);
            pathList.add(bestnode.node);
             prq.clear();
            
            break;
              
            
               }
           
            }
            
        }
    
     }
    
    }

    
     private static int manhattan(String node) {

    int h = 0;
    char x;
    
    for(int i=0;i<node.length();i++){
        char v = node.charAt(i);
        
       x = goalState.charAt(i);
       
       if(v!= x && v!=0){
           int a = goalState.indexOf(v);
           
           int a1 = Math.abs(i/4 - a/4);
           int a2 = Math.abs((i%4) - (a%4));
           h += a1+a2;
       }
    }
    
    return h;
     }
     
     private static int misplacedTiles(String node){
         
                  
          int h = 0;
          
          for(int i = 0; i<15 ; i++){
         if(node.charAt(i) != goalState.charAt(i)){
             h= h+1;
         }
         
          }
              return h;

     }
   
        private static void successor(String currentState) {
        
            int i;
            String newState;
                i = currentState.indexOf("0");  //finding the location of blank = 0
             
              //move left
              while(i!=0 && i!=4 && i!=8 && i!=12){
                  newState = currentState.substring(0, i-1) + "0" + currentState.charAt(i-1) 
                          + currentState.substring(i+1); //replacement with new state
                list.add(newState);
                  break;
              }
              
              //move right
              while(i!=3 && i!=7 && i!=11 && i!=15){
                  newState = currentState.substring(0, i) + currentState.charAt(i+1)+ "0" 
                          + currentState.substring(i+2); //replacement with new state
               list.add(newState);
                  break;
              }
              
              //move up
              while(i!=0 && i!=1 && i!=2 && i!=3){
                  newState = currentState.substring(0, i-4) + "0" + currentState.substring(i-3, i) 
                          + currentState.charAt(i-4) + currentState.substring(i+1); //replacement with new state
                 list.add(newState);
                  break;
              }
              
              //move down
              while(i!=12 && i!=13 && i!=14 && i!=15){
                  newState = currentState.substring(0, i) + currentState.charAt(i+4)
                          + currentState.substring(i+1, i+4) + "0" + currentState.substring(i+5); //replacement with new state
                list.add(newState);
                  break;
              }
              
                
             }
     
    

    private static void printSolution(LinkedList<String> pathList) {
        System.out.println("");
        System.out.println("Path to be followed");
        System.out.println("");
        
        
        //printing the path
        while(!pathList.isEmpty()){
            String pathState = pathList.remove();
            System.out.println(pathState.substring(0, 4));
            System.out.println(pathState.substring(4, 8));
            System.out.println(pathState.substring(8, 12));
            System.out.println(pathState.substring(12, 16));
          
            System.out.println("");
        }
    }

  }
    
 class Node{

     String node ; 
     int f,g ;
     
     
     public Node(String node, int g,int f){
         
         this.node = node;
         this.f= f;
         this.g = g;
     }
     
}

class NodeComparator implements Comparator<Node> {

    @Override
    public int compare(Node o1, Node o2) {
        if(o1.f== o2.f){
            return o2.g - o1.g;
        }
        else 
    return o1.f - o2.f ;
    
    }
    
}