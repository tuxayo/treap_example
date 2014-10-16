package fr.univ_amu.treap;

import java.lang.Math;

public class Treap<Key extends Comparable<Key>, Val> {
	private Node<Key> node;

	public Node<Key> getNode() {
		return node;
	}

	public Treap(Node<Key> node) {
		this.node = node;
	}

	public Pair<Treap<Key, Val>, Treap<Key, Val>> split(Node<Key> node, Key key) {
		if (node == null)
			return new Pair<>(new Treap<>(null), new Treap<>(null));

		if (nodeKeyLessThanKey(node, key)) {
			Pair<Treap<Key, Val>, Treap<Key, Val>> rightChildSplit = split(node.rightChild, key);
			node.rightChild = rightChildSplit.getFirst().node;
			return new Pair<>(new Treap<>(node), rightChildSplit.getSecond());

		} else if (nodeKeyMoreThanKey(node, key)) {
			Pair<Treap<Key, Val>, Treap<Key, Val>> leftChildSplit = split(node.leftChild, key);
			node.leftChild = leftChildSplit.getSecond().node;
			return new Pair<>(leftChildSplit.getFirst(), new Treap<>(node));

		} else { // root key equals key
			return new Pair<>(new Treap<Key, Val>(node.leftChild),
					new Treap<Key, Val>(node.rightChild));
		}
	}

	public Node<Key> recursiveInsert (Key key, int priority, Node<Key> currentNode) {
		if (this.IsEmpty()) {
			return createRoot(key, priority);
		}

		if (currentNode.priority > priority) {
			return insertInMiddleOfTreap(key, priority, currentNode);
		}

		if ( !currentNode.hasChild()) {
			return insertLeaf(key, priority, currentNode);
		}

		return searchDeeper(key, priority, currentNode);
	} // recursiveInsert ()

	private Node<Key> searchDeeper(Key key, int priority, Node<Key> currentNode) {
		if (nodeKeyMoreThanKey(currentNode, key)) {
			if(currentNode.leftChild == null) {
				return createLeftChild(key, priority, currentNode);
			} else {
				return continueRecursionLeft(key, priority, currentNode); // TODO: use refactored version when mysterious bug is fixed
//				return continueRecursion(key, priority, currentNode, currentNode.leftChild);
			}
		} else {
			if(currentNode.rightChild == null) {
				return createRightChild(key, priority, currentNode);
			} else {
				return continueRecursionRight(key, priority, currentNode); // TODO: use refactored version when mysterious bug is fixed
//				return continueRecursion(key, priority, currentNode, currentNode.rightChild);
			}
		}
	}

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

	// TODO: bug: it doesn't work for right child
//	private Node<Key> continueRecursion(Key key, int priority, Node<Key> currentNode, Node<Key> currentNodeChild) {
//		currentNodeChild = recursiveInsert(key, priority, currentNodeChild);
//		return currentNode;
//	}

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
		if (nodeKeyLessThanKey(currentNode, key)) {
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

	public void insert(Key key) {
		int priority = (int)(Math.random()* Integer.MAX_VALUE);
		insertWithPriority(key, priority);
	}



	/*package*/ void insertWithPriority(Key key, int priority) {
		if (this.contains(key)) return; // not allow duplicates

		this.node = recursiveInsert(key, priority, this.node);
		return;
	}

	private boolean IsEmpty () {
		return this.node == null;
	}

	private boolean search (Node<Key> node, Key key) {
		if (node.key == key) return true;

		if (nodeKeyLessThanKey(node, key)) {
			if (node.rightChild == null) return false;
			return search(node.rightChild, key);

		} else {
			if (node.leftChild == null) return false;
			return search(node.leftChild, key);
		}

	} // search()

	private boolean contains(Key key) {
		if(this.node == null) return false;
		return search(this.node, key);
	}

	private Node<Key> searchWithPriority (Node<Key> node, Key key, int priority) {
		if (nodeKeyLessThanKey(node, key)) {
			if (node.rightChild == null) return node;	// leaf reached
			if (node.rightChild.priority > priority) return node.rightChild;
			return searchWithPriority(node.rightChild, key, priority);
		} else {
			if (node.leftChild == null) return node;	// leaf reached
			if (node.leftChild.priority > priority) return node.leftChild;
			return searchWithPriority(node.leftChild, key, priority);
		}
	}


	private boolean nodeKeyLessThanKey(Node<Key> root, Key key) {
		return root.key.compareTo(key) < 0;
	}

	private boolean nodeKeyMoreThanKey(Node<Key> root, Key key) {
		return root.key.compareTo(key) > 0;
	}

}
