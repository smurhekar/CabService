package test;

import main.Cab;
import main.CabService;
import org.junit.Test;

import java.util.*;

import static java.util.Calendar.MINUTE;
import static org.junit.Assert.assertEquals;

public class CabServiceTest {

    Map<String, List<Cab>> availableCabs = new HashMap<String, List<Cab>>() {
        {
            put("Bavdhan", Arrays.asList(new Cab("Cab1")));
            put("Baner", Arrays.asList(new Cab("Cab2"), new Cab("Cab3")));
        }
    };

    @Test
    public void shouldBeAbleToPickUpFrom15MinTo1Hour(){
        Calendar currentDay = Calendar.getInstance();
        currentDay.set(MINUTE, currentDay.get(MINUTE) + 16);
        assertEquals(new Cab("Cab1"), new CabService(availableCabs).book(currentDay, "Bavdhan"));
    }

    @Test
    public void shouldNotBeAbleToPickUpBefore15Min(){
        Calendar currentDay = Calendar.getInstance();
        currentDay.set(MINUTE, currentDay.get(MINUTE) + 14);
        assertEquals(new Cab("NoCab"), new CabService(availableCabs).book(currentDay, "Bavdhan"));
    }

    @Test
    public void shouldNotBeAbleToPickUpAfter60Min(){
        Calendar currentDay = Calendar.getInstance();
        currentDay.set(MINUTE, currentDay.get(MINUTE) + 61);
        assertEquals(new Cab("NoCab"), new CabService(availableCabs).book(currentDay, "Bavdhan"));
    }

    @Test
    public void shouldBeAbleToBookOnlyAvailableCabForTheGivenLocation(){
        Calendar currentDay = Calendar.getInstance();
        currentDay.set(MINUTE, currentDay.get(MINUTE) + 20);
        assertEquals(new Cab("Cab1"), new CabService(availableCabs).book(currentDay, "Bavdhan"));
    }

    @Test
    public void shouldNotBeAbleToBookNonAvailableCabForTheGivenLocation(){
        Calendar currentDay = Calendar.getInstance();
        currentDay.set(MINUTE, currentDay.get(MINUTE) + 20);
        assertEquals(new Cab("NoCab"), new CabService(availableCabs).book(currentDay, "Bhusari Colony"));
    }


    @Test
    public void shouldBeAbleToConfirmBookingForAvailableCab(){
        Calendar currentDay = Calendar.getInstance();
        currentDay.set(MINUTE, currentDay.get(MINUTE) + 16);
        CabService cabService = new CabService(availableCabs);
        Cab cab = cabService.book(currentDay, "Bavdhan");
        assertEquals(true, cabService.confirmBooking(cab, "Bavdhan"));
    }
}
