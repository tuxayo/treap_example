package projet.treap;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

public class TreapTest {

	@Test
	public void testSimpleSplitWithThreeNodes() {
		Node<String> nodeYard = new Node<String>("yard");
		Node<String> nodePied = new Node<String>("pied");
		Node<String> nodeSillon = new Node<String>("sillon");
		nodeSillon.setLeftChild(nodePied);
		nodeSillon.setRightChild(nodeYard);
		Treap<String, Integer> treap = new Treap<>(nodeSillon);
		Pair<Treap<String, Integer>, Treap<String, Integer>> splitResult = treap.split(treap.getNode(), "t");

		nodeSillon = splitResult.getFst().getNode();
		assertEquals("sillon", nodeSillon.key);
		assertEquals("pied", nodeSillon.leftChild.key);
		assertEquals(null, nodeSillon.rightChild);

		nodePied = nodeSillon.leftChild;
		assertEquals(null, nodePied.leftChild);
		assertEquals(null, nodePied.rightChild);

		nodeYard = splitResult.getSnd().getNode();
		assertEquals("yard", nodeYard.key);
		assertEquals(null, nodeYard.leftChild);
		assertEquals(null, nodeYard.rightChild);
	}

	@Test
	public void testSimpleSplitWithThreeNodes2() {
		Node<String> nodeYard = new Node<String>("yard");
		Node<String> nodePied = new Node<String>("pied");
		Node<String> nodeSillon = new Node<String>("sillon");
		nodeSillon.setLeftChild(nodePied);
		nodeSillon.setRightChild(nodeYard);
		Treap<String, Integer> treap = new Treap<>(nodeSillon);
		Pair<Treap<String, Integer>, Treap<String, Integer>> splitResult = treap.split(treap.getNode(), "po");

		nodeSillon = splitResult.getSnd().getNode();
		assertEquals("sillon", nodeSillon.key);
		assertEquals(null, nodeSillon.leftChild);
		assertEquals("yard", nodeSillon.rightChild.key);

		Node<String> newNodePied = splitResult.getFst().getNode();
		assertEquals("pied", newNodePied.key);
	}

	@Test
	public void testSimpleSplitWithThreeNodes3() {
		Node<String> nodeYard = new Node<>("yard");
		Node<String> nodePied = new Node<>("pied");
		Node<String> nodeSillon = new Node<>("sillon");
		nodeSillon.setLeftChild(nodePied);
		nodeSillon.setRightChild(nodeYard);
		Treap<String, Integer> treap = new Treap<>(nodeSillon);
		Pair<Treap<String, Integer>, Treap<String, Integer>> splitResult = treap.split(treap.getNode(), "aaaaaa");

		Node<String> nodeNull = splitResult.getFst().getNode();
		assertEquals(null, nodeNull);

		nodeSillon = splitResult.getSnd().getNode();
		assertEquals("sillon", nodeSillon.key);
		assertEquals("pied", nodeSillon.leftChild.key);
		assertEquals("yard", nodeSillon.rightChild.key);
	}

	@Test
	public void testComplexTree() {
		Node<String> nodeMille = new Node<String>("mille");
		Node<String> nodeAnnee = new Node<String> ("année");
		Node<String> nodeCoudee = new Node<String> ("coudée");
		Node<String> nodeMetre = new Node<String> ("mètre");

		nodeMille.leftChild = nodeAnnee;
		nodeMille.rightChild = nodeMetre;
		nodeAnnee.rightChild = nodeCoudee;

		Treap<String, Integer> treap = new Treap<>(nodeMille);
		Pair<Treap<String, Integer>, Treap<String, Integer>> splitResult = treap.split(treap.getNode(), "baba");

		nodeMille = splitResult.getSnd().getNode();
		assertEquals("mille", nodeMille.key);
		assertEquals("coudée", nodeMille.leftChild.key);
		assertEquals("mètre", nodeMille.rightChild.key);

		nodeAnnee = splitResult.getFst().getNode();
		assertEquals("année", nodeAnnee.key);
		assertEquals(null, nodeAnnee.leftChild);
		assertEquals(null, nodeAnnee.rightChild);
	}

	@Test
	public void testComplexTreeSplitRoot() {
		Node<String> nodeMille = new Node<String>("mille");
		Node<String> nodeAnnee = new Node<String> ("année");
		Node<String> nodeCoudee = new Node<String> ("coudée");
		Node<String> nodeMetre = new Node<String> ("mètre");

		nodeMille.leftChild = nodeAnnee;
		nodeMille.rightChild = nodeMetre;
		nodeAnnee.rightChild = nodeCoudee;

		Treap<String, Integer> treap = new Treap<>(nodeMille);
		Pair<Treap<String, Integer>, Treap<String, Integer>> splitResult = treap.split(treap.getNode(), "mille");

		nodeMetre = splitResult.getSnd().getNode();
		assertEquals("mètre", nodeMetre.key);

		nodeAnnee = splitResult.getFst().getNode();
		assertEquals("année", nodeAnnee.key);
		assertEquals(null, nodeAnnee.leftChild);
		assertEquals("coudée", nodeAnnee.rightChild.key);
	}
}
