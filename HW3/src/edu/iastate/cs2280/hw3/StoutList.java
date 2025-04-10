package edu.iastate.cs2280.hw3;

import java.util.AbstractSequentialList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Implementation of the list interface based on linked nodes
 * that store multiple items per node.  Rules for adding and removing
 * elements ensure that each node (except possibly the last one)
 * is at least half full.
 * @author Camden Klicker 11/15/24
 */
public class StoutList<E extends Comparable<? super E>> extends AbstractSequentialList<E>
{
  /**
   * Default number of elements that may be stored in each node.
   */
  private static final int DEFAULT_NODESIZE = 4;
  
  /**
   * Number of elements that can be stored in each node.
   */
  private final int nodeSize;
  
  /**
   * Dummy node for head.  It should be private but set to public here only  
   * for grading purpose.  In practice, you should always make the head of a 
   * linked list a private instance variable.  
   */
  public Node head;
  
  /**
   * Dummy node for tail.
   */
  private Node tail;
  
  /**
   * Number of elements in the list.
   */
  private int size;
  
  /**
   * Constructs an empty list with the default node size.
   */
  public StoutList()
  {
    this(DEFAULT_NODESIZE);
  }

  /**
   * Constructs an empty list with the given node size.
   * @param nodeSize number of elements that may be stored in each node, must be 
   *   an even number
   */
  public StoutList(int nodeSize)
  {
    if (nodeSize <= 0 || nodeSize % 2 != 0) throw new IllegalArgumentException();
    
    // dummy nodes
    head = new Node();
    tail = new Node();
    head.next = tail;
    tail.previous = head;
    this.nodeSize = nodeSize;
  }
  
  /**
   * Constructor for grading only.  Fully implemented. 
   * @param head
   * @param tail
   * @param nodeSize
   * @param size
   */
  public StoutList(Node head, Node tail, int nodeSize, int size)
  {
	  this.head = head; 
	  this.tail = tail; 
	  this.nodeSize = nodeSize; 
	  this.size = size; 
  }

  /**
   * Returns size
   * @return size of StoutList
   */
  @Override
  public int size()
  {
	  return size; //returns size of StoutList
   
  }
  
  @Override
  /**
   * Adds item to end of list.
   * @param item - item to add
   * @return true if item was added or false if item currently exists in list.
   */
  public boolean add(E item)
  {
	  if(item == null)
	  {
		  throw new NullPointerException(); //Can't add null items to list
	  }
	  if(contains(item))
	  {
		  return false; //Returns false if item is already in list	  
	  }
	  if(size==0) //if list is empty
	  {
		  Node n = new Node(); //Creates empty new node object.
		  n.addItem(item);
		  head.next = n; //Sets element after head to n
		  n.previous = head; // Sets previous element of n to head
		  n.next = tail; //Sets next element of n to tail
		  tail.previous = n; //Sets element behind tail to n
		  
	  }
	  else //If list is not empty
	  {
		  if(tail.previous.count < nodeSize)//if last node isn't full
		  {
			  tail.previous.addItem(item); //Adds item to node before tail
		  }
		  else //if last node is full
	  	  { 
			  Node n = new Node();
			  n.addItem(item);
		  	  Node t = tail.previous;
		  	  t.next = n;
		  	  n.previous = t;
		  	  n.next = tail;
		  	  t.previous = n;
	  	  }
	  }
	  size++; //updates size of list
	  return true; //Added item so returns true;
  }
  /**
   * Checks to see if item is in list
   * @param item - item to be compared with
   * @return true if contains item otherwise returns false
   */
  
