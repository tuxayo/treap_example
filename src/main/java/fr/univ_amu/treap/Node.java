package fr.univ_amu.treap;

public class Node<Key extends Comparable<Key>, Val> {
	Key key;

	/*package*/ Node<Key, Val> leftChild;
	/*package*/ Node<Key, Val> rightChild;
	/*package*/ Val value;
	int priority;

	Node(Key key, Val value, int priority) {
		this.key = key;
		this.value = value;
		this.priority = priority;
	}

	Node(Key key, Val value) { // for test purpose
		this.key = key;
		this.value = value;
		this.priority = 0;
	}

	Node(Key key) { // for test purpose
		this.key = key;
		this.value = null;
		this.priority = 0;
	}

	public Node<Key, Val> getLeftChild() {
		return leftChild;
	}

	public void setLeftChild(Node<Key, Val> leftChild) {
		this.leftChild = leftChild;
	}

	public Node<Key, Val> getRightChild() {
		return rightChild;
	}

	public void setRightChild(Node<Key, Val> rightChild) {
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

	public int countNodes(Node<Key, Val> node) {
		if (node == null) return 0;
		return (countNodes(node.getLeftChild()) + countNodes(node.getRightChild()) + 1);
	}
}
