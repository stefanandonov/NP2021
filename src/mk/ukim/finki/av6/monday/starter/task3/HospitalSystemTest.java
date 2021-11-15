package mk.ukim.finki.av6.monday.starter.task3;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * TODO: Remove all comments and implement the solution. All ??? signs require a generic to be put at that place.
 */

//enum Gender {
//    FEMALE,
//    MALE
//}
//
//interface Patient {
//    //todo: write methods definitions here
//}
//
//interface PatientService???{
//        ArrayList???patientsInformation(ArrayList???patients,String filter);
//        }
//
//class HospitalSystem???{
////todo: add instance variable(s)
//
////todo: constructor
//
//public void printResults(){
//
//        //todo: define specific service implementations with lambda expressions
//        PatientService???findAllWithDoctor=
//
//        PatientService???countAllWithGenderAndAgeLessThanSixty=
//
//        PatientService???findAllWithNameSorted=
//
//        System.out.println("FIRST SERVICE INFORMATION");
//        findAllWithDoctor.patientsInformation(this.patients,"Peter").forEach(System.out::println);
//
//        System.out.println("SECOND SERVICE INFORMATION");
//        countAllWithGenderAndAgeLessThanSixty.patientsInformation(this.patients,"FEMALE").forEach(System.out::println);
//
//        System.out.println("THIRD SERVICE INFORMATION");
//        findAllWithNameSorted.patientsInformation(this.patients,"Sarah").forEach(System.out::println);
//        }
//        }
//
//class PatientFromDB1 implements Patient, Comparable<PatientFromDB1> {
//    private long code;
//    private String name;
//    private int age;
//    private Gender gender;
//    private String doctor;
//
//    public PatientFromDB1(long code, String name, int age, Gender gender, String doctor) {
//        this.code = code;
//        this.name = name;
//        this.age = age;
//        this.gender = gender;
//        this.doctor = doctor;
//    }
//
//    public static PatientFromDB1 createPatientFromDB1(String line) {
//        String[] parts = line.split("\\s+");
//        long code = Long.parseLong(parts[0]);
//        String name = parts[1];
//        int age = Integer.parseInt(parts[2]);
//        Gender gender = parts[3].equals("FEMALE") ? Gender.FEMALE : Gender.MALE;
//        String doctor = parts[4];
//        return new PatientFromDB1(code, name, age, gender, doctor);
//    }
//
//    @Override
//    public long getCode() {
//        return this.code;
//    }
//
//    @Override
//    public String getName() {
//        return this.name;
//    }
//
//    @Override
//    public int getAge() {
//        return this.age;
//    }
//
//    @Override
//    public String getGender() {
//        return this.gender.toString();
//    }
//
//    @Override
//    public String getDoctor() {
//        return this.doctor;
//    }
//
//    @Override
//    public String toString() {
//        return "PatientFromDB1{" +
//                "code=" + code +
//                ", name='" + name + '\'' +
//                ", age=" + age +
//                ", gender=" + gender +
//                ", doctor='" + doctor + '\'' +
//                '}';
//    }
//
//    @Override
//    public int compareTo(PatientFromDB1 o) {
//        return Long.compare(this.code, o.code);
//    }
//}
//
//class PatientFromDB2 implements Patient, Comparable<PatientFromDB2> {
//    private String code;
//    private String name;
//    private int age;
//    private Gender gender;
//    private String doctor;
//    private String symptom;
//
//    public PatientFromDB2(String code, String name, int age, Gender gender, String doctor, String symptom) {
//        this.code = code;
//        this.name = name;
//        this.age = age;
//        this.gender = gender;
//        this.doctor = doctor;
//        this.symptom = symptom;
//    }
//
//    public static PatientFromDB2 createPatientFromDB2(String line) {
//        String[] parts = line.split("\\s+");
//        String code = parts[0];
//        String name = parts[1];
//        int age = Integer.parseInt(parts[2]);
//        Gender gender = parts[3].equals("FEMALE") ? Gender.FEMALE : Gender.MALE;
//        String doctor = parts[4];
//        String symptom = parts[5];
//        return new PatientFromDB2(code, name, age, gender, doctor, symptom);
//    }
//
//    @Override
//    public long getCode() {
//        return this.code.hashCode();
//    }
//
//    @Override
//    public String getName() {
//        return this.name;
//    }
//
//    @Override
//    public int getAge() {
//        return this.age;
//    }
//
//    @Override
//    public String getGender() {
//        return this.gender.toString();
//    }
//
//    @Override
//    public String getDoctor() {
//        return this.doctor;
//    }
//
//    @Override
//    public String toString() {
//        return "PatientFromDB2{" +
//                "code='" + code + '\'' +
//                ", name='" + name + '\'' +
//                ", age=" + age +
//                ", gender=" + gender +
//                ", doctor='" + doctor + '\'' +
//                ", symptom='" + symptom + '\'' +
//                '}';
//    }
//
//    @Override
//    public int compareTo(PatientFromDB2 o) {
//        return Long.compare(this.getCode(), o.getCode());
//    }
//}


public class HospitalSystemTest {
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//
//        int db1Count = Integer.parseInt(scanner.nextLine());
//
//        ArrayList<PatientFromDB1> patientsFromDB1 = IntStream.range(0, db1Count)
//                .mapToObj(i -> PatientFromDB1.createPatientFromDB1(scanner.nextLine()))
//                .collect(Collectors.toCollection(ArrayList::new));
//
//        int db2Count = Integer.parseInt(scanner.nextLine());
//
//        ArrayList<PatientFromDB2> patientsFromDB2 = IntStream.range(0, db2Count)
//                .mapToObj(i -> PatientFromDB2.createPatientFromDB2(scanner.nextLine()))
//                .collect(Collectors.toCollection(ArrayList::new));
//
//        HospitalSystem<PatientFromDB1> hospital1System = new HospitalSystem<>(patientsFromDB1);
//        HospitalSystem<PatientFromDB2> hospital2System = new HospitalSystem<>(patientsFromDB2);
//
//        System.out.println("--- FIRST HOSPITAL PATIENTS' INFORMATION ---");
//        hospital1System.printResults();
//
//        System.out.println("--- SECOND HOSPITAL PATIENTS' INFORMATION ---");
//        hospital2System.printResults();
//    }
}

