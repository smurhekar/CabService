package main;

import java.util.*;

public class CabService {

    private final Cab NO_CAB = new Cab("NoCab");
    private Map<String, List<Cab>> availableCabs;

    public CabService(Map<String, List<Cab>> availableCabs) {
        this.availableCabs = availableCabs;
    }

    public Cab checkAvailabilityFor(Calendar bookingTime, String location) {
        if(isValidTime(bookingTime)){
            if(availableCabs.containsKey(location)) {
                return availableCabs.get(location).get(0);
            }
        }
        return NO_CAB;
    }

    private boolean isValidTime(Calendar bookingTime) {
        return bookingTime.getTimeInMillis() >= getLongDateByAddingMinutes(15) && bookingTime.getTimeInMillis() <= getLongDateByAddingMinutes(45);
    }

    private long getLongDateByAddingMinutes(int minutes) {
        Calendar currentDay = Calendar.getInstance();
        currentDay.add(Calendar.MINUTE, minutes);
        return currentDay.getTimeInMillis();
    }

    public Cab book(Calendar bookingTime, String location) {
        Cab cab = checkAvailabilityFor(bookingTime, location);
        if(!cab.equals(NO_CAB)) {
            List<Cab> cabs = removeFromAvailability(cab, availableCabs.get(location));
            updateAvailabilityWith(cabs, location);
        }
        return cab;
    }

    private List<Cab> removeFromAvailability(Cab cab, List<Cab> cabs) {
        cabs.remove(cab);
        return cabs;
    }

    private void updateAvailabilityWith(List<Cab> cabs, String location){
        if (!cabs.isEmpty()){
            availableCabs.put(location, cabs);
        }else {
            availableCabs.remove(location);
        }
    }
}
