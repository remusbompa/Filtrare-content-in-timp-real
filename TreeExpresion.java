/**
 * Clasa reprezinta un arbore binar de expresii booleene in care nodurile contin stringuri
 * astfel incat: frunzele contin expresii de forma: Op name/value filter_value unde
 * Op:eq,ne,gt,ge,lt,le iar celelalte celule contin cate un operator logic: &amp;&amp; sau ||.
 * Arborele propriu-zis este reprezentat de campul {@link TreeExpresion#node node} de
 * tipul {@link Node}. Rezolvarea unui filtru in functie de caracteristicile unui feed
 * se face prin apelarea metodei {@link TreeExpresion#accept(TreeExpresionVisitor, String, double)
 * accept(TreeExpresionVisitor, String, double)}.
 * @author Remus
 *
 */
public class TreeExpresion {
	/**
	 * Reprezinta arborele binar propriu-zis, acest camp fiind o referinta catre celula de pe
	 * primul nivel al arborelui.
	 */
	public Node node;
	/**
	 * Metoda rezolva arborele binar de expresii booleene, returnand true daca caracteristicile feed-ului
	 * : numele si valoarea, primite ca parametri trec de filtrul reprezentat de {@link TreeExpresion#node
	 * node} si false in caz contrar. Metoda este specifica design pattern-ului visitor, deoarece primeste
	 * ca parametru un obiect de tip {@link TreeExpresionVisitor}, asupra caruia efectueaza operatia de 
	 * verificare daca ceilalti doi parametri trec sau nu de filtrul {@link TreeExpresion#node node}, prin
	 * apelul metodei visitor-ului: {@link TreeExpresionVisitor#visit(Node, String, double) visit(Node, String,
	 *  double)}.
	 * @param visitor un obiect de tipul {@link TreeExpresionVisitor}, caruia i se apeleaza metoda 
	 * {@link TreeExpresionVisitor#visit(Node, String, double) visit} pentru a verifica daca ceilalti doi 
	 * parametri trec sau nu de filtru
	 * @param stock_name numele feed-ului
	 * @param stock_value valoarea feed-ului
	 * @return O valoare booleana: true daca feed-ul trece de filtru si false in caz contrar.
	 */
	public boolean accept(TreeExpresionVisitor visitor,String stock_name,double stock_value) {
		return visitor.visit(this.node,stock_name,stock_value);
	}
	/**
	 * Constructorul clasei {@link TreeExpresion}. Construieste arborele prin atribuirea referintei 
	 * intoarse de apelul metodei {@link TreeExpresion#readExpresion(String, String[]) readExpresion}
	 * campului {@link TreeExpresion#node node}.
	 * @param linie linie citita din fisierul de intrare, folosita pentru a construi un vector de
	 * paranteze si un vector de expresii booleene, in ordinea in care apar acestea in linie.
	 */
	TreeExpresion(String linie){
		String paranteze="";		
		String[] paranteze_vect=linie.split("([^\\(\\)]+)");
		String[] expresii=linie.split("\\s[\\(]+[\\(\\s]*|[\\s]*\\)+[\\s]*");
		for(int i=0;i<paranteze_vect.length;i++) {
			paranteze+=paranteze_vect[i];
		}
		this.node=readExpresion(paranteze,expresii);
	}
	/**
	 * Clasa statica interna care modeleaza un arbore binar cu informatii de tip String.
	 * @author Remus
	 *
	 */
	public static class Node{
		private String info;
		private Node st;
		private Node dr;
		/**
		 * Constructorul clasei {@link Node}. Construieste un nod primind ca parametru o
		 * informatie de tip String.
		 * @param info informatia din noul nod
		 */
		public Node(String info) {
			this.info=info;
			this.st=null;
			this.dr=null;
		}
		/**
		 * getter pentru campul {@link Node#st st} din clasa {@link Node}
		 * @return valoarea campului {@link Node#st st}
		 */
		public Node getNodeSt() {
			return st;
		}
		/**
		 * getter pentru campul {@link Node#dr dr} din clasa {@link Node}
		 * @return valoarea campului {@link Node#dr dr}
		 */
		public Node getNodeDr() {
			return dr;
		}
		/**
		 * getter pentru campul {@link Node#info info} din clasa {@link Node}
		 * @return valoarea campului {@link Node#info info}
		 */
		public String getInfo() {
			return info;
		}
	}
	/**
	 * Clasa folosita pentru a mentine valoarea unor intregi la intoarcerea din apelurile
	 * recursive.
	 * @author Remus
	 *
	 */
	private static class IntegerRef{
		/**
		 * Valoarea propriu-zisa a intregului de tip {@link IntegerRef}.
		 */
		int value;
		/**
		 * Constructorul clasei {@link IntegerRef}.
		 * @param value valoarea campului {@link IntegerRef#value value} din noul obiect
		 * {@link IntegerRef} construit.
		 */
		IntegerRef(int value){
			this.value=value;
		}
		/**
		 * Setter pentru campul {@link IntegerRef#value value}.
		 * @param value noua valoare a campului {@link IntegerRef#value value}
		 */
		void setValue(int value) {
			this.value=value;
		}
	}
	/**
	 * Metoda este folosita pentru constructia arborelui binar de expresii booleene. Aceasta insereaza noduri 
	 * si se apeleaza recursiv in functie de pozitia in vectorul de paranteze.
	 * @param paranteze vectorul de paranteze folosite la citirea filtrului, luate in ordinea data
	 * @param expresii vector de expresii booleene, aflate intre paranteze la citirea filtrului
	 * @param i pozitia la care s-a ajuns in vectorul de paranteze, de tip {@link IntegerRef} pentru a nu se
	 * modifica la intoarcerea din apelurile recursive
	 * @param j pozitia la care s-a ajuns in vectorul de expresii, de tip {@link IntegerRef} pentru a nu se
	 * modifica la intoarcerea din apelurile recursive
	 * @param niv nivelul la care s-a ajuns in arbore, numerotarea facandu-se da la nivelul 0
	 * @return
	 */
	private static TreeExpresion.Node insertNode(String paranteze,String[] expresii,IntegerRef i,IntegerRef j,int niv) {
		char citit=paranteze.charAt(i.value);
		i.setValue(i.value+1);
		if(citit=='(') {
			Node operator=new Node("");
			operator.st=insertNode(paranteze,expresii,i,j,niv+1);
            while(expresii[j.value].equals(""))
                j.setValue(j.value+1);
			operator.info=expresii[j.value];
			j.setValue(j.value+1);
			if(operator.st==null) return operator;
			operator.dr=insertNode(paranteze,expresii,i,j,niv+1);
			
			citit=paranteze.charAt(i.value);
			i.setValue(i.value+1);
			while(citit!=')') {
				i.setValue(i.value-1);;
				Node operator2=new Node("");
				operator2.st=operator;
				operator=operator2;
				while(expresii[j.value].equals(""))
					j.setValue(j.value+1);
				operator.info=expresii[j.value];
				j.setValue(j.value+1);
				operator.dr=insertNode(paranteze,expresii,i,j,niv+1);	
				citit=paranteze.charAt(i.value);
				i.setValue(i.value+1);
			}
			if(niv==0&&i.value<paranteze.length()) {
				while(i.value<paranteze.length()) {
					Node operator2=new Node("");
					operator2.st=operator;
					operator=operator2;
					while(expresii[j.value].equals(""))
						j.setValue(j.value+1);
					operator.info=expresii[j.value];
					j.setValue(j.value+1);
					operator.dr=insertNode(paranteze,expresii,i,j,niv+1);	
				}
			}
			return operator;	
		}
		else if(citit==')')
			return null;
		return null;
	}
	/**
	 * Construieste un arbore binar de expresii booleene in functie de vectorul de paranteze primit
	 * ca parametru, prin apelul functiei recursive {@link TreeExpresion#insertNode(String, String[]
	 * , IntegerRef, IntegerRef, int) insertNode}.
	 * @param paranteze vectorul de paranteze folosite la citirea filtrului, luate in ordinea data
	 * @param expresii vector de expresii booleene, aflate intre paranteze la citirea filtrului
	 * @return referinta catre celula de pe primul nivel al arborelui construit
	 */
	private static Node readExpresion(String paranteze,String[] expresii) {
		if(paranteze.length()==0) return new Node("nil");	
		Node expresion=insertNode(paranteze,expresii,new IntegerRef(0),new IntegerRef(1),0);
		return expresion;	
	}
	
}
