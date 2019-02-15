import java.util.Locale;
import java.util.Scanner;
/**
 * Clasa contine metoda {@link ReadClass#readCommand(Scanner, FeedSet) readCommand}
 * care citeste informatiile din fisierul de intrare, tinand cont de formatul acestuia,
 * precizat in cerinta temei.
 * @author Remus
 *
 */
public class ReadClass {
	/**
	 * Functia este apelata in clasa {@link MainClass} si citeste pe rand fiecare linie
	 * a textului de la intrare. Primul cuvant al fiecarei linii reprezinta o comanda,
	 * in functie de care se vor executa instructiuni diferite.
	 * @param in un obiect de tip {@link java.util.Scanner Scanner}, folosit pentru citirea 
	 * de la intrarea standard 
	 * @param feed instanta clasei {@link FeedSet}
	 */
	public static void readCommand(Scanner in,FeedSet feed) {
		ObserversFactory obsf=ObserversFactory.getInstance();
		while(in.hasNextLine()) {
			String linie=in.nextLine();	
			Scanner scan=new Scanner(linie);
			scan.useLocale(Locale.US);
			String command =scan.next();
			switch(command) {
			case "begin": continue;
			case "end": scan.close(); return;
			case "create_obs": 	
								{int obs_id=scan.nextInt();
								TreeExpresion tree=new TreeExpresion(linie);
								obsf.createObserver(feed,obs_id,tree);
								continue;
								}
			case "delete_obs": 	{int obs_id=scan.nextInt();
								feed.remove(obs_id);
								continue;
								}
			case "print":		{
								int obs_id=scan.nextInt();
								feed.findObs(obs_id).print();
								continue;
								}
			case "feed":		{String name=scan.next();
								double value=scan.nextDouble();
								feed.addStock(name, value);
								continue;
								}
			default: scan.close(); return;
			}
		}
	}
}
