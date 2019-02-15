/**
 * Clasa cuprinde operatiile efectuate de obiectul de tip {@link TreeExpresionVisitor}
 * la apelul metodei {@link TreeExpresionVisitor#visit(tema2.TreeExpresion.Node, String, double)
 * visit}.
 * @author Remus
 *
 */
public class OperatorsClass {
	/**
	 * Metoda evalueaza o expresie booleana primita ca parametru si intoarce true daca feed-ul 
	 * primit ca parametru prin nume si valoare respecta expresia booleana  si false in caz contrar.
	 * @param expresie o expresie booleana de forma: Op name/value filter_value unde
	 * Op poate fi: eq,ne,gt,ge,lt sau le.
	 * @param stock_name numele feed-ului
	 * @param stock_value valoarea feed-ului
	 * @return O valoare booleana: true daca feed-ul respecta expresia booleana primita
	 * ca paramteru si false in caz contrar.
	 */
	public static boolean evaluateOperand(String expresie,String stock_name,double stock_value) {
		if(expresie.equals("nil")) return true;
		String[] cuvinte=expresie.split(" ");
		if(cuvinte[1].equals("name")) {
			if(cuvinte[0].equals("eq")) return cuvinte[2].equals(stock_name);
			if(cuvinte[0].equals("ne")) return !cuvinte[2].equals(stock_name);
		}
		if(cuvinte[1].equals("value")) {
			if(cuvinte[0].equals("eq")) return Double.parseDouble(cuvinte[2])==stock_value;
			if(cuvinte[0].equals("ne")) return Double.parseDouble(cuvinte[2])!=stock_value;
			if(cuvinte[0].equals("gt")) return Double.parseDouble(cuvinte[2])<stock_value;
			if(cuvinte[0].equals("ge")) return Double.parseDouble(cuvinte[2])<=stock_value;
			if(cuvinte[0].equals("lt")) return Double.parseDouble(cuvinte[2])>stock_value;
			if(cuvinte[0].equals("le")) return Double.parseDouble(cuvinte[2])>=stock_value;
		}
		return false;
	}
	/**
	 * Metoda determina ce operator boolean se afla in String-ul primit in primul parametru
	 * : &amp;&amp; sau || si, in functie de acesta, metoda intoarce rezultatul obtinut prin 
	 * aplicarea operatorului pe cei doi operanzi primiti ca parametri.
	 * @param expresie un operator boolean: &amp;&amp; sau || sub forma de String
	 * @param a primul operand boolean
	 * @param b al doilea operand boolean
	 * @return o valoare booleana
	 */
	public static boolean evaluateOperator(String expresie,boolean a,boolean b) {
		switch(expresie){
		case "&&": return a&&b;
		case "||": return a||b;
		default: return false;
		}
	}
}
