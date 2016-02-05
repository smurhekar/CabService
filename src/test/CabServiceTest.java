package test;

import main.Bill;
import main.Cab;
import main.CabService;
import main.LocationService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.*;

import static java.util.Calendar.MINUTE;
import static org.junit.Assert.assertEquals;

public class CabServiceTest {
    Calendar currentDay;

    Map<String, List<Cab>> availableCabsForLocation = new HashMap<String, List<Cab>>() {
        {
            put("Bavdhan", new ArrayList<Cab>(Arrays.asList(new Cab("Cab1"))));
            put("Baner", new ArrayList<Cab>(Arrays.asList(new Cab("Cab2"), new Cab("Cab3"))));
        }
    };

    LocationService locationService;

    @Before
    public void setup(){
        currentDay = Calendar.getInstance();
        locationService = new LocationService();
    }

    @Test
    public void shouldBeAllowedToCheckTheAvailabilityWhenThePickUpTimeIsBetween15MinTo1Hour(){
        currentDay.set(MINUTE, currentDay.get(MINUTE) + 16);
        assertEquals(new Cab("Cab1"), new CabService(availableCabsForLocation, locationService).checkAvailabilityFor(currentDay, "Bavdhan"));
    }

    @Test
    public void shouldNotBeAllowedToCheckAvailabilityWhenThePickUpTimeIsBefore15Min(){
        currentDay.set(MINUTE, currentDay.get(MINUTE) + 14);
        assertEquals(new Cab("NoCab"), new CabService(availableCabsForLocation, locationService).checkAvailabilityFor(currentDay, "Bavdhan"));
    }

    @Test
    public void shouldNotBeAllowedToCheckAvailabilityWhenThePickUpTimeIsAfter60Min(){
        currentDay.set(MINUTE, currentDay.get(MINUTE) + 61);
        assertEquals(new Cab("NoCab"), new CabService(availableCabsForLocation, locationService).checkAvailabilityFor(currentDay, "Bavdhan"));
    }

    @Test
    public void shouldGetTheCabIfItIsAvailableForTheGivenLocation(){
        currentDay.set(MINUTE, currentDay.get(MINUTE) + 20);
        assertEquals(new Cab("Cab1"), new CabService(availableCabsForLocation, locationService).checkAvailabilityFor(currentDay, "Bavdhan"));
    }

    @Test
    public void shouldNotGetTheCabIfItNotAvailableForTheGivenLocation(){
        currentDay.set(MINUTE, currentDay.get(MINUTE) + 20);
        assertEquals(new Cab("NoCab"), new CabService(availableCabsForLocation, locationService).checkAvailabilityFor(currentDay, "Bhusari Colony"));
    }

    @Test
    public void shouldBeAbleToConfirmBookingForAvailableCab(){
        currentDay.set(MINUTE, currentDay.get(MINUTE) + 16);
        assertEquals(new Cab("Cab1"), new CabService(availableCabsForLocation, locationService).book(currentDay, "Bavdhan"));
    }

    @Test
    public void shouldNotBeAbleToConfirmBookingIfCabIsNotAvailableForTheLocation(){
        currentDay.set(MINUTE, currentDay.get(MINUTE) + 16);
        assertEquals(new Cab("Cab1"), new CabService(availableCabsForLocation, locationService).book(currentDay, "Bavdhan"));
        assertEquals(new Cab("NoCab"), new CabService(availableCabsForLocation, locationService).book(currentDay, "Bavdhan"));
    }

    @Test
    public void shouldBeAbleToGenerateBillOnceTheJourneyIsEnded(){
        currentDay.set(MINUTE, currentDay.get(MINUTE) + 16);
        LocationService mockLocationService = Mockito.mock(LocationService.class);
        CabService cabService = new CabService(availableCabsForLocation, mockLocationService);
        Cab cab = cabService.book(currentDay, "Bavdhan");
        Mockito.when(mockLocationService.getDistanceTravelledByCabFrom("Bavdhan", cab)).thenReturn(10.0);
        Double cost = cabService.endJourneyOf(cab, "Bavdhan");
        assertEquals(120.0, cost, 0);

    }
}
