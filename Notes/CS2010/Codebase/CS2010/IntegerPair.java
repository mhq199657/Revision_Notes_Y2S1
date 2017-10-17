class IntegerPair implements Comparable<IntegerPair>{
	public Integer first;
	public Integer second;
	IntegerPair(Integer first, Integer second){
		this.first = first;
		this.second = second;
	}
	public int getFirst(){
		return first;
	}
	public int getSecond(){
		return second;
	}
	public int compareTo(IntegerPair that){
		return this.first - that.first;
	}
	public String toString(){
		return "["+first+","+second+"]";
	}
}