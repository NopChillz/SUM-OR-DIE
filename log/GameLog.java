package log;

import java.util.ArrayList;
import java.util.List;

public class GameLog {

    private static final List<String> logs = new ArrayList<>();
    private static LogListener listener;

    public static void setListener(LogListener l){
        listener = l;
    }

    public static void add(String message){

        logs.add(message);

        if(listener != null)
            listener.onLogAdded(message);
    }

    public static List<String> getLogs(){
        return logs;
    }
}