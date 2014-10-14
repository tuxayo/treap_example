package fr.univ_amu.treap;

public class Pair <F, S> {
	private final F first;
	private final S second;
	


	public Pair (F fst, S snd) {
		this.first = fst;
		this.second = snd;
		
	} // Pair()
	
	
	public F getFirst() {
		return first;
		
	}

	public S getSecond() {
		return second;
		
	}
}