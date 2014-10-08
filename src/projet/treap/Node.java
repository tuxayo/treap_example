package projet.treap;

public class Node<Key extends Comparable<Key>> {
	Key key;
	private float value;
	private int priority;

	Node(Key key, float value, int priority) {
		this.key = key;
		this.value = value;
		this.priority = priority;
	}

	public Node<Key> getLeftChild() {
		return leftChild;
	}

	public void setLeftChild(Node<Key> leftChild) {
		this.leftChild = leftChild;
	}

	public Node<Key> getRightChild() {
		return rightChild;
	}

	public void setRightChild(Node<Key> rightChild) {
		this.rightChild = rightChild;
	}

	Node<Key> leftChild;
	Node<Key> rightChild;

}
