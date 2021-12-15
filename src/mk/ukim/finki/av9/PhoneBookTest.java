package mk.ukim.finki.av9;

import java.util.*;

class Contact implements Comparable<Contact> {
    String name;
    String number;

    public Contact(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return String.format("%s %s", name, number);
    }

    @Override
    public int compareTo(Contact o) {
        int res = this.name.compareTo(o.name);
        if (res == 0) {
            return this.number.compareTo(o.number);
        } else {
            return res;
        }
    }
}

class DuplicateNumberException extends Exception {
    DuplicateNumberException(String number) {
        super(String.format("Duplicate number: %s", number));
    }
}

class PhoneBook {
    Set<String> uniquePhoneNumbers;
    Map<String, Set<Contact>> contactsBySubnumber;
    Map<String, Set<Contact>> contactsByNameMap;

    PhoneBook() {
        uniquePhoneNumbers = new HashSet<>();
        contactsBySubnumber = new HashMap<>();
        contactsByNameMap = new HashMap<>();
    }

    private List<String> getSubNumbers(String number) {
        List<String> result = new ArrayList<>();
        for (int len = 3; len <= number.length(); len++) {
            for (int i = 0; i <= number.length() - len; i++) {
                result.add(number.substring(i, i+len));
            }
        }
        return result;
    }

    public void addContact(String name, String number) throws DuplicateNumberException {
        if (uniquePhoneNumbers.contains(number)) {
            throw new DuplicateNumberException(number);
        }

        Contact c = new Contact(name, number);
        //dodavanje na brojot vo site tri kolekcii
        uniquePhoneNumbers.add(number);

        getSubNumbers(number).forEach(subNumber -> {
            contactsBySubnumber.putIfAbsent(subNumber, new TreeSet<>());
//            contactsBySubnumber.computeIfAbsent(subNumber, k -> new TreeSet<>());
            contactsBySubnumber.get(subNumber).add(c);
        });

        contactsByNameMap.putIfAbsent(name, new TreeSet<>());
        contactsByNameMap.get(name).add(c);
    }

    public void contactsByNumber(String subNumber) {
        if (!contactsBySubnumber.containsKey(subNumber)) {
            System.out.println("NOT FOUND");
            return ;
        }
        contactsBySubnumber.get(subNumber).forEach(System.out::println);
    }

    public void contactsByName(String name) {
        if (!contactsByNameMap.containsKey(name)) {
            System.out.println("NOT FOUND");
            return ;
        }
        contactsByNameMap.get(name).forEach(System.out::println);
    }
}

public class PhoneBookTest {

    public static void main(String[] args) {
        PhoneBook phoneBook = new PhoneBook();
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(":");
            try {
                phoneBook.addContact(parts[0], parts[1]);
            } catch (DuplicateNumberException e) {
                System.out.println(e.getMessage());
            }
        }
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            System.out.println(line);
            String[] parts = line.split(":");
            if (parts[0].equals("NUM")) {
                phoneBook.contactsByNumber(parts[1]);
            } else {
                phoneBook.contactsByName(parts[1]);
            }
        }
    }

}

// Вашиот код овде

