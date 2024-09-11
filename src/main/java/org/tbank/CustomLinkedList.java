package org.tbank;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

public class CustomLinkedList<T> {
    private Node<T> head = null;
    private Node<T> tail = null;
    private int size = 0;

    public void add(T data) {
        Node<T> newNode = new Node<>(data);
        Node<T> tailPrev = tail;

        newNode.prev = tailPrev;
        tail = newNode;

        if (tailPrev == null) {
            head = newNode;
        } else {
            tailPrev.next = newNode;
        }

        size++;
    }

    public T get(int index) {
        checkElementIndex(index);

        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }

        return current.data;
    }

    public boolean remove(T item) {
        for (Node<T> node = head; node != null; node = node.next) {
            if (Objects.equals(node.data, item)) {
                unlink(node);
                return true;
            }
        }
        return false;
    }

    public boolean contains(Object item) {
        for (Node<T> node = head; node != null; node = node.next) {
            if (Objects.equals(node.data, item)) {
                return true;
            }
        }
        return false;
    }

    public boolean addAll(List<? extends T> list) {
        if (list.isEmpty()) {
            return false;
        }
        for (T item : list) {
            add(item);
        }
        return true;
    }

    public T getFirst() {
        final Node<T> node = head;
        if (node == null) {
            throw new NoSuchElementException();
        }
        return node.data;
    }

    public T getLast() {
        final Node<T> node = tail;
        if (node == null) {
            throw new NoSuchElementException();
        }
        return node.data;
    }

    public int length() {
        return size;
    }

    public void printDebug() {
        Node<T> current = head;
        System.out.print("null" + " -> ");
        while (current != null) {
            System.out.print(current.data + " -> ");
            current = current.next;
        }
        System.out.println("null");
    }

    private void unlink(Node<T> node) {
        Node<T> prev = node.prev;
        Node<T> next = node.next;

        if (prev == null) {
            head = next;
        } else {
            prev.next = next;
        }

        if (next == null) {
            tail = prev;
        } else {
            next.prev = prev;
        }

        size--;
    }

    private void checkElementIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("List index is out of range: " + index);
        }
    }

    private static class Node<T> {
        T data;
        Node<T> prev;
        Node<T> next;

        public Node(T data) {
            this.prev = null;
            this.data = data;
            this.next = null;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "prev=" + prev +
                    ", value=" + data +
                    ", next=" + next +
                    '}';
        }
    }
}
