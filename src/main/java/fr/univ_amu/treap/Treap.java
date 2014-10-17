package fr.univ_amu.treap;

import java.lang.Math;

public class Treap<Key extends Comparable<Key>, Val> {
	Node<Key, Val> node;  // default access right only for tests

	public Treap(Node<Key, Val> node) {
		this.node = node;
	}

	public Pair<Treap<Key, Val>, Treap<Key, Val>> split(Node<Key, Val> node, Key key) {
		if (node == null)
			return new Pair<>(new Treap<>(null), new Treap<>(null));

		if (node.keyLessThan(key)) {
			Pair<Treap<Key, Val>, Treap<Key, Val>> rightChildSplit = split(node.rightChild, key);
			node.rightChild = rightChildSplit.getFirst().node;
			return new Pair<>(new Treap<>(node), rightChildSplit.getSecond());

		} else if (node.keyMoreThan(key)) {
			Pair<Treap<Key, Val>, Treap<Key, Val>> leftChildSplit = split(node.leftChild, key);
			node.leftChild = leftChildSplit.getSecond().node;
			return new Pair<>(leftChildSplit.getFirst(), new Treap<>(node));

		} else { // root key equals key
			return new Pair<>(new Treap<Key, Val>(node.leftChild),
					new Treap<Key, Val>(node.rightChild));
		}
	}

	public void insert(Key key, Val value) {
		int priority = (int)(Math.random()* Integer.MAX_VALUE);
		System.out.println(priority);
		insertWithPriority(key, value, priority);
	}

	public boolean remove(Key key) {
		if(!contains(key)) return false;
		if (node.key == key) { // TODO write test remove root
			Treap<Key, Val> leftSubTree = new Treap<>(this.node.leftChild);
			Treap<Key, Val> rightSubTree = new Treap<>(this.node.rightChild);

			this.node = leftSubTree.merge(rightSubTree).node;
			return true;
		}

		Node<Key, Val> nodeFound = findFatherOfNodeToDelete(key);


		if (nodeFound.leftChild != null && nodeFound.leftChild.key.compareTo(key) == 0) {
			Treap<Key, Val> leftSubTreeOfDeletedNode = new Treap<>(nodeFound.leftChild.leftChild);
			Treap<Key, Val> rightSubTreeOfDeletedNode = new Treap<>(nodeFound.leftChild.rightChild);
			nodeFound.leftChild = leftSubTreeOfDeletedNode.merge(rightSubTreeOfDeletedNode).node;
		} else {
			Treap<Key, Val> leftSubTreeOfDeletedNode = new Treap<>(nodeFound.rightChild.leftChild);
			Treap<Key, Val> rightSubTreeOfDeletedNode = new Treap<>(nodeFound.rightChild.rightChild);
			nodeFound.rightChild = leftSubTreeOfDeletedNode.merge(rightSubTreeOfDeletedNode).node;
		}

		return true;

	}

	public boolean contains(Key key) {
		if(this.node == null) return false;
		return search(this.node, key);
	}

	public int countNodes() {
		if (this.node == null) return 0;
		return this.node.countNodes(node);
	}


	public Val find(Key key) {
		if(this.node == null) return null;

		if(this.node.key == key) return this.node.value;

		Treap<Key, Val> subTreap = null;
		if(this.node.keyLessThan(key)) {
			subTreap = new Treap<>(this.node.rightChild);
			return subTreap.find(key);
		} else {
			subTreap = new Treap<>(this.node.leftChild);
			return subTreap.find(key);
		}
	}

	// default access right only for tests
	Treap<Key, Val> merge(Treap<Key, Val> otherTreap) throws MergeFoundDuplicateKeysException {
		// if one or both treaps are empty
		if(this.node == null) return otherTreap;
		if(otherTreap.node == null) return this;

		if(this.node.priorityIsGreaterThan(otherTreap.node.priority)) {
			Treap<Key, Val> newRoot = otherTreap;
			Treap<Key, Val> treapWithGreaterPriority = this;
			return newRoot.selectChildAndMerge(treapWithGreaterPriority);

		} else { // lower or equal case
			Treap<Key, Val> newRoot = this;
			Treap<Key, Val> treapWithGreaterPriority = otherTreap;
			return newRoot.selectChildAndMerge(treapWithGreaterPriority);
		}
	}

	void insert(Key key) { // only for tests
		insert(key, null);
	}

	void insertWithPriority(Key key, Val value, int priority) { // only for tests
		if (this.contains(key)) return; // not allow duplicates

		this.node = recursiveInsert(key, value, priority, this.node);
		return;
	}

	void insertWithPriority(Key key, int priority) { // only for tests
		insertWithPriority(key, null, priority);
	}


	private Treap<Key, Val> selectChildAndMerge(Treap<Key, Val> treapWithGreaterPriority) throws MergeFoundDuplicateKeysException {
		Treap<Key, Val> selectedChild = null;

		if(this.node.keyLessThan(treapWithGreaterPriority.node.key)) {
			selectedChild = new Treap<>(node.rightChild);
			Treap<Key, Val> mergeResult = selectedChild.merge(treapWithGreaterPriority);
			this.node.rightChild = mergeResult.node;
		} else if(this.node.keyMoreThan(treapWithGreaterPriority.node.key)) {
			selectedChild = new Treap<>(node.leftChild);
			Treap<Key, Val> mergeResult = selectedChild.merge(treapWithGreaterPriority);
			this.node.leftChild = mergeResult.node;
		} else {
			throw new MergeFoundDuplicateKeysException("Duplicate key between the two Treap to merge " +
					"and cannot merge without loosing data, duplicate key: " + this.node.key);
		}
		return this;
	}

