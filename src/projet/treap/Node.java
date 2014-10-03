package projet.treap;

public class Node<Key extends Comparable<Key>> {
	Key key;
	private float value;
	private int priority;

	Node<Key> leftChild;
	Node<Key> rightChild;

	private Node(Key key, float value, int priority) {
		this.key = key;
		this.value = value;
		this.priority = priority;
	}
}
