package t2018;
/**
 * Tietorakenteet 2018 -harjoitustyö.
 * Tähän luokkaan on koottu kaikki ohjelmassa tarvittavat operaatiot.
 */
import java.util.*;
import java.io.*;

/**
 * @author Petteri Juvalainen
 * petteri.juvalainen@tuni.fi
 * opiskelijanumero 427130
 */ 
@SuppressWarnings("unchecked")
public class Operations {
   
   // Tiedostosta luettava rivimäärä.
   private static final int READSIZE = 500;
      
   // Laskenta-alue.
   private static float outliersPointP = 0;
   private static float outliersPointN = 0;
   
   // Tiedoston nimi tulosteisiin.
   private static String fileName;
   
   // Ei tulostetan näytölle tekstejä.
   private static boolean noPrint = false;
   
   // Asetus, jolla ohjataan laskentaa.
   private static boolean set = false;
   
    /** 
     * Luetaan  tiedosto, josta samalla muodostetaan listalle solmuja. *
     */
   public static void readInput(GraphNodeLinkedList l){
      if(!noPrint)
         System.out.println("Reading file...");
      
      try {         
         String line;
         float x;
         float y;
         
         BufferedReader br = new BufferedReader( new FileReader("Tdata.txt"));

         for(int i=0; i < READSIZE; i++) {
           line = br.readLine();
           
           if(line != null) {
              String[] values=line.split(",");	
              x = Float.parseFloat(values[0]);	
              y = Float.parseFloat(values[1]);
              
              if(outliersPointP < x || outliersPointP < y || outliersPointN > x || outliersPointN > y) {
                 l.add(x,y,i);
                 l.addSize();
               }
            }
         }
         if(!noPrint)
            System.out.println("GraphNodes created.");
      } 
      catch(IOException e) {
         System.out.println("File not found.");
      }
   }
   
   /** 
    * Operaatio, jolla lasketaan lähimmät naapurit solmulle. 
    */
   public static void findNeighbours(GraphNodeLinkedList l) {
      if(!noPrint)
         System.out.println("Finding neighbours...");
      
      if(!l.isEmpty()) {    
         GraphNode crr = l.getHead();
         
         while(crr != null) {
            float x1 = crr.getX();
            float y1 = crr.getY();
            GraphNode tmp = l.getHead();
            PriorityQueue<PQNode> closestGraphNodes = new PriorityQueue<>();
            
            while(tmp != null) {
               if(crr.getId() != tmp.getId()){
                  float x2 = tmp.getX();
                  float y2 = tmp.getY();
                  float distance = calculateD(x1, y1, x2, y2);
                  closestGraphNodes.add(new PQNode(distance, tmp.getId()));
                  crr.setClosestGraphNodes(closestGraphNodes);  
               }
               tmp = tmp.getNext();
            }
            crr = crr.getNext();
         }
         l.setCalculated(true);
         
         if(!noPrint)
            System.out.println("Neighbours founded.");
      }
      else errorListEmpty();
   }
   
   /** 
    * Operaatio jolla lisätään naapureita. 
    */
   public static void addNeighbours(GraphNodeLinkedList l) {
      if(!noPrint)
         System.out.println("Adding neighbours for " + l.getnCount() + " time(s).");
      
      if(l.getCalculated()) {
         if(1 < l.getSize()){
            GraphNode crr = l.getHead();
            PQNode first;
            
            while(crr != null) {
               GraphNode  tmp = l.getHead();
               first = crr.getClosestGraphNode();
               
               while(tmp != null) {
                  if(first.getId() == tmp.getId()){
                     
                     for( int i = 0; i < crr.getNLength(); i++ ) {
                        if(crr.getNeighbourId(i) ==  tmp.getId()){
                           set = true;
                           crr.setOutDegrees();
                        }
                     }
                     
                     if(!set) {
                        crr.insertNeighbour(tmp);
                        crr.setOutDegrees();
                        tmp.insertNeighbour(crr);
                        tmp.setInDegrees();
                     }
                  }
                  tmp = tmp.getNext();
               }
               set = false;
               crr = crr.getNext();
            }
            l.setnCount();
          }
          else System.out.println("Error, Only on GraphNode, "
                  + "cannot add Neighbours.");
      }
       else System.out.println("Error, Neighbours not calculated.");
   }
   
