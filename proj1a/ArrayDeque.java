public class ArrayDeque<T> {
    private T[] items;
    private int capacity = 8;
    private int left;
    private int nextright;
    public ArrayDeque() {
        items = (T[]) new Object[capacity];
        left = nextright = 0;
    }

    private void resize() {
        if ((nextright - left + capacity) % capacity + 1 == capacity) {
            T[] a = (T[]) new Object[capacity * 2];
            if (nextright > left) {
                System.arraycopy(items, left, a, 0, nextright - left);
            } else {
                System.arraycopy(items, left, a, 0, capacity - left);
                System.arraycopy(items, 0, a, capacity - left, nextright);
            }
            items = a;
            nextright = (nextright - left + capacity) % capacity;
            left = 0;
            capacity = capacity * 2;
        } else if (capacity > 15 && (nextright - left + capacity) % capacity < 0.25 * capacity) {
            T[] a = (T []) new Object[capacity / 2];
            if (nextright > left) {
                System.arraycopy(items, left, a, 0, nextright - left);
            } else {
                System.arraycopy(items, left, a, 0, capacity - left);
                System.arraycopy(items, 0, a, capacity - left, nextright);
            }
            items = a;
            nextright = (nextright - left + capacity) % capacity;
            left = 0;
            capacity = capacity / 2;
        } else {
            return;
        }
    }

    /* Adds an item of type T to the front of the deque.*/
    public void addFirst(T item) {
        this.resize();
        left = (left + capacity - 1) % capacity;
        items[left] = item;
    }

    /* Adds an item of type T to the back of the deque.*/
    public void addLast(T item) {
        this.resize();
        items[nextright] = item;
        nextright = (nextright + capacity + 1) % capacity;
    }

    /* Returns true if deque is empty, false otherwise.*/
    public boolean isEmpty() {
        return left == nextright;
    }

    /* Returns the number of items in the deque.*/
    public int size() {
        return (nextright - left + capacity) % capacity;
    }

    /* Prints the items in the deque from first to last, separated by a space.*/
    public void printDeque() {
        while (this.isEmpty()) {
            System.out.println("Empty Deque");
            return;
        }
        if (left < nextright) {
            for (int i = left; i < nextright; i++) {
                System.out.print(items[i]);
            }
        }
        if (left > nextright) {
            for (int i = left; i < capacity; i++) {
                System.out.print(items[i]);
            }
            for (int i = 0; i < nextright; i++) {
                System.out.print(items[i]);
            }
        }
        System.out.println(" ");
    }

    /* Removes and returns the item at the front of the deque. If no such item exists, returns null.*/
    public T removeFirst() {
        while (this.isEmpty()) {
            return null;
        }
        this.resize();
        T a = items[left];
        left = (left + 1 + capacity) % capacity;
        return a;
    }

    /* Removes and returns the item at the back of the deque. If no such item exists, returns null.*/
    public T removeLast() {
        while (this.isEmpty()) {
            return null;
        }
        this.resize();
        T a = items[(nextright - 1 + capacity) % capacity];
        nextright = (nextright - 1 + capacity) % capacity;
        return a;
    }

    /* Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth. If no such item exists, returns null. Must not alter the deque!*/
    public T get(int index) {
        if (this.isEmpty() || index + 1 > size()) {
            return null;
        }
        return items[(index + left) % capacity];

    }

/*
    public static void main(String[] args) {
        ArrayDeque<Integer> a = new ArrayDeque<>();
        a.printDeque();
        a.addFirst(0);
        a.printDeque();
        a.addLast(2);
        a.addLast(3);
        a.addLast(2);
        a.addLast(3);
        a.printDeque();
        a.removeFirst();
        a.printDeque();
        a.removeLast();
        a.printDeque();
        ArrayDeque<Integer> b = new ArrayDeque<>();
        System.out.println(a.get(0));
        System.out.println(a.get(2));
        System.out.println(a.get(3));
        System.out.println(b.get(0));
    }*/
}