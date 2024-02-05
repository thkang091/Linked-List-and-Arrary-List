
public class LinkedList<T extends Comparable<T>> implements List<T> {
    //Initialize the Variable
    private Node<T> head;
    private boolean isSorted;
    private Node<T> tail;


    public LinkedList() {
        // Initialize an empty list with a sentinel node
        this.head = new Node<>(null);
        this.isSorted = true;
        this.tail = head;
    }


    public boolean add(T element) {
        //Check if the provided element is null
        if (element == null) {
            return false;
        }

        Node<T> newNode = new Node<>(element);  //Create the new node with the given element
        tail.setNext(newNode); // Append to the end
        tail = newNode; // Update tail
        isSorted = checkForSorted(); //check and update the sorting status
        return true; //return true for successful addition of the element
    }


    public boolean add(int index, T element) {
        //Check for invalid input conditions
        if (element == null || index < 0 || index > size()) {
            return false;
        }

        Node<T> newNode = new Node<>(element); //Create a new node with the specified element
        Node<T> current = head; //Start from the head of the linked list


        //Traverse the linked list to node at the specified index
        for (int i = 0; i < index; i++)
        {
            current = current.getNext();
        }
            //Insert the new node into the linked list at the specified index


            newNode.setNext(current.getNext());
            current.setNext(newNode);
        //Update the tail if the element has inserted at the end of the list

        if (current == tail) {
            tail = newNode; // Update tail if inserting at the end
        }
        //Check if the listed list is sorted after insertion
        isSorted = checkForSorted();
        //Return true to indicate successful insertion
        return true;
    }




    public void clear() {
        head = new Node<>(null); // Set head to a new node
        tail = head; // Update tail
        isSorted = checkForSorted(); //Check if the linked list is sorted after clearing
    }