   /** 
    * Operaatio, joka kirjottaa näytölle saadun listan solmuista 
    * lähtö- ja tuloasteet.
    */
   public static void printDegrees(GraphNodeLinkedList l) {
      if(!l.isEmpty()) {   
         System.out.println("Degrees:");
         GraphNode crr = l.getHead();
         
         while(crr != null) {
            crr.printDegrees();
            crr = crr.getNext();
         }
      }
      else errorListEmpty();
   }
   
   /** 
    * Operaatio, joka kirjottaa tiedostoon saadun listan 
    * solmuista lähtö- ja tuloasteet.
    */
   public static void writeDegrees(GraphNodeLinkedList l) {
      try {
         BufferedWriter degrees 
                 = new BufferedWriter(new FileWriter("Degrees.txt"));          
         GraphNode crr = l.getHead();
                  
         while(crr != null) {
            degrees.write("ID");
            degrees.write(Integer.toString(crr.getId()));
            degrees.write(" Out: ");
            degrees.write(Integer.toString(crr.getOutDegrees()));
            degrees.write(" In: ");
            degrees.write(Integer.toString(crr.getInDegrees()));
            degrees.newLine();
            crr = crr.getNext();
         }
         degrees.close();
         System.out.println("Degrees file created.");
      }
      catch (IOException e) { System.err.format("IOException: %s%n", e);}
   }
   
   /** 
    * Graafin solmujen ja naapuruston tulostaminen näytölle.
    */
   public static void printGraphNodes(GraphNodeLinkedList l) {
      System.out.println("GraphNodes on list:");
      
      if(!l.isEmpty()) {
         GraphNode crr = l.getHead();
         
         while(crr != null) {
            System.out.print(crr);
            crr.printNeighbours();
            System.out.println();
            crr = crr.getNext();
         }
      }
      else errorListEmpty();
   }
   
