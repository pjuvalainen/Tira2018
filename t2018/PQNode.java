package t2018;
/**
 * Tietorakenteet 2018 -harjoitustyö.
 * Tämä luokka on prioriteettijonoon kuuluvia solmuja.
 */
import java.util.Objects;

/**
 * @author Petteri Juvalainen
 * petteri.juvalainen@tuni.fi
 * opiskelijanumero 427130
 */
class PQNode implements Comparable<PQNode > {
   /**
    * Prioriteetisolmu -luokan atribuutit.
    */
   
   // Etäisyys solmusta toiseen.
   private float distance;
   
   // Tunniste.
   private int id;
   
   /** Rakentaja */
   public PQNode (float f, int i) {
        setDistance(f);
        setId(i);
   }

   /** 
    * Aksessorit 
    */
   
   public int getId() {
      return id;
   }

   public void setId(int i) {
      id = i;
   }

   public float getDistance() {
      return distance;
   }

   public void setDistance(float d) {
      distance = d;
   }
   
   /****
    * Metodit.
    * 
    */
   
   // Verrataan solmuja.
   @Override
   public boolean equals(Object o) {
      if (this == o) 
         return true;
   
      if (o == null || getClass() != o.getClass()) 
         return false;
  
      PQNode  GraphNode = (PQNode ) o;
      return Float.compare(GraphNode.distance, distance) == 
              0 && Objects.equals(id, GraphNode.id);
   }
    
   // Verrataan solmuja.
   @Override
   public int compareTo(PQNode  PQNode ) {
      if(getDistance() > PQNode .getDistance())
         return 1;
      
      else if (getDistance() < PQNode .getDistance())
         return -1;
      
      else return 0;
   }

   @Override
   public int hashCode() {
      return Objects.hash(id, distance);
   }

   @Override
   public String toString() {
      return "Id" + id + " distance " + distance;
   }
}