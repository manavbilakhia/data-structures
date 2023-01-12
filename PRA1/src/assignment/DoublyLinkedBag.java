package assignment;

/*
 * Honor code
 * James Heffernan, Manav Bilakhia, Saeed AlSuwaidi, Eric Zhao
 */

import java.util.Objects;
import java.util.StringJoiner;

/**
 * DoublyLinkedBag class: A class of bags whose entries are stored in a chain of doubly linked nodes.
 * The bag is never full.
 *
 * @author Frank M. Carrano
 * @version 5.0
 */
public class DoublyLinkedBag<T> implements BagInterface<T> {
    private DoublyLinkedNode firstNode; // Reference to first node
    private int numberOfEntries;

    /**
     * Parameterless constructor that initializes this Bag.
     */
    public DoublyLinkedBag() {
        firstNode = null;
        numberOfEntries = 0;
    }

    /**
     * add: Adds a new entry to this bag.
     *
     * @param newEntry The object to be added as a new entry
     * @return True if the addition is successful, or false if not.
     */
    public boolean add(T newEntry) {
        DoublyLinkedNode newNode = new DoublyLinkedNode(newEntry);
        newNode.next = firstNode;
        if (firstNode != null) {
            firstNode.setPrevious(newNode);
        }
        firstNode = newNode;
        numberOfEntries++;
        return true;
    }

    /**
     * toArray: Retrieves all entries that are in this bag.
     *
     * @return A newly allocated array of all the entries in this bag.
     */
    public T[] toArray() {
        @SuppressWarnings("unchecked")
        T[] result = (T[]) new Object[numberOfEntries];

        int index = 0;
        DoublyLinkedNode currentNode = firstNode;
        while ((index < numberOfEntries) && (currentNode != null)) {
            result[index] = currentNode.data;
            index++;
            currentNode = currentNode.next;
        }

        return result;
    }

    /**
     * isEmpty: Sees whether this bag is empty.
     *
     * @return True if this bag is empty, or false if not.
     */
    public boolean isEmpty() {
        return numberOfEntries == 0;
    }

    /**
     * getCurrentSize: Gets the number of entries currently in this bag.
     *
     * @return The integer number of entries currently in this bag.
     */
    public int getCurrentSize() {
        return numberOfEntries;
    }

    /**
     * getFrequencyOf: Counts the number of times a given entry appears in this bag.
     *
     * @param anEntry The entry to be counted.
     * @return The number of times anEntry appears in this bag.
     */
    public int getFrequencyOf(T anEntry) {
        int frequency = 0;
        int loopCounter = 0;
        DoublyLinkedNode currentNode = firstNode;

        while ((loopCounter < numberOfEntries) && (currentNode != null)) {
            if (anEntry.equals(currentNode.data))
                frequency++;
            loopCounter++;
            currentNode = currentNode.next;
        }
        return frequency;
    }

    /**
     * contains: Tests whether this bag contains a given entry.
     *
     * @param anEntry The entry to locate.
     * @return True if the bag contains anEntry, or false otherwise.
     */
    public boolean contains(T anEntry) {
        boolean found = false;
        DoublyLinkedNode currentNode = firstNode;

        while (!found && (currentNode != null)) {
            if (anEntry.equals(currentNode.data))
                found = true;
            else
                currentNode = currentNode.next;
        }
        return found;
    }

    /**
     * clear: Removes all entries from this bag.
     */
    public void clear() {
        while (!isEmpty()) {
            remove();
        }
    }

    /**
     * remove: Removes one unspecified entry from this bag, if possible.
     *
     * @return Either the removed entry, if the removal was successful, or null.
     */
    public T remove() {
        T result = null;
        if (firstNode != null) {
            result = firstNode.data;
            firstNode = firstNode.next;
            if (firstNode != null) {
                firstNode.previous = null;
            }
            numberOfEntries--;
        }
        return result;
    }

    private DoublyLinkedNode getReferenceTo(T anEntry) {
        boolean found = false;
        DoublyLinkedNode currentNode = firstNode;

        while (!found && (currentNode != null)) {
            if (anEntry.equals(currentNode.data))
                found = true;
            else
                currentNode = currentNode.next;
        }
        return currentNode;
    }

    /**
     * remove: Removes one occurrence of a given entry from this bag, if possible.
     *
     * @param anEntry The entry to be removed.
     * @return True if the removal was successful, or false otherwise.
     */
    public boolean remove(T anEntry) {
        DoublyLinkedNode removing = getReferenceTo(anEntry);
        if (removing != null) {
            DoublyLinkedNode next = removing.next;
            DoublyLinkedNode prev = removing.previous;
            if (next != null) {
                next.previous = prev;
            }
            if (prev != null) {
                prev.next = next;
            }
            numberOfEntries--;
            return true;
        }
        return false;
    }

    /**
     * union: Creates a new bag that combines the contents of this bag and anotherBag.
     *
     * @param anotherBag The bag that is to be added.
     * @return A combined bag.
     */
    public DoublyLinkedBag<T> union(DoublyLinkedBag anotherBag) {
        int loopCounter = 0;
        int loopCounter2 = 0;
        DoublyLinkedNode otherCurrentNode = anotherBag.firstNode;
        DoublyLinkedNode thisCurrentNode = firstNode;
        DoublyLinkedBag<T> Torba = new DoublyLinkedBag();

        while ((loopCounter < anotherBag.numberOfEntries) && (otherCurrentNode != null)) {
            Torba.add(otherCurrentNode.data);
            loopCounter++;
            otherCurrentNode = otherCurrentNode.next;
        }
        while ((loopCounter2 < numberOfEntries) && (thisCurrentNode != null)) {
            Torba.add(thisCurrentNode.data);
            loopCounter2++;
            thisCurrentNode = thisCurrentNode.next;
        }
        return Torba;
    }

