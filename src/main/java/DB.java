import org.sql2o.*;

public class DB {
  // connect with local server
  public static Sql2o sql2o = new Sql2o("jdbc:postgresql://localhost:5432/band_tracker", null, null);

  // connect to heroku through maven dependency manager
  //public static Sql2o sql2o = new Sql2o("jdbc:postgresql://ec2-54-243-107-66.compute-1.amazonaws.com:5432/dcp462jpuni78q","pyswayqmkuqbtm", "b340c5e463552f285bba4380680240aa8d9d1e8d23400bcdb85d3a4c47f10947");
}
