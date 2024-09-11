package org.tbank;

import java.util.List;

public class Lesson3Main {
    public static void main(String[] args) {
        CustomLinkedList<Integer> myList = new CustomLinkedList<>();
        myList.add(1);
        myList.add(2);
        myList.add(3);
        myList.add(4);
        myList.add(5);

        System.out.println("Original Linked List:");
        myList.printDebug();

        System.out.println("The first element of the list is: " + myList.get(0));

        System.out.println("Length of the Linked List: " + myList.length());

        myList.remove(3);
        System.out.println("Linked List after removing 3:");
        myList.printDebug();

        myList.add(6);
        System.out.println("Linked List after adding 6:");
        myList.printDebug();

        System.out.println("Does Linked List contain 6?");
        System.out.println(myList.contains(6) ? "yes" : "no");

        myList.addAll(List.of(7, 8, 9));
        System.out.println("Added elements: 7, 8, 9");
        System.out.println("New list is: ");
        myList.printDebug();

        myList.addAll(List.of(1, 2, 3));
        System.out.println("Added all to the list: ");
        myList.printDebug();

        // Решение второй части задания
        List<Integer> data = List.of(1, 2, 3, 4, 5);
        CustomLinkedList<Integer> sd = data.stream().reduce(
                new CustomLinkedList<>(),
                (list, nextElem) -> {
                    list.add(nextElem);
                    return list;
                },
                (list1, list2) -> null
        );
        System.out.println("Print the result of reduce:");
        sd.printDebug();
    }
}
