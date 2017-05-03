import static spark.Spark.*;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import java.util.List;
import java.util.HashMap;

public class App {

  // connect to heroku through maven dependency manager
  // static int getHerokuAssignedPort() {
  //   ProcessBuilder processBuilder = new ProcessBuilder();
  //   if (processBuilder.environment().get("PORT") != null) {
  //     return Integer.parseInt(processBuilder.environment().get("PORT"));
  //   }
  //   return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
  // }

  public static void main(String [] args) {
    // connect to heroku through gradle dependency manager
    ProcessBuilder process = new ProcessBuilder();
     Integer port;
     if (process.environment().get("PORT") != null) {
         port = Integer.parseInt(process.environment().get("PORT"));
     } else {
         port = 4567;
     }

    setPort(port);

    // connect to heroku through maven dependency manager
    //port(getHerokuAssignedPort());

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
      if (!newInputBand.equals("")) {
        Band band = new Band(newInputBand);
        band.save();
      }
      model.put("bands", Band.all());
      model.put("template", "templates/bands.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    // post new venue
    post("/venues", (request, response) -> {
      HashMap model = new HashMap();
      String newInputVenue = request.queryParams("inputVenue");
      if (!newInputVenue.equals("")) {
        Venue venue = new Venue(newInputVenue);
        venue.save();
      }
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

    // post new venue to a band. redirects to get request
    post("/bands/:band_id", (request, response) -> {
      HashMap model = new HashMap();
      Band band = Band.find(Integer.parseInt(request.params(":band_id")));
      model.put("band", band);
      int venueId = Integer.parseInt(request.queryParams("addVenue"));
      Venue venue = Venue.find(venueId);
      band.addVenue(venue);
      response.redirect("/bands/" + band.getId());
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    // delete band through link of band.vtl page. redirects to allBands page
    get("/bands/:band_id/remove", (request, response) -> {
      HashMap model = new HashMap();
      Band band = Band.find(Integer.parseInt(request.params(":band_id")));
      band.delete();
      response.redirect("/bands");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    // update bandname through post request from the form on band.vtl page. redirects to band details page
    post("/bands/:band_id/edit", (request, response) -> {
      HashMap model = new HashMap();
      Band band = Band.find(Integer.parseInt(request.params(":band_id")));
      String editBand = request.queryParams("editBand");
      band.update(editBand);
      response.redirect("/bands/" + band.getId());
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

    post("/venues/:venue_id", (request, response) -> {
      HashMap model = new HashMap();
      Venue venue = Venue.find(Integer.parseInt(request.params(":venue_id")));
      model.put("venue", venue);
      int bandId = Integer.parseInt(request.queryParams("addBand"));
      Band band = Band.find(bandId);
      venue.addBand(band);
      response.redirect("/venues/" + venue.getId());
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    // delete venue through link of venue.vtl page. redirects to allVenues page
    get("/venues/:venue_id/remove", (request, response) -> {
      HashMap model = new HashMap();
      Venue venue = Venue.find(Integer.parseInt(request.params(":venue_id")));
      venue.delete();
      response.redirect("/venues");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    // update venuename through post request from the form on venue.vtl page. redirects to venue details page
    post("/venues/:venue_id/edit", (request, response) -> {
      HashMap model = new HashMap();
      Venue venue = Venue.find(Integer.parseInt(request.params(":venue_id")));
      String editVenue = request.queryParams("editVenue");
      venue.update(editVenue);
      response.redirect("/venues/" + venue.getId());
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    // add venue to band form
    get("/bands/:band_id/add_venue", (request, response) -> {
      HashMap model = new HashMap();
      Band band = Band.find(Integer.parseInt(request.params(":band_id")));
      model.put("band", band);
      model.put("allVenues", Venue.all());
      model.put("template", "templates/addVenue.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    // add band to venue form
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
