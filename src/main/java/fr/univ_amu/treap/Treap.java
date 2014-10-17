package fr.univ_amu.treap;

import java.lang.Math;

public class Treap<Key extends Comparable<Key>, Val> {
	/*package*/ Node<Key> node;

	public Treap(Node<Key> node) {
		this.node = node;
	}

	public Pair<Treap<Key, Val>, Treap<Key, Val>> split(Node<Key> node, Key key) {
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

	/*package*/ Treap<Key, Val> merge(Treap<Key, Val> otherTreap) throws MergeFoundDuplicateKeysException {
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

	public void insert(Key key) {
		int priority = (int)(Math.random()* Integer.MAX_VALUE);
		insertWithPriority(key, priority);
	}

	public boolean contains(Key key) {
		if(this.node == null) return false;
		return search(this.node, key);
	}

	public int countNodes() {
		if (this.node == null) return 0;
		return this.node.countNodes(node);
	}


	/*package*/ void insertWithPriority(Key key, int priority) {
		if (this.contains(key)) return; // not allow duplicates

		this.node = recursiveInsert(key, priority, this.node);
		return;
	}

	private Node<Key> recursiveInsert (Key key, int priority, Node<Key> currentNode) {
		if (this.IsEmpty()) {
			return createRoot(key, priority);
		}

		if (currentNode.priorityIsGreaterThan(priority)) {
			return insertInMiddleOfTreap(key, priority, currentNode);
		}

		if ( !currentNode.hasChild()) {
			return insertLeaf(key, priority, currentNode);
		}

		return searchDeeper(key, priority, currentNode);
	} // recursiveInsert ()

	private Node<Key> searchDeeper(Key key, int priority, Node<Key> currentNode) {
		if (currentNode.keyLessThan(key)) {
			if(currentNode.rightChild == null) {
				return createRightChild(key, priority, currentNode);
			} else {
				return continueRecursionRight(key, priority, currentNode); // TODO: use refactored version when mysterious bug is fixed
//				return continueRecursion(key, priority, currentNode, currentNode.rightChild);
			}
		} else {
			if(currentNode.leftChild == null) {
				return createLeftChild(key, priority, currentNode);
			} else {
				return continueRecursionLeft(key, priority, currentNode); // TODO: use refactored version when mysterious bug is fixed
//				return continueRecursion(key, priority, currentNode, currentNode.leftChild);
			}
		}
	}

	// TODO: bug: it doesn't work for right child
//	private Node<Key> continueRecursion(Key key, int priority, Node<Key> currentNode, Node<Key> currentNodeChild) {
//		currentNodeChild = recursiveInsert(key, priority, currentNodeChild);
//		return currentNode;
//	}

	private Node<Key> createLeftChild(Key key, int priority, Node<Key> currentNode) {
		Node<Key> newNode = new Node<Key>(key, 0, priority);
		currentNode.leftChild = newNode;
		return currentNode;
	}

	private Node<Key> createRightChild(Key key, int priority, Node<Key> currentNode) {
		Node<Key> newNode = new Node<Key>(key, 0, priority);
		currentNode.rightChild = newNode;
		return currentNode;
	}

	private Node<Key> continueRecursionLeft(Key key, int priority, Node<Key> currentNode) {
		currentNode.leftChild = recursiveInsert(key, priority, currentNode.leftChild);
		return currentNode;
	}

	private Node<Key> continueRecursionRight(Key key, int priority, Node<Key> currentNode) {
		currentNode.rightChild = recursiveInsert(key, priority, currentNode.rightChild);
		return currentNode;
	}

	private Node<Key> createRoot(Key key, int priority) {
		this.node = new Node<Key>(key, 0, priority);
		this.node.leftChild = null;
		this.node.rightChild = null;
		return this.node;
	}

	private Node<Key> insertLeaf(Key key, int priority, Node<Key> currentNode) {
		Node<Key> newNode = new Node<Key>(key, 0, priority);
		if (currentNode.keyLessThan(key)) {
			currentNode.rightChild = newNode;
		} else {
			currentNode.leftChild = newNode;
		}
		return currentNode;
	}

	private Node<Key> insertInMiddleOfTreap(Key key, int priority, Node<Key> currentNode) {
		Pair<Treap<Key, Val>, Treap<Key, Val> > treaps = split(currentNode, key);
		Node<Key> newNode = new Node<Key>(key, 0, priority);

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

	private boolean search (Node<Key> node, Key key) {
		if (node.key == key) return true;

		if (node.keyLessThan(key)) {
			if (node.rightChild == null) return false;
			return search(node.rightChild, key);

		} else {
			if (node.leftChild == null) return false;
			return search(node.leftChild, key);
		}

	} // search()

	public boolean remove(Key key) {
		if(!contains(key)) return false;
		if (node.key == key) { // TODO write test
			Treap<Key, Val> leftSubTree = new Treap<>(this.node.leftChild);
			Treap<Key, Val> rightSubTree = new Treap<>(this.node.rightChild);

			this.node = leftSubTree.merge(rightSubTree).node;
			return true;
		}

		Node<Key> nodeFound = findFatherOfNodeToDelete(key);


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


	private Node<Key> findFatherOfNodeToDelete(Key key) {
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
