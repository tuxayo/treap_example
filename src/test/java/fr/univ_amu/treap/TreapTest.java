package fr.univ_amu.treap;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

public class TreapTest {

	@Test
	public void testSimpleSplitWithThreeNodes() {
		Node<String> nodeYard = new Node<>("yard");
		Node<String> nodePied = new Node<>("pied");
		Node<String> nodeSillon = new Node<>("sillon");
		nodeSillon.setLeftChild(nodePied);
		nodeSillon.setRightChild(nodeYard);
		Treap<String, Integer> treap = new Treap<>(nodeSillon);
		Pair<Treap<String, Integer>, Treap<String, Integer>> splitResult = treap.split(treap.getNode(), "t");

		nodeSillon = splitResult.getFirst().getNode();
		assertEquals("sillon", nodeSillon.key);
		assertEquals("pied", nodeSillon.leftChild.key);
		assertEquals(null, nodeSillon.rightChild);

		nodePied = nodeSillon.leftChild;
		assertEquals(null, nodePied.leftChild);
		assertEquals(null, nodePied.rightChild);

		nodeYard = splitResult.getSecond().getNode();
		assertEquals("yard", nodeYard.key);
		assertEquals(null, nodeYard.leftChild);
		assertEquals(null, nodeYard.rightChild);
	}

	@Test
	public void testSimpleSplitWithThreeNodes2() {
		Node<String> nodeYard = new Node<>("yard");
		Node<String> nodePied = new Node<>("pied");
		Node<String> nodeSillon = new Node<>("sillon");
		nodeSillon.setLeftChild(nodePied);
		nodeSillon.setRightChild(nodeYard);
		Treap<String, Integer> treap = new Treap<>(nodeSillon);
		Pair<Treap<String, Integer>, Treap<String, Integer>> splitResult = treap.split(treap.getNode(), "po");

		nodeSillon = splitResult.getSecond().getNode();
		assertEquals("sillon", nodeSillon.key);
		assertEquals(null, nodeSillon.leftChild);
		assertEquals("yard", nodeSillon.rightChild.key);

		Node<String> newNodePied = splitResult.getFirst().getNode();
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

		Node<String> nodeNull = splitResult.getFirst().getNode();
		assertEquals(null, nodeNull);

		nodeSillon = splitResult.getSecond().getNode();
		assertEquals("sillon", nodeSillon.key);
		assertEquals("pied", nodeSillon.leftChild.key);
		assertEquals("yard", nodeSillon.rightChild.key);
	}

	@Test
	public void testComplexTree() {
		Node<String> nodeMille = new Node<>("mille");
		Node<String> nodeAnnee = new Node<> ("année");
		Node<String> nodeCoudee = new Node<> ("coudée");
		Node<String> nodeMetre = new Node<> ("mètre");

		nodeMille.leftChild = nodeAnnee;
		nodeMille.rightChild = nodeMetre;
		nodeAnnee.rightChild = nodeCoudee;

		Treap<String, Integer> treap = new Treap<>(nodeMille);
		Pair<Treap<String, Integer>, Treap<String, Integer>> splitResult = treap.split(treap.getNode(), "baba");

		nodeMille = splitResult.getSecond().getNode();
		assertEquals("mille", nodeMille.key);
		assertEquals("coudée", nodeMille.leftChild.key);
		assertEquals("mètre", nodeMille.rightChild.key);

		nodeAnnee = splitResult.getFirst().getNode();
		assertEquals("année", nodeAnnee.key);
		assertEquals(null, nodeAnnee.leftChild);
		assertEquals(null, nodeAnnee.rightChild);
	}

	@Test
	public void testComplexTreeSplitOnRoot() { // root should not be in result
		Node<String> nodeMille = new Node<>("mille");
		Node<String> nodeAnnee = new Node<> ("année");
		Node<String> nodeCoudee = new Node<> ("coudée");
		Node<String> nodeMetre = new Node<> ("mètre");

		nodeMille.leftChild = nodeAnnee;
		nodeMille.rightChild = nodeMetre;
		nodeAnnee.rightChild = nodeCoudee;

		Treap<String, Integer> treap = new Treap<>(nodeMille);
		Pair<Treap<String, Integer>, Treap<String, Integer>> splitResult = treap.split(treap.getNode(), "mille");

		nodeMetre = splitResult.getSecond().getNode();
		assertEquals("mètre", nodeMetre.key);

		nodeAnnee = splitResult.getFirst().getNode();
		assertEquals("année", nodeAnnee.key);
		assertEquals(null, nodeAnnee.leftChild);
		assertEquals("coudée", nodeAnnee.rightChild.key);
	}

	@Test @Ignore
	public void testInsertEmptyTreap() {
		Treap<String, Integer> treap = new Treap<>(null);
		treap.insert("sillon");
		assertEquals("sillon", treap.getNode().key);
	}

	@Test
	public void testInsert() {
		Treap<String, Integer> treap = new Treap<>(null);
		treap.insertWithPriority("année-lumière", 51);
		treap.insertWithPriority("coudée", 89);
		treap.insertWithPriority("mille", 11);
		treap.insertWithPriority("aaaa", 52);
		treap.insertWithPriority("mètre", 23);
		treap.insertWithPriority("pied", 96);
		treap.insertWithPriority("sillon", 65);
		treap.insertWithPriority("yard", 73);
		treap.insertWithPriority("zèbre", 300);

		Node<String> root = treap.getNode();
		assertEquals("mille", root.key);
		assertEquals("mètre", root.rightChild.key);
		assertEquals("année-lumière", root.leftChild.key);

		Node<String> aaaa = root.leftChild.leftChild;
		assertEquals("aaaa", aaaa.key);
		assertEquals(null, aaaa.leftChild);
		assertEquals(null, aaaa.rightChild);
		
		Node<String> sillon = root.rightChild.rightChild;
		assertEquals("sillon", sillon.key);
		assertEquals("pied", sillon.leftChild.key);
		assertEquals("yard", sillon.rightChild.key);
		assertEquals("zèbre", sillon.rightChild.rightChild.key);
	}
}
