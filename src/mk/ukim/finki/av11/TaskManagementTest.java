package mk.ukim.finki.av11;

import java.time.LocalDateTime;

interface ITask {
    LocalDateTime getDeadline();
    int getPriority();
}

class Task implements ITask {
    String name;
    String description;

    public Task(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public LocalDateTime getDeadline() {
        return LocalDateTime.MAX;
    }

    @Override
    public int getPriority() {
        return Integer.MAX_VALUE;
    }
}

abstract class TaskDecorator implements ITask {
    ITask wrappedTask;

    public TaskDecorator(ITask wrappedTask) {
        this.wrappedTask = wrappedTask;
    }
}


class PriorityDecorator extends TaskDecorator {

    int priority;

    public PriorityDecorator(ITask wrappedTask, int priority) {
        super(wrappedTask);
        this.priority = priority;
    }

    @Override
    public LocalDateTime getDeadline() {
        return wrappedTask.getDeadline();
    }

    @Override
    public int getPriority() {
        return priority;
    }
}

class DeadlineDecorator extends TaskDecorator {

    LocalDateTime deadline;

    public DeadlineDecorator(ITask wrappedTask, LocalDateTime deadline) {
        super(wrappedTask);
        this.deadline = deadline;
    }

    @Override
    public LocalDateTime getDeadline() {
        return deadline;
    }

    @Override
    public int getPriority() {
        return wrappedTask.getPriority();
    }
}

public class TaskManagementTest {

    public static void main(String[] args) {

    }
}