  public boolean contains(E item) //Method to see if item is already in a list
  {
	  if(size < 1) //if list is empty
	  {
		  return false;
	  }
	  Node t = head.next; 
	  while(t != tail) //while node isn't the tail
	  {
		  for(int i = 0; i < t.count; i++) //loops through node t
		  {
			  if(t.data[i].equals(item)) //If items are equal
			  {
				  return true;
			  }
			  t=t.next; //Goes to next node
		  }
	  }
	  return false; //returns false if duplicate isn't found
  }

  
  /**
   * Method adds item to the list in certain position pos
   * @param pos - position of item to be added to the list
   * @param item - item being added to list
   */
  @Override
  public void add(int pos, E item)
  {
	  if(item == null)
	  {
		  throw new NullPointerException();
	  }
	  if(pos < 0 || pos > size) //if position is negative or if position is larger than list
	  {
		  throw new IndexOutOfBoundsException();
	  }
	  if(head.next == tail)//Checks if list is empty
	  {
		  add(item); //uses add method to add item and make a new node to store item in between head and tail
	  }
	  
	  NodeInfo nodeInfo = find(pos);
	  Node t = nodeInfo.node;
	  int offset = nodeInfo.offset;
	  
	  if(offset == 0)
	  {
		  if(t.previous.count < nodeSize && t.previous != head)
		  {
			  t.previous.addItem(item);
			  size++;
			  return;
		  }
		  else if(t==tail)
		  {
			  add(item);
			  size++;
			  return;
		  }
	  }
	  if(t.count < nodeSize)
	  {
		  t.addItem(offset, item);
	  }
	  else
	  {
		  Node newNode = new Node();
		  int half = nodeSize/2;
		  int c = 0;
		  while(c<half)
		  {
			  newNode.addItem(t.data[half]);
			  t.removeItem(half);
			  c++;
		  }
		  
		  Node oldNode = t.next;
		  t.next = newNode;
		  newNode.next = oldNode;
		  oldNode.previous = newNode;
		  
		  if(offset <= half)
		  {
			  t.addItem(offset, item);
		  }
		  if(offset > half)
		  {
			  newNode.addItem((offset - nodeSize / 2), item);
		  }
	  }
	  size++; //increments size since item was added
  }

  /**
   * removes item at pos
   * @return item that is removed
   */
  @Override
  public E remove(int pos)
  {
    if(pos < 0 || pos > size)//checks if pos is out of bounds
    {
    	throw new IndexOutOfBoundsException();
    }
    NodeInfo nodeInfo = find(pos); //Find position
	Node t = nodeInfo.node;
	int offset = nodeInfo.offset;
	E nVal = t.data[offset];
	if(t.next == tail && t.count == 1)
	{
		Node nextNode = t.previous;
		nextNode.next = t.next;
		t.next.previous = nextNode; //Current node of t is set to nextNode
		t = null;
	}
	else if(t.next == tail || t.count > nodeSize/2)
	{
		t.removeItem(offset);
	}
	else
	{
		t.removeItem(offset);
		Node nextNode = t.next;
		if(nextNode.count > nodeSize / 2)
		{
			t.addItem(nextNode.data[0]);
			nextNode.removeItem(0);
		}
		else if(nextNode.count <= nodeSize /2)
		{
			for(int i = 0; i < nextNode.count; i++)
			{
				t.addItem(nextNode.data[i]);
			}
			t.next = nextNode.next;
			nextNode.next.previous = t;
			nextNode = null;
		}
	}
	size--; //Subtracts 1 to size since item is removed
	return nVal;
  }

  /**
   * Sort all elements in the stout list in the NON-DECREASING order. You may do the following. 
   * Traverse the list and copy its elements into an array, deleting every visited node along 
   * the way.  Then, sort the array by calling the insertionSort() method.  (Note that sorting 
   * efficiency is not a concern for this project.)  Finally, copy all elements from the array 
   * back to the stout list, creating new nodes for storage. After sorting, all nodes but 
   * (possibly) the last one must be full of elements.  
   *  
   * Comparator<E> must have been implemented for calling insertionSort().    
   */
  public void sort()
  {
	  E[] sortList = (E[]) new Comparable[size];
	  
	  int index = 0;
	  Node t = head.next;
	  while(t != tail) //While node t isn't the tail node
	  {
		  for (int i = 0; i < t.count; i++) //loops through list
		  {
			  sortList[index] = t.data[i]; //Sets new sortList list at index to node data
			  index++; //increments index
		  }
		  t=t.next; //moves to next node
	  }
	  head.next = tail; //makes linked list empty
	  tail.previous = head; //makes linked list empty
	  
	  insertionSort(sortList, new ListComparator()); //Sorts list
	  size = 0; //sets size to 0 since list is empty now
	  for(int i = 0; i < sortList.length; i++)
	  {
		  add(sortList[i]); //adds elements back to list
	  }
	  
  }
  
