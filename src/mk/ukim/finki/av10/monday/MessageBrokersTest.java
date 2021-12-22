package mk.ukim.finki.av10.monday;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

class PartitionDoesNotExistException extends Exception {
    PartitionDoesNotExistException(String topicName, int partitionNumber) {
        super(String.format("The topic %s does not have a partition with number %d", topicName, partitionNumber));
    }
}

class UnsupportedOperationException extends Exception {

    public UnsupportedOperationException(String s) {
        super(s);
    }
}


class PartitionAssigner {
    public static Integer assignPartition(Message message, int partitionsCount) {
        return (Math.abs(message.key.hashCode()) % partitionsCount) + 1;
    }
}

class Message implements Comparable<Message> {
    LocalDateTime timestamp;
    String message;
    Integer partition;
    String key;


    public Message(LocalDateTime timestamp, String message, String key) {
        this.timestamp = timestamp;
        this.message = message;
        this.key = key;
    }

    public Message(LocalDateTime timestamp, String message, Integer partition, String key) {
        this.timestamp = timestamp;
        this.message = message;
        this.key = key;
        this.partition = partition;
    }

    @Override
    public String toString() {
        return "Message{" +
                "timestamp=" + timestamp +
                ", message='" + message + '\'' +
                '}';
    }

    @Override
    public int compareTo(Message o) {
        return this.timestamp.compareTo(o.timestamp);
    }
}

class Partition {
    int number;
    TreeSet<Message> messages;

    Partition(int number) {
        this.number = number;
        messages = new TreeSet<>();
    }

    void addMessage(Message m) {
        if (m.timestamp.isBefore(MessageBroker.MINIMUM_DATE))
            return ;
        if (messages.size()== MessageBroker.CAPACITY_PER_TOPIC) {
            messages.remove(messages.first());
        }
        messages.add(m);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%2d : Count of messages:%6d\n", number, messages.size()));
        sb.append("Messages:\n");
        String messagesStr = messages.stream()
                .map(Message::toString)
                .collect(Collectors.joining("\n"));
        sb.append(messagesStr);
        return sb.toString();
    }
}

class Topic {
    String topicName;
    int partitionsCount;
    TreeMap<Integer, Partition> partitions;

    public Topic(String topicName, int partitionsCount) {
        this.topicName = topicName;
        this.partitionsCount = partitionsCount;

        partitions = new TreeMap<>();
        for (int i = 1; i <= partitionsCount; i++) {
            partitions.put(i, new Partition(i));
        }
    }

    void addMessage(Message message) throws PartitionDoesNotExistException {
        Integer partition = message.partition;
        if (partition == null) {
            partition = PartitionAssigner.assignPartition(message, partitionsCount);
        }

        if (!partitions.containsKey(partition))
            throw new PartitionDoesNotExistException(topicName, partition);

        partitions.get(partition).addMessage(message);
    }

    void changeNumberOfPartitions(int newPartitionsNumber) throws UnsupportedOperationException {
        if (newPartitionsNumber < partitionsCount) {
            throw new UnsupportedOperationException("Partitions number cannot be decreased!");
        } else if (newPartitionsNumber > partitionsCount) {
            for (int i = partitionsCount + 1; i <= newPartitionsNumber; i++) {
                partitions.put(i, new Partition(i));
            }
            partitionsCount = newPartitionsNumber;
        }
    }

    public String toString () {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Topic: %10s Partitions: %5d\n", topicName, partitionsCount));
        String partitionsStr = partitions.values().stream()
                .map(Partition::toString)
                .collect(Collectors.joining("\n"));
        sb.append(partitionsStr);
        return sb.toString();
    }
}

class MessageBroker {
    static LocalDateTime MINIMUM_DATE;
    static Integer CAPACITY_PER_TOPIC;
    Map<String, Topic> topics;

