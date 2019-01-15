/**
 * Tietorakenteet 2018 -harjoitustyö.
 * Harjoitustyössä on toteutettu tehtävänannon toiminnallisuudet kohdista 1-8 ja 10.
 * Javan omia luokkia, kuten prioriteettijonoa ja linkitettyälistaa on hyödynnetty harjoitustyössä.
 */
import t2018.*;

/**
 * @author Petteri Juvalainen
 * petteri.juvalainen@tuni.fi
 * opiskelijanumero 427130
 */
public class T2018 {
   public static void main(String[] args) {
      /**
       * Kohta 1.
       * Luodaan linkitetty lista johon luetaan tiedostosta solmut.
       * Lasketaan lähimmät naapurit, jotka talleteaan solmulle prioriteettijonoon etäisyyden mukaan.
       * Naapurit lisätään poistamalla prioriteettijonosta ensimmäinen solmu ja linkitys tehdään kaksisuuntaisena.
       */
      GraphNodeLinkedList graph = new GraphNodeLinkedList();    
      Operations.readInput(graph);
      Operations.findNeighbours(graph);      
      Operations.addNeighbours(graph);

      /**
       * Kohta 2.
       * Poistetaan prioriteettijonosta ensimmäinen solmu.
       */
      Operations.addNeighbours(graph);
      
      /**
       * Kohta 3.
       *
       */
      Operations.writeBFS(graph);
      
      /* Tämä tulostaa tiedot näytölle*/
      //Operations.printBFS(graph);
      
      
      /**
       * Kohta 4.
       * 
       */
      Operations.writeDFS(graph);
      
      /* Tämä tulostaa tiedot näytölle*/
      //Operations.printDFS(graph);
      
      
      /**
       * Kohta 5.
       * Asteet on laskettu seuraavasti:
       * Kuinka monta lyhimmän solmun kaarta on solmuun ja montako kaarta lähtee solmusta.
       */
      Operations.writeDegrees(graph);
      
      /* Tämä tulostaa tiedot näytölle*/
      //Operations.printDegrees(graph);
      
      /**
       * Kohta 6.
       *
       */ 
       
      // Poistosta tuli vähän kömpelö operaatio, kun solmujen tiedot tyhjätään ja lasketaan uudelleen.
      // Järkevämpi olisi ollut vain laskea uudelleen tiedot solmuista, joihin poisto vaikuttaa.
      Operations.remove(graph, 1002);
      Operations.remove(graph, 1003);
      Operations.remove(graph, 1003);
      Operations.remove(graph, 1001);
      Operations.remove(graph, 1001);
      Operations.remove(graph, 1005);
      Operations.remove(graph, 1004);
      Operations.writeRemaining(graph);
      
      /**
       * Kohta 7.
       * Luodaan uusi linkitetty lista.
       * Lisätään solmuille prioriteettijonosta naapureita, kunnes se on kokonainen.
       */
      GraphNodeLinkedList BigGraph = new GraphNodeLinkedList();
      Operations.makeGraphGreatAgain(BigGraph);
      
      /**
       * Kohta 8.
       * Minimivirittävä puu on yritetty toteuttaa mukaille Kruskalia.
       * Solmujen kaaret lasketaan prioriteettijonoon, josta aletaan yhdistelemään solmuja lyhimpien etäisyyksien mukaan.
       * Samalla tarkistetaan, että graafiin ei tule syklejä.
       */
      GraphNodeLinkedList  minimumGraph = new GraphNodeLinkedList();
      Operations.createSpanningTree(minimumGraph);

      
      /**
       * Kohta 9.
       * Tätä ei ole vertailtu muuten kuin paperilla, mutta en keksi muuta roolia, että lähellä toisiaan olevat klusterit edustavat samankaltaisa tuloksia.
       */
      
      /**
      * Kohta 10.
      *
      */
      GraphNodeLinkedList outliersGraph = new GraphNodeLinkedList();
      Operations.createOutliers(outliersGraph);
   }
}
