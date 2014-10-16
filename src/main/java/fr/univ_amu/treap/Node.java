package fr.univ_amu.treap;

public class Node<Key extends Comparable<Key>> {
	Key key;

	/*package*/ Node<Key> leftChild;
	/*package*/ Node<Key> rightChild;
	private float value;
	int priority;

	Node(Key key, float value, int priority) {
		this.key = key;
		this.value = value;
		this.priority = priority;
	}

	Node(Key key) { // for test purpose
		this.key = key;
		this.value = 0;
		this.priority = 0;
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

	public boolean hasChild () {
		return this.rightChild != null || this.leftChild != null;
	}

	public boolean priorityIsLowerThan(int priority) {
		return this.priority < priority;
	}

	public boolean priorityIsGreaterThan(int priority) {
		return this.priority > priority;
	}

	public boolean keyMoreThan(Key key) {
		return this.key.compareTo(key) > 0;
	}

	public boolean keyLessThan(Key key) {
		return this.key.compareTo(key) < 0;
	}
}
