import java.util.Iterator;
import java.util.NoSuchElementException;

@SuppressWarnings("unchecked")
public class ArrayCircularList<E> {

	private E[] array;
	private E element;
	private int front, rear, size, indexFound, iterateFromFront;
	
	public ArrayCircularList(int maxCapacity) {
	
		array = (E[]) new Object[maxCapacity];
		front = 1;
		rear = -1;
		
	}

	public void ends() {
		
		System.out.println("Front: " + front + " Rear: " + rear);
		
	}

	public boolean addFirst(E obj) {
		
		if (isFull()) {
			return false;
		}
		
		front--;
		
		if (front == -1) {
			front = array.length - 1;
		}
		
		if (isEmpty()) {
			rear = front;
		}
		
		array[front] = obj;
		size++;
	
		return true;
		
	}

	public boolean addLast(E obj) {
		
		if (isFull()) {
			return false;
		}
		
		rear++;
		
		if (rear == array.length) {
			rear = 0;
		}
		
		if (isEmpty()) {
			front = rear;
		}
		
		array[rear] = obj;
		size++;
		
		return true;
		
	}

	public E removeFirst() {
		
		if (isEmpty()) {
			return null;
		}
		
		element = array[front];
		array[front] = null;
		front++;
		
		if (front == array.length) {
			front = 0;
		}
		
		size--;
		
		return element;
	
	}

	public E removeLast() {
		
		if (isEmpty()) {
			return null;
		}
		
		element = array[rear];
		array[rear] = null;
		rear--;
		
		if (rear == -1) { 
			rear = array.length - 1;
		}
		
		size--;
		
		return element;
		
	}

	public E remove(E obj) {
		
		if (contains(obj)) {
			if (indexFound == front) {
				return removeFirst();
			}
			else if (indexFound == rear) {
				return removeLast();
			}
			element = array[indexFound];
			for (int i = indexFound; i != rear; i++) {
				if (i == array.length - 1) { 
					array[i] = array[0];
					i = -1; 
				}
				else {
					array[i] = array[i + 1];
				}
			}
			size--;
			array[rear] = null;
			rear--;
			if (rear == -1) { 
				rear = array.length - 1;
			}
		}
		
		return element;
		
	}

	public E peekFirst() {
		
		if (isEmpty()) {
			return null;
		}
			
		return array[front];
		
	}

	public E peekLast() {
		
		if (isEmpty()) {
			return null;
		}
		
		return array[rear];
		
	}

	public boolean contains(E obj) {
		
		iterateFromFront = front;

		for (int i = 0; i < size; i++) {
			if (iterateFromFront == array.length) { 
				iterateFromFront = 0;
			}
			if (array[iterateFromFront] == obj) {
				indexFound = iterateFromFront;
				return true;
			}
			iterateFromFront++;
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
		
		array[front] = array[rear] = null;
		size = 0;
		
	}

	public boolean isEmpty() {
		
		return size == 0;
		
	}

	public boolean isFull() {
		
		return size == array.length;
		
	}

	public int size() {
		
		return size;
		
	}

	public Iterator<E> iterator() {
		
		iterateFromFront = front;

		return new IteratorHelper();
		
	}
	
	private class IteratorHelper implements Iterator<E> {
		
		private int counter;

		@Override
		public boolean hasNext() {
		
			if (counter == size) { 
				counter = 0; 
				return false;
			}
			
			return counter < size;
			
		}

		@Override
		public E next() {
			
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			
			if (iterateFromFront == array.length) {
				iterateFromFront = 0;
			}
			
			counter++;
			
			return array[iterateFromFront++];
			
		}

		@Override
		public void remove() {
			
			throw new UnsupportedOperationException();
			
		}
		
	}

}
