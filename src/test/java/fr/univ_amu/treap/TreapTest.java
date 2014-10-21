package fr.univ_amu.treap;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

public class TreapTest {

	@Test
	public void testSimpleSplitWithThreeNodes() {
		Node<String, Integer> nodeYard = new Node<>("yard");
		Node<String, Integer> nodePied = new Node<>("pied");
		Node<String, Integer> nodeSillon = new Node<>("sillon");
		nodeSillon.setLeftChild(nodePied);
		nodeSillon.setRightChild(nodeYard);
		Treap<String, Integer> treap = new Treap<>(nodeSillon);
		Pair<Treap<String, Integer>, Treap<String, Integer>> splitResult = treap.split(treap.node, "t");

		nodeSillon = splitResult.getFirst().node;
		assertEquals("sillon", nodeSillon.key);
		assertEquals("pied", nodeSillon.leftChild.key);
		assertEquals(2, splitResult.getFirst().countNodes());

		nodeYard = splitResult.getSecond().node;
		assertEquals("yard", nodeYard.key);
		assertEquals(1, splitResult.getSecond().countNodes());
	}

	@Test
	public void testSimpleSplitWithThreeNodes2() {
		Node<String, Integer> nodeYard = new Node<>("yard");
		Node<String, Integer> nodePied = new Node<>("pied");
		Node<String, Integer> nodeSillon = new Node<>("sillon");
		nodeSillon.setLeftChild(nodePied);
		nodeSillon.setRightChild(nodeYard);
		Treap<String, Integer> treap = new Treap<>(nodeSillon);
		Pair<Treap<String, Integer>, Treap<String, Integer>> splitResult = treap.split(treap.node, "po");

		nodeSillon = splitResult.getSecond().node;
		assertEquals("sillon", nodeSillon.key);
		assertEquals("yard", nodeSillon.rightChild.key);
		assertEquals(2, splitResult.getSecond().countNodes());

		Node<String, Integer> newNodePied = splitResult.getFirst().node;
		assertEquals("pied", newNodePied.key);
		assertEquals(1, splitResult.getFirst().countNodes());
	}

	@Test
	public void testSimpleSplitWithThreeNodes3() {
		Node<String, Integer> nodeYard = new Node<>("yard");
		Node<String, Integer> nodePied = new Node<>("pied");
		Node<String, Integer> nodeSillon = new Node<>("sillon");
		nodeSillon.setLeftChild(nodePied);
		nodeSillon.setRightChild(nodeYard);
		Treap<String, Integer> treap = new Treap<>(nodeSillon);
		Pair<Treap<String, Integer>, Treap<String, Integer>> splitResult = treap.split(treap.node, "aaaaaa");

		Node<String, Integer> nodeNull = splitResult.getFirst().node;
		assertEquals(null, nodeNull);
		assertEquals(0, splitResult.getFirst().countNodes());

		nodeSillon = splitResult.getSecond().node;
		assertEquals("sillon", nodeSillon.key);
		assertEquals("pied", nodeSillon.leftChild.key);
		assertEquals("yard", nodeSillon.rightChild.key);
		assertEquals(3, splitResult.getSecond().countNodes());
	}

	@Test
	public void testSplitComplexTree() {
		Node<String, Integer> nodeMille = new Node<>("mille");
		Node<String, Integer> nodeAnnee = new Node<> ("année");
		Node<String, Integer> nodeCoudee = new Node<> ("coudée");
		Node<String, Integer> nodeMetre = new Node<> ("mètre");

		nodeMille.leftChild = nodeAnnee;
		nodeMille.rightChild = nodeMetre;
		nodeAnnee.rightChild = nodeCoudee;

		Treap<String, Integer> treap = new Treap<>(nodeMille);
		Pair<Treap<String, Integer>, Treap<String, Integer>> splitResult = treap.split(treap.node, "baba");

		nodeMille = splitResult.getSecond().node;
		assertEquals("mille", nodeMille.key);
		assertEquals("coudée", nodeMille.leftChild.key);
		assertEquals("mètre", nodeMille.rightChild.key);
		assertEquals(3, splitResult.getSecond().countNodes());

		nodeAnnee = splitResult.getFirst().node;
		assertEquals("année", nodeAnnee.key);
		assertEquals(1, splitResult.getFirst().countNodes());
	}

