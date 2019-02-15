/**
 * O clasa Singleton Factory care creeaza un obiect de tip {@link Observator} prin apelul
 * metodei {@link ObserversFactory#createObserver(FeedSet, int, TreeExpresion)}. Instanta
 * acestei clase se obtine prin apelul metodei {@link ObserversFactory#getInstance() 
 * getInstance()}.
 * @author Remus
 *
 */
public class ObserversFactory {
	/**
	 * O referinta catre singura instanta a clasei {@link ObserversFactory}.
	 */
	private static ObserversFactory observersFactory=null; 
	/**
	 * Constructor avand modificatorul de acces private si care impiedica instantierea unui obiect de tipul
	 * {@link ObserversFactory} in afara clasei.
	 */
	private ObserversFactory() {
	}
	/**
	 * Functia creeaza un obiect de tipul {@link Observator} apeland constructorul acestei clase:
	 * {@link Observator#Observator(FeedSet, int, TreeExpresion)} cu parametrii dati.
	 * @param subject instanta clasei {@link FeedSet}
	 * @param obs_id id-ul observatorului care trebuie construit
	 * @param treeExpresion filtrul observatorului, reprezentat sub forma unui arbore de 
	 * expresii booleene.
	 * @return o instanta a clasei {@link Observator} care, folosind cei 3 parametri primiti,
	 * initializeaza cele 3 campuri ale noului obiect: {@link Observator#subject subject},
	 * {@link Observator#obs_id obs_id} si {@link Observator#treeExpresion treeExpresion}.
	 */
	public Observator createObserver(FeedSet subject,int obs_id,TreeExpresion treeExpresion) {
		return new Observator(subject,obs_id,treeExpresion);

	}
	/**
	 * Returneaza instanta clasei Singleton {@link ObserversFactory}. 
	 * @return o instanta a clasei {@link ObserversFactory} a carei referinta este salvata in variabila 
	 * {@link ObserversFactory#observersFactory observersFactory}.
	 */
	public static ObserversFactory getInstance(){
		if(observersFactory==null)
			observersFactory=new ObserversFactory();
		return observersFactory;
	}
}
