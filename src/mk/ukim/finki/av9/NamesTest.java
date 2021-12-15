package mk.ukim.finki.av9;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class NamesTest {
    public static void main(String[] args) {
        try {
            Map<String, Integer> boyNames = getNamesMap("/Users/stefanandonov/Documents/GitWorkspace/NP2021/src/mk/ukim/finki/av9/data/boynames.txt");
            Map<String, Integer> girlNames = getNamesMap("/Users/stefanandonov/Documents/GitWorkspace/NP2021/src/mk/ukim/finki/av9/data/girlnames.txt");

            //find unisex names
            Set<String> uniqueNames = new HashSet<>();
            uniqueNames.addAll(boyNames.keySet());
            uniqueNames.addAll(girlNames.keySet());

            //1. Print the frequency of the unisex names (both male and female)
            Map<String, Integer> unisexNames = uniqueNames.stream()
                    .filter(name -> boyNames.containsKey(name) && girlNames.containsKey(name))
                    .collect(Collectors.toMap(
                            name -> name,
                            name -> boyNames.get(name) + girlNames.get(name)
                    ));

//            System.out.println(unisexNames);

            //2. Print the names and frequencies sorted by the frequency in desc order
//            wrong approach
//            Map<Integer, String> mapByFrequency = new TreeMap<>(Comparator.reverseOrder());
//            unisexNames.entrySet().forEach(entry -> mapByFrequency.put(entry.getValue(), entry.getKey()));
//            System.out.println(mapByFrequency);
//
//            System.out.println(unisexNames.size() + " " + mapByFrequency.size());

            unisexNames.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .forEach(entry -> System.out.printf("%s: %d\n", entry.getKey(), entry.getValue()));


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static Map<String, Integer> getNamesMap(String path) throws FileNotFoundException {
        InputStream is = new FileInputStream(path);
//        Map<String, Integer> result = new HashMap<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path)));

//        br.lines().forEach(line -> {
//            String [] parts = line.split("\\s+");
//            String name = parts[0];
//            int freq = Integer.parseInt(parts[1]);
//            result.put(name, freq);
//        })
//        return result;

        return br.lines().collect(Collectors.toMap(
                line -> line.split("\\s+")[0],
                line -> Integer.parseInt(line.split("\\s+")[1])
        ));


    }
}