	@Test
	public void testSplitOnRootComplexTree() { // root should not be in result
		Node<String, Integer> nodeMille = new Node<>("mille");
		Node<String, Integer> nodeAnnee = new Node<> ("année");
		Node<String, Integer> nodeCoudee = new Node<> ("coudée");
		Node<String, Integer> nodeMetre = new Node<> ("mètre");

		nodeMille.leftChild = nodeAnnee;
		nodeMille.rightChild = nodeMetre;
		nodeAnnee.rightChild = nodeCoudee;

		Treap<String, Integer> treap = new Treap<>(nodeMille);
		Pair<Treap<String, Integer>, Treap<String, Integer>> splitResult = treap.split(treap.node, "mille");

		nodeMetre = splitResult.getSecond().node;
		assertEquals("mètre", nodeMetre.key);
		assertEquals(1, splitResult.getSecond().countNodes());

		nodeAnnee = splitResult.getFirst().node;
		assertEquals("année", nodeAnnee.key);
		assertEquals("coudée", nodeAnnee.rightChild.key);
		assertEquals(2, splitResult.getFirst().countNodes());
	}

	@Test
	public void testInsertEmptyTreap() {
		Treap<String, Integer> treap = new Treap<>();
		treap.insert("sillon");
		assertEquals("sillon", treap.node.key);

		assertEquals(1, treap.countNodes());
	}

	@Test
	public void testInsertOrdered() {
		Treap<String, Integer> treap = new Treap<>();

		treap.insertWithPriority("mille", 11);
		treap.insertWithPriority("année-lumière", 51);
		treap.insertWithPriority("mètre", 23);
		treap.insertWithPriority("aaaa", 52);
		treap.insertWithPriority("coudée", 89);
		treap.insertWithPriority("sillon", 65);
		treap.insertWithPriority("pied", 96);
		treap.insertWithPriority("yard", 73);
		treap.insertWithPriority("zèbre", 300);

		assertEquals("mille", treap.node.key);
		assertEquals("mètre", treap.node.rightChild.key);
		assertEquals("année-lumière", treap.node.leftChild.key);

		Node<String, Integer> aaaa = treap.node.leftChild.leftChild;
		assertEquals("aaaa", aaaa.key);

		Node<String, Integer> sillon = treap.node.rightChild.rightChild;
		assertEquals("sillon", sillon.key);
		assertEquals("pied", sillon.leftChild.key);
		Node<String, Integer> yard = sillon.rightChild;
		assertEquals("yard", yard.key);
		assertEquals("zèbre", yard.rightChild.key);

		assertEquals(9, treap.countNodes());
	}

	@Test
	public void testInsertUnordered() {
		Treap<String, Integer> treap = new Treap<>();

		treap.insertWithPriority("yard", 73);
		treap.insertWithPriority("sillon", 65);
		treap.insertWithPriority("zèbre", 300);
		treap.insertWithPriority("année-lumière", 51);
		treap.insertWithPriority("aaaa", 52);
		treap.insertWithPriority("mètre", 23);
		treap.insertWithPriority("coudée", 89);
		treap.insertWithPriority("pied", 96);
		treap.insertWithPriority("mille", 11);

		assertEquals("mille", treap.node.key);
		assertEquals("mètre", treap.node.rightChild.key);
		assertEquals("année-lumière", treap.node.leftChild.key);

		Node<String, Integer> aaaa = treap.node.leftChild.leftChild;
		assertEquals("aaaa", aaaa.key);

		Node<String, Integer> sillon = treap.node.rightChild.rightChild;
		assertEquals("sillon", sillon.key);
		assertEquals("pied", sillon.leftChild.key);
		Node<String, Integer> yard = sillon.rightChild;
		assertEquals("yard", yard.key);
		assertEquals("zèbre", yard.rightChild.key);

		assertEquals(9, treap.countNodes());
	}

