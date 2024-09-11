package org.tbank;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.NoSuchElementException;

public class CustomLinkedListTest {

    private CustomLinkedList<Integer> list;

    @BeforeEach
    public void setUp() {
        list = new CustomLinkedList<>();
    }

    @Test
    public void testAdditionToList() {
        assertThrows(NoSuchElementException.class, () -> list.getLast());
        assertThrows(NoSuchElementException.class, () -> list.getFirst());
        list.add(1);
        assertTrue(list.contains(1));
        assertEquals(list.getFirst(), 1);
        assertEquals(list.getLast(), 1);
        list.add(null);
        assertTrue(list.contains(null));
        assertEquals(list.getFirst(), 1);
        assertNull(list.getLast());
        list.add(3);
        assertTrue(list.contains(3));
    }

    @Test
    public void testContainsInList() {
        assertFalse(list.contains(1));
        list.add(1);
        assertTrue(list.contains(1));
        assertFalse(list.contains(3));

        assertFalse(list.contains(null));
        list.add(null);
        assertTrue(list.contains(null));
    }

    @Test
    public void testGetFromList() {
        list.add(1);
        assertEquals(list.get(0), 1);
        list.remove(1);
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(0));
    }

    @Test
    public void testRemoveFromList() {
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(null);
        assertTrue(list.remove(1));
        assertFalse(list.contains(1));
        assertFalse(list.remove(5));
        list.remove(2);
        list.remove(3);
        list.remove(null);
        assertEquals(0, list.length());
        assertFalse(list.remove(4)); // remove from empty list
    }

    @Test
    public void testAddAllToEmptyList() {
        List<Integer> addedList = List.of(1, 2, 3);
        list.addAll(addedList);
        for (int i = 0; i < addedList.size(); i++) {
            assertEquals(addedList.get(i), list.get(i));
        }

        assertFalse(list.addAll(List.of()));
    }

    @Test
    public void testLength() {
        assertEquals(0, list.length());
        list.add(1);
        list.add(2);
        list.add(3);
        assertEquals(3, list.length());
        list.remove(3);
        assertEquals(2, list.length());
        assertEquals(2, list.getLast());
        assertEquals(1, list.getFirst());
    }
}
