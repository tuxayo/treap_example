package projet.treap;

public class Treap<Key extends Comparable<Key>, Valeur> {
	private Node<Key> node;

	public Node<Key> getNode() {
		return node;
	}

	public Treap(Node<Key> node) {
		this.node = node;
	}

	public Pair<Treap<Key, Valeur>, Treap<Key, Valeur>> split(Node<Key> node, Key key) {

		if (node == null)
			return new Pair<Treap<Key, Valeur>,Treap<Key, Valeur>>(new Treap<>(null),new Treap<>(null));

		if (nodeKeyLessThanKey(node, key)) {
			Pair<Treap<Key, Valeur>, Treap<Key, Valeur>> rightChildSplit = split(node.rightChild, key);
			node.rightChild = rightChildSplit.getFst().node;
			return new Pair<Treap<Key, Valeur>, Treap<Key, Valeur>>(new Treap<>(node), rightChildSplit.getSnd());

		} else if (nodeKeyMoreThanKey(node, key)) {
			Pair<Treap<Key, Valeur>, Treap<Key, Valeur>> leftChildSplit = split(node.leftChild, key);
			node.leftChild = leftChildSplit.getSnd().node;
			return new Pair<Treap<Key, Valeur>, Treap<Key, Valeur>>(leftChildSplit.getFst(), new Treap<>(node));


		} else { // root key equals key
			return new Pair<Treap<Key, Valeur>,Treap<Key, Valeur>>(
					new Treap<Key, Valeur>(node.leftChild),
					new Treap<Key, Valeur>(node.rightChild));
		}
	}

	private boolean nodeKeyLessThanKey(Node<Key> root, Key key) {
		return root.key.compareTo(key) < 0;
	}

	private boolean nodeKeyMoreThanKey(Node<Key> root, Key key) {
		return root.key.compareTo(key) > 0;
	}

}