	@Test
	public void testInsertOnRoot() throws Exception {
		Treap<String, String> treap = new Treap<>();
		treap.insertWithPriority("yard", "something", 30);
		treap.insertWithPriority("sillon","string2",  40);
		treap.insertWithPriority("string", "no",      20);
		treap.insertWithPriority("pied", "lol",       10);
		assertEquals(4, treap.countNodes());
	}

	@Test
	public void testInsertSameKey() throws Exception {
		Treap<String, String> treap = new Treap<>();
		treap.insert("yard", "something");
		treap.insert("yard", "double something");
		assertEquals(1, treap.countNodes());
		assertEquals("double something", treap.find("yard"));
	}

	@Test
	public void testMerge() {
		Treap<String, Integer> treap1 = new Treap<>();
		Treap<String, Integer> treap2 = new Treap<>();

		treap1.insertWithPriority("mille", 11);
		treap1.insertWithPriority("année-lumière", 51);
		treap1.insertWithPriority("coudée", 89);
		treap1.insertWithPriority("mètre", 23);
		treap1.insertWithPriority("pied", 96);

		treap2.insertWithPriority("sillion", 65);
		treap2.insertWithPriority("yard", 73);

		Treap<String, Integer> treapResult = treap1.merge(treap2);

		assertEquals("mille", treapResult.node.key);
		assertEquals("année-lumière", treapResult.node.getLeftChild().key);
		assertEquals("coudée", treapResult.node.getLeftChild().getRightChild().key);

		assertEquals("mètre", treapResult.node.getRightChild().key);
		Node<String, Integer> sillion = treapResult.node.getRightChild().getRightChild();
		assertEquals("sillion", sillion.key);
		assertEquals("pied", sillion.getLeftChild().key);
		assertEquals("yard", sillion.getRightChild().key);
	}

	@Test(expected = MergeFoundDuplicateKeysException.class)
	public void testMergeException() throws Exception {
		Treap<String, Integer> treap1 = new Treap<>();
		treap1.insert("foobar");

		Treap<String, Integer> treap2 = new Treap<>();
		treap2.insert("foobar");

		treap1.merge(treap2);
	}

	@Ignore@Test(expected = MergeFoundDuplicateKeysException.class)
	public void testMergeWithPotentialDuplicates() throws Exception {
		Treap<String, Integer> treap1 = new Treap<>();
		treap1.insertWithPriority("foo", 4);
		treap1.insertWithPriority("bar", 459);
		treap1.insertWithPriority("foobar", 1);

		Treap<String, Integer> treap2 = new Treap<>();
		treap2.insertWithPriority("gee", 1);
		treap2.insertWithPriority("gaga", 2);
		treap2.insertWithPriority("foobar", 5044);
		treap2.insertWithPriority("fa", 5100);

		treap1.merge(treap2);
	}

	@Ignore@Test(expected = MergeFoundDuplicateKeysException.class)
	public void testMergeWithPotentialDuplicatesOnlyLeftChilds() {
		Treap<String, Integer> treap1 = new Treap<>();

		treap1.insertWithPriority("foobar", 1);

		Treap<String, Integer> treap2 = new Treap<>();
		treap2.insertWithPriority("gaga", 2);
		treap2.insertWithPriority("fa", 5100);
		treap2.insertWithPriority("foobar", 5044);

		treap1.merge(treap2);
	}

	@Test
	public void testMergeExceptionMessage() {
		Treap<String, Integer> treap1 = new Treap<>();
		treap1.insert("foo");
		treap1.insert("bar");
		treap1.insert("foobar");

		Treap<String, Integer> treap2 = new Treap<>();
		treap2.insert("foobar");
		try {
			treap1.merge(treap2);
		} catch (MergeFoundDuplicateKeysException e) {
			String message = "Duplicate key between the two Treap to merge " +
					"and cannot merge without loosing data, duplicate key: foobar";
			assertEquals(message, e.getMessage());
		}
	}