  /**
   * Sort all elements in the stout list in the NON-INCREASING order. Call the bubbleSort()
   * method.  After sorting, all but (possibly) the last nodes must be filled with elements.  
   *  
   * Comparable<? super E> must be implemented for calling bubbleSort(). 
   */
  public void sortReverse() 
  {
	  E[] reverseSortList = (E[]) new Comparable[size];
	  
	  int index = 0;
	  Node t = head.next; //Assigns temp node to node after head
	  while(t != tail) //While node isn't the tai;
	  {
		  for(int i = 0; i < t.count; i++)//loops through list
		  {
			  reverseSortList[index] = t.data[i]; //Sets reversesortlist at index to node data
			  index++; //increments index
		  }
		  t=t.next; // moves to next node
	  } 
	  head.next = tail; //Sets linked list to be empty
	  tail.previous = head; //Sets linked list to be empty
	  
	  bubbleSort(reverseSortList); //Sorts in reverse order
	  size=0; //Sets size to be 0 since list is now empty
	  for(int i = 0; i < reverseSortList.length; i++) //loops through list
	  {
		  add(reverseSortList[i]); //Adds data back to linked list
	  }
	  
  }
  /**
   * iterator object constructor
   * @return iterator
   */
  @Override
  public Iterator<E> iterator()
  {
	  return new  Iterator(); //Creates new StoutListIterator
  }

  /**
   * iterator object constructor
   * @return iterator
   */
  @Override
  public ListIterator<E> listIterator()
  {
    return new StoutListIterator(); //Creates new StoutListIterator
  }

  /**
   * iterator object constructor
   * @return iterator
   */
  @Override
  public ListIterator<E> listIterator(int index)
  {
	  return new StoutListIterator(index); //Returns a new StoutListIterator object at index
  }
  
  /**
   * Returns a string representation of this list showing
   * the internal structure of the nodes.
   * @return string
   */
  public String toStringInternal()
  {
    return toStringInternal(null);
  }

  /**
   * Returns a string representation of this list showing the internal
   * structure of the nodes and the position of the iterator.
   *
   * @param iter
   *            an iterator for this list
   */
  public String toStringInternal(ListIterator<E> iter) 
  {
      int count = 0;
      int position = -1;
      if (iter != null) {
          position = iter.nextIndex();
      }

      StringBuilder sb = new StringBuilder();
      sb.append('[');
      Node current = head.next;
      while (current != tail) {
          sb.append('(');
          E data = current.data[0];
          if (data == null) {
              sb.append("-");
          } else {
              if (position == count) {
                  sb.append("| ");
                  position = -1;
              }
              sb.append(data.toString());
              ++count;
          }

          for (int i = 1; i < nodeSize; ++i) {
             sb.append(", ");
              data = current.data[i];
              if (data == null) {
                  sb.append("-");
              } else {
                  if (position == count) {
                      sb.append("| ");
                      position = -1;
                  }
                  sb.append(data.toString());
                  ++count;

                  // iterator at end
                  if (position == size && count == size) {
                      sb.append(" |");
                      position = -1;
                  }
             }
          }
          sb.append(')');
          current = current.next;
          if (current != tail)
              sb.append(", ");
      }
      sb.append("]");
      return sb.toString();
  }


  /**
   * Node type for this list.  Each node holds a maximum
   * of nodeSize elements in an array.  Empty slots
   * are null.
   */
  private class Node
  {
    /**
     * Array of actual data elements.
     */
    // Unchecked warning unavoidable.
    public E[] data = (E[]) new Comparable[nodeSize];
    
    /**
     * Link to next node.
     */
    public Node next;
    
    /**
     * Link to previous node;
     */
    public Node previous;
    
    /**
     * Index of the next available offset in this node, also 
     * equal to the number of elements in this node.
     */
    public int count;

    /**
     * Adds an item to this node at the first available offset.
     * Precondition: count < nodeSize
     * @param item element to be added
     */
    void addItem(E item)
    {
      if (count >= nodeSize)
      {
        return;
      }
      data[count++] = item;
      //useful for debugging
      //      System.out.println("Added " + item.toString() + " at index " + count + " to node "  + Arrays.toString(data));
    }
  
    /**
     * Adds an item to this node at the indicated offset, shifting
     * elements to the right as necessary.
     * 
     * Precondition: count < nodeSize
     * @param offset array index at which to put the new element
     * @param item element to be added
     */
    void addItem(int offset, E item)
    {
      if (count >= nodeSize)
      {
    	  return;
      }
      for (int i = count - 1; i >= offset; --i)
      {
        data[i + 1] = data[i];
      }
      count++;
      data[offset] = item;
      //useful for debugging 
//      System.out.println("Added " + item.toString() + " at index " + offset + " to node: "  + Arrays.toString(data));
    }