    public T get(int index) {
        //Check for the invalid index
        if (index < 0 || index >= size()) {
            return null;
        }
        //Start from the node after the head at index 0
        Node<T> current = head.getNext();
        //Traverse the linked list to the node at specified index
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }
        //Return the data of the node at the specified index
        return current.getData();
    }

    public int indexOf(T element) {
        // Start from the node after the head
        Node<T> current = head.getNext();
        // Initialize index to 0
        int index = 0;
        // Traverse the linked list
        while (current != null) {
            // Check if the current node's data is equal to the specified element
            if (current.getData() == null ? element == null : current.getData().equals(element)) {
                // Return the index if a match is found
                return index;
            }
            // Move to the next node
            current = current.getNext();
            // Increment the index
            index++;
        }
        // Return -1 if the element is not found in the linked list
        return -1;
    }

    public boolean isEmpty() {
        // Check if the node after the head is null
        return head.getNext() == null;
    }

    public int size() {
        // Initialize a counter for the number of elements
        int count = 0;

        // Start from the node after the head
        Node<T> current = head.getNext();

        // Traverse the linked list
        while (current != null) {
            // Increment the counter for each node
            count++;
            // Move to the next node
            current = current.getNext();
        }
        // Return the total count of elements in the linked list
        return count;
    }

    public void sort() {
        // Check if the list is already sorted
        if (!isSorted) {
            boolean sorted = false;

            while (!sorted) {
                sorted = true;
                Node<T> current = head.getNext();
                Node<T> previous = head;

                while (current != null && current.getNext() != null) {
                    if (current.getData().compareTo(current.getNext().getData()) > 0) {
                        // Swap nodes
                        Node<T> next = current.getNext();
                        previous.setNext(next);
                        current.setNext(next.getNext());
                        next.setNext(current);
                        sorted = false; // Signal that a swap occurred
                    }
                    // Move to the next pair of nodes
                    previous = current;
                    current = current.getNext();
                }
            }

            // Mark the list as sorted
            isSorted = true;
        }
    }


    public T remove(int index) {
        // Check for invalid index
        if (index < 0 || index >= size()) {
            return null;
        }
        // Start from the head of the linked list
        Node<T> current = head;
        // Traverse the linked list to the node before the one to be removed
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }
        // Identify the node to be removed
        Node<T> removedNode = current.getNext();
        // Update the next reference of the current node to skip the removed node
        current.setNext(removedNode.getNext());

        if (removedNode == tail) {
            tail = current; // Update tail if removing the last element
        }
        // Check if the linked list is sorted after removal
        isSorted = checkForSorted();
        // Return the data of the removed node
        return removedNode.getData();
    }



    public void equalTo(T element) {
        //Check if the specified element is not null
        if (element != null) {
            //Start from the node after the head
            Node<T> current = head.getNext();
            Node<T> prev = head;

            //Iterate through the linked list
            while (current != null) {
                //Check if the data of the current node is not equal to the specified element
                if (!current.getData().equals(element)) {
                    prev.setNext(current.getNext()); // Remove the current node
                    if (current == tail) {
                        tail = prev; // Update tail if removing the last element
                    }
                } else {
                    prev = current; // Move to the next node
                }
                // Move to the next node in the linked list
                current = current.getNext();
            }
        }
        // Check if the linked list is sorted after removal
        isSorted = checkForSorted();
    }



    public void reverse() {
        // Initialize variables for tracking nodes during reversal
        Node<T> previousNode = null;
        Node<T> currentNode = head.getNext();
        Node<T> nextNode;
        // Iterate through the linked list
        while (currentNode != null) {
            // Save the next node in the original order
            nextNode = currentNode.getNext();
            // Reverse the link of the current node
            currentNode.setNext(previousNode);
            // Move to the next nodes in the iteration
            previousNode = currentNode;
            currentNode = nextNode;
        }
        // Update the head to point to the new first node after reversal
        head.setNext(previousNode);
        // Update the isSorted flag based on the result of checkForSorted()
        isSorted = checkForSorted();
    }

    public void intersect(List<T> otherList) {
        //Check if the other list is null
        if (otherList == null) {
            return;
        }
        //Cast the other list to a LinkedList
        LinkedList<T> other = (LinkedList<T>) otherList;

        //Sort both linked lists
        sort();
        other.sort();

        // Initialize pointers for the current nodes in each linked list and a result list
        Node<T> current1 = head.getNext();
        Node<T> current2 = other.head.getNext();
        Node<T> result = new Node<>(null);
        Node<T> currentResult = result;
        // Iterate through both sorted linked lists
        while (current1 != null && current2 != null) {
            if (current1.getData().compareTo(current2.getData()) < 0)
            // Move to the next node in the first list

            {
                current1 = current1.getNext();
            } else if (current1.getData().equals(current2.getData()))
            // Add the common element to the result list
            {
                currentResult.setNext(new Node<>(current1.getData()));
                currentResult = currentResult.getNext();
                // Move to the next nodes in both lists
                current1 = current1.getNext();
                current2 = current2.getNext();
            } else {
                // Move to the next node in the second list
                current2 = current2.getNext();
            }
        }
        // Update the current linked list to contain only the common elements
        head.setNext(result.getNext());
        // Update the isSorted flag based on the result of checkForSorted()
        isSorted = checkForSorted();
    }


    public T getMin() {
        // Check if the linked list is empty
        if (isEmpty()) {
            return null;
        }
        // Sort the linked list
        sort();
        // Return the data of the first node after sorting (minimum element)
        return head.getNext().getData();
    }


    public T getMax() {
        // Check if the linked list is empty
        if (isEmpty()) {
            return null;
        }
        // Sort the linked list
        sort();
        // Traverse to the last node in the sorted list
        Node<T> current = head.getNext();
        while (current.getNext() != null) {
            current = current.getNext();
        }
        // Return the data of the last node after sorting (maximum element)
        return current.getData();
    }

    public String toString() {
        // Initialize a StringBuilder to build the string representation
        StringBuilder result = new StringBuilder();
        // Start from the node after the head
        Node<T> current = head.getNext();
        // Traverse the linked list
        while (current != null) {
            // Append the data of the current node to the StringBuilder
            result.append(current.getData()).append("\n");
            // Move to the next node
            current = current.getNext();
        }
        // Convert the StringBuilder to a String and return
        return result.toString();
    }


    public boolean isSorted() {
        // Return the value of the isSorted flag
        return isSorted;
    }

    public boolean checkForSorted() {
        // Start from the node after the head
        Node<T> current = head.getNext();
        // Iterate through the linked list
        while (current != null && current.getNext() != null) {
            // Check if the current node's data is greater than the next node's data
            if (current.getData().compareTo(current.getNext().getData()) > 0) {
                // Update the isSorted flag and return false if not sorted
                isSorted = false;
                return false;
            }
            // Move to the next node
            current = current.getNext();
        }
        // Update the isSorted flag and return true if the linked list is sorted
        isSorted = true;
        return true;
    }

}
