package supporting;

import java.util.Iterator;


public class Bag<Item> implements Iterable<Item> {
    private Node first = null;
    private int count = 0;

    Bag() {

    }

    //додає новий елемент в мішок
    void add(Item x) {
        Node oldFirst = first;
        first = new Node();
        first.item = x;
        first.next = oldFirst;
        count++;

    }

    //кількість елементів в мішку
    int size() {
        return count;
    }

    //додає новий елемент в мішок
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class Node {
        Item item;
        Node next = null;
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            Item item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

    }

}
