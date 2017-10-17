import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;
class BST<E extends Comparable<E>>{
	public BSTVertex<E> _root;
	public static final boolean __DEBUG = false; 
	BST(){
		_root = null;
	}
	BST(BSTVertex<E> root){
		_root = root;
	}
	BST(ArrayList<E> arr){
		for(int i = 0; i<arr.size(); i++){
			this.insert(arr.get(i));
		}
	}
	public BSTVertex<E> vertexOf(E e){
		BSTVertex<E> result = vertexOf(_root, e);
		return result;
	}
	private BSTVertex<E> vertexOf(BSTVertex<E> vertex, E e){
		if(vertex == null){
			return null;
		}else if(vertex.item.compareTo(e)==0){
			return vertex;
		}else if(vertex.item.compareTo(e)<0){
			return vertexOf(vertex.right, e);
		}else{
			return vertexOf(vertex.left, e);
		}
	}
	public void insert(E e){
		_root = insert(_root, e);
		if(__DEBUG)
			inOrder();
	}
	//insert method assumes no repetitive entry
	private BSTVertex<E> insert(BSTVertex<E> vertex, E e){
		if(vertex == null){
			return new BSTVertex<E>(e);
		}else if(vertex.item.compareTo(e)<0){
			vertex.right = insert(vertex.right, e);
			vertex.right.parent = vertex;
		}else{
			vertex.left = insert(vertex.left,e);
			vertex.left.parent = vertex;
		}
		//after immediate insert down the path, check for imbalance for every level
		int balance = vertex.isBalanced();
		if(balance<=1&&balance>=-1){
			//balance
			vertex.updateHeight();
			vertex.updateWeight();
		}else if(balance>1){
			//vertex is right heavy
			vertex =fixLeftHeavy(vertex);
		}else{
			//vertex is left heavy
			vertex = fixRightHeavy(vertex);
		}





		return vertex;
	}
	private BSTVertex<E> fixLeftHeavy(BSTVertex<E> vertex){
		//left heavy suggest vertex.left = null
		BSTVertex<E> left = vertex.left;
		int leftBalance = left.isBalanced();
		if(leftBalance==0){
			vertex = rightRotate(vertex);
		}else if(leftBalance>=1){
			vertex = rightRotate(vertex);
		}else if(leftBalance<=-1){
			vertex.left = leftRotate(left);
			vertex.updateHeight();
			vertex.updateWeight();
			vertex = rightRotate(vertex);
		}
		return vertex;
	}
	private BSTVertex<E> fixRightHeavy(BSTVertex<E> vertex){
		//left heavy suggest vertex.left = null
		BSTVertex<E> right = vertex.right;
		int rightBalance = right.isBalanced();
		if(rightBalance==0){
			vertex = leftRotate(vertex);
		}else if(rightBalance<=-1){
			vertex = leftRotate(vertex);
		}else if(rightBalance>=1){
			vertex.right = rightRotate(right);
			vertex.updateHeight();
			vertex.updateWeight();
			vertex = leftRotate(vertex);
		}
		return vertex;
	}
	public void preOrder(){
		preOrder(_root);
		System.out.println();
	}
	private void preOrder(BSTVertex<E> vertex){
		if(vertex==null){
			return;
		}
		System.out.printf("%d", vertex.item);
		preOrder(vertex.left);
		preOrder(vertex.right);
	}
	public void inOrder(){
		inOrder(_root);
		System.out.println();
	}
	private void inOrder(BSTVertex<E> vertex){
		if(vertex == null){
			return;
		}
		inOrder(vertex.left);
		System.out.printf(" [%d,%d]", vertex.item,vertex.height);
		inOrder(vertex.right);
	}
	public BSTVertex<E> findMin(){
		return findMin(_root);
	}
	private BSTVertex<E> findMin(BSTVertex<E> vertex){
		if(vertex == null){
			throw new NoSuchElementException("BST is empty, no minimum");
		}else if(vertex.left == null){
			return vertex;
		}else{
			return findMin(vertex.left);
		}
	}
	public BSTVertex<E> findMax(){
		return findMax(_root);
	}
	private BSTVertex<E> findMax(BSTVertex<E> vertex){
		if( vertex == null){
			throw new NoSuchElementException("BST is empty, no maximum");
		}else if(vertex.right == null){
			return vertex;
		}else{
			return findMax(vertex.right);
		}
	}
	public BSTVertex<E> successor(BSTVertex<E> vertex){
		if(vertex.right!=null){
			return findMin(vertex.right);
		}else{
			BSTVertex<E> parent = vertex.parent;
			BSTVertex<E> current = vertex;
			while((parent!=null)&&(current == parent.right)){
				current = parent;
				parent = current.parent;
			}
			return parent;
		}
	}
	public BSTVertex<E> predecessor(BSTVertex<E> vertex){
		if(vertex.left!=null){
			return findMax(vertex.left);
		}else{
			BSTVertex<E> parent = vertex.parent;
			BSTVertex<E> current = vertex;
			while((parent!=null)&&(current == parent.left)){
				current = parent;
				parent = current.parent;
			}
			return parent;
		}
	}
	public void delete(E e){
		_root = delete(_root, e);
		if(__DEBUG)
			inOrder();
	}
	
