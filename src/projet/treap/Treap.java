package projet.treap;

public class Treap <Key extends Comparable<Key>, Valeur> {
	private Node<Key> node;

	public Pair<Node<Key>, Node<Key>> split(Node<Key> node, Key key) { // OMFG
		// if (root == null) return null;
		if (rootKeyLessThanKey (node, key)) {
			return split (node.rightChild, key);

		} else if (rootKeyMoreThanKey (node, key)) {
			return split (node.leftChild, key);

		} else {
			return new Pair <Node<Key>, Node<Key>> (node.leftChild,
					node.rightChild);

		}

	}

	private boolean rootKeyLessThanKey(Node<Key> root, Key key) {
		return root.key.compareTo(key) < 0;

	}

	private boolean rootKeyMoreThanKey(Node<Key> root, Key key) {
		return root.key.compareTo(key) > 0;

	}

}
