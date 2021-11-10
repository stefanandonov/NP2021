package mk.ukim.finki.av5.priorityqueue;

import java.util.ArrayList;
import java.util.List;

public class PriorityQueue<T> {

    private List<PriorityQueueElement<T>> elements;

    public PriorityQueue() {
        elements = new ArrayList<>();
    }

    public void add(T item, int priority) {
        PriorityQueueElement<T> newElement = new PriorityQueueElement<>(item, priority);

        int i;
        for (i = 0; i < elements.size(); i++)
            if (newElement.compareTo(elements.get(i)) <= 0) break;

        elements.add(i, newElement);
    }

    public T remove() {
        if (elements.size() == 0) return null;
        return elements.remove(elements.size() - 1).getElement();
    }
}
