/**
 * Clasa de tip visitor folosita pentru a evalua un feed pe un filtru de tip
 * {@link TreeExpresion}.
 * @author Remus
 *
 */
public class TreeExpresionVisitor {
	/**
	 * Metoda parcurge in adancime arborele binar primit ca prim parametru iar in momentul in care
	 * ajunge la o frunza evalueaza feed-ul pe expresia booleana din frunza, prin apelul metodei statice
	 * {@link OperatorsClass#evaluateOperand(String, String, double) evaluateOperand} din clasa
	 * {@link OperatorsClass}. Valorile booleene obtinute in doua noduri cu acelasi parinte se duc la nodul 
	 *  parinte a acestora, unde se evalueaza rezultatul prin apelul metodei statice 
	 *  {@link OperatorsClass#evaluateOperator(String, boolean, boolean) evaluateOperator} din clasa 
	 *  {@link OperatorsClass}.
	 * @param tree un abore de expresii booleene, reprezentat prin campul {@link TreeExpresion#node
	 * node} al unui obiect de tipul {@link TreeExpresion}
	 * @param stock_name numele feed-ului
	 * @param stock_value valoarea feed-ului
	 * @return O valoare booleana: true daca feed-ul trece de filtru si false in caz contrar.
	 */
	public boolean visit(TreeExpresion.Node tree,String stock_name,double stock_value) {
		if(tree.getNodeSt()==null) return OperatorsClass.evaluateOperand(tree.getInfo(),stock_name,stock_value);
		return OperatorsClass.evaluateOperator(tree.getInfo(), visit(tree.getNodeSt(),stock_name,stock_value),
				visit(tree.getNodeDr(),stock_name,stock_value));
	}
}