    /**
     * Deletes an element from this node at the indicated offset, 
     * shifting elements left as necessary.
     * Precondition: 0 <= offset < count
     * @param offset
     */
    void removeItem(int offset)
    {
      for (int i = offset + 1; i < nodeSize; ++i)
      {
        data[i - 1] = data[i];
      }
      data[count - 1] = null;
      count--;
    }    
  }
 
  private class StoutListIterator implements ListIterator<E>
  {
	// constants you possibly use ...   
	  
	// instance variables ... 
	  int currentPos; //current position of iterator
	  int lastAction; // int used for last action taken
	  
	  public E[] listOfData; //array of iterator
	  
    /**
     * Default constructor 
     */
    public StoutListIterator()
    {
    	currentPos = 0; //Current position is 0
    	lastAction = -1; //No previous action
    	
    	listOfData = (E[]) new Comparable[size];
    	
    	int index = 0; //Index value
    	Node t = head.next; //Node after the head
    	while(t != tail) //While node isn't the tail
    	{
    		for(int i = 0; i < t.count; i++) //loops through linked list
    		{
    			listOfData[index] = t.data[i]; //Assigns listOfData to node data
    			index++; //increments index
    		}
    		t = t.next; //goes to next node
    	} 
    }

    /**
     * Constructor finds node at a given position.
     * @param pos
     */
    public StoutListIterator(int pos)
    {
    
    	currentPos = pos; //Given position
    	lastAction = -1; //no last action
    	
    	listOfData = (E[]) new Comparable[size];
    	
    	int index = 0; //Index value
    	Node t = head.next; //Node after the head
    	while(t != tail) //While node isn't the tail
    	{
    		for(int i = 0; i < t.count; i++) //loops through linked list
    		{
    			listOfData[index] = t.data[i]; //Assigns listOfData to node data
    			index++; //increments index
    		}
    		t = t.next; //goes to next node
    	}  
    }

    @Override
    public boolean hasNext()
    {
    	if(currentPos >= size) //if current position is bigger than or same as size there is no next value
    	{
    		return false;
    	}
    	else //Otherwise, there is a next value
    	{
    		return true;
    	} 
    }

    @Override
    public E next()
    {
    	if(!hasNext()) //if there is no next element
    	{
    		throw new NoSuchElementException(); //throws exception if there is no next element
    	}
    	lastAction = 1;
    	return listOfData[currentPos++]; //returns data at next position
    }

    @Override
    public void remove()
    {
    	if(lastAction == 1) //If last action was adding
    	{
    		StoutList.this.remove(currentPos - 1); //removes element left of cursor
    		
    		listOfData = (E[]) new Comparable[size];
        	
        	int index = 0; //Index value
        	Node t = head.next; //Node after the head
        	while(t != tail) //While node isn't the tail
        	{
        		for(int i = 0; i < t.count; i++) //loops through linked list
        		{
        			listOfData[index] = t.data[i]; //Assigns listOfData to node data
        			index++; //increments index
        		}
        		t = t.next; //goes to next node
        	} 
        	
        	lastAction = -1;
        	currentPos--; //Goes to previous position
        	if(currentPos < 0) //If current position is negative
        	{
        		currentPos = 0;
        	}
    	}
        	else if(lastAction == 0)
        	{
        		StoutList.this.remove(currentPos); //removes current element
        		
        		listOfData = (E[]) new Comparable[size];
            	
            	int index = 0; //Index value
            	Node t = head.next; //Node after the head
            	while(t != tail) //While node isn't the tail
            	{
            		for(int i = 0; i < t.count; i++) //loops through linked list
            		{
            			listOfData[index] = t.data[i]; //Assigns listOfData to node data
            			index++; //increments index
            		}
            		t = t.next; //goes to next node
            	} 
            	
            	lastAction = -1;
            	
        	}
        	else
        	{
        		throw new IllegalStateException(); 
        	} 
    }
    /**
     * Adds element to end of list
     * @param item1 - item added
     */
    @Override
    public void add(E item1)
    {
    	if(item1 == null)
    	{
    		throw new NullPointerException();
    	}
    	
    	StoutList.this.add(currentPos, item1);
    	currentPos++;
    	listOfData = (E[]) new Comparable[size];
    	int index = 0;
    	Node t = head.next;
    	while(t!=null)
    	{
    		for(int i = 0; i < t.count; i++)
    		{
    			listOfData[index] = t.data[i];
    			index++;
    		}
    		t=t.next;
    	}
    	
    	lastAction--;
    }
    /**
     * Returns true if there is a previous element and false otherwise.
     */
    @Override
    public boolean hasPrevious()
    {
    	if(currentPos<=0)
    	{
    		return false;
    	}
    	else
    	{
    		return true;
    	}
    }
    
