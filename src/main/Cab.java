package main;

/**
 * Created by Lenovo on 1/30/2016.
 */
public class Cab {
    private final String name;

    public Cab(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object object){
        Cab cab = (Cab)object;
        return name.equals(cab.name);
    }
}