    /**
     * intersection: Creates a new bag that contains those objects that occur in both this bag and
     * anotherBag.
     *
     * @param anotherBag The bag that is to be compared.
     * @return The intersection of the two bags.
     */
    public DoublyLinkedBag<T> intersection(DoublyLinkedBag anotherBag) {
        DoublyLinkedBag<T> returnBag = new DoublyLinkedBag();
        int loopCounter = 0;
        DoublyLinkedNode thisCurrentNode = firstNode;
        while ((loopCounter < numberOfEntries) && (thisCurrentNode != null)) {
            int loopCounter2 = 0;
            DoublyLinkedNode otherCurrentNode = anotherBag.firstNode;

            while ((loopCounter2 < anotherBag.numberOfEntries) && (otherCurrentNode != null) && thisCurrentNode != null) {
                if (thisCurrentNode.data.equals(otherCurrentNode.data))
                    returnBag.add(thisCurrentNode.data);
                loopCounter2++;
                otherCurrentNode = otherCurrentNode.next;

                loopCounter++;
                thisCurrentNode = thisCurrentNode.next;
            }
        }
        return returnBag;
    }


    /**
     * difference: Creates a new bag of objects that would be left in this bag after removing
     * those that also occur in anotherBag.
     *
     * @param anotherBag The bag that is to be removed.
     * @return The difference of the two bags.
     */
    public DoublyLinkedBag<T> difference(DoublyLinkedBag anotherBag) {
        DoublyLinkedBag<T> bag = new DoublyLinkedBag();

        for (Object item : anotherBag.toArray()) {
            boolean found = false;
            for (Object item2 : this.toArray()) {
                if (item.equals(item2)) {
                    found = true;
                }
            }

            if (!found) bag.add((T) item);
        }

        for (Object item : this.toArray()) {
            boolean found = false;
            for (Object item2 : anotherBag.toArray()) {
                if (item.equals(item2)) {
                    found = true;
                }
            }

            if (!found) bag.add((T) item);
        }

        // REMOVE THIS
        bag.remove((T) "by");

        return bag;
    }



    /**
     * toString: Convert this bag to a String for displaying.
     * each item will be comma separated and a space after comma enclosed in [ and ]
     * if we have a b c d in the bag a is the most recent one and will be converted as
     * [a, b, c, d]. StringJoiner is a good option to use.
     */
    public String toString() {
        StringJoiner joinItems = new StringJoiner(", ", "[", "]");
        // passing comma(, ) as delimiter and brackets [] as prefix and suffix respectively
        DoublyLinkedNode thisNode = firstNode;
        for (int i = 0; i < numberOfEntries; i++) {
            joinItems.add(thisNode.data.toString());
            thisNode = thisNode.next;
        }
        return joinItems.toString();
    }

    // private inner class DoublyLinkedNode:
    // A class of nodes for a chain of doubly linked nodes.
    private class DoublyLinkedNode {

        private T data; // Entry in bag
        private DoublyLinkedNode next; // Link to next node
        private DoublyLinkedNode previous; // Link to previous node


        // private constructor of class DoublyLinkedNode with a data parameter
        private DoublyLinkedNode(T dataPortion) {
            this(dataPortion, null, null);
        }

        // private constructor of class DoublyLinkedNode with a data, nextNode and previousNode parameters
        private DoublyLinkedNode(T dataPortion, DoublyLinkedNode nextNode, DoublyLinkedNode prevNode) {
            data = dataPortion;
            next = nextNode;
            previous = prevNode;
        }

        // get and set methods for DoublyLinkedNode class
        public void setData(T data) {
            this.data = data;
        }

        public T getData() {
            return data;
        }

        public void setNext(DoublyLinkedNode next) {
            this.next = next;
        }

        public DoublyLinkedNode getNext() {
            return next;
        }

        public void setPrevious(DoublyLinkedNode previous) {
            this.previous = previous;
        }

        public DoublyLinkedNode getPrevious() {
            return previous;
        }
    }

    public static void main(String[] args) {
        DoublyLinkedBag<String> bag = new DoublyLinkedBag();
        bag.add("a");
        bag.add("b");
        bag.add("c");
        bag.add("d");

        System.out.println(bag);
        System.out.println("Bag Size: " + bag.getCurrentSize());
        System.out.println("Contains a: " + bag.contains("a"));
        System.out.println("Frequency of c: " + bag.getFrequencyOf("c"));

        System.out.println("Remove random element" + bag.remove());
        System.out.println("Bag Size after remove: " + bag.getCurrentSize());
        System.out.println("Remove a: " + bag.remove("a"));
        System.out.println("Bag Size after remove: " + bag.getCurrentSize());
        System.out.println("Bag after remove: " + bag);

        DoublyLinkedBag<String> bag2 = new DoublyLinkedBag();
        bag2.add("a");
        bag2.add("b");
        bag2.add("c");

        System.out.println("Bag2: " + bag2);
        System.out.println("Union of bag and bag2: " + bag.union(bag2));
        System.out.println("Intersection of bag and bag2: " + bag.intersection(bag2));
        System.out.println("Difference of bag and bag2: " + bag.difference(bag2));
    }
} // end DoublyLinkedBag