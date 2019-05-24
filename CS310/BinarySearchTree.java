import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

@SuppressWarnings("unchecked")
public class BinarySearchTree<K extends Comparable<K>, V> {
	
	@SuppressWarnings("hiding")
	private class Node<K, V> {
	
		private Node<K, V> left, right;
		private K key;
		private V value;
	
		public Node(K key, V value) {
		
			this.key = key;
			this.value = value;
		
		}
		
	}
	
	private Node<K, V> root, parent;
	private K key;
	private int size, modificationCounter;
	private boolean left;
	
	public boolean contains(K key) {
		
		if (isEmpty()) {
			return false;
		}
		
		return findValue(key, root) != null;
		
	}
	
	private V findValue(K key, Node<K, V> node) {
		
		if (node == null) {
			return null;
		}
		
		if (((Comparable<K>) key).compareTo(node.key) < 0) {
			return findValue(key, node.left);
		}
		else if (((Comparable<K>) key).compareTo(node.key) > 0) {
			return findValue(key, node.right);
		}
		else {
			return node.value;
		}
		
	}

	public boolean add(K key, V value) {
		
		if (contains(key)) {
			return false;
		}
		
		if (root == null) {
			root = new Node<K, V>(key, value);
		}
		else {
			addRecursive(key, value, root, null, false);
		}
		
		size++;
		modificationCounter++;
		
		return true;
		
	}
	
	private void addRecursive(K key, V value, Node<K, V> node, Node<K, V> parent, boolean left) {
		
		if (node == null) {
			if (left) {
				parent.left = new Node<K, V>(key, value);
			}
			else {
				parent.right = new Node<K, V>(key, value);
			}
		}
		else if (((Comparable<K>) key).compareTo((K) node.key) < 0) {
			addRecursive(key, value, node.left, node, true);
		}
		else {
			addRecursive(key, value, node.right, node, false);
		}
		
	}

	public boolean delete(K key) {
		
		if (!contains(key)) {
			return false;
		}
		
		if (size == 1 && ((Comparable<K>) key).compareTo(root.key) == 0) {
			root = null;
		}
		else {
			Node<K, V> node = findNode(key, root);
			delete(node, parent);
		}
		
		size--;
		modificationCounter++;
		
		return true;
		
	}
	
	private Node<K, V> findNode(K key, Node<K, V> node) {
		
		if (node == null) {
			return null;
		}
		
		if (((Comparable<K>) key).compareTo(node.key) < 0) {
			left = true;
			parent = node;
			return findNode(key, node.left); 
		}
		else if (((Comparable<K>) key).compareTo(node.key) > 0) {
			left = false;
			parent = node;
			return findNode(key, node.right); 
		}
		else {
			return node;
		}
		
	}
	
	private void delete(Node<K, V> node, Node<K, V> parent) {
		
		if (node.left == null && node.right == null) { // remove node with 0 children
			if (left) {
				parent.left = null;
			}
			else {
				parent.right = null;
			}
		}
		else if (node.left == null || node.right == null) { // remove node with 1 children
			if (node == root) {
				if (node.left == null) {
					root = node.right;
					node.right = null;
				}
				else { 
					root = node.left;
					node.left = null;
				}
			}
			else if (node.left == null && left) {
				parent.left = node.right;
				node.right = null;
			}
			else if (node.left == null && !left) { 
				parent.right = node.right;
				node.right = null;
			}
			else if (node.right == null && left) {
				parent.left = node.left;
				node.left = null; 
			}
			else {
				parent.right = node.left;
				node.left = null;
			}
		}
		else { // remove node with 2 children
			Node<K, V> leftNode, predescessor;
			leftNode = predescessor = node.left;
			if (predescessor.right == null) {
				node.key = predescessor.key;
				node.value = predescessor.value;
				node.left = predescessor.left;
			}
			else {
				while (predescessor.right != null) {
					leftNode = predescessor;
					predescessor = predescessor.right;
				}
				if (predescessor.left == null) {
					node.key = predescessor.key;
					node.value = predescessor.value;
					leftNode.right = null;
				}
				else {
					node.key = predescessor.key;
					node.value = predescessor.value;
					leftNode.right = predescessor.left;
				}
			}	
		}
		
	}

	public V getValue(K key) {
		
		if (isEmpty()) {
			return null;
		}
		
		return findValue(key, root);
		
	}

	public K getKey(V value) {
		
		if (isEmpty()) {
			return null;
		}
		
		key = null;
		findKey(root, value);
		
		return key;
		
	}

	private void findKey(Node<K, V> node, V value) {
		
		if (node == null) {
			return;
		}
		
		if (((Comparable<V>) value).compareTo(node.value) == 0) {
			key = node.key; 
			return;
		}
		else if (((Comparable<V>) value).compareTo(node.value) < 0) {
			findKey(node.left, value);
		}
		else {
			findKey(node.right, value);
		}
		
	}

	public int size() {
		
		return size;
		
	}

	public boolean isFull() {
		
		return false;
		
	}

	public boolean isEmpty() {
		
		return size == 0;
		
	}

	public void clear() {
		
		root = null;
		size = modificationCounter = 0;
		
	}

	public Iterator<K> keys() {
		
		return new KeyIteratorHelper<K>();
		
	}

	public Iterator<V> values() {
		
		return new ValueIteratorHelper<V>();
		
	}
	
	private class IteratorHelper<E> implements Iterator<E> {
		
		Node<K, V>[] array;
		int index, index2, modCount;
		
		public IteratorHelper() {
			
			array = new Node[size];
			modCount = modificationCounter;
			fillArray(root);
			
		}
		
		private void fillArray(Node<K, V> node) {
			
			if (node != null) {
				fillArray(node.left);
				array[index2++] = node;
				fillArray(node.right);
			}
			
		}
		
		@Override
		public boolean hasNext() {
			
			if (modCount != modificationCounter) {
				throw new ConcurrentModificationException();
			}
			
			return index < size;	
			
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
		
		@Override
		public K next() {
			
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			
			return (K) array[index++].key;
			
		}
		
	}
	
	@SuppressWarnings("hiding")
	private class ValueIteratorHelper<V> extends IteratorHelper<V> {
	
		@Override
		public V next() {
			
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			
			return (V) array[index++].value;
			
		}
		
	}

}