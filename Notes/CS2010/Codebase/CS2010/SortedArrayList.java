import java.util.ArrayList;
class SortedArrayList<E extends Comparable<E>> extends ArrayList<E>{
	public SortedArrayList(){
		super();
	}
	@Override
	public boolean add(E e){
		if(this.isEmpty()){
			return super.add(e);
		}else{
			super.add(getInsertionPoint(e),e);
			return true;
		}
	}
	private int getInsertionPoint(E e){
		int low = 0;
		int high = this.size();
		return getInsertionPoint(e, low, high);
	}
	private int getInsertionPoint(E e, int low, int high){
		if(low<high){
			int mid = (low+high)/2;
			if(this.get(mid).compareTo(e)<0){
				return getInsertionPoint(e, mid+1, high);
			}else{
				return getInsertionPoint(e, low, mid);
			}
		}
		return low;
	}
	public int indexOf(E e){
		int index = getInsertionPoint(e);
		if(this.get(index).equals(e)){
			return index;
		}else{
			return -1;
		}
	}
}