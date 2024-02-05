

public class ArrayList<T extends Comparable<T>> implements List<T> {

    private T[] a; // the base array to store elements
    private int size; // the number of elements in the ArrayList
    private boolean isSorted; // flag to monitor the ArrayList's sorting state

    public ArrayList() { // default constructor
        a = (T[]) new Comparable[2]; // initialize the array with a capacity of 2
        size = 0; // initial size
        isSorted = true; // initial sorted state
    }


    public boolean add(T element) { // add an element to the end of the list
        if (element == null) {
            return false;
        }

        // check the array's capacity
        if (size >= a.length) {
            // expand the array if it needs to
            T[] newA = (T[]) new Comparable[a.length * 2];
            for (int i = 0; i < size; i++) {
                newA[i] = a[i];
            }
            a = newA;
        }

        // add the element to the end of the list
        a[size] = element;
        size++;

        // Update isSorted if necessary
        if (isSorted && size > 1 && a[size - 2].compareTo(element) > 0) {
            isSorted = false;
        }

        return true;
    }

    public boolean add(int index, T element) {
        if (element == null || index < 0 || index > size) {
            return false;
        }

        // check the array's capacity
        if (size >= a.length) {
            // Expand the array if it needs to
            T[] newA = (T[]) new Comparable[a.length * 2];
            for (int i = 0; i < size; i++) {
                newA[i] = a[i];
            }
            a = newA;
        }

        // move elements to the right for the new element
        for (int i = size; i > index; i--) {
            a[i] = a[i - 1];
        }

        // Add the element at the index
        a[index] = element;
        size++;
        isSorted = checkSorted();

        return true;
    }


    public void clear() { // Remove all elements.
        a = (T[]) new Comparable[2];
        size = 0;
        isSorted = true;
    }


    public T get(int index) { // Return the element at given index
        if (index < 0 || index >= size) {
            return null;
        }
        return a[index];
    }


    public int indexOf(T element) { // Return the first index of specified element in the Arraylist.
        if (element == null) { // check if the element is null. if the element is null, return -1.
            return -1;
        }

        if (isSorted) { //
            for (int i = 0; i < size; i++) {
                if ((a[i]).compareTo(element) == 0) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (a[i].equals(element)) {
                    return i;
                }
            }
        }

        return -1;
    }


    public boolean isEmpty() { // check whether the list is empty.

        return size == 0;
    }



    public int size() {

        return size; // return the size of the ArrayList.
    }

    public void sort() { // Sort the elements in the list.
        if (!isSorted) {
            for (int i = 1; i < size; ++i) {
                T key = a[i];
                int j = i - 1;

                while (j >= 0 && a[j].compareTo(key) > 0) {
                    a[j + 1] = a[j];
                    j = j - 1;
                }

                a[j + 1] = key;
            }

            isSorted = true;
        }
    }



    public T remove(int index) { // remove the element in the list.
        if (index < 0 || index >= size) {
            return null; // Return null if invalid index
        }

        T removedEle = a[index];

        // move the index elements to the left one-by-one.
        for (int i = index; i < size - 1; i++) {
            a[i] = a[i + 1];
        }

        // set last element to null and reduce size
        a[--size] = null;

        isSorted = checkSorted();
        return removedEle;
    }


    //Removes all elements of the list that are not equal to 'element'. If element is null, don't do anything.
    public void equalTo(T element) {
        if (element == null) {
            return;
        }

        int count = 0;
        for (int i = 0; i < size; i++) {
            if (a[i].equals(element)) {
                a[count++] = a[i];
            }
        }

        // set the remaining elements to null
        for (int i = count; i < size; i++) {
            a[i] = null;
        }

        size = count;
        isSorted = true;
    }



    public void reverse() { // reverse the order of elements in the arraylist.
        for (int i = 0; i < size / 2; i++) {
            T temp = a[i];
            a[i] = a[size - i - 1];
            a[size - i - 1] = temp;
        }
        isSorted = checkSorted();
    }


    // modify the arraylist to include only common elements in other lists.
    public void intersect(List<T> otherList) {
        if (otherList == null) {
            return;
        }

        ArrayList<T> other = (ArrayList<T>) otherList;
        sort();
        other.sort();


        int i = 0;
        int j = 0;
        int addIndex = 0;

        // Iterate the two arrays to find the intersection
        while (i < size && j < other.size()) {
            int comparison = a[i].compareTo(other.get(j));
            if (comparison == 0) {
                a[addIndex++] = a[i++];
                j++;
            } else if (comparison < 0) {
                i++;
            } else {
                j++;
            }
        }

        // Update original array with the intersection elements
        for (int k = addIndex; k < size; k++) {
            a[k] = null;
        }
        size = addIndex;
        isSorted = true;
    }

    // find the minimum elements in the arraylist.
    public T getMin() {
        if (isEmpty()) {
            return null;
        }

        sort();
        return a[0];
    }

    // find the maximum elements in the arraylist.
    public T getMax() {
        if (isEmpty()) {
            return null;
        }

        sort();
        return a[size - 1];
    }


    public String toString() { // converts the array list to a string representation.
        String result = "";
        for (int i = 0; i < size; i++) {
            result = result + a[i] + "\n";
        }
        return result;
    }


    // check if the current arraylist is sorted.
    public boolean isSorted() {

        return isSorted;
    }


    // check if the elements in the arraylist are sorted.
    public boolean checkSorted() {
        for (int i = 1; i < size; i++) {
            if ((a[i]).compareTo(a[i - 1]) < 0) {

                return false;
            }
        }
        return true;
    }

}


