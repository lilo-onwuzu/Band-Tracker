import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import org.sql2o.*;

public class Band {
  private int id;
  private String name;

  public Band(String name){
    this.name = name;
  }

  public String getName(){
    return name;
  }

  public int getId() {
    return id;
  }

  public static List<Band> all(){
    String sql = "SELECT id, name FROM bands";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Band.class);
    }
  }

  @Override
  public boolean equals(Object otherBand) {
    if (!(otherBand instanceof Band)) {
      return false;
    } else {
      Band newBand = (Band) otherBand;
      return this.getName().equals(newBand.getName()) &&
             this.getId() == newBand.getId();
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO bands (name) VALUES (:name)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .executeUpdate()
        .getKey();
    }
  }

  public static Band find(int id) {
    String sql = "SELECT * FROM bands WHERE id=:id";
    try(Connection con = DB.sql2o.open()) {
      Band band = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Band.class);
      return band;
    }
  }

  public void update(String update) {
    String sql = "UPDATE bands SET name=:name WHERE id=:id";
    try(Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
        .addParameter("name", update)
        .addParameter("id", this.id)
        .executeUpdate();
    }
  }

  public void addVenue(Venue myVenue) {
    String sql = "INSERT INTO bands_venues (venue_id, band_id) VALUES (:venue_id, :band_id)";
    try(Connection con = DB.sql2o.open()) {
      con.createQuery(sql)
        .addParameter("venue_id", myVenue.getId())
        .addParameter("band_id", this.getId())
        .executeUpdate();
    }
  }

  public List<Venue> getVenues() {
    String joinQuery = "SELECT venue_id FROM bands_venues WHERE band_id=:band_id";
    try (Connection con = DB.sql2o.open()) {
      List<Integer> venueIds = con.createQuery(joinQuery)
        .addParameter("band_id", this.getId())
        .executeAndFetch(Integer.class);

      List<Venue> venueList = new ArrayList<Venue>();

      for (Integer venueId : venueIds) {
        String taskQuery = "SELECT * FROM venues WHERE id=:venue_id";
          Venue band_venue = con.createQuery(taskQuery)
            .addParameter("venue_id", venueId)
            .executeAndFetchFirst(Venue.class);
            venueList.add(band_venue);
      }
      return venueList;
    }
  }

  public void delete() {
    String deleteQuery = "DELETE FROM bands WHERE id=:id";
    try(Connection con = DB.sql2o.open()) {
      con.createQuery(deleteQuery)
        .addParameter("id", this.id)
        .executeUpdate();
    String joinDeleteQuery = "DELETE FROM bands_venues WHERE band_id=:band_id";
      con.createQuery(joinDeleteQuery)
        .addParameter("band_id", this.getId())
        .executeUpdate();
    }
  }

}
