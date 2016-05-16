Band Venues Web Application for Epicodus Level 2 Java Project 4:

By Ililochi Onwuzu

Description:

This site uses the concepts of RESTful routing to set up Band Venues web application. In this application, Bands are added to play or have played in certain Venues. There are two classes Band and Venue with a many to many relationship association. The application is backed with a database (band_tracker) on the POSTGREs DB server. The user can add as many bands and venues as possible, add Bands to Venues and vice versa. The user can also select a band to see all the Venues they have played or are going to play at. The user can also select a Venue and see what bands have played or are going to play there. The code was designed using BDD testing principles. The units were automatically build in gradle, tested independently using jUnit and the integration/Selenium tests were done FluentLenium.

Setup/Installation Requirements :

. First clone this repository
. Install auto-build and test application such as Gradle
. Build in the terminal using 'gradle run' command
. Database Set-Up
  In PSQL:
  CREATE DATABASE band_tracker;
  \c to_do;
  CREATE TABLE bands (id serial PRIMARY KEY, name varchar);
  CREATE TABLE venues (id serial PRIMARY KEY, name varchar);
  CREATE TABLE bands_venues (id serial PRIMARY KEY, band_id int, venue_id int)
  CREATE DATABASE band_tracker_test WITH TEMPLATE band_tracker;
. Open localhost:4567 in any browser to use the web application

Known Bugs:

None

Support and contact details:

lpr422@gmail.com

Technologies Used:

HTML, CSS, Object Oriented Java, SQL on POSTGREs DB, Fluentinum/Selenium, Gradle, BDD Testing, jUnit, Apache Spark, Apache Velocity

License

This Software Is Licensed Under MIT.

Copyright (c) 2015 Onwuzu, LPO Inc.