   /** Aputemodi, jolla lasketaan Euklidista etäisyyttä. */
   private static float calculateD(float x1, float y1, float x2, float y2) {      
      double d = Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));            
      return (float)d;
   }
   
   /** 
    * Operaatio, joka kirjoittaa näytölle saamansa listan solmujen naapurit leveyshaun suorittamassa järjestyksessä. 
    * Operaatiossa käytetään Javan omaa linkitettyä lista -rakennetta hyödyksi.
    */
   public static void printBFS(GraphNodeLinkedList l) {
      if(1 < l.getnCount()) {
         System.out.println("BFS printed:");
         
         if(!l.isEmpty()) {   
            GraphNode crr = l.getHead();
            
             while(crr != null) {       
               Queue<GraphNode> queue = new LinkedList<>();
               
               if (!crr.getVisited()) {
                  crr.setVisited(true);            
                  BFS(queue, crr);
                  System.out.print("x");
                  System.out.println();
               }
               crr = crr.getNext();
            }
            resetVisited(l);
         }
         else errorListEmpty();
      }
      else errorNeighbours();
   }
   
   /** 
    * Leveyshaku operaation apumetodi, joka tulostaa solmun, 
    * merkitsee solmut löydetyiksi, ja lisää ne linkitettyyn listaan. 
    */
   public static void BFS(Queue q, GraphNode n) {           
      System.out.print(n);
      System.out.print("->");
      
      for( int i = 0; i < n.getNLength(); i++ ) {
         GraphNode tmp = n.getNeighbour(i);

         if(!tmp.getVisited()) {
            tmp.setVisited(true);
            n.setNeighbour(i, tmp);
            q.add(tmp);
         }
      }

      if (!q.isEmpty()){
         BFS(q, (GraphNode)q.remove());
      }
   }
   
   /** 
   * Operaatio, joka kirjoittaa tiedostoon saamansa listan solmujen naapurit 
   * leveyshaun suorittamassa järjestyksessä. 
   * Operaatiossa käytetään Javan omaa linkitettyä lista -rakennetta hyödyksi.
   */
   public static void writeBFS(GraphNodeLinkedList l) {
      if(1 < l.getnCount()) {
         try {
            BufferedWriter bfs;
            
            if(fileName != null)
               bfs = new BufferedWriter(new FileWriter(fileName));
            else
               bfs = new BufferedWriter(new FileWriter("BFS.txt"));
            
            GraphNode crr = l.getHead();
            
            while(crr != null) {       
               Queue<GraphNode> queue = new LinkedList<>();
               
               if (!crr.getVisited()) {
                  crr.setVisited(true);            
                  BFSwriter(queue, crr, bfs);
                  bfs.write("x");
                  bfs.newLine();
               }
               crr = crr.getNext();
            }
            bfs.close();
            System.out.println("BFS file created.");
            resetVisited(l);
            
         }
         catch (IOException e) {System.err.format("IOException: %s%n", e);}
      }
      else errorNeighbours();
   }
   
   /** 
    * Leveyshaku operaation apumetodi, joka tulostaa solmun, merkitsee 
    * solmut löydetyiksi, sekä lisää ne linkitettyyn listaan ja lopuksi 
    * kutsuu itseään
    */
   public static void BFSwriter(Queue q, GraphNode n, BufferedWriter bw) {
      try {       
         bw.write(Float.toString(n.getX()));
         bw.write(",");
         bw.write(Float.toString(n.getY()));
         bw.write(" -> ");
         
         for( int i = 0; i < n.getNLength(); i++ ) {
            GraphNode tmp = n.getNeighbour(i);

            if(!tmp.getVisited()) {
               tmp.setVisited(true);
               n.setNeighbour(i, tmp);
               q.add(tmp);
            }
         }
         if (!q.isEmpty()){
            BFSwriter(q, (GraphNode)q.remove(), bw);
         }
      }
      catch (IOException e) {System.err.format("IOException: %s%n", e);}
   }
   
   /** 
    * Operaatio, joka tulostaa näytölle saamansa listan solmujen naapurit 
    * syvyyshaun suorittamassa järjestyksessä. 
    */   
   public static void printDFS(GraphNodeLinkedList l) {
      if(1 < l.getnCount()) {
         System.out.println("DFS printed:");
         
            if(!l.isEmpty()) {      
             GraphNode crr = l.getHead();
               
             while(crr != null) {
               if(!crr.getVisited()){
                  crr.setVisited(true); 
                  DFS(crr);
                  System.out.print("x");
                  System.out.println();
                }
            crr = crr.getNext();
            }
            resetVisited(l);
         }
         else errorListEmpty();
      }
      else errorNeighbours();
   }
   
   /** 
    * Syvyyshaku operaation apumetodi, 
    * joka tulostaa solmun, merkitsee solmut löydetyiksi ja lopuksi 
    * kutsuu itseään.
    */
   public static void DFS(GraphNode n) {
      System.out.print(n);
      System.out.print("->");
      
      for( int i = 0; i < n.getNLength(); i++ ) {
         GraphNode tmp = n.getNeighbour(i);
         if(!tmp.getVisited()) {
            tmp.setVisited(true);
            n.setNeighbour(i, tmp);
            DFS(n.getNeighbour(i));
         }
      }
   }
   
   /** 
    * Operaatio, joka kirjoittaa tiedostoon saamansa listan solmujen naapurit 
    * syvyyshaun suorittamassa järjestyksessä. 
    */   
   public static void writeDFS(GraphNodeLinkedList l) {
      if(1 < l.getnCount()) {
         try {
            BufferedWriter dfs;
               
            if(fileName != null)
               dfs = new BufferedWriter(new FileWriter(fileName));
            else
               dfs = new BufferedWriter(new FileWriter("DFS.txt"));
         
            GraphNode crr = l.getHead();
               
            while(crr != null) {
               if(!crr.getVisited()){
                  crr.setVisited(true); 
                  DFS(crr, dfs);
                  dfs.write("x");
                  dfs.newLine();
                }
               crr = crr.getNext();
            }
            dfs.close();
            System.out.println("DFS file created.");
            resetVisited(l);
         }
          catch (IOException e) {System.err.format("IOException: %s%n", e);}
      }
      else errorNeighbours();
   }
   
   /** 
    * Syvyyshaku operaation apumetodi, joka tulostaa solmun, 
    * merkitsee solmut löydetyiksi ja lopuksi kutsuu itseään. 
    */
   public static void DFS(GraphNode n, BufferedWriter bw) {
      try {
         bw.write(Float.toString(n.getX()));
         bw.write(",");
         bw.write(Float.toString(n.getY()));
         bw.write(" -> ");
         
         for( int i = 0; i < n.getNLength(); i++ ) {
            GraphNode tmp = n.getNeighbour(i);
            if(!tmp.getVisited()) {
               tmp.setVisited(true);
               n.setNeighbour(i, tmp);
               DFS(n.getNeighbour(i), bw);
            }
         }
      }
      catch (IOException e) {System.err.format("IOException: %s%n", e);}
   }
   
   /** Poistetaan solmu listalta */
   public static void remove(GraphNodeLinkedList l, int n) {
      System.out.println("Removing GraphNode...");
      
      if(!l.isEmpty()) {    
         GraphNode first = l.getHead();
         GraphNode last = l.getTail();
         
         if(first.getId() <= n && n <= last.getId()){
            if(0 < l.getSize()) {
               GraphNode remove = l.remove(n);
               
               if(remove != null) {
                  System.out.println("Removed GraphNode: " + remove);
                  l.lessSize();
               }
               else
                  System.out.println("GraphNode ID" + n + " not found.");
               
               if(1 <  l.getSize()) {
                 noPrint = true;
                 resetGraphNodes(l);                 
                 findNeighbours(l);

                 for (int i = 0; i < l.getnCount(); i++) {
                     addNeighbours(l);
                     l.decnCount();
                 }
                 noPrint = false;
               }
               
               else if (0 == l.getSize())
                  System.out.println("List is empty.");
            }
         }
         else System.out.println("ID" + n + " number out of range.");
      }
      else errorListEmpty();
   }

   /** Apumetodi, jolla alustetaan solmussa käyty -tieto. */
   public static void resetVisited(GraphNodeLinkedList l) {
      if(!l.isEmpty()) {       
          GraphNode crr = l.getHead();
            
          while(crr != null) {
            crr.setVisited(false);
            crr = crr.getNext();
         }
      }
      else errorListEmpty();
   }
   
   /** Alustetaan solmujen asteet, naapurit ja etäisyyden toisiin solmuihin. */
   public static void resetGraphNodes(GraphNodeLinkedList l) {
      if(!noPrint)
         System.out.println("Resetting GraphNodes...");
      
      if(!l.isEmpty()) {      
          GraphNode crr = l.getHead();
            
          while(crr != null) {
            crr.reset();
            crr = crr.getNext();
         }
         if(!noPrint)
            System.out.println("Reset Successfully.");
      }
      else errorListEmpty();
   }
   
   /**
    * Tulostetaan tiedostoon graafi, josta on poisettu solmuja.
    * Tämä muuttaa vain tulostettavan tiedoston nimen.
    */
   public static void writeRemaining(GraphNodeLinkedList l) {
      noPrint = true;
      fileName = "DIM.txt";         
      writeBFS(l);
      fileName = null;
      noPrint = false;
   }

   /** 
    * Lisätään solmuille naapureita, kunnes jokaisesta solmusta 
    * pääsee jokaiseen solmuun. 
    */
   public static void makeGraphGreatAgain(GraphNodeLinkedList l) {
      if(l != null) {
         System.out.println("Completing Graph...");
         noPrint = true;
         readInput(l);
         
         if(!l.isEmpty()) {
            if(!l.getCalculated()){
               findNeighbours(l);
               noPrint = false;
               addNeighbours(l);
            }

            GraphNode crr = l.getHead();
            traversalDFS(crr);

            while(!checkTraversal(crr)){
               resetVisited(l);
               addNeighbours(l);
               traversalDFS(crr);
            }
            System.out.println("Graph complete, neighbours were added " + l.getnCount() + " times.");
            resetVisited(l);
            fileName = "COMP.txt";
            writeBFS(l);
            fileName = null;
         }
         else errorListEmpty();
      }
      else error();
   }
   
   /** Apumetodi, jolla käydään graafi läpi syvyyssuunnassa. */
   public static void traversalDFS(GraphNode n) {
      for( int i = 0; i < n.getNLength(); i++ ) {
         GraphNode tmp = n.getNeighbour(i);
         
         if(!tmp.getVisited()) {
            tmp.setVisited(true);
            n.setNeighbour(i, tmp);
            traversalDFS(n.getNeighbour(i));
         }
      }
   }
   
   /** Apumetodi, jolla tarkistetaan, onko solmu käyty läpi. */
   private static boolean checkTraversal(GraphNode n) {
       while(n != null) {
         if(!n.getVisited())
            return false;
            
         n = n.getNext();
      }    
      return true;
   }
   
    /** 
     * Operaatio, joka luo minimivirittävän puun. 
     */
   public static void createSpanningTree(GraphNodeLinkedList l) {
      System.out.println("Creating spanning tree...");
         
      if(l != null) {
         noPrint = true;
         readInput(l);
         GraphNode crr = l.getHead();
         PriorityQueue<PQEdge> edges = new PriorityQueue<>();
         
         while(crr != null) {
            float x1 = crr.getX();
            float y1 = crr.getY();
            GraphNode tmp = l.getHead();
            
            
            while(tmp != null) {
               if(crr.getId() != tmp.getId()){
                  float x2 = tmp.getX();
                  float y2 = tmp.getY();
                  float distance = calculateD(x1, y1, x2, y2);
                  edges.add(new PQEdge(distance, crr.getId(), tmp.getId()));        
               }
               tmp = tmp.getNext();
            }
            crr = crr.getNext();
         }
         l.setCalculated(true);         
         addEdges(l, edges);
         l.setnCount();
         fileName = "MSP.txt"; 
         writeBFS(l);
         noPrint = false;

         if(!noPrint)
            System.out.println("Spanning tree complete.");
      }
      else errorListEmpty();
   }
   
   /** Apumetodi, joka lisää solmujen välille kaaren. */
   private static void addEdges(GraphNodeLinkedList l, PriorityQueue e) {
      boolean cycle;
      int addedEdges =  0;
      
      if(l.getCalculated()) {
         while( addedEdges < l.getSize() && !e.isEmpty()){
            PQEdge GraphNode = (PQEdge)e.remove();            
            GraphNode source = l.findGraphNode(GraphNode.getSource());
            GraphNode destination = l.findGraphNode(GraphNode.getDestination());
            
            for( int i = 0; i < source.getNLength(); i++ ) {
               if(source.getNeighbourId(i) ==  destination.getId()){
                  set = true;
               }
            }
            
            if(!set){               
               cycle = checkCycles(source, destination);
               
               if(!cycle){
                  source.insertNeighbour(destination);
                  destination.insertNeighbour(source);
                  addedEdges++;
               }
            }
            resetVisited(l);
            set = false;
         }
      }
      else error();
   }
   
   /** Apumetodi, jolla tarkistetaan, että graafiin ei tule silmukoita. */
   public static boolean checkCycles(GraphNode s, GraphNode d) {
      if(s.getNeighbour(0) != null) {   
          for( int i = 0; i < s.getNLength(); i++ ) {
            GraphNode tmp = s.getNeighbour(i);
            
            if(!tmp.getVisited()) {
               tmp.setVisited(true);
               s.setNeighbour(i, tmp);
               checkCycles(s.getNeighbour(i), d);
            }
         }
         return d.getVisited();
      }
      else 
         return false;
   }
   
   /** 
    * Luodaan tiedosto pistejoukon reunalla olevista solmuista. 
    */
   public static void createOutliers(GraphNodeLinkedList l) {
      if(l != null) {
         outliersPointP = 2.2f;
         outliersPointN = -outliersPointP;
         System.out.println("Creating outliers file, cutting area: " + outliersPointN +", " + outliersPointP  + ".");
         noPrint = true;
         readInput(l);
         findNeighbours(l);
         addNeighbours(l);
         fileName = "OUTLIERS.txt"; 
         writeBFS(l);
         fileName = null;
         outliersPointN = outliersPointP = 0;
         noPrint = false;
      }
      else error();
   }
         
   /** Testi tulostus. */
   public static void test() {
      System.out.println("test");
   }
   
   /** Virhe ilmoituksen tulostaminen. */
   public static void error() {
      System.out.println("-error");
   }
   
   // Virhe, naapureita ei lisätty.
   private static void errorNeighbours() {
      System.out.println("-Error, neighbours not added.");
   }
   
   // Virhe, lista tyhjä.
   private static void errorListEmpty() {
      System.out.println("-Error, LinkedList empty.");
   }
}
