import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public interface ScreenI {

    HashSet<String> input = new HashSet<>();
    List<Powercube> cubes = new ArrayList<>();

    default boolean isPressed(String str){
        return input.contains(str.toLowerCase());
    }

    default List<Powercube> getCubes(){
        return cubes;
    }

}
