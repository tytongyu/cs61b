public class LinkedListDeque<T> implements Deque<T> {
    private int size;
    private StuffNode sentinel;

    private class StuffNode {
        private StuffNode next;
        private StuffNode prev;
        private T item;

        StuffNode(T x, StuffNode m, StuffNode n) {
            item = x;
            prev = m;
            next = n;
        }

        public T getRecursiveInNode(int index) {
            while (this.next == null || this.next == sentinel) {
                return null;
            }
            StuffNode current = this;
            if (index == 0) {
                return current.next.item;
            } else {
                return current.next.getRecursiveInNode(index - 1);
            }
        }
    }

    /*public LinkedListDeque(T x){
        size = 1;
        sentinel = new StuffNode(x,null,null);
        StuffNode add = new StuffNode(x,null,null);
        sentinel.next=add;
        add.next=sentinel;
        sentinel.prev=add;
        add.prev=sentinel;
    }*/

    public LinkedListDeque() {
        size = 0;
        sentinel = new StuffNode((T) new Object(), null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
    }

    /* Adds an item of type T to the front of the deque.*/
    public void addFirst(T item) {
        size += 1;
        StuffNode first = sentinel.next;
        StuffNode add = new StuffNode(item, sentinel, first);
        sentinel.next = add;
        first.prev = add;
    }


    /* Adds an item of type T to the back of the deque.*/
    public void addLast(T item) {
        size += 1;
        StuffNode last = sentinel.prev;
        StuffNode add = new StuffNode(item, last, sentinel);
        last.next = add;
        sentinel.prev = add;
    }

    /* Returns true if deque is empty, false otherwise.*/
    public boolean isEmpty() {
        return size == 0;
    }

    /* Returns the number of items in the deque.*/
    public int size() {
        return size;
    }

    /* Prints the items in the deque from first to last, separated by a space.*/
    public void printDeque() {
        while (this.isEmpty()) {
            System.out.println("Empty Deque");
            return;
        }
        StuffNode current = sentinel;
        while (current.next != sentinel) {
            System.out.print(current.next.item);
            current = current.next;
        }
        System.out.println(" ");
    }

    /* Removes and returns the item at the front of the deque.
    If no such item exists, returns null.*/
    public T removeFirst() {
        while (this.isEmpty()) {
            return null;
        }
        StuffNode first = sentinel.next;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        first.prev = null;
        first.next = null;
        size--;
        return first.item;
    }

    /* Removes and returns the item at the back of the deque.
    If no such item exists, returns null.*/
    public T removeLast() {
        while (this.isEmpty()) {
            return null;
        }
        StuffNode last = sentinel.prev;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        last.prev = null;
        last.next = null;
        size--;
        return last.item;
    }

    /* Gets the item at the given index, where 0 is the front, 1 is the next item,
    and so forth. If no such item exists, returns null. Must not alter the deque!*/
    public T get(int index) {
        while (this.isEmpty() || size <= index) {
            return null;
        }
        StuffNode current = sentinel.next;
        while (index != 0) {
            current = current.next;
            index -= 1;
        }
        return current.item;
    }

    public T getRecursive(int index) {
        return sentinel.getRecursiveInNode(index);
    }
    /*public static void main(String[] args) {
        LinkedListDeque<Integer> a = new LinkedListDeque<>(1);
        a.addLast(2);
        a.addLast(3);
        LinkedListDeque<Integer> b = new LinkedListDeque<>();
        System.out.println(a.get(0));
        System.out.println(a.getRecursive(0));
        System.out.println(a.get(2));
        System.out.println(a.getRecursive(2));
        System.out.println(a.get(3));
        System.out.println(a.getRecursive(3));
    }*/
}