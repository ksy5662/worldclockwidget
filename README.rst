World Clock & Weather
=====================

A simple application to display the local time and current weather conditions in places all over the world.
It comes with two home screen widgets which show weather and time or time only.

Features
--------

* Show local time and weather conditions in more than 4000 places.
* Add your own places: Add a place in the same time zone and change its name and coordinates.
* A compact clock widget, displaying the local time in your favorite locations.
* A resizable widget, showing the time and weather (not available for Gingerbread devices)
* Simple and clean UI

Remarks
-------

The Internet connectivity permission is **only** used to retrieve current weather conditions.

Please help to improve this application. We very much appreciate your feedback.

Download
--------

The latest version of the app is available on `Google Play`__.

__ https://play.google.com/store/apps/details?id=ch.corten.aha.worldclock

Screenshots
-----------

`wiki/Screenshots <https://github.com/arminha/worldclockwidget/wiki/Screenshots>`_

Building
--------

To build the app change to the *worldclockwidget* folder and run ``ant debug`` or ``ant release`` to create a debug or a release build.

Yahoo App Id
############

Using the Yahoo weather service requires an application id for the Yahoo PlaceFinder API. You can create one on the `PlaceFinder page <http://developer.yahoo.com/geo/placefinder/guide/>`_. Just click on *Create Project*.

Once you have an application id, write it into a file called ``./worldclockwidget/assets/yahooAppId``. The app will read the id from this file.

Attributions
------------

* Weather data is provided by `Yahoo! Weather <http://weather.yahoo.com/>`_.
* The weather icons are based on `Meteocons <http://www.alessioatzeni.com/meteocons/>`_ from Alessio Atzeni.
* Time zone and geographical data is from `Geonames.org <http://www.geonames.org/>`_.

