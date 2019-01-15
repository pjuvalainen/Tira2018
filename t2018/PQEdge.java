package t2018;
/**
 * Tietorakenteet 2018 -harjoitustyö.
 * Tämä luokka on prioriteettijonoon kuuluvia
 * graafin solmujen välisien kaaria.
 */
import java.util.Objects;

/**
 * @author Petteri Juvalainen
 * petteri.juvalainen@tuni.fi
 * opiskelijanumero 427130
 */
class PQEdge implements Comparable<PQEdge> {
   /**
    * Prioriteetikaarisolmu -luokan atribuutit.
    */
   
   // Kaaren paino
   private float distance;
   
   // Lähtösolmu
   private int source;
   
   // Tulosolmu
   private int destination;
   
   /** Rakentaja */
   public PQEdge(float f, int s, int d) {
      setDistance(f);
      setSource(s);
      setDestination(d);
   }
   
   /** 
    * Aksessorit 
    */
   
    public int getSource() {
        return source;
    }

    public void setSource(int s) {
        source = s;
    }
    
    public int getDestination() {
        return destination;
    }

    public void setDestination(int d) {
        destination = d;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float d) {
        distance = d;
    }
    
   /**
    * Metodit
    */
    
   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      PQEdge GraphNode = (PQEdge) o;
      return Float.compare(GraphNode.distance, distance) == 0;
   }
    
   // Verrataan solmuja.
   @Override
   public int compareTo(PQEdge PQEdge) {
      if(getDistance() > PQEdge.getDistance())
         return 1;
      
      else if (getDistance() < PQEdge.getDistance())
         return -1;
      
      else return 0;
    }    

   @Override
   public int hashCode() {
      return Objects.hash(source, destination, distance);
   }

   @Override
   public String toString() {
      return "S Id" + source +  " D Id" + destination + " distance " + distance;
   }
}
