import java.util.TreeMap;
/**
 * Clasa are rolul de a afisa informatiile continute in variabila sa de tip {@link 
 * java.util.TreeMap TreeMap}, {@link Observator#prev_stock prev_stock}, care trec 
 * de filtrul de tip {@link TreeExpresion}, {@link Observator#treeExpresion treeExpresion}.
 *  In afara de constructor: {@link Observator#Observator(FeedSet, int, 
 * TreeExpresion) Observator(FeedSet, int, TreeExpresion)} care este apelat in {@link 
 * ObserversFactory} si de metoda {@link Observator#print() print()} care este apelata 
 * in {@link ReadClass}, celelalte 2 metode: {@link Observator#addStock(String, double)
 * addStock} si {@link Observator#updateObserverStock(String, double) updateObserverStock}
 * sunt folosite pentru a modifica lista cu informatii de afisare {@link Observator#prev_stock
 * prev_stock} si sunt apelate de metode din clasa {@link FeedSet}, care este subject pentru 
 * clasa {@link Observator}.
 * @author Remus
 *
 */
public class Observator {
	/**
	 * O referinta catre instanta clasei {@link FeedSet}, de care instanta curenta a clasei
	 * {@link Observator} se va folosi la apelul constructorului pentru a fi adaugata in 
	 * lista de observatori {@link FeedSet#observers observers} ai instantei 
	 * {@link FeedSet#feedSet feedSet}.
	 * 
	 */
	private FeedSet subject;
	
	/**
	 * Id-ul observatorului reprezentat de instanta curenta a clasei {@link Observator}.
	 */
	private int obs_id;
	/**
	 * Filtrul observatorului, reprezentat sub forma unui arbore de expresii booleene.
	 */
	private TreeExpresion treeExpresion;
	/**
	 * O lista ordonata de tipul {@link java.util.TreeMap TreeMap} in care cheile sunt numele
	 * feed-urilor iar valoarea este un vector generic avand elemente de tipul {@link Object}.
	 * Vectorul valorii contine 4 elemente ocupand pozitiile cu index-ul de la 0 la 3 astfel:
	 * <br>
	 * -Pe pozitia 0 se afla penultima valoare a feed-ului afisata de observator, considerand ca ultima valoare
	 * afisata este cea  actuala
	 * <br>
	 * -Pe pozitia 1 se afla acuala valoare a feed-ului sau ultima valoare afisata a feed-ului,
	 * considerand ca ultima valoare afisata este cea  actuala
	 * <br>
	 * -Pe pozitia 2 se afla procentul de crestere a valorii feed-ului, intre penultima si ultima 
	 * valoare afisata 
	 * <br>
	 * -Pe pozitia 3 se afla numarul de modificari facute asupra valorii feed-ului de la ultima afisare
	 * a acesteia
	 */
	private TreeMap <String,Object[]>prev_stock=new TreeMap<String,Object[]>();
	/**
	 * Constructorul clasei {@link Observator} primeste 3 parametri cu ajutorul carora, acesta
	 * initializeaza cele 3 campuri ale noului obiect: {@link Observator#subject subject},
	 * {@link Observator#obs_id obs_id} si {@link Observator#treeExpresion treeExpresion}.
	 * De asemenea, constructorul se foloseste de instanta clasei {@link FeedSet} primita ca
	 * parametru pentru a adauga obiectul curent in lista de observatori {@link FeedSet#observers observers}
	 * a acesteia.
	 * @param subject instanta clasei {@link FeedSet}
	 * @param obs_id id-ul noului observator construit
	 * @param treeExpresion filtrul folosit de noul observator, sub forma unui arbore de expresii 
	 * booleene de tip {@link TreeExpresion}
	 */
	public Observator(FeedSet subject,int obs_id,TreeExpresion treeExpresion) {
		this.subject=subject;
		this.obs_id=obs_id;
		this.subject.add(obs_id,this);
		this.treeExpresion=treeExpresion;
	}
	/**
	 * Functia adauga un feed cu informatiile primite ca parametri in lista cu informatiile de
	 * afisare a feed-urilor {@link Observator#prev_stock prev_stock}.
	 * @param stock_name numele feed-ului adaugat in {@link Observator#prev_stock prev_stock}
	 * @param stock_value valoarea feed-ului adaugat in {@link Observator#prev_stock prev_stock}
	 */
	public void addStock(String stock_name,double stock_value) {
		double penult_value=0;
		double ult_value=stock_value;
		double increase=0;
		int nr_schimbari=0;
		if(prev_stock.containsKey(stock_name)) {
			penult_value=(double)prev_stock.get(stock_name)[0];
			if(penult_value!=0)
				increase=(double)(ult_value-penult_value)/penult_value*100;
			nr_schimbari=(int)prev_stock.get(stock_name)[3];
		}
		prev_stock.put(stock_name,new Object[]{new Double(penult_value),new Double(ult_value),
				new Double(increase),new Integer(nr_schimbari+1)});
	}
	/**
	 * Functia este apelata dupa construirea unui nou obiect de tipul {@link Observator} si 
	 * actualizeaza lista cu informatiile de afisare a acestuia {@link Observator#prev_stock prev_stock},
	 * punand in ea:
	 * <br>-penultima valoare afisata a feed-ului=0
	 * <br>-ultima valoare afisata a feed-ului=valoarea feed-ului
	 * <br>-cresterea procentuala a valorii feed-ului=0
	 * <br>-numarul de modificari ale feed-ului=0
	 * @param stock_name numele feed-ului
	 * @param stock_value valoarea feed-ului
	 */
	public void updateObserverStock(String stock_name,double stock_value) {
		prev_stock.put(stock_name,new Object[]{new Double(0),new Double(stock_value),
				new Double(0),new Integer(0)});
	}
	/**
	 * Functia afiseaza informatiile despre toate feed-urile care trec de filtrul observatorului
	 * {@link Observator#treeExpresion treeExpresion}, adica pentru care parcurgerea arborelui 
	 * de expresii booleene {@link Observator#treeExpresion treeExpresion} intoarce true. Dupa
	 * afisarea informatiilor, se modifica informatiile din lista {@link Observator#prev_stock 
	 * prev_stock} astfel incat ele sa depinda de informatiile de la ultima afisare.
	 */
	void print() {
		for(String key:prev_stock.keySet()) {
			if(treeExpresion.accept(new TreeExpresionVisitor(), key, (double)prev_stock.get(key)[1])) {
					System.out.println("obs "+obs_id+": "+key+" "+
							String.format("%.2f %.2f%% %d",(double)prev_stock.get(key)[1],(double)prev_stock.get(key)[2]
							,(int)prev_stock.get(key)[3]).replace(",", "."));
					double ult_value=(double)prev_stock.get(key)[1];
					prev_stock.put(key, new Object[] {new Double(ult_value),new Double(ult_value),
							new Double(0),new Integer(0)});
			}
		}
		
	}
}