	private Node<Key, Val> recursiveInsert (Key key, Val value, int priority, Node<Key, Val> currentNode) {
		if (this.IsEmpty()) {
			return createRoot(key, value, priority);
		}

		if (currentNode.priorityIsGreaterThan(priority)) {
			return insertInMiddleOfTreap(key, value, priority, currentNode);
		}

		if ( !currentNode.hasChild()) {
			return insertLeaf(key, value, priority, currentNode);
		}

		return searchDeeper(key, value, priority, currentNode);
	} // recursiveInsert ()

	private Node<Key, Val> searchDeeper(Key key, Val value, int priority, Node<Key, Val> currentNode) {
		if (currentNode.keyLessThan(key)) {
			if(currentNode.rightChild == null) {
				return createRightChild(key, value, priority, currentNode);
			} else {
				return continueRecursionRight(key, value, priority, currentNode); // TODO: use refactored version when mysterious bug is fixed
//				return continueRecursion(key, priority, currentNode, currentNode.rightChild);
			}
		} else {
			if(currentNode.leftChild == null) {
				return createLeftChild(key, value, priority, currentNode);
			} else {
				return continueRecursionLeft(key, value, priority, currentNode); // TODO: use refactored version when mysterious bug is fixed
//				return continueRecursion(key, priority, currentNode, currentNode.leftChild);
			}
		}
	}

	// TODO: bug: it doesn't work for right child
//	private Node<Key, Val> continueRecursion(Key key, int priority, Node<Key, Val> currentNode, Node<Key, Val> currentNodeChild) {
//		currentNodeChild = recursiveInsert(key, priority, currentNodeChild);
//		return currentNode;
//	}

	private Node<Key, Val> createLeftChild(Key key, Val value, int priority, Node<Key, Val> currentNode) {
		Node<Key, Val> newNode = new Node<Key, Val>(key, value, priority);
		currentNode.leftChild = newNode;
		return currentNode;
	}

	private Node<Key, Val> createRightChild(Key key, Val value, int priority, Node<Key, Val> currentNode) {
		Node<Key, Val> newNode = new Node<Key, Val>(key, value, priority);
		currentNode.rightChild = newNode;
		return currentNode;
	}

	private Node<Key, Val> continueRecursionLeft(Key key, Val value, int priority, Node<Key, Val> currentNode) {
		currentNode.leftChild = recursiveInsert(key, value, priority, currentNode.leftChild);
		return currentNode;
	}

	private Node<Key, Val> continueRecursionRight(Key key, Val value, int priority, Node<Key, Val> currentNode) {
		currentNode.rightChild = recursiveInsert(key, value, priority, currentNode.rightChild);
		return currentNode;
	}

	private Node<Key, Val> createRoot(Key key, Val value, int priority) {
		this.node = new Node<Key, Val>(key, value, priority);
		this.node.leftChild = null;
		this.node.rightChild = null;
		return this.node;
	}

	private Node<Key, Val> insertLeaf(Key key, Val value, int priority, Node<Key, Val> currentNode) {
		Node<Key, Val> newNode = new Node<Key, Val>(key, value, priority);
		if (currentNode.keyLessThan(key)) {
			currentNode.rightChild = newNode;
		} else {
			currentNode.leftChild = newNode;
		}
		return currentNode;
	}

	private Node<Key, Val> insertInMiddleOfTreap(Key key, Val value, int priority, Node<Key, Val> currentNode) {
		Pair<Treap<Key, Val>, Treap<Key, Val> > treaps = split(currentNode, key);
		Node<Key, Val> newNode = new Node<Key, Val>(key, value, priority);

		if (treaps.getFirst().node != null) {
			newNode.leftChild = treaps.getFirst().node;
		} else {
			newNode.rightChild = treaps.getSecond().node;
		}
		return newNode;
	}

	private boolean IsEmpty () {
		return this.node == null;
	}

	private boolean search (Node<Key, Val> node, Key key) {
		if (node.key == key) return true;

		if (node.keyLessThan(key)) {
			if (node.rightChild == null) return false;
			return search(node.rightChild, key);

		} else {
			if (node.leftChild == null) return false;
			return search(node.leftChild, key);
		}

	} // search()

	private Node<Key, Val> findFatherOfNodeToDelete(Key key) {
		if (oneChildMatches(key))
			return node;

		// case where target is certain to be found in subtree
		if (node.keyLessThan(key)) {
			Treap<Key, Val> treap = new Treap<>(node.rightChild);
			return treap.findFatherOfNodeToDelete(key);

		} else {
			Treap<Key, Val> treap = new Treap<>(node.leftChild);
			return treap.findFatherOfNodeToDelete(key);
		}
	}

	private boolean oneChildMatches(Key key) {
		return (node.rightChild != null && node.rightChild.key.compareTo(key) == 0 ) ||
			   (node.leftChild != null && node.leftChild.key.compareTo(key) == 0);
	}
}
