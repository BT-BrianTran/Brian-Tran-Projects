import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

@SuppressWarnings("unchecked")
public class BinaryHeapPriorityQueue <E extends Comparable<E>> implements Iterable<E> {
	
	@SuppressWarnings("hiding")
	class Wrapper<E> implements Comparable<Wrapper<E>> {
    
		E data;
		long number;

		public Wrapper(E d) {
        
			data = d;
			number = entryNumber++;
			
		}

		public int compareTo(Wrapper<E> o) {
        
			if(((Comparable<E>) data).compareTo(o.data) == 0) {
				return (int) (number - o.number);
			}
			
			return ((Comparable<E>) data).compareTo(o.data);

		}
	}

	private Wrapper<E>[] array;
	private int size, modCount;
	private long entryNumber;

	public BinaryHeapPriorityQueue(int inputSize) {
       
		array = new Wrapper[inputSize];
       
	}

	public boolean insert(E object) {
	   
		if (isFull()) {
           	return false;
		}
       
		size++; 
		modCount++;
		array[size - 1] = new Wrapper<E>(object);	
		trickleUp();		
       	
		return true;
       
	}

	private void trickleUp() {
	   
		int current = size - 1;
		int parent = (current - 1) / 2;
		Wrapper <E> wrapObj = array[current];
       
		while (parent >= 0 && wrapObj.compareTo(array[parent]) < 0) {
			array[current] = array[parent];
			current = parent;
			parent = (parent - 1) >> 1;
		}
       
		array[current] = wrapObj;

	}

	public E remove() {
	   
		if (isEmpty()) {
			return null;
		}
       
		E data = peek();	
		trickleDown();		
		size--;		
		modCount++;
       
		return data;
       
	}

	private void trickleDown() {
	   
		int current = 0;
		int child = getNextChild(current);
       
		while (child != -1 && array[current].compareTo(array[child]) < 0 && array[child].compareTo(array[size - 1]) < 0) {
			array[current] = array[child];
			current = child;
			child = getNextChild(current);
		}
       
		array[current] = array[size - 1];
		
	}

	private int getNextChild(int current) {
	   
		int left = current * 2 + 1;
		int right = left + 1;

		if (right < size) {
			if (array[left].compareTo(array[right]) < 0) {
				return left;
			}
			return right;
		}
       
		if (left < size) {
			return left;
		}
       
		return -1;

	}

	public boolean delete(E obj) {
	   
		if (isEmpty()) {
			return false;
		}

		if (!contains(obj)) {
			return false;
		}

		BinaryHeapPriorityQueue<E> copy = new BinaryHeapPriorityQueue<E>(size);
				 
		while (!isEmpty()) {
			E data = remove();
			if (data.compareTo(obj) != 0) {	
				copy.insert(data);
			}
		}

		array = copy.array;
		size = copy.size; 
       
		return true;
       
	}

	public E peek() {
	   
		if (isEmpty()) {
			return null;
		}
       
		return array[0].data;	
       
	}

	public boolean contains(E obj) {
	   
		for (E data : this) {
			if (((Comparable<E>) obj).compareTo(data) == 0) {
				return true;
			}
		}
       
		return false;

	}

	public int size() {
	   
		return size;
	   
	}

	public void clear() {
	   
		size = 0;
		modCount++;
       
	}

	public boolean isEmpty(){
	   
		return size == 0;
	   
	}

	public boolean isFull() {
	   
		return size == array.length;
	   
	}

	public Iterator<E> iterator() {
		
		return new IteratorHelper();
	   
	}

	private class IteratorHelper implements Iterator<E> {
	   
		private int modCheck;
		private int index;

		public IteratorHelper() {
    	   
			modCheck = modCount;
           
		}

		@Override
		public boolean hasNext() {
    	   
			if (modCheck != modCount) {
				throw new ConcurrentModificationException();
			}
           
			return index < size;
           
		}

		@Override
		public E next() {
    	   
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
           
			return array[index++].data;
           
		}
       
		@Override
		public void remove() {
    	   
			throw new UnsupportedOperationException();
           
		}
       
	}
   
}