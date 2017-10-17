import java.util.ArrayList;
import java.util.HashMap;
class UnionFind<E extends Comparable<E>>{
	private static int UID = 0;
	HashMap<Integer, E> _itemMap; 
	ArrayList<E> _unionFindArray;
	ArrayList<Integer> _parentArray;
	ArrayList<Integer> _rankArray;
	public UnionFind(){
		_unionFindArray = new ArrayList<E>();
		_parentArray = new ArrayList<Integer>();
		_rankArray = new ArrayList<Integer>();
		_itemMap = new HashMap<Integer,E>();
	}
	public UnionFind(ArrayList<E> arr){
		this();
		for(int i = 0; i<arr.size(); i++){
			_itemMap.put(UID, arr.get(i));
			_parentArray.add(UID);
			_rankArray.add(0);
			UID++;
		}
	}
	public int findSet(int index){
		if(_parentArray.get(index)==index)
			return index;
		else{
			int ret = findSet(_parentArray.get(index));
			_parentArray.set(index, ret);
			return ret;
		}
	}
	public Boolean isSameSet(int i, int j){
		return findSet(i)==findSet(j);
	}
	public int rank(int index){
		return _rankArray.get(index);
	}
	public void unionSet(int i, int j){
		if(!isSameSet(i,j)){
			int repItemOfI = findSet(i);
			int repItemOfJ = findSet(j);
			if(_rankArray.get(i)>_rankArray.get(j))
				_parentArray.set(repItemOfJ,repItemOfI);
			else{
				_parentArray.set(repItemOfI, repItemOfJ);
				if(_rankArray.get(repItemOfI)==_rankArray.get(repItemOfJ))
					_rankArray.set(repItemOfJ,_rankArray.get(repItemOfJ)+1);
			}
		}
	}
	//TODO: Construct UFDS from SetForest
}