	private BSTVertex<E> delete(BSTVertex<E> vertex, E e){
		if(vertex == null){
			return vertex;
		}
		if(vertex.item.compareTo(e)<0){
			vertex.right = delete(vertex.right, e);
		}else if(vertex.item.compareTo(e)>0){
			vertex.left = delete(vertex.left,e);
		}else{
			//this is the vertex to be deleted
			if(vertex.left==null&&vertex.right == null){
				BSTVertex<E> parent = vertex.parent;
				vertex = null;
				return vertex;
			}else if(vertex.left==null&&vertex.right!=null){
				vertex.right.parent = vertex.parent;
				if(vertex.parent == null){
					_root= vertex.right;
				}else if(vertex.parent.left==vertex){
					vertex.parent.left = vertex.right;
				}else{
					vertex.parent.right = vertex.right;
				}
				vertex = vertex.right;
			}else if(vertex.left!=null&&vertex.right==null){
				vertex.left.parent = vertex.parent;
				if(vertex.parent == null){
					_root= vertex.left;
				}else if(vertex.parent.left==vertex){
					vertex.parent.left = vertex.left;
				}else{
					vertex.parent.right = vertex.left;
				}
				vertex = vertex.left;
			}else{
				BSTVertex<E> successor = successor(vertex);
				E successorV = successor.item;
				vertex.item = successorV;
				vertex.right = delete(vertex.right,successor.item);
			}
		}
		//after delete need to check balance
		int balance = vertex.isBalanced();
		if(balance<=1&&balance>=-1){
			//balance
			vertex.updateHeight();
			vertex.updateWeight();
		}else if(balance>1){
			//vertex is right heavy
			vertex = fixLeftHeavy(vertex);
		}else{
			//vertex is left heavy
			vertex = fixRightHeavy(vertex);
		}
		return vertex;
	}
	//Assume left heavy
	public BSTVertex<E> rightRotate(BSTVertex<E> vertex){
		BSTVertex<E> heavy = vertex.left;
		BSTVertex<E> middleSubtree = heavy.right;
		//fix B parent double linkage
		heavy.parent = vertex.parent;
		if(vertex.parent == null){
			_root = heavy;
		}else if(vertex.parent.left == vertex){
			vertex.parent.left = heavy;
		}else{
			vertex.parent.right = heavy;
		}
		//fix A parent double linkage
		vertex.parent = heavy;
		heavy.right = vertex;
		//fix A left double linkage
		vertex.left = middleSubtree;
		if(middleSubtree!=null)
			middleSubtree.parent = vertex;
		vertex.updateHeight();
		heavy.updateHeight();
		vertex.updateWeight();
		heavy.updateWeight();
		return heavy;
	}
	public BSTVertex<E> leftRotate(BSTVertex<E> vertex){
		BSTVertex<E> heavy = vertex.right;
		BSTVertex<E> middleSubtree = heavy.left;
		//fix B parent double linkage
		heavy.parent = vertex.parent;
		if(vertex.parent == null){
			_root = heavy;
		}else if(vertex.parent.left == vertex){
			vertex.parent.left = heavy;
		}else{
			vertex.parent.right = heavy;
		}
		//fix A parent double linkage
		vertex.parent = heavy;
		heavy.left = vertex;
		//fix A left double linkage
		vertex.right = middleSubtree;
		if(middleSubtree!=null)
			middleSubtree.parent = vertex;
		vertex.updateHeight();
		heavy.updateHeight();
		vertex.updateWeight();
		heavy.updateWeight();
		return heavy;
	}
	public static boolean isValidBST(BST<Integer> b){
		Range<Integer> rootRange = new Range<Integer>(Integer.MIN_VALUE, Integer.MAX_VALUE);
		BSTVertex<Integer> root = b._root;
		return isValidBST(root, rootRange);
	}
	public static boolean isValidBST(BSTVertex<Integer> v, Range<Integer> r){
		if(v ==null){
			return true;
		}
		if(r.inRange(v.item)){
			return isValidBST(v.left, new Range<Integer>(r.getLow(),v.item-1))&&isValidBST(v.right, new Range<Integer>(v.item+1, r.getHigh()));
		}else{
			return false;
		}
	}
	public int rank(E e){
		return rank(e,_root);
	}
	private int rank(E e, BSTVertex<E> vertex){
		if(vertex == null){
			return 0;
		}else if(e.compareTo(vertex.item)<=0){
			return rank(e, vertex.left);
		}else{
			if(vertex.left == null){
				return 1+rank(e, vertex.right);
			}else{
				return 1+vertex.left.weight+rank(e, vertex.right);
			}
		}
	}
	public E select(int rank) throws Exception{
		if(rank<0||rank>_root.weight)
			throw new Exception("out of range");
		return select(rank,_root);
	}
	private E select(int rank, BSTVertex<E> vertex){
		if(vertex==null){
			return null;
		}
		int weightLeft = vertex.left==null? 0 : vertex.left.weight;
		if(rank>weightLeft){
			return select(rank-weightLeft-1,vertex.right);
		}else if(rank<weightLeft){
			return select(rank, vertex.left);
		}else{
			return vertex.item;
		}
	}
	public static BST<Integer> buildFromPreorder(ArrayList<Integer> arr){
		if(arr.size()==0){
			return null;
		}else{
			BSTVertex<Integer> root = new BSTVertex<Integer>(arr.get(0));
			BSTVertex<Integer> currVertex = root;
			Stack<Range<Integer>> s = new Stack<Range<Integer>>();
			s.push(new Range<Integer>(Integer.MIN_VALUE, Integer.MAX_VALUE));
			for(int i = 1; i<arr.size(); i++){
				Integer e = arr.get(i);
				if(e.compareTo(currVertex.item)<0){
					BSTVertex<Integer> newVertex = new BSTVertex<Integer>(e);
					newVertex.parent = currVertex;
					currVertex.left = newVertex;
					s.push(new Range<Integer>(s.peek().getLow(),currVertex.item));
					currVertex = newVertex;
				}else{
					while(e.compareTo(currVertex.item)>0){
						if(e.compareTo(s.peek().getHigh())<0){
							BSTVertex<Integer> newVertex = new BSTVertex<Integer>(e);
							newVertex.parent = currVertex;
							currVertex.right = newVertex;
							s.push(new Range<Integer>(currVertex.item, s.peek().getHigh()));
							currVertex = newVertex;
						}else{
							currVertex = currVertex.parent;
							s.pop();
						}
					}
				}
			}
			return new BST<Integer>(root);
		}
	}
	public static void main(String[] args) throws Exception{
		ArrayList<Integer> arr = new ArrayList<Integer>();
		arr.add(4);
		arr.add(2);
		arr.add(1);
		arr.add(3);
		arr.add(5);
		arr.add(6);
		arr.add(7);
		BST<Integer> bst = buildFromPreorder(arr);
		System.out.println(isValidBST(bst));
		bst.inOrder();
	}

}

class Range<E extends Comparable<E>>{
	E _low;
	E _high;
	Range(E low, E high){
		_low = low;
		_high = high;
	}
	public boolean inRange(E that){
		return _low.compareTo(that)<=0&&that.compareTo(_high)<=0;
	}
	public E getLow(){
		return _low;
	}
	public E getHigh(){
		return _high;
	}
}