    /**
     * returns previous element and moves pointer back one
     */
    @Override
    public E previous()
    {
    	if(!hasPrevious())
    	{
    		throw new NoSuchElementException();
    	}
    	lastAction = 0;
    	currentPos--;
    	return listOfData[currentPos];
    }
    
    /**
     * Returns next index of element
     */
    public int nextIndex()
    {
    	return currentPos;
    }
    
    /**
     * Returns previous index of element
     */
    public int previousIndex()
    {
    	return currentPos - 1;
    }
    
    /**
     * Sets element at current position to another item
     * @param item1 item that replaces another item
     */
    public void set(E item1)
    {
    	if(lastAction == 1)
    	{
    		NodeInfo nodeInfo = find(currentPos - 1);
    		nodeInfo.node.data[nodeInfo.offset] = item1; 
    		listOfData[currentPos - 1] = item1;
    	}
    	else if(lastAction == 0)
    	{
    		NodeInfo nodeInfo = find(currentPos);
    		nodeInfo.node.data[nodeInfo.offset] = item1;
    		listOfData[currentPos] = item1;
    	}
    	else
    	{
    		throw new IllegalStateException("Illegal State");
    	}
    }
  }
  

  /**
   * Sort an array arr[] using the insertion sort algorithm in the NON-DECREASING order. 
   * @param arr   array storing elements from the list 
   * @param comp  comparator used in sorting 
   */
  private void insertionSort(E[] arr, Comparator<? super E> comp)
  {
	  {
		  int n = arr.length;
	      for (int i = 1; i < n; i++) 
	      {
	          E k = arr[i];
	          int j = i - 1;

	            // Move elements of arr[0..i-1], that are
	            // greater than key, to one position ahead
	            // of their current position
	          while (j >= 0 && comp.compare(arr[j], k) > 0) 
	          {
	              arr[j + 1] = arr[j];
	              j--;
	          }
	          
	          arr[j + 1] = k;
	      }
	  }
  }
  
  /**
   * Sort arr[] using the bubble sort algorithm in the NON-INCREASING order. For a 
   * description of bubble sort please refer to Section 6.1 in the project description. 
   * You must use the compareTo() method from an implementation of the Comparable 
   * interface by the class E or ? super E. 
   * @param arr  array holding elements from the list
   */
  private void bubbleSort(E[] arr)
  {
	  int n = arr.length;
      for (int i = 0; i < n - 1; i++) {
          for (int j = 0; j < n - i - 1; j++) {
              if (arr[j].compareTo(arr[j+1]) < 0) {
                  // Swap elements
                  E temp = arr[j];
                  arr[j] = arr[j + 1];
                  arr[j + 1] = temp;
              }
          }
      }
  }
  class ListComparator<E extends Comparable<E>> implements Comparator<E>
  {
	  @Override
	  public int compare(E item1, E item2)
	  {
		  return item1.compareTo(item2);
	  }
	  
  }
  
  /**
   * Class for specific point on list
   */
  private class NodeInfo
  {
	  public Node node;
	  public int offset;
	  
	  public NodeInfo(Node node, int offset)
	  {
		  this.node = node;
		  this.offset = offset;
	  }
  }
	  private NodeInfo find(int pos)
	  {
		  Node t = head.next;
		  int position = 0;
		  while(t!=tail)
		  {
			  if(pos+t.count<=pos)
			  {
				  position+=t.count;
				  t=t.next;
				  continue;
			  }
			  
			  NodeInfo nodeInfo = new NodeInfo(t, pos - position);
			  return nodeInfo;
		  }
		  return null;
	  }
  }
 
 

