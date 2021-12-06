package mk.ukim.finki.av7;

import java.util.*;
import java.util.stream.Collectors;

class InvalidArgumentException extends Exception {
    InvalidArgumentException(String msg) {
        super (msg);
    }
}

class FinalistPicker {

    int finalists;
    static Random RANDOM = new Random();

    public FinalistPicker(int finalists) {
        this.finalists = finalists;
    }

    public Collection<Integer> pick(int prizes) throws InvalidArgumentException {
        if (prizes<=0){
            throw new InvalidArgumentException("Prizes number must be positive!");
        }
        if (prizes>finalists){
            throw new InvalidArgumentException(String.format("Cannot divide %d prizes to %d finalists", prizes, finalists));
        }

//        List<Integer> result = new ArrayList<>();
//
//        while (result.size()!=prizes){
////            Random random = new Random(); // GRESKA
//            int pick = RANDOM.nextInt(finalist)+1;
//            if (!result.contains(pick)){
//                result.add(pick);
//            }
//        }

        return RANDOM.ints(5*prizes, 1, finalists+1)
                .distinct()
                .limit(prizes)
                .boxed()
                .collect(Collectors.toList());

//        Set<Integer> result = new HashSet<>();
//        while (result.size()!=prizes){
//            result.add(RANDOM.nextInt(finalists)+1);
//        }
//
//        return result;

    }
}

public class FinalistPickerTest {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int finalists = sc.nextInt();
        int prizes = sc.nextInt();

        FinalistPicker fp = new FinalistPicker(finalists);
        try {
            System.out.println(fp.pick(prizes));
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        }
    }
}