	@Test
	public void testCountNodes() {
		Treap<String, Integer> treap = new Treap<>();

		treap.insertWithPriority("mille", 11);
		treap.insertWithPriority("année-lumière", 51);
		treap.insertWithPriority("mètre", 23);
		treap.insertWithPriority("aaaa", 52);
		treap.insertWithPriority("coudée", 89);
		treap.insertWithPriority("sillon", 65);
		treap.insertWithPriority("pied", 96);
		treap.insertWithPriority("yard", 73);
		treap.insertWithPriority("zèbre", 300);

		assertEquals(9, treap.countNodes());
	}

	@Test
	public void testRemove() {
		Treap<String, Integer> treap = new Treap<>();

		treap.insertWithPriority("mille", 11);
		treap.insertWithPriority("année-lumière", 51);
		treap.insertWithPriority("mètre", 23);
		treap.insertWithPriority("coudée", 89);
		treap.insertWithPriority("sillon", 65);
		treap.insertWithPriority("pied", 96);
		treap.insertWithPriority("yard", 73);

		treap.remove("année-lumière");
		assertFalse(treap.contains("année-lumière"));
		assertEquals(6, treap.countNodes());
		assertEquals("mille", treap.node.key);
		assertEquals("coudée", treap.node.leftChild.key);


		treap.remove("coudée");
		assertFalse(treap.contains("coudée"));
		assertEquals(5, treap.countNodes());
		assertEquals(null, treap.node.leftChild);

		treap.remove("sillon");
		assertFalse(treap.contains("sillon"));
		assertEquals(4, treap.countNodes());
		assertEquals("mètre", treap.node.rightChild.key);
		assertEquals("yard", treap.node.rightChild.rightChild.key);
		assertEquals("pied", treap.node.rightChild.rightChild.leftChild.key);
	}

	@Test
	public void testFind() throws Exception {
		Treap<String, String> treap = new Treap<>();
		treap.insert("yard", "something");
		treap.insert("sillon", "please give us a good mark");
		treap.insert("as you like...", "no");
		treap.insert("pied", "lol");

		assertEquals("no", treap.find("as you like..."));
		assertEquals(4, treap.countNodes());


		assertEquals("something", treap.find("yard"));
		treap.remove("yard");
		assertEquals(null, treap.find("yard"));
	}

	@Test
	public void testInsertBigTree() throws Exception {
		Treap<String, Integer> treap = new Treap<>();

		treap.insert("dantès", 1);
		treap.insert("yard", 1);
		treap.insert("sillon", 1);
		treap.insert("test", 1);
		treap.insert("fdds", 1);
		treap.insert("dsfdgsd", 1);
		treap.insert("dantès", 2);
		treap.insert("sillon", 2);
		treap.insert("fdsqfdsf", 1);
		treap.insert("qesfesfd", 1);
		treap.insert("marche", 1);
		treap.insert("sqdfdsfs", 1);
		treap.insert("dsgfdsf", 1);
		treap.insert("dantès", 3);
		treap.insert("dantès", 4);
		treap.insert("dantès", 5);
		treap.insert("dsfds", 1);
		treap.insert("qsdsqdq", 1);
		treap.insert("dsfdsf", 1);
		treap.insert("yard", 2);
		treap.insert("qsfdss", 1);
		treap.insert("dsfdsfs", 1);
		treap.insert("srdgffds", 1);
		treap.insert("yjhythjytjt", 1);
		treap.insert("rdgfdgfd", 1);
		treap.insert("dantès", 6);
		treap.insert("dantès", 7);
		treap.insert("dantès", 8);
		treap.insert("fdsgfd", 1);
		treap.insert("yard", 3);
		treap.insert("yard", 4);
		treap.insert("yard", 5);
		treap.insert("marche", 2);
		treap.insert("fdsdsfds", 1);
		treap.insert("dsfsdf", 1);
		treap.insert("esfsdfsf", 1);
		treap.insert("gdfgfd", 1);
		treap.insert("sillon", 3);
		treap.insert("sgftrrhyt", 1);
		treap.insert("dantès", 9);
		treap.insert("dantès", 10);

		assertEquals(25, treap.countNodes());
	}

}
