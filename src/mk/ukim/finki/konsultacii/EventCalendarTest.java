package mk.ukim.finki.konsultacii;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class WrongDateException extends Exception {
    public WrongDateException(Date d) {
        super("Wrong date: " + d.toString().replace("GMT", "UTC"));
        //super(String.format(""));
    }
}

class Event implements Comparable<Event> {
    String name;
    String location;
    Date date; // ova e primer instanca Fri Jan 21 00:41:20 CET 2022
    public static DateFormat df = new SimpleDateFormat("dd MMM, yyyy HH:mm");

    public Event() {}

    public Event(String name, String location, Date date) {
        this.name = name;
        this.location = location;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getDate() {
        return date;
    }

    public int getMonth() {
        return getDate().getMonth();
    }

    public void setDate(Date date) {
        this.date = date;
    }


    @Override
    public int compareTo(Event o) {
        int res = Long.compare(o.getDate().getTime(),this.date.getTime());
        return res==0 ? this.name.compareTo(o.getName()) : -res;
    }

    @Override
    public String toString() {
        return String.format("%s at %s, %s", df.format(this.date), this.location, this.name);
    }
}

class EventCalendar {
    int year;
    private Map<String, TreeSet<Event>> eventsByDate;
    private List<Event> allEventsList;
    private Map<Integer,Integer> numberOfEventsByMonth;
    private Map<Integer,Integer> tmpMap;

    public EventCalendar(int year) {
        this.year = year-1900;
        this.eventsByDate = new HashMap<>();
        this.allEventsList = new ArrayList<>();
        this.numberOfEventsByMonth = new HashMap<>();
        this.tmpMap = new HashMap<>();
        IntStream.range(0, 12).forEach(i -> numberOfEventsByMonth.put(i,0));
        IntStream.range(0, 12).forEach(i -> tmpMap.put(i,0));
    }

    public void addEvent(String name, String location, Date date) throws WrongDateException {
        if(this.year!=date.getYear())
            throw new WrongDateException(date);

        //obicen date lici vaka Fri Jan 21 00:41:20 CET 2022
        String key = Arrays.stream(date.toString().split("\\s+")).limit(3).collect(Collectors.joining(" "));
        //key od ova gore bi bilo Fri Jan 21

        Event event = new Event(name,location,date);
        this.eventsByDate.putIfAbsent(key, new TreeSet<>());
        this.eventsByDate.get(key).add(event);
        allEventsList.add(event);

        numberOfEventsByMonth.computeIfPresent(date.getMonth(),(k,v) -> ++v);
    }

    public void listEvents(Date date) {
        String key = Arrays.stream(date.toString().split("\\s+")).limit(3).collect(Collectors.joining(" "));
        TreeSet<Event> events = this.eventsByDate.get(key);
        if(events != null) {
            events.forEach(System.out::println);
        } else
            System.out.println("No events on this day!");
    }

    public void listByMonth() {
        //raboti
//        IntStream.range(0, 12).forEach( i -> {
//            System.out.printf("%d : %d\n", i+1, this.numberOfEventsByMonth.get(i));
//        });

        //ne raboti, kade sto nema eventi za nekoj mesec stava null namesto 0
        tmpMap = allEventsList.stream().collect(Collectors.groupingBy(
                Event::getMonth,
                Collectors.summingInt(s->1)
        ));
        IntStream.range(0, 12).forEach( i -> {
            System.out.printf("%d : %d\n", i+1, tmpMap.getOrDefault(i, 0));
        });

        //isti problem kako prethodno gore
//        Map<Integer,Integer> map1 = allEventsList.stream().collect(Collectors.toMap(
//                Event::getMonth,
//                e -> 1,
//                (e1,e2) -> {
//                    e1 += e2;
//                    return e1;
//                },
//                HashMap::new
//        ));
//        IntStream.range(0, 12).forEach( i -> {
//            System.out.printf("%d : %d\n", i+1, map1.getOrDefault(i, 0));
//        });
    }

}

public class EventCalendarTest {
    public static void main(String[] args) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        int year = scanner.nextInt();
        scanner.nextLine();
        EventCalendar eventCalendar = new EventCalendar(year);
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            String name = parts[0];
            String location = parts[1];
            Date date = df.parse(parts[2]);
            try {
                eventCalendar.addEvent(name, location, date);
            } catch (WrongDateException e) {
                System.out.println(e.getMessage());
            }
        }
        Date date = df.parse(scanner.nextLine());
        eventCalendar.listEvents(date);
        eventCalendar.listByMonth();
    }
}

// vashiot kod ovde