package test;

import main.Cab;
import main.CabService;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static java.util.Calendar.MINUTE;
import static org.junit.Assert.assertEquals;

public class CabServiceTest {
    Calendar currentDay;

    Map<String, List<Cab>> availableCabs = new HashMap<String, List<Cab>>() {
        {
            put("Bavdhan", new ArrayList<Cab>(Arrays.asList(new Cab("Cab1"))));
            put("Baner", new ArrayList<Cab>(Arrays.asList(new Cab("Cab2"), new Cab("Cab3"))));
        }
    };

    @Before
    public void setup(){
        currentDay = Calendar.getInstance();
    }

    @Test
    public void shouldBeAllowedToCheckTheAvailabilityWhenThePickUpTimeIsBetween15MinTo1Hour(){
        currentDay.set(MINUTE, currentDay.get(MINUTE) + 16);
        assertEquals(new Cab("Cab1"), new CabService(availableCabs).checkAvailabilityFor(currentDay, "Bavdhan"));
    }

    @Test
    public void shouldNotBeAllowedToCheckAvailabilityWhenThePickUpTimeIsBefore15Min(){
        currentDay.set(MINUTE, currentDay.get(MINUTE) + 14);
        assertEquals(new Cab("NoCab"), new CabService(availableCabs).checkAvailabilityFor(currentDay, "Bavdhan"));
    }

    @Test
    public void shouldNotBeAllowedToCheckAvailabilityWhenThePickUpTimeIsAfter60Min(){
        currentDay.set(MINUTE, currentDay.get(MINUTE) + 61);
        assertEquals(new Cab("NoCab"), new CabService(availableCabs).checkAvailabilityFor(currentDay, "Bavdhan"));
    }

    @Test
    public void shouldGetTheCabIfItIsAvailableForTheGivenLocation(){
        currentDay.set(MINUTE, currentDay.get(MINUTE) + 20);
        assertEquals(new Cab("Cab1"), new CabService(availableCabs).checkAvailabilityFor(currentDay, "Bavdhan"));
    }

    @Test
    public void shouldNotGetTheCabIfItNotAvailableForTheGivenLocation(){
        currentDay.set(MINUTE, currentDay.get(MINUTE) + 20);
        assertEquals(new Cab("NoCab"), new CabService(availableCabs).checkAvailabilityFor(currentDay, "Bhusari Colony"));
    }

    @Test
    public void shouldBeAbleToConfirmBookingForAvailableCab(){
        currentDay.set(MINUTE, currentDay.get(MINUTE) + 16);
        assertEquals(new Cab("Cab1"), new CabService(availableCabs).book(currentDay, "Bavdhan"));
    }

    @Test
    public void shouldNotBeAbleToConfirmBookingIfCabIsNotAvailableForTheLocation(){
        currentDay.set(MINUTE, currentDay.get(MINUTE) + 16);
        assertEquals(new Cab("Cab1"), new CabService(availableCabs).book(currentDay, "Bavdhan"));
        assertEquals(new Cab("NoCab"), new CabService(availableCabs).book(currentDay, "Bavdhan"));
    }

    /*@Test
    public void shouldBeAbleToGenerateBillOnceTheJourneyIsEnded(){
        new CabService(availableCabs).endJourney();
    }*/
}
