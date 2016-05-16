import org.junit.*;
import static org.junit.Assert.*;
import org.fluentlenium.adapter.FluentTest;
import org.junit.ClassRule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import static org.assertj.core.api.Assertions.assertThat;
// filter Constructor is the fluentinum API class that allows us to use the click "withText" method
import static org.fluentlenium.core.filter.FilterConstructor.*;

public class AppTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();

  // webdriver is used for selenium test which automatically navigates the web to simulate a human user
  @Override
  public WebDriver getDefaultDriver() {
    return webDriver;
  }

  // @After
  // public void tearDown() {
  //   // whenever the testing program executes (even in the integration test), new word objects are being created that gets added to the wordInstances arraylist. @After tells the test program to clear all the word objects used in testingso they are not mixed up with the word objects in the program itself. We also need to clear the word objects so each new test starts on a fresh note and the word ids are not mixed up. To use "@After" rule, we have to call the jUnit class library in the integration test
  //   Word.clear();
  // }

  // to dry up code use a DatabaseRule.jave file instead of the above. This is a rule for this class AppTest
  @Rule
  public DatabaseRule database = new DatabaseRule();

  @ClassRule
  public static ServerRule server = new ServerRule();

  @Test
  public void homePageTest_homePage() {
    goTo("http://localhost:4567/");
    assertThat(pageSource()).contains("Band Venues");
  }

  @Test
  public void bandIsCreatedTest() {
    goTo("http://localhost:4567/");
    click("a", withText("Bands"));
    click("a", withText("Add New Band"));
    fill("#inputBand").with("The National");
    submit(".btn");
    assertThat(pageSource()).contains("The National");
  }

  @Test
  public void venueIsCreated() {
    goTo("http://localhost:4567/");
    click("a", withText("Venues"));
    click("a", withText("Add New Venue"));
    fill("#inputVenue").with("Doug Fir");
    submit(".btn");
    assertThat(pageSource()).contains("Doug Fir");
  }

  @Test
  public void emptyStringDoesNotMakeNewBandObject() {
    goTo("http://localhost:4567/");
    click("a", withText("Bands"));
    click("a", withText("Add New Band"));
    fill("#inputBand").with("");
    submit(".btn");
    assertThat(pageSource()).contains("No bands have been added to the list!");
  }

  @Test
  public void emptyStringDoesNotMakeNewVenueObject() {
    goTo("http://localhost:4567/");
    click("a", withText("Venues"));
    click("a", withText("Add New Venue"));
    fill("#inputVenue").with("");
    submit(".btn");
    assertThat(pageSource()).contains("No venues have been added to the list!");
  }

  @Test
  public void bandIsAddedToVenue() {
    Band testBand = new Band("Polica");
    testBand.save();
    Venue testVenue = new Venue("Doug Fir");
    testVenue.save();
    goTo("http://localhost:4567/bands/" + testBand.getId());
    click("a", withText("Add a Venue For Polica"));
    fillSelect("#addVenue").withText("Doug Fir");
    submit(".btn");
    assertThat(pageSource()).contains("Doug Fir");
  }

  @Test
  public void venueIsAddedToBand() {
    Venue testVenue = new Venue("Doug Fir");
    testVenue.save();
    Band testBand = new Band("Polica");
    testBand.save();
    goTo("http://localhost:4567/venues/" + testVenue.getId());
    click("a", withText("Add A Band"));
    fillSelect("#addBand").withText("Polica");
    submit(".btn");
    assertThat(pageSource()).contains("Polica");
  }

}
