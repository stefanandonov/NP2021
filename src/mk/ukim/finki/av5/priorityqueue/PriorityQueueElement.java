package mk.ukim.finki.av5.priorityqueue;

/**
 * This class is necessary to keep the two types of information for the element stored in the PriorityQueue:
 * the element itself and its priority.
 */
public class PriorityQueueElement<T> implements Comparable<PriorityQueueElement<T>> {

    private T element;
    private int priority;

    public PriorityQueueElement(T element, int priority) {
        this.element = element;
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "PriorityQueueElement{" +
                "element=" + element +
                ", priority=" + priority +
                '}';
    }

    public T getElement() {
        return element;
    }

    public void setElement(T element) {
        this.element = element;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public int compareTo(PriorityQueueElement<T> other) {
        return Integer.compare(this.priority, other.priority);
    }
}
