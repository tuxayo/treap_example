package projet.treap;

import static org.junit.Assert.*;

import org.junit.Test;

public class TreapTest {

	@Test
	public void test() {
		Node<String> nodeYard = new Node<String>("yard", 0, 0);
		Node<String> nodePied = new Node<String>("pied", 0, 0);
		Node<String> nodeSillon = new Node<String>("sillon", 0, 0); // 0 because we don't need those values
		nodeSillon.setLeftChild(nodePied);
		nodeSillon.setRightChild(nodeYard);
		Treap<String, Integer> treap = new Treap<String, Integer>(nodeSillon);
		Pair<Treap<String, Integer>, Treap<String, Integer>> splitResult = treap.split(treap.getNode(), "t");

		assertEquals("sillon", splitResult.getFst().getNode().key);
	}
}
