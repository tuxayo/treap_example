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
	public void testInsertOrdered() {
		Treap<String, Integer> treap = new Treap<>(null);

		treap.insertWithPriority("mille", 11);
		treap.insertWithPriority("année-lumière", 51);
		treap.insertWithPriority("mètre", 23);
		treap.insertWithPriority("aaaa", 52);
		treap.insertWithPriority("coudée", 89);
		treap.insertWithPriority("sillon", 65);
		treap.insertWithPriority("pied", 96);
		treap.insertWithPriority("yard", 73);
		treap.insertWithPriority("zèbre", 300);

		assertEquals("mille", treap.getNode().key);
		assertEquals("mètre", treap.getNode().rightChild.key);
		assertEquals("année-lumière", treap.getNode().leftChild.key);

		Node<String> aaaa = treap.getNode().leftChild.leftChild;
		assertEquals("aaaa", aaaa.key);
		assertEquals(null, aaaa.leftChild);
		assertEquals(null, aaaa.rightChild);

		Node<String> sillon = treap.getNode().rightChild.rightChild;
		assertEquals("sillon", sillon.key);
		assertEquals("pied", sillon.leftChild.key);
		Node<String> yard = sillon.rightChild;
		assertEquals("yard", yard.key);
		assertEquals("zèbre", yard.rightChild.key);

		assertEquals(null, yard.rightChild.leftChild);
		assertEquals(null, yard.rightChild.rightChild);
		assertEquals(null, sillon.rightChild.rightChild.rightChild);
	}

	@Test
	public void testInsertUnordered() {
		Treap<String, Integer> treap = new Treap<>(null);

		treap.insertWithPriority("yard", 73);
		treap.insertWithPriority("mille", 11);
		treap.insertWithPriority("sillon", 65);
		treap.insertWithPriority("zèbre", 300);
		treap.insertWithPriority("année-lumière", 51);
		treap.insertWithPriority("aaaa", 52);
		treap.insertWithPriority("mètre", 23);
		treap.insertWithPriority("coudée", 89);
		treap.insertWithPriority("pied", 96);

		assertEquals("mille", treap.getNode().key);
		assertEquals("mètre", treap.getNode().rightChild.key);
		assertEquals("année-lumière", treap.getNode().leftChild.key);

		Node<String> aaaa = treap.getNode().leftChild.leftChild;
		assertEquals("aaaa", aaaa.key);
		assertEquals(null, aaaa.leftChild);
		assertEquals(null, aaaa.rightChild);

		Node<String> sillon = treap.getNode().rightChild.rightChild;
		assertEquals("sillon", sillon.key);
		assertEquals("pied", sillon.leftChild.key);
		Node<String> yard = sillon.rightChild;
		assertEquals("yard", yard.key);
		assertEquals("zèbre", yard.rightChild.key);

		assertEquals(null, yard.rightChild.leftChild);
		assertEquals(null, yard.rightChild.rightChild);
	}
}
