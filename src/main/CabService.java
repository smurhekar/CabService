package main;

import java.util.*;

public class CabService {

    private Map<String, List<Cab>> availableCabs;

    public CabService(Map<String, List<Cab>> availableCabs) {
        this.availableCabs = availableCabs;
    }

    public Cab book(Calendar bookingTime, String location) {
        if(bookingTime.getTimeInMillis() >= getLongDateByAddingMinutes(15) && bookingTime.getTimeInMillis() <= getLongDateByAddingMinutes(45)){
            if(availableCabs.containsKey(location)) {
                return availableCabs.get(location).get(0);
            }
        }
        return new Cab("NoCab");
    }

    private long getLongDateByAddingMinutes(int minutes) {
        Calendar currentDay = Calendar.getInstance();
        currentDay.add(Calendar.MINUTE, minutes);
        return currentDay.getTimeInMillis();
    }

    public boolean confirmBooking(Cab cab, String location) {
        List<Cab> cabs = availableCabs.get(location);
        boolean isRemoved = cabs.remove(cab);
        if (isRemoved && !cabs.isEmpty()){
            availableCabs.put(location, cabs);
        }else {
            availableCabs.remove(location);
        }
        return isRemoved;
    }
}
