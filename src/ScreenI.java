import java.util.HashSet;

public interface ScreenI {

    HashSet<String> input = new HashSet<>();
    default boolean isPressed(String str){
        return input.contains(str.toLowerCase());
    }

}
