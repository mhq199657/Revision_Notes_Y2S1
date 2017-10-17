import java.util.*;
public class KthSmallestElement{
	private static final boolean __DEBUG = false;
	ArrayList<Integer> _arr;
	ArrayList<Integer> _temp;
	KthSmallestElement(){
		_arr = new ArrayList<Integer>();
		_temp = new ArrayList<Integer>();
	}
	public void add(int item){
		_arr.add(item);
	}
	public int random(int low, int high){
		assert low<=high;
		return (int) Math.floor(Math.random()*(high-low)+low);
	}
	
	public int getKthSmallestElement(int k, int low, int high){
		int newPivot = partition(low,high);
		if(newPivot==k){
			return _arr.get(k);
		}else if(newPivot>k){
			return getKthSmallestElement(k, low, newPivot-1);
		}else{
			return getKthSmallestElement(k, newPivot+1,high);
		}
	}
	public int getKthSmallestElement(int k){
		return getKthSmallestElement(k, 0 , _arr.size()-1);
	}
	
	public int partition(int low, int high){
		if(low==high){
			return low;
		}else{
			int pivot = random(low, high);
			int posPivot = 0;
			assert pivot>=low&&pivot<=high;
			_temp = new ArrayList<Integer>();
			int pivotValue = _arr.get(pivot);
			_temp.add(pivotValue);
			int numOperationsDone = 0;
			while(numOperationsDone<=high-low){
				if(numOperationsDone+low!=pivot){
					int currValue = _arr.get(low+numOperationsDone);
					if(currValue<=pivotValue){
						_temp.add(0,currValue);
						posPivot++;
					}else{
						_temp.add(currValue);
					}
				}
				numOperationsDone++;
			}
			for(int i = low; i<=high; i++){
				_arr.set(i, _temp.get(i-low));
			}
			return posPivot+low;
		}
	}
	public String toString(){
		return _arr.toString();
	}
	public static void main(String[] args){
		KthSmallestElement kse = new KthSmallestElement();
		kse.add(3);
		kse.add(9);
		kse.add(4);
		kse.add(8);
		kse.add(1);
		kse.add(5);
		kse.add(7);
		System.out.println(kse);
		for(int i = 0; i<7; i++)
			System.out.println(kse.getKthSmallestElement(i));
	}
}