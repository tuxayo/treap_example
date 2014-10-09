package projet.treap;

import static org.junit.Assert.*;

import org.junit.Test;

public class TreapTest {

	@Test
	public void testSplit() {
		Node<String> nodeYard = new Node<String>("yard", 0, 0);
		Node<String> nodePied = new Node<String>("pied", 0, 0);
		Node<String> nodeSillon = new Node<String>("sillon", 0, 0); // 0 because we don't need those values
		nodeSillon.setLeftChild(nodePied);
		nodeSillon.setRightChild(nodeYard);
		Treap<String, Integer> treap = new Treap<String, Integer>(nodeSillon);
		Pair<Treap<String, Integer>, Treap<String, Integer>> splitResult = treap.split(treap.getNode(), "t");

		Node<String> newNodeSillon = splitResult.getFst().getNode();
		assertEquals("sillon", newNodeSillon.key);
		assertEquals("pied", newNodeSillon.leftChild.key);
		assertEquals(null, newNodeSillon.rightChild);

		Node<String> newNodePied = newNodeSillon.leftChild;
		assertEquals(null, newNodePied.leftChild);
		assertEquals(null, newNodePied.rightChild);

		Node<String> newNodeYard = splitResult.getSnd().getNode();
		assertEquals("yard", newNodeYard.key); // sillon is here, WTF???
//		assertEquals(null, newNodeYard.leftChild);
//		assertEquals(null, newNodeYard.rightChild);
	}
}
