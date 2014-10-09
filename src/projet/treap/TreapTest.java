package projet.treap;

import static org.junit.Assert.*;

import org.junit.Test;

public class TreapTest {

	@Test
	public void testSimpleSplitWithThreeNode() {
		Node<String> nodeYard = new Node<String>("yard");
		Node<String> nodePied = new Node<String>("pied");
		Node<String> nodeSillon = new Node<String>("sillon");
		nodeSillon.setLeftChild(nodePied);
		nodeSillon.setRightChild(nodeYard);
		Treap<String, Integer> treap = new Treap<>(nodeSillon);
		Pair<Treap<String, Integer>, Treap<String, Integer>> splitResult = treap.split(treap.getNode(), "t");

		Node<String> newNodeSillon = splitResult.getFst().getNode();
		assertEquals("sillon", newNodeSillon.key);
		assertEquals("pied", newNodeSillon.leftChild.key);
		assertEquals(null, newNodeSillon.rightChild);

		Node<String> newNodePied = newNodeSillon.leftChild;
		assertEquals(null, newNodePied.leftChild);
		assertEquals(null, newNodePied.rightChild);

		Node<String> newNodeYard = splitResult.getSnd().getNode();
		assertEquals("yard", newNodeYard.key);
		assertEquals(null, newNodeYard.leftChild);
		assertEquals(null, newNodeYard.rightChild);
	}

	@Test
	public void testSimpleSplitWithThreeNode2() {
		Node<String> nodeYard = new Node<String>("yard");
		Node<String> nodePied = new Node<String>("pied");
		Node<String> nodeSillon = new Node<String>("sillon");
		nodeSillon.setLeftChild(nodePied);
		nodeSillon.setRightChild(nodeYard);
		Treap<String, Integer> treap = new Treap<>(nodeSillon);
		Pair<Treap<String, Integer>, Treap<String, Integer>> splitResult = treap.split(treap.getNode(), "po");

		Node<String> newNodeSillon = splitResult.getSnd().getNode();
		assertEquals("sillon", newNodeSillon.key);
		assertEquals(null, newNodeSillon.leftChild);
		assertEquals("yard", newNodeSillon.rightChild.key);

		Node<String> newNodePied = splitResult.getFst().getNode();
		assertEquals("pied", newNodePied.key);
	}
}
