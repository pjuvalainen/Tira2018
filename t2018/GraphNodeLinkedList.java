package t2018;
/**
 * Tietorakenteet 2018 -harjoitustyö.
 * Tämä luokka on oma linkitettylista -rakenne.
 */

/**
 * @author Petteri Juvalainen
 * petteri.juvalainen@tuni.fi
 * opiskelijanumero 427130
 */
 public class GraphNodeLinkedList {
   /**
    * Lista -luokan atribuutit.
    */
     
   // Listan pää
   private GraphNode head;
  
   // Listan häntä
   private GraphNode tail;
   
   // Listan koko.
   private int listSize = 0;
   
   // Lähimmät naapurit laskettu.
   private boolean calculated = false;
   
   // Naapureita lisätään n kertaa.
   private int nCount = 1;
   
   /**  Rakentaja */
   public GraphNodeLinkedList() {
      head = tail = null;
   }
     
   /** 
    * Aksessorit 
    */
   
   public GraphNode getHead() {
      return head;
   }
   
   public GraphNode getTail() {
      return tail;
   }
       
   public int getSize() {
      return listSize;
   }
   
   public int getnCount() {
      return nCount;
   }
   
   public boolean getCalculated() {
      return calculated;
   }
   
   public void setCalculated(boolean v) {
      calculated = v;
   }

   /****
    * Metodit.
    * 
    */
   
   /* Naapureita lisätty kertaa. */
   public void setnCount() {
        nCount++;
    }
    
   /* Vähennetään naapureita lisätty summaa. */
   public void decnCount() {
        nCount--;
    }
    
  
   /** Lisätään uusi solmu listan perään */
   public void add(float a, float b, int i) {
      GraphNode n = new GraphNode(a, b, i);
      if (isEmpty())
              head = tail = n;
      else {
         tail.setNext(n);
         tail = n;
      }
   }   
    
    /** Listan koon kasvattaminen. */
    public void addSize() {
      listSize++;
    }
    
    /** Listan koon pienentäminen. */
    public void lessSize() {
      listSize--;
    }
   
   /** Tarkistetaan, onko lista tyhjä */
    public boolean isEmpty() {
      return head == null;
   }
   
    /** Etsitään löytyykö solmua. */
   public GraphNode findGraphNode(int n) {
      GraphNode crr = head;

      while(crr != null) {
         if (crr.getId() == n)
            return crr;
         crr = crr.getNext();
      }
      return null;
   }
   
   /** Etsitään edellinen solmu */
   private GraphNode findPrevGraphNode(GraphNode f) {
      GraphNode crr = head;
      GraphNode prev = null;
      
      while(crr != null) {
         if(crr.getId() == f.getId())
            return prev;
            
         prev = crr;         
         crr = crr.getNext();
      }
      return prev;
   }
   
   /** Poistetaan solmu */
   public GraphNode remove(int n) {
      if(isEmpty())
        return null;
        
      else {
         GraphNode remove = findGraphNode(n);
         
         if(remove!= null) {
            if(head.getId() == remove.getId()) {
               return removeHead();
            }
            
            else if (tail.getId() == remove.getId()) {
               return removeTail();
            }
            
            else if(remove != null) {
               GraphNode prev = findPrevGraphNode(remove);
               prev.setNext(remove.getNext());
               return remove;
            }
            else return null;
         }
         else return null;
      }
   }
   
   /** Poistetaan pää */
   private GraphNode removeHead() {
      if(isEmpty())
        return null;
        
      else if (listSize == 1) {
         GraphNode oldHead = head;
         head = tail = null;
         return oldHead;
       }

      else {
         GraphNode newHead = head.getNext();
         GraphNode oldHead = head;
         head = newHead;
         return oldHead;
      }
   }
   
   /** Poistetaan häntä */
   private GraphNode removeTail() {
      if(isEmpty())
        return null;

      else {
         GraphNode newTail =  findPrevGraphNode(tail);
         GraphNode oldTail = tail;
         newTail.setNext(null);
         tail = newTail;
         return oldTail;
      }
   }
   
   /** Virhe */
   private void errorNoGraphNode() {
      System.out.println("-Error, GraphNode not found.");
   }
 }