package mk.ukim.finki.av11;

class Logger {
    StringBuilder sb;
    static Logger instance;

    private Logger () {
        sb = new StringBuilder();
    }

    public static Logger getInstance(){
        if (instance==null){
            instance = new Logger();
        }
        return instance;
    }

    public void addLog (String log) {
        sb.append(log).append("\n");
    }

    public String toString() {
        return sb.toString();
    }
}

public class LoggerTest {
    public static void main(String[] args) {
        Logger l1 = Logger.getInstance();
        Logger l2 = Logger.getInstance();

        l1.addLog("Test1");
        l2.addLog("Test2");

        System.out.println(l1);
        System.out.println(l2);
    }
}
