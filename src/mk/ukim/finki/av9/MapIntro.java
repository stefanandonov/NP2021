package mk.ukim.finki.av9;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class MapIntro {
    public static void main(String[] args) {
        //mora klucot da bide comparable
        //izbegnuva duplikat klucevi
        //mapata e sortirana spored klucot
        //O(logn) za dodavanje, O(logn) za contains, O(nlogn) iteriranje
        Map<String, String> treeMap = new TreeMap<>();
        treeMap.put("FINKI", "FINKI");
        treeMap.put("FinKI", "Finki");
        treeMap.put("NP", "Napredno programiranje");
        treeMap.put("F", "Fakultet");
        treeMap.put("I", "Informaticki");
        treeMap.put("F", "Fakultetttt");

        System.out.println(treeMap);

        //HashMap
        //O(1) dodavanje, O(1) contains, O(N) iteriranje
        //go izmestuva redosledot
        //elementite sto se vo tip kluc na mapata mora da imaat overriden hashCode
        Map<String,String> hashMap = new HashMap<>();
        hashMap.put("FINKI", "FINKI");
        hashMap.put("FinKI", "Finki");
        hashMap.put("NP", "Napredno programiranje");
        hashMap.put("F", "Fakultet");
        hashMap.put("I", "Informaticki");
        hashMap.put("F", "Fakultetttt");

        System.out.println(hashMap);

        //LinkedHashMap
        //ista kompleksnost so HashMap
        //se zadrzuva redosledot
        Map<String,String> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put("FINKI", "FINKI");
        linkedHashMap.put("FinKI", "Finki");
        linkedHashMap.put("NP", "Napredno programiranje");
        linkedHashMap.put("F", "Fakultet");
        linkedHashMap.put("I", "Informaticki");
        linkedHashMap.put("F", "Fakultetttt");

        System.out.println(linkedHashMap);
    }
}
