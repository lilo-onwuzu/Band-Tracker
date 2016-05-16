import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;

public class VenueTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Venue_InstantiatesWithString_true() {
    Venue testVenue = new Venue("venue");
    assertEquals("venue",testVenue.getName());
  }

  @Test
  public void all_EmptyAtFirst_true() {
    assertEquals(0, Venue.all().size());
  }

  @Test
  public void equals_returnsTrueIfDescriptionsAreTheSame_true() {
    Venue firstVenue = new Venue("venue1");
    Venue secondVenue = new Venue("venue1");
    assertTrue(firstVenue.equals(secondVenue));
  }

  @Test
  public void save_savesObjectIntoDatabase_true() {
    Venue testVenue = new Venue("venue");
    testVenue.save();
    assertTrue(Venue.all().get(0).equals(testVenue));
  }

  @Test
  public void save_assignsIdToObject_int() {
    Venue testVenue = new Venue("venue");
    testVenue.save();
    Venue savedVenue = Venue.all().get(0);
    assertEquals(testVenue.getId(), savedVenue.getId());
  }

  @Test
  public void find_findVenueInDatabase_true() {
    Venue testVenue = new Venue("venue");
    testVenue.save();
    Venue savedVenue = Venue.all().get(0);
    assertEquals(savedVenue, Venue.find(testVenue.getId()));
  }

  @Test
  public void update_updateVenueNameInDatabase_true() {
    Venue testVenue = new Venue("venue");
    testVenue.save();
    testVenue.update("other venue");
    assertEquals("other venue", Venue.find(testVenue.getId()).getName());
  }

  @Test
  public void addBand_addsBandToVenue_true() {
    Venue myVenue = new Venue("James Blake");
    myVenue.save();
    Band myBand = new Band("Boy Meets World");
    myBand.save();
    myVenue.addBand(myBand);
    List savedBands = myVenue.getBands();
    assertEquals(1, savedBands.size());
  }

  @Test
  public void getBands_getsBandsForAnVenue_true() {
    Venue myVenue = new Venue("James Blake");
    myVenue.save();
    Band myBand = new Band("Boy meets World");
    myBand.save();
    myVenue.addBand(myBand);
    List savedBands = myVenue.getBands();
    assertEquals(1, savedBands.size());
  }

  @Test
  public void deleteVenues_deletesVenuesfromDatabase_true() {
    Venue myVenue = new Venue("Bram Stoker");
    myVenue.save();
    Band myBand = new Band("Dracula");
    myBand.save();
    myVenue.addBand(myBand);
    myVenue.delete();
    assertEquals(0, myBand.getVenues().size());
  }
}
