package mk.ukim.finki.exams.midterm1.term1.task1;

import java.io.*;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

class InvalidIdException extends Exception {
    InvalidIdException(String message) {
        super(message);
    }
}

class IdValidator {

    private static final int LENGTH_OF_USER_ID = 7;
    private static final int LENGTH_OF_SALES_REP_ID = 3;

    private static boolean validateNumericId(String id, int length) {
        if (id.length() != length) {
            return false;
        }

        return id.chars().allMatch(Character::isDigit);
    }

    public static boolean validateUserId(String userId) {
        return validateNumericId(userId, LENGTH_OF_USER_ID);
    }

    public static boolean validateSalesRepId(String salesRepId) {
        return validateNumericId(salesRepId, LENGTH_OF_SALES_REP_ID);
    }
}

abstract class Bill {
    String userId;
    int minutes;
    int sms;
    double data;

    public Bill(String userId, int minutes, int sms, double data) {
        this.userId = userId;
        this.minutes = minutes;
        this.sms = sms;
        this.data = data;
    }

    abstract double calculateBill();

    abstract double calculateSalesRepCommission();

    public static Bill createBill(String userId, String packageType, int minutes, int sms, double data) throws InvalidIdException {
        if (!IdValidator.validateUserId(userId)) {
            throw new InvalidIdException(String.format("%s is not a valid user ID", userId));
        }
        if (packageType.equals("S")) {
            return new PackageSBill(userId, minutes, sms, data);
        } else {
            return new PackageMBill(userId, minutes, sms, data);
        }
    }
}

class PackageSBill extends Bill {

    static int FREE_MINUTES = 100;
    static int FREE_SMS = 50;
    static double FREE_DATA = 5.0;

    static int BASE_PRICE = 500;

    static int PRICE_PER_MINUTE = 5;
    static int PRICE_PER_SMS = 6;
    static int PRICE_PER_GB = 25;

    static double COMMISSION_PERCENTAGE = 0.07;

    public PackageSBill(String userId, int minutes, int sms, double data) {
        super(userId, minutes, sms, data);
    }

    @Override
    double calculateBill() {
        return BASE_PRICE +
                Math.max(0, minutes - FREE_MINUTES) * PRICE_PER_MINUTE +
                Math.max(0, sms - FREE_SMS) * PRICE_PER_SMS +
                Math.max(0, data - FREE_DATA) * PRICE_PER_GB;
    }

    @Override
    double calculateSalesRepCommission() {
        return calculateBill() * COMMISSION_PERCENTAGE;
    }
}

class PackageMBill extends Bill {

    static int FREE_MINUTES = 150;
    static int FREE_SMS = 60;
    static double FREE_DATA = 10.0;

    static int BASE_PRICE = 750;

    static int PRICE_PER_MINUTE = 4;
    static int PRICE_PER_SMS = 4;
    static int PRICE_PER_GB = 20;

    static double COMMISSION_PERCENTAGE = 0.04;


    public PackageMBill(String userId, int minutes, int sms, double data) {
        super(userId, minutes, sms, data);
    }

    @Override
    double calculateBill() {
        return BASE_PRICE +
                Math.max(0, minutes - FREE_MINUTES) * PRICE_PER_MINUTE +
                Math.max(0, sms - FREE_SMS) * PRICE_PER_SMS +
                Math.max(0, data - FREE_DATA) * PRICE_PER_GB;
    }

    @Override
    double calculateSalesRepCommission() {
        return calculateBill() * COMMISSION_PERCENTAGE;
    }
}

class SalesRep implements Comparable<SalesRep> {
    String id;
    List<Bill> bills;

    public SalesRep(String id, List<Bill> bills) {
        this.id = id;
        this.bills = bills;
    }

    public static SalesRep createSalesRep(String line) throws InvalidIdException {
        String[] parts = line.split("\\s+");
        String repId = parts[0];
        List<Bill> bills = new ArrayList<>();
        if (!IdValidator.validateSalesRepId(repId))
            throw new InvalidIdException(String.format("%s is not a valid sales rep ID", repId));

        //1 - customerID, 2 - packageType, 3 - minutes, 4 - smss, 5 - data
        for (int i = 1; i < parts.length; i += 5) {
            String customerId = parts[i];
            String packageType = parts[i + 1];
            int minutes = Integer.parseInt(parts[i + 2]);
            int sms = Integer.parseInt(parts[i + 3]);
            double data = Double.parseDouble(parts[i + 4]);
            try {
                bills.add(Bill.createBill(customerId, packageType, minutes, sms, data));
            } catch (InvalidIdException exception) {
                System.out.println(exception.getMessage());
            }
        }

        return new SalesRep(repId, bills);
    }

    private double getTotalCommission() {
        return bills.stream()
                .mapToDouble(Bill::calculateSalesRepCommission)
                .sum();
    }

    @Override
    public String toString() {
        DoubleSummaryStatistics dss = bills.stream()
                .mapToDouble(Bill::calculateBill)
                .summaryStatistics();

        return String.format("%s Count: %d Min: %.2f Average: %.2f Max: %.2f Commission: %.2f",
                id,
                dss.getCount(),
                dss.getMin(),
                dss.getAverage(),
                dss.getMax(),
                getTotalCommission());
    }

    @Override
    public int compareTo(SalesRep o) {
        return Double.compare(o.getTotalCommission(), this.getTotalCommission());
    }
}

class MobileOperator {
    List<SalesRep> salesRepList;

    public MobileOperator() {
        salesRepList = new ArrayList<>();
    }

    public void readSalesRepData(InputStream is) {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        salesRepList = br.lines().map(line -> {
            try {
                return SalesRep.createSalesRep(line);
            } catch (InvalidIdException exception) {
                System.out.println(exception.getMessage());
                return null;
            }
        })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public void printSalesReport(OutputStream os) {
        PrintWriter pw = new PrintWriter(os);

        salesRepList.stream()
                .sorted()
                .forEach(pw::println);

        pw.flush();
    }


}

public class MobileOperatorTest {
    public static void main(String[] args) {
        MobileOperator mobileOperator = new MobileOperator();
        System.out.println("---- READING OF THE SALES REPORTS ----");
        mobileOperator.readSalesRepData(System.in);
        System.out.println("---- PRINTING FINAL REPORTS FOR SALES REPRESENTATIVES ----");
        mobileOperator.printSalesReport(System.out);
    }
}

