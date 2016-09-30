# _Wildlife-Tracker_

#### _September, 30th, 2016_

#### By _**Brian Dutz**_

## Description

_A wildlife tracking application that records sightings of both endangered and non-endangered animals._

## Setup/Installation Requirements

_Clone the repo and open it up.
In PSQL:
CREATE DATABASE wildlife_tracker;
CREATE TABLE animals (id serial PRIMARY KEY, name varchar, health int, age int, type varchar);
CREATE TABLE sightings (id serial PRIMARY KEY, location varchar, rangerName varchar, animalId int);
CREATE DATABASE wildlife_tracker_test WITH TEMPLATE wildlife_tracker;
_

## Specifications

* Behavior: Save data
  * **Example input:** common owl, healthy, adult, seen at 3:42 in NE quadrant
  * **Example output:** common owl, healthy, adult, seen at 3:42 in NE quadrant
* Behavior: Correctly associate the stored information with specific details/relationships
  * **Example input:**  find common owl
  * **Example output:** common owl, healthy, adult, seen at 3:42 in NE quadrant...(list other instances of owl sightings)

## Known Bugs

_None so far_

## Support and contact details

* _Any issues email me_

## Technologies Used

_Java_ and _Spark_ and _Postgres_

### License

*This software is licensed under the MIT license*

Copyright (c) 2016 **_Brian Dutz_**
