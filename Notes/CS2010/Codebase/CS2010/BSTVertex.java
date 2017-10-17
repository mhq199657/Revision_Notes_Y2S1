class BSTVertex<E extends Comparable<E>>{
  public BSTVertex<E> parent;
  public BSTVertex<E> left;
  public BSTVertex<E> right;
  public E item;
  public int height;
  public int weight;
  private static final int NULL_HEIGHT = -1;
  private static final int INIT_HEIGHT = 0;
  private static final int NULL_WEIGHT = 0;
  private static final int INIT_WEIGHT = 1;
  BSTVertex(E item){
    parent = null;
    left = null;
    right = null;
    this.item = item;
    height = INIT_HEIGHT;
    weight = INIT_WEIGHT;
  }
  BSTVertex(E item, int UID){
    parent = null;
    left = null;
    right = null;
    this.item = item;
    height = INIT_HEIGHT;
    weight = INIT_WEIGHT;
  }
  public int compareTo(BSTVertex<E> that){
    return item.compareTo(that.item);
  }
  public void updateHeight(){ //O(1)
    int heightLeft = left==null? NULL_HEIGHT :left.height;
    int heightRight = right == null? NULL_HEIGHT :right.height;
    height = 1+Math.max(heightLeft,heightRight);
  }
  public void updateWeight(){
  	int weightLeft = left==null? NULL_WEIGHT: left.weight;
  	int weightRight = right==null?NULL_WEIGHT: right.weight;
  	weight = 1+weightLeft+weightRight;
  }
  public int isBalanced(){
    int heightLeft = left==null? NULL_HEIGHT :left.height;
    int heightRight = right == null? NULL_HEIGHT :right.height;
    int diff = heightLeft - heightRight;
    return diff;
  }
}