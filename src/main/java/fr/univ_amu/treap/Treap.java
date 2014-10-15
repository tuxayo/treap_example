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
			this.node = new Node<Key>(key, 0, priority);
			this.node.leftChild = null;
			this.node.rightChild = null;
			return this.node;
		}

		if (currentNode.priority > priority) {
			Pair<Treap<Key, Val>, Treap<Key, Val> > treaps = split(currentNode, key);
			Node<Key> newNode = new Node<Key>(key, 0, priority);

			if (treaps.getFirst().node != null) {
				newNode.leftChild = treaps.getFirst().node;
			}
			if (treaps.getSecond().node != null) {
				newNode.rightChild = treaps.getSecond().node;
			}
			return newNode;
		}

		if ( !currentNode.hasChild()) {
			Node<Key> newNode = new Node<Key>(key, 0, priority);
			if (nodeKeyLessThanKey(currentNode, key)) {
				currentNode.rightChild = newNode;
			} else {
				currentNode.leftChild = newNode;
			}
			return currentNode;
		}

		if (nodeKeyMoreThanKey(currentNode, key)) {
			if(currentNode.leftChild != null) {
				currentNode.leftChild = recursiveInsert(key, priority, currentNode.leftChild);
				return currentNode;
			} else {
				Node<Key> newNode = new Node<Key>(key, 0, priority);
				currentNode.leftChild = newNode;
				return currentNode;
			}
		} else {
			if(currentNode.rightChild != null) {
				currentNode.rightChild = recursiveInsert(key, priority, currentNode.rightChild);
				return currentNode;
			} else {
				Node<Key> newNode = new Node<Key>(key, 0, priority);
				currentNode.rightChild = newNode;
				return currentNode;
			}
		}
	} // recursiveInsert ()

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

	public Node<Key> searchWithPriority (Node<Key> node, Key key, int priority) {
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
