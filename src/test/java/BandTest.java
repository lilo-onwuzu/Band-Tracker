import org.junit.*;
import static org.junit.Assert.*;
import org.sql2o.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class BandTest {

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Test
  public void Band_InstantiatesWithString_true() {
    Band testBand = new Band("band");
    assertEquals("band",testBand.getName());
  }

  @Test
  public void all_EmptyAtFirst_true() {
    assertEquals(0, Band.all().size());
  }

  @Test
  public void equals_returnsTrueIfDescriptionsAreTheSame_true() {
    Band firstBand = new Band("band1");
    Band secondBand = new Band("band1");
    assertTrue(firstBand.equals(secondBand));
  }

  @Test
  public void save_savesObjectIntoDatabase_true() {
    Band testBand = new Band("band");
    testBand.save();
    assertTrue(Band.all().get(0).equals(testBand));
  }

  @Test
  public void save_assignsIdToObject_int() {
    Band testBand = new Band("band");
    testBand.save();
    Band savedBand = Band.all().get(0);
    assertEquals(testBand.getId(), savedBand.getId());
  }

  @Test
  public void find_findBandInDatabase_true() {
    Band testBand = new Band("band");
    testBand.save();
    Band savedBand = Band.all().get(0);
    assertEquals(savedBand, Band.find(testBand.getId()));
  }

  @Test
  public void update_updateBandDescriptionInDatabase_true() {
    Band testBand = new Band("band");
    testBand.save();
    testBand.update("other band");
    assertEquals("other band", Band.find(testBand.getId()).getName());
  }

  @Test
  public void addVenue_addsVenueToBand_true() {
    Band myBand = new Band("Boy Meets World");
    // adds Book to list of books
    myBand.save();
    Venue myVenue = new Venue("James Blake");
    // adds Venue to list of venues
    myVenue.save();
    // create relationship between venue and book
    myBand.addVenue(myVenue);
    Venue savedVenue = myBand.getVenues().get(0);
    assertTrue(myVenue.equals(savedVenue));
  }

  @Test
  public void getVenue_getsVenueForABand_true() {
    Band myBook = new Band("Boy meets World");
    myBook.save();
    Venue myVenue = new Venue("James Blake");
    myVenue.save();
    myBook.addVenue(myVenue);
    List savedVenues = myBook.getVenues();
    assertEquals(1, savedVenues.size());
  }

  @Test
  public void delete_deletesAllBandsAndVenuesAssociations() {
    Venue myVenue = new Venue("JK Rowling");
    myVenue.save();
    Band myBand = new Band("Harry Potter");
    myBand.save();
    myBand.addVenue(myVenue);
    myBand.delete();
    assertEquals(0, myVenue.getBands().size());
  }

}
