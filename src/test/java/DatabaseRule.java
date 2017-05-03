import org.junit.rules.ExternalResource;
import org.sql2o.*;

public class DatabaseRule extends ExternalResource {

  @Override
  protected void before() {
    // connect with local server
    public static Sql2o sql2o = new Sql2o("jdbc:postgresql://localhost:5432/band_tracker_test", null, null);

    // connect to heroku through maven dependency manager
    //public static Sql2o sql2o = new Sql2o("jdbc:postgresql://ec2-54-243-107-66.compute-1.amazonaws.com:5432/dcp462jpuni78q","pyswayqmkuqbtm", "b340c5e463552f285bba4380680240aa8d9d1e8d23400bcdb85d3a4c47f10947");
  }

  @Override
  protected void after() {
    try(Connection con = DB.sql2o.open()) {
      String deleteBandsQuery = "DELETE FROM bands *;";
      String deleteVenuesQuery = "DELETE FROM venues *;";
      String deleteJoinQuery = "DELETE FROM bands_venues *;";
      con.createQuery(deleteBandsQuery).executeUpdate();
      con.createQuery(deleteVenuesQuery).executeUpdate();
      con.createQuery(deleteJoinQuery).executeUpdate();
    }
  }

}
