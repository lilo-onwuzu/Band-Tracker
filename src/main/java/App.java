import static spark.Spark.*;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import java.util.List;
import java.util.HashMap;

public class App {

  public static void main(String [] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      HashMap model = new HashMap();
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    // new Band form
    get("/bands/new", (request, response) -> {
      HashMap model = new HashMap();
      model.put("template", "templates/newBand.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    // new Venue form
    get("/venues/new", (request, response) -> {
      HashMap model = new HashMap();
      model.put("template", "templates/newVenue.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    // all bands page
    get("/bands", (request, response) -> {
      HashMap model = new HashMap();
      model.put("bands", Band.all());
      model.put("template", "templates/bands.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    // all venues page
    get("/venues", (request, response) -> {
      HashMap model = new HashMap();
      model.put("venues", Venue.all());
      model.put("template", "templates/venues.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    // post new band
    post("/bands", (request, response) -> {
      HashMap model = new HashMap();
      String newInputBand = request.queryParams("inputBand");
      Band band = new Band(newInputBand);
      band.save();
      model.put("bands", Band.all());
      model.put("template", "templates/bands.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    // post new venue
    post("/venues", (request, response) -> {
      HashMap model = new HashMap();
      String newInputVenue = request.queryParams("inputVenue");
      Venue venue = new Venue(newInputVenue);
      venue.save();
      model.put("venues", Venue.all());
      model.put("template", "templates/venues.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    // band details
    get("/bands/:band_id", (request, response) -> {
      HashMap model = new HashMap();
      Band band = Band.find(Integer.parseInt(request.params(":band_id")));
      model.put("band", band);
      model.put("venues", band.getVenues());
      model.put("template", "templates/band.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    // venue details
    get("/venues/:venue_id", (request, response) -> {
      HashMap model = new HashMap();
      Venue venue = Venue.find(Integer.parseInt(request.params(":venue_id")));
      model.put("venue", venue);
      model.put("bands", venue.getBands());
      model.put("template", "templates/venue.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    // add venue form
    get("/bands/:band_id/add_venue", (request, response) -> {
      HashMap model = new HashMap();
      Band band = Band.find(Integer.parseInt(request.params(":band_id")));
      model.put("band", band);
      model.put("allVenues", Venue.all());
      model.put("template", "templates/addVenue.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    // add band form
    get("/venues/:venue_id/add_band", (request, response) -> {
      HashMap model = new HashMap();
      Venue venue = Venue.find(Integer.parseInt(request.params(":venue_id")));
      model.put("venue", venue);
      model.put("allBands", Band.all());
      model.put("template", "templates/addBand.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

  }
}
