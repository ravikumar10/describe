
/**
 * Cette classe teste la création d'une table de <b>10 000 réponses</b> correspondant au nombre de réponses
 * d'une session sur <b>un an</b> avec une question <b>toutes les heures</b>.
 * Le test a mis environ <b>20 minutes</b> à s'effectuer, le fichier pesait alors <b>1Mb</b>.
 * @author Beber
 */
import api.dbc.DBConnexion;

public class DBTest {

    public static void main(String args[]) {
        DBConnexion dbc = DBConnexion.getConnexion();
        for (int i = 0; i < 10000; i++) {

            if (i == 2500) {
                System.out.println("Quarter done !");
            }
            if (i == 5000) {
                System.out.println("Half way trough !");
            }
            if (i == 7500) {
                System.out.println("Almost there !");
            }
        }
    }
}
