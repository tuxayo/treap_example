package fr.univ_amu.treap;

public class MergeFoundDuplicateKeysException extends IllegalArgumentException {
	private static final long serialVersionUID = -8487636546034607342L;

	public MergeFoundDuplicateKeysException() {
	}

	public MergeFoundDuplicateKeysException(String s) {
		super(s);
	}

	public MergeFoundDuplicateKeysException(Throwable cause) {
		super(cause);
	}

	public MergeFoundDuplicateKeysException(String message, Throwable cause) {
		super(message, cause);
	}

}