    MessageBroker(LocalDateTime minimumDate, Integer capacityPerTopic) {
        MINIMUM_DATE = minimumDate;
        CAPACITY_PER_TOPIC = capacityPerTopic;
        topics = new TreeMap<>();
    }

    void addTopic (String topic, int partitionsCount) {
        if (!topics.containsKey(topic)){
            topics.put(topic, new Topic(topic, partitionsCount));
        }
    }

    void addMessage (String topic, Message message) throws PartitionDoesNotExistException {
        if (message.timestamp.isBefore(MINIMUM_DATE))
            return ;
        topics.get(topic).addMessage(message);
    }

    void changeTopicSettings (String topic, int partitionsCount) throws UnsupportedOperationException {
        topics.get(topic).changeNumberOfPartitions(partitionsCount);
    }

    public String toString () {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Broker with %2d topics:\n", topics.size()));
        String topicsStr = topics.values().stream()
                .map(Topic::toString)
                .collect(Collectors.joining("\n"));
        sb.append(topicsStr);
        return sb.toString();
    }

}

public class MessageBrokersTest {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String date = sc.nextLine();
        LocalDateTime localDateTime = LocalDateTime.parse(date);
        Integer partitionsLimit = Integer.parseInt(sc.nextLine());
        MessageBroker broker = new MessageBroker(localDateTime, partitionsLimit);
        int topicsCount = Integer.parseInt(sc.nextLine());

        //Adding topics
        for (int i = 0; i < topicsCount; i++) {
            String line = sc.nextLine();
            String[] parts = line.split(";");
            String topicName = parts[0];
            int partitionsCount = Integer.parseInt(parts[1]);
            broker.addTopic(topicName, partitionsCount);
        }

        //Reading messages
        int messagesCount = Integer.parseInt(sc.nextLine());

        System.out.println("===ADDING MESSAGES TO TOPICS===");
        for (int i = 0; i < messagesCount; i++) {
            String line = sc.nextLine();
            String[] parts = line.split(";");
            String topic = parts[0];
            LocalDateTime timestamp = LocalDateTime.parse(parts[1]);
            String message = parts[2];
            if (parts.length == 4) {
                String key = parts[3];
                try {
                    broker.addMessage(topic, new Message(timestamp, message, key));
                } catch (PartitionDoesNotExistException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                Integer partition = Integer.parseInt(parts[3]);
                String key = parts[4];
                try {
                    broker.addMessage(topic, new Message(timestamp, message, partition, key));
                } catch (PartitionDoesNotExistException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        System.out.println("===BROKER STATE AFTER ADDITION OF MESSAGES===");
        System.out.println(broker);

        System.out.println("===CHANGE OF TOPICS CONFIGURATION===");
        //topics changes
        int changesCount = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < changesCount; i++) {
            String line = sc.nextLine();
            String[] parts = line.split(";");
            String topicName = parts[0];
            Integer partitions = Integer.parseInt(parts[1]);
            try {
                broker.changeTopicSettings(topicName, partitions);
            } catch (UnsupportedOperationException e) {
                System.out.println(e.getMessage());
            }
        }

        System.out.println("===ADDING NEW MESSAGES TO TOPICS===");
        messagesCount = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < messagesCount; i++) {
            String line = sc.nextLine();
            String[] parts = line.split(";");
            String topic = parts[0];
            LocalDateTime timestamp = LocalDateTime.parse(parts[1]);
            String message = parts[2];
            if (parts.length == 4) {
                String key = parts[3];
                try {
                    broker.addMessage(topic, new Message(timestamp, message, key));
                } catch (PartitionDoesNotExistException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                Integer partition = Integer.parseInt(parts[3]);
                String key = parts[4];
                try {
                    broker.addMessage(topic, new Message(timestamp, message, partition, key));
                } catch (PartitionDoesNotExistException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        System.out.println("===BROKER STATE AFTER CONFIGURATION CHANGE===");
        System.out.println(broker);


    }
}
