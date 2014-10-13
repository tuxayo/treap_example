package fr.univ_amu.treap;

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
			node.rightChild = rightChildSplit.getFst().node;
			return new Pair<>(new Treap<>(node), rightChildSplit.getSnd());

		} else if (nodeKeyMoreThanKey(node, key)) {
			Pair<Treap<Key, Val>, Treap<Key, Val>> leftChildSplit = split(node.leftChild, key);
			node.leftChild = leftChildSplit.getSnd().node;
			return new Pair<>(leftChildSplit.getFst(), new Treap<>(node));

		} else { // root key equals key
			return new Pair<>(new Treap<Key, Val>(node.leftChild),
					new Treap<Key, Val>(node.rightChild));
		}
	}

	private boolean nodeKeyLessThanKey(Node<Key> root, Key key) {
		return root.key.compareTo(key) < 0;
	}

	private boolean nodeKeyMoreThanKey(Node<Key> root, Key key) {
		return root.key.compareTo(key) > 0;
	}

}
