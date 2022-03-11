# Java Cinestar Editor

Simple CRUD application made in Java with a Swing UI. The app recognises two types of users: admins and users.
Logging in as an admin opens up the admin console which allows the admin to either flush the database or fill it with new data.
Data about movie projections is retrieved from a public RSS feed of Cinestar, a brand of Croatian cinemas. 
Movie information is parsed from the RSS and the poster images are downloaded in an asset folder. 
The data is then stored in a Microsoft SQL database. 
The app allows a regular user to view, add, update or delete actors, genres and movies themselves from the database using a GUI.
