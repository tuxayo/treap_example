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

	/* ********** INSERT ************************************/
	public void insertWithPriority (Key key, int priority) {
		Node<Key> nodeToInsert = new Node<Key>(key, 0, priority);	// à vérifier la value avec mister

		if (this.IsEmpty()) {
			this.node = nodeToInsert;
			this.node.leftChild = null;
			this.node.rightChild = null;
			return;
		}

		if (this.contains(key)) return;	// check if duplicates

		// cas de la racine ayant une priorité supérieure
		if (priority < this.node.priority) {
			Node<Key> nodeSwap = this.node;
			if (nodeKeyMoreThanKey(node, key)) {
				this.node = nodeToInsert;
				this.node.rightChild = nodeSwap;
				return;


			} else if (nodeKeyLessThanKey(node, key)) {
				this.node = nodeToInsert;
				this.node.leftChild = nodeSwap;
				return;
			}

		} // FIN TRAITEMENT RACINE


		Node<Key> nodeFound = searchWithPriority(this.node, key, priority);

		if (nodeFound == null) return;	// cas où la clef est déjà présente


		if ( ! nodeFound.hasChild()) {
			if (nodeKeyLessThanKey(nodeFound, key)) {
				nodeFound.rightChild = nodeToInsert;

			} else {
				nodeFound.leftChild = nodeToInsert;

			}
			return;

		} // if

		// case where we have to split
		Pair<Treap<Key, Val>, Treap<Key, Val> > treaps = split(nodeFound, key);


		if (nodeKeyLessThanKey(nodeFound, key)) {
			nodeFound.rightChild = nodeToInsert;	//TODO:prob insertion de sillon après pied
			nodeToInsert.rightChild = treaps.getSecond().node;

		} else { // nodeKeyMoreThan
			nodeToInsert.leftChild = treaps.getFirst().node;
			//treaps.getSecond().node.leftChild = nodeToInsert;
			nodeFound.leftChild = nodeToInsert;

		}


	} // insert ()

	/*package*/ void insert(Key key) {
		int priority = (int)(Math.random()* Integer.MAX_VALUE);
		insertWithPriority(key, priority);
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
		return search(this.node, key);
	}

	public Node<Key> searchWithPriority (Node<Key> node, Key key, int priority) {
		//if (this.contains(key)) return null;	// check if duplicates

		if (nodeKeyLessThanKey(node, key)) {
			if (node.rightChild == null) return node;	// leaf reached
			if (node.rightChild.priority > priority) return node;
			return searchWithPriority(node.rightChild, key, priority);

		} else {
			if (node.leftChild == null) return node;	// leaf reached
			if (node.leftChild.priority > priority) return node;
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
