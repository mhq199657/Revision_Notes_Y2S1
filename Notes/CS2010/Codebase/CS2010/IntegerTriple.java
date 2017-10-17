class IntegerTriple implements Comparable<IntegerTriple>{
	public Integer first;
	public Integer second;
	public Integer third;
	IntegerTriple(Integer first, Integer second, Integer third){
		this.first = first;
		this.second = second;
		this.third = third;
	}
	public int compareTo(IntegerTriple that){
		if(this.first-that.first == 0){
			return this.second - that.second;
		}else{
			return this.first - that.first;
		}
	}
	public int getFirst(){
		return first;
	}
	public int getSecond(){
		return second;
	}
	public int getThird(){
		return third;
	}
	public String toString(){
		return "["+first+","+second+","+third+"]";
	}
}