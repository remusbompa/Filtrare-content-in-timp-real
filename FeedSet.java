import java.util.TreeMap;
/**
 * Clasa Singleton care retine o lista ordonata de observatori {@link FeedSet#observers observers} si o
 * lista ordonata de feed-uri {@link FeedSet#stock stock}.
 * @author Remus
 *
 */
public class FeedSet {
	/**
	 * O referinta catre singura instanta a clasei {@link FeedSet}.
	 */
	private static FeedSet feedSet=null;
	/**
	 * O lista ordonata de observatori de tipul {@link java.util.TreeMap TreeMap}, in care cheile
	 * sunt id-ul observatorului si valorile sunt obiecte de tip 
	 * Observator.
	 */
	private TreeMap<Integer,Observator>observers=new TreeMap<Integer,Observator>();
	/**
	 * O lista ordonata de feed-uri de tipul {@link java.util.TreeMap TreeMap}, in care cheile sunt numele 
	 * feed-urilor iar valorile sunt valorile feed-urilor.
	 */
	private TreeMap<String,Double>stock=new TreeMap<String,Double>();
	/**
	 * Constructor avand modificatorul de acces private si care impiedica instantierea unui obiect de tipul
	 * {@link FeedSet} in afara clasei.
	 */
	private FeedSet() {
		
	}
	/**
	 * Functia adauga obiectul de tip Observator primit ca parametru,
	 * impreuna cu id-ul sau, in lista de observatori observers. Dupa adaugare, 
	 * pentru observatorul dat, se va actualiza lista de informatii despre feed-urile
	 * afisate prin apelul metodei {@link Observator#updateObserverStock updateObserverStock}
	 *  din clasa {@link Observator}.
	 * @param obs_id id-ul obiectului de tip Observator
	 * @param o un obiect de tip Observator care trebuie adaugat in lista de observatori
	 * ai  instantei de tip FeedSet
	 */
	public void add(int obs_id,Observator o) {
		observers.put(obs_id, o);
		for(String key:stock.keySet())
			o.updateObserverStock(key, stock.get(key));
	}
	/**
	 * Functia elimina obiectul {@link Observator} cu id-ul dat ca parametru din lista de observatori
	 * {@link FeedSet#observers observers}. 
	 * @param obs_id id-ul observatorului care trebuie eliminat din lista de observatori
	 * {@link FeedSet#observers observers}.
	 */
	public void remove(int obs_id) {
		observers.remove(obs_id);
	}
	/**
	 * Functia cauta obiectul de tip {@link Observator} cu id-ul primit ca parametru in lista de
	 * observatori {@link FeedSet#observers observers}. 
	 * @param obs_id id-ul observatorului care trebuie gasit in lista de observatori 
	 * {@link FeedSet#observers observers}.
	 * @return Referinta catre obiectul de tip {@link Observator} cu id-ul primit ca parametru, gasit
	 * in lista {@link FeedSet#observers observers}.
	 */
	public Observator findObs(int obs_id) {
		return observers.get(obs_id);
	}
	/**
	 * Functia adauga feed-ul cu numele si valoarea primite ca parametri in lista de feed-uri
	 * {@link FeedSet#stock stock}.
	 * @param stock_name numele feed-ului care se adauga in lista de feed-uri {@link FeedSet#stock stock}.
	 * @param stock_value valoarea feed-ului care se adauga in lista de feed-uri {@link FeedSet#stock stock}.
	 */
	public void addStock(String stock_name,double stock_value) {
		stock.put(stock_name, stock_value);
		for(Integer key:observers.keySet()) {
			observers.get(key).addStock(stock_name,stock_value);
		}
		
	}
	/**
	 * Returneaza instanta clasei Singleton {@link FeedSet}. 
	 * @return o instanta a clasei {@link FeedSet} a carei referinta este salvata in variabila 
	 * {@link FeedSet#feedSet feedSet}.
	 */
	public static FeedSet getInstance(){
		if(feedSet==null)
			feedSet=new FeedSet();
		return feedSet;
	}
}
