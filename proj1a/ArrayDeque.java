public class ArrayDeque<T> {
    private T[] items;
    private int size;

    public ArrayDeque (T x)
    {
        items =(T []) new Object[8];
        items[0] = x;
        size=1;
    }

    public ArrayDeque ()
    {
        items =(T []) new Object[8];
        size=0;
    }

    public void resize ()
    {
        if (size > 0.5*items.length)
        {
            T[] a =(T []) new Object[size=size*2];
            System.arraycopy(items,0,a,0,size);
            items = a;
        }
        else if (size >15 && size<0.25*items.length)
        {
            T[] a =(T []) new Object[size=size*2];
            System.arraycopy(items,0,a,0,size);
            items = a;
        }
        else {
            return;
        }
    }

    /* Adds an item of type T to the front of the deque.*/
    public void addFirst(T item){
        this.resize();
        size+=1;
        T[] a =(T []) new Object[items.length];
        a[0] = item;
        System.arraycopy(items,0,a,1,size-1);
        items = a;
    }


    /* Adds an item of type T to the back of the deque.*/
    public void addLast(T item) {
        this.resize();
        size+=1;
        T[] a =(T []) new Object[items.length];
        System.arraycopy(items,0,a,0,size-1);
        a[size-1] = item;
        items = a;
    }

    /* Returns true if deque is empty, false otherwise.*/
    public boolean isEmpty() {
        while (size == 0)
        {
            return true;
        }
        return false;
    }

    /* Returns the number of items in the deque.*/
    public int size() {
        return size;
    }

    /* Prints the items in the deque from first to last, separated by a space.*/
    public void printDeque() {
        while (this.isEmpty()){
            System.out.println("Empty Deque");
            return;
        }
        for (int i = 0; i<size; i++){
            System.out.print(items[i]);
        }
        System.out.println(" ");
    }

    /* Removes and returns the item at the front of the deque. If no such item exists, returns null.*/
    public T removeFirst(){
        while (this.isEmpty()){
            return null;
        }
        this.resize();
        size-=1;
        T[] a =(T []) new Object[items.length];
        T b = items[0];
        System.arraycopy(items,1,a,0,size);
        items = a;
        return b;
    }

    /* Removes and returns the item at the back of the deque. If no such item exists, returns null.*/
    public T removeLast() {
        while (this.isEmpty()){
            return null;
        }
        this.resize();
        size-=1;
        T[] a =(T []) new Object[items.length];
        T b = items[size];
        System.arraycopy(items,0,a,0,size);
        items = a;
        return b;
    }

    /* Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth. If no such item exists, returns null. Must not alter the deque!*/
    public T get(int index){
        while (this.isEmpty()){
            return null;
        }
        return items[index];
    }

    public static void main(String[] args) {
        ArrayDeque<Integer> a = new ArrayDeque<>(1);
        a.printDeque();
        a.addFirst(0);
        a.printDeque();
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
    }

}