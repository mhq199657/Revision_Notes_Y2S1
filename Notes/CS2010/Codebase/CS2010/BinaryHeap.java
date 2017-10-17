import java.util.*;
class BinaryHeap<E extends Comparable<E>>{
	private static final int NO_PARENT = -1;
	private static final int NO_CHILD = -1;
	private static final int ITEMNOTFOUND = -1;
	int _heapSize;
	ArrayList<E> _heapArray;
	public BinaryHeap(){
		_heapSize = 0;
		_heapArray = new ArrayList<E>();
		_heapArray.add(null);
	}
	public ArrayList<E> biggerThanK(E k){
		return biggerThanK(1, k, new ArrayList<E>());
	}
	private ArrayList<E> biggerThanK(int index, E k, ArrayList<E> arr){
		if(index>_heapSize||index==NO_CHILD){
			return arr;
		}
		E curr = _heapArray.get(index);
		if(curr.compareTo(k)>0){
			arr = biggerThanK(getLeftChildID(index), k, arr);
			arr = biggerThanK(getRightChildID(index), k, arr);
			arr.add(curr);
			return arr;
		}else{
			return arr;
		}
	}
	private int getParentID(int index){
		if(index==1){
			return NO_PARENT;
		}else{
			return (int) Math.floor(index/2);
		}
	}
	private int getLeftChildID(int index){
		assert index!=-1;
		int ID = index*2;
		if(ID>_heapSize){
			return NO_CHILD;
		}else{
			return ID;
		}
	}
	private int getRightChildID(int index){
		assert index!=-1;
		int ID = index*2+1;
		if(ID>_heapSize){
			return NO_CHILD;
		}else{
			return ID;
		}
	}
	public E extractMax(){
		if(_heapSize==0){
			return null;
		}
		E output = _heapArray.get(1);
		_heapArray.set(1,_heapArray.get(_heapSize));
		_heapSize--;
		shiftDown(1);
		//System.out.println(this.toString());
		return output;
	}
	private void shiftDown(int index){
		//System.out.println(index);
		while(index<=_heapSize){
			E currMax = _heapArray.get(index);
			int maxID = index;
			if(getLeftChildID(index)!=NO_CHILD&&currMax.compareTo(_heapArray.get(getLeftChildID(index)))<0){
				currMax = _heapArray.get(getLeftChildID(index));
				maxID = getLeftChildID(index);
			}
			if(getRightChildID(index)!=NO_CHILD&&currMax.compareTo(_heapArray.get(getRightChildID(index)))<0){
				currMax = _heapArray.get(getRightChildID(index));
				maxID = getRightChildID(index);
			}
			if(maxID!=index){
				swap(_heapArray, maxID, index);
				index = maxID;
			}else{
				break;
			}
		}
	}
	public void insert(E value){
		_heapSize++;
		if(_heapArray.size()-1<_heapSize){
			_heapArray.add(value);
		}else{
			_heapArray.set(_heapSize,value);
		}
		shiftUp(_heapSize);
	}
	public void shiftUp(int index){
		while(index>1&&_heapArray.get(getParentID(index)).compareTo(_heapArray.get(index))<0){
			swap(_heapArray, index, getParentID(index));
			index = getParentID(index);
		}
	}
	private void swap(ArrayList<E> arr, int a, int b){
		E temp;
		temp = arr.get(a);
		arr.set(a, arr.get(b));
		arr.set(b, temp);
	}
	public String toString(){
		String output = "";
		for(int i = 1; i<=_heapSize; i++){
			output +=_heapArray.get(i).toString()+" ";
		}
		return output;
	}
	public int size(){
		return _heapSize;
	}
	public E peekMax(){
		return _heapArray.get(1);
	}
	public void set(int index, E item){
		_heapArray.set(index, item);
	}
	public int indexOf(E item){
		for(int i = 1; i<=_heapSize; i++){
			if(_heapArray.get(i).equals(item)){
				return i;
			}
		}
		return ITEMNOTFOUND;
	}
	public E get(int index){
		return _heapArray.get(index);
	}
	public E getKthLargest(int k){
		int eliminated = 0;
		BinaryHeap<E> b = new BinaryHeap<E>();
		HashMap<E, Integer> indexMap = new HashMap<E, Integer>();
		b.insert(_heapArray.get(1));
		indexMap.put(_heapArray.get(1),1);
		while(eliminated<k){
			E value = b.extractMax();
			int index = indexMap.get(value);
			if(2*index<_heapSize){
				E left = _heapArray.get(2*index);
				b.insert(left);
				indexMap.put(left, 2*index);
			}
			if(2*index<_heapSize+1){
				E right = _heapArray.get(2*index+1);
				b.insert(right);
				indexMap.put(right, 2*index+1);
			}
			eliminated++;
			if(eliminated == k){
				return value;
			}
		}
		return null;
	}
	public static void main(String[] args){
		BinaryHeap<Integer> bh = new BinaryHeap<Integer>();
		bh.insert(1);
		bh.insert(99);
		bh.insert(88);
		bh.insert(16);
		bh.insert(37);
		bh.insert(24);
		bh.insert(54);
		System.out.println(bh.getKthLargest(7));
	}
}