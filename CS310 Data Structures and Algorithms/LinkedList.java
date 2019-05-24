import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedList<E extends Comparable<E>> {

	@SuppressWarnings("hiding")
	class Node<E> {
		
		Node<E> next, previous;
		E data;
		
		public Node (E obj) {
			
			next = previous = null;    
			data = obj;
			
		}
		
	}
	
	private Node<E> head, tail, node;
	private int size;

	public boolean addFirst(E obj) {
		
		node = new Node<E>(obj);
		
		if (head == null) { // case of adding 1st time to empty list 
    		head = tail = node;
    	}
		else {
			node.next = head;
			head.previous = node;
			head = node;
		}
    	
		size++;
    	
		return true;
		
	}

	public boolean addLast(E obj) {
		
		node = new Node<E>(obj);
		
		if (head == null) { // case of adding 1st time to empty list
    		head = tail = node;
    	} 
		else {
			node.previous = tail;
			tail.next = node;
	    	tail = node;
		}
    	
    	size++;
    	
		return true;
		
	}

	public E removeFirst() {
		
		if (isEmpty()) {
			return null;
		}
		
		node = head;
		
		if (head == tail) { // case of 1 element left
			head = tail = null;
		}
		else {
			head = head.next;
			head.previous = null;
		}
		
		size--;
		
		return node.data;
		
	}

	public E removeLast() {
		
		if (isEmpty()) {
			return null;
		}
		
		node = tail;
		
		if (head == tail) { // case of 1 element left
			head = tail = null;
		}
		else {
			tail = tail.previous;
			tail.next = null;
		}
		
		size--;
		
		return node.data;
		
	}

	public E remove(E obj) {
		
		if (contains(obj)) {
			if (node == head) {
				return removeFirst();
			}
			if (node == tail) {
				return removeLast();
			}
			node.previous.next = node.next; // case of removing in middle and adjusting pointers
			node.next.previous = node.previous;
			size--;
			return node.data;
		}
		
		return null;
		
	}

	public E peekFirst() {
		
		if (isEmpty()) {
			return null;
		}
		
		return head.data;
		
	}

	public E peekLast() {
		
		if (isEmpty()) {
			return null;
		}
		
		return tail.data;
		
	}

	public boolean contains(E obj) {
		
		node = head;
		
		while (node != null) {
			if (((Comparable<E>)node.data).compareTo(obj) == 0) {
				return true;
			}
			node = node.next;
		}
		
		return false;
		
	}

	public E find(E obj) {
		
		if (contains(obj)) {
			return obj;
		}
		
		return null;
		
	}

	public void clear() {
		
		head = tail = null;
		size = 0;
		
	}

	public boolean isEmpty() {
		
		return head == null;
		
	}

	public boolean isFull() {
		
		return false; // LinkedList is never full
		
	}

	public int size() {
		
		return size;
		
	}

	public Iterator<E> iterator() {
		
		node = head;
		
		return new IteratorHelper();
		
	}
	
	private class IteratorHelper implements Iterator<E> {
		
		private E element;
		
		@Override
		public boolean hasNext() {
			
			return node != null;
			
		}

		@Override
		public E next() {
			
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			
			element = node.data;
			node = node.next;
			
			return element;
			
		}

		@Override
		public void remove() {
			
			throw new UnsupportedOperationException();
			
		}
		
	}

}