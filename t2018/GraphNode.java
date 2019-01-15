package t2018;
/**
 * Tietorakenteet 2018 -harjoitustyö.
 * Tämä luokka on omaan linkitettyyn listaan kuuluvia solmuja.
 */
import java.util.PriorityQueue;

/**
 * @author Petteri Juvalainen
 * petteri.juvalainen@tuni.fi
 * opiskelijanumero 427130
 */
@SuppressWarnings("unchecked")
public class GraphNode {   
   /**
    * Solmu luokan atribuutit.
    */
   
   // Sijainti.
   private float x;   
   private float y;
   
   // Lähimmät solmut.
   private GraphNode neighbours[];
   
   // Solmujen määrä säiliössä.
   private int GraphNodeNumber;
   
   // Lähtö ja tulosolmut.
   private int in, out = 0;
      
   // Totuusarvo läpikäyntiä varten.
   private boolean visited;
   
   // Seuraava solmu.
   private GraphNode next;
   
   // Yksilöivä solmun tunniste.
   private int id = 1001;
   
   // Solmun etäisyys toisiin solmuihin järjestettynä pienimmistä suurimpaan.
   private PriorityQueue<PQNode> closestGraphNodes;
   
   /**
    * Solmu luokan rakentaja.
    */
   public GraphNode(float a, float b, int i) {
      setX(a);
      setY(b);
      setId(i);
      visited = false;
      next = null;
      GraphNodeNumber = 0;
      neighbours = new GraphNode[1];
      PriorityQueue<PQNode> closestGraphNodes = new PriorityQueue<>();
   }
   
   /**
    * Aksessorit.
    */
    
   public void setClosestGraphNodes(PriorityQueue pq) {
      closestGraphNodes = pq;
   }   
    
    public PriorityQueue getClosestGraphNodes() {
      return closestGraphNodes;
   }
    
   public PQNode getClosestGraphNode() {
      return closestGraphNodes.remove();
    }   
   
   public void setX(float a) {
      x = a;
   }

   private void setY(float b) {
      y = b;
   }
   
   public void setVisited(boolean value) {
      visited = value;
   }
   
   public boolean getVisited() {
      return visited;
   }

   public void setId(int i) {
      id +=i;
      }
   
   public float getX() {
      return x;
   }
   
   public float getY() {
      return y;
   }
    
   public int getId() {
      return id;
   }
   
   public void setNext(GraphNode n) {
      next = n;
   }

   public GraphNode getNext() { 
      return next; 
   }
   
   public void setInDegrees() {
      in++;
   }
   
   public  void setOutDegrees() {
      out++;
   }
   
   public int getInDegrees() {
      return in;
   }
   
   public int getOutDegrees() {
      return out;
   }
         
   public void setNeighbour(int i, GraphNode n) {
      neighbours[i] = n;
   }
   
   public GraphNode getNeighbour(int i) {
      if(neighbours[i] != null)
         return neighbours[i];
      else
        return null;
   }
   
   public int getNLength() {
      return neighbours.length;
   }
      
   public int getNeighbourId(int i) {
       if(neighbours[i] != null)
        return neighbours[i].getId();
      else
        return 0;
   }
   /**
    * Metodit
    */
   
   /** Poistetaan solmun asteet, naapurit ja etäisyydet toisiin solmuihin. */
   public void reset() {
      in = out = GraphNodeNumber = 0;
      closestGraphNodes = null;
      GraphNode empty[] = new GraphNode[1];
      neighbours = empty;
   }
   
   /** Lisätään naapuri ja lisätään taulukon kokoa yhdellä. */
   public void insertNeighbour(GraphNode n) {
      if(neighbours.length == GraphNodeNumber){
         GraphNode[] neighboursCopy = new GraphNode[neighbours.length+1];
         
         System.arraycopy(neighbours, 0, neighboursCopy, 0, neighbours.length);
            neighbours = neighboursCopy;
      }
      
      if (GraphNodeNumber < neighbours.length)
         neighbours[GraphNodeNumber] = n;
         
      GraphNodeNumber++;
   }
   
   /** Tulostetaan naapurit. */
   public void printNeighbours() {
      System.out.print(" {");
      for (GraphNode neighbour : neighbours) {
         if (neighbour != null) {
            System.out.print(" " + neighbour.getId());
         } else { 
            System.out.print(" no neighbours");
         }
      }
      System.out.print(" }");
   }
   
   /** Tulostetaan asteet. */
   public void printDegrees() {
      System.out.println("ID" + id + " Degrees" +" Out: "+out + " In: " + in );
   }
     
   @Override
   public String toString() {
        return "ID"+id ;
    }   
}
