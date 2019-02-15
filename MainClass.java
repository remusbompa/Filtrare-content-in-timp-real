import java.util.Scanner;
/**
 * Clasa principala a temei.
 * @author Remus
 *
 */
public class MainClass {
/**
 * Metoda principala creeaza un obiect de tip {@link java.util.Scanner Scanner} si un obiect 
 * de tip {@link FeedSet} pe care le trimite ca parametri la apelul metodei statice
 * {@link ReadClass#readCommand(Scanner, FeedSet) }.
 * @param args metoda principala nu primeste parametri la rularea programului.
 */
	public static void main(String[] args) {
		Scanner in=new Scanner(System.in);
		FeedSet feed=FeedSet.getInstance();
		ReadClass.readCommand(in, feed);
	}

}
