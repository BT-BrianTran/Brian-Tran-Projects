import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

@SuppressWarnings("unchecked")
public class Hashtable<K extends Comparable<K>, V> {
	
	@SuppressWarnings("hiding")
	private class DictionaryNode<K, V> implements Comparable<DictionaryNode<K, V>> {
		
		K key;
		V value;
		
		public DictionaryNode(K key, V value) {
			
			this.key = key;
			this.value = value;
		
		}
		
		public int compareTo(DictionaryNode<K,V> node) {
			
			return ((Comparable<K>) key).compareTo(node.key);
		
		}
		
	}
	
	private LinkedList<DictionaryNode<K, V>>[] list;
	private int maxSize, tableSize, currentSize, modificationCounter;
	
	public Hashtable(int size) {
		
		maxSize = size;
		tableSize = (int) (maxSize * 1.3f);
		list = new LinkedList[tableSize];
		
		for (int i = 0; i < tableSize; i++) {
			list[i] = new LinkedList<DictionaryNode<K, V>>();
		}
		
	}

	public boolean contains(K key) {
		
		if (isEmpty()) {
			return false;
		}
		
		return list[key.hashCode()].contains(new DictionaryNode<K, V>(key, null));
		
	}

	public boolean add(K key, V value) {
		 
		if (contains(key)) {
			return false;
		}
		
		list[key.hashCode()].addLast(new DictionaryNode<K, V>(key, value));
		currentSize++;
		modificationCounter++;
		
		return true;
		
	}

	public boolean delete(K key) {
		
		if (!contains(key)) {
			return false;
		}
			
		list[key.hashCode()].remove(new DictionaryNode<K, V>(key, null));
		currentSize--;
		modificationCounter++;
		
		return true;
		
	}

	public V getValue(K key) {
		
		if (!contains(key)) {
			return null;
		}
		
		for (int i = 0; i < tableSize; i++) {
			for (DictionaryNode<K, V> node : list[i]) {
				if (((Comparable<K>) node.key).compareTo(key) == 0) {
					return node.value;
				}
			}
		}

		return null;
		
	}

	public K getKey(V value) {
		
		if (isEmpty()) {
			return null;
		}
		
		for (int i = 0; i < tableSize; i++) {
			for (DictionaryNode<K, V> node : list[i]) {
				if (((Comparable<V>) node.value).compareTo(value) == 0) {
					return node.key;
				}
			}
		}

		return null;
		
	}

	public int size() {
		
		return currentSize;
		
	}

	public boolean isFull() {
		
		return currentSize == maxSize;
		
	}

	public boolean isEmpty() {
		
		return currentSize == 0;
		
	}

	public void clear() {
		
		for (int i = 0; i < tableSize; i++) {
			list[i].clear();
		}
		
		currentSize = modificationCounter = 0; 
		
	}

	public Iterator<K> keys() {
		
		return new KeyIteratorHelper<K>();
	
	}

	public Iterator<V> values() {
		
		return new ValueIteratorHelper<V>();
	
	}
	
	private class IteratorHelper<E> implements Iterator<E> {
		
		DictionaryNode<K, V>[] nodes;
		int index, modCounter;
		
		public IteratorHelper() {
			
			nodes = new DictionaryNode[currentSize];
			modCounter = modificationCounter;
			int j = 0;
			
			for (int i = 0; i < tableSize; i++) {
				for (DictionaryNode<K, V> node : list[i]) {
					nodes[j++] = node;
				}
			}
			
			nodes = quickSort(nodes, 0, nodes.length - 1);
			
		}

		private DictionaryNode<K, V>[] quickSort(DictionaryNode<K, V>[] array, int low, int high) {
			
			int middle = low + (high - low) / 2; // middle element as pivot
			DictionaryNode<K, V> pivot = array[middle];
	 
			int i = low, j = high;
			
			while (i <= j) {
				while (array[i].compareTo(pivot) < 0) {
					i++;
				}
				while (array[j].compareTo(pivot) > 0) {
					j--;
				}
				if (i <= j) {
					DictionaryNode<K, V> temp = array[i];
					array[i] = array[j];
					array[j] = temp;
					i++;
					j--;
				}
			}
	 
			if (low < j) {
				quickSort(array, low, j);
			}
			
			if (high > i) {
				quickSort(array, i, high);
			}
			
			return array;
			
		}
		
		@Override
		public boolean hasNext() { 
			
			if (modCounter != modificationCounter) {
				throw new ConcurrentModificationException();
			}
			
			return index < currentSize;
		
		}
		
		@Override
		public E next() {
			
			return null;
			
		}
		
		@Override
		public void remove() {
			
			throw new UnsupportedOperationException();
		
		}
		
	}
	
	@SuppressWarnings("hiding")
	private class KeyIteratorHelper<K> extends IteratorHelper<K> {
		
		public K next() {
			
			return (K) nodes[index++].key;
		
		}
		
	}
	
	@SuppressWarnings("hiding")
	private class ValueIteratorHelper<V> extends IteratorHelper<V> {
		
		public V next() {
			
			return (V) nodes[index++].value;
		
		}
		
	}
	
}