# Watchlist

* App that provides a way to keep track of media consumption across several categories
* Available as a web app backed by server-side DB or electron app backed by local storage

# TODOs

## Electron

* Use kotlin DCE to remove unneeded packages?
* Add sign-in & cloud save

## Service

* When signing in, if local data is not empty and server-side data exists and does not match local data (check 
timestamp), prompt user if they would like to keep local data or server data.

## UI

* Filters
* Implement New Category button
* JSON data import/export
* Dark mode
* Autocomplete/suggestions for tags
* Score pips look bad between 1200 and 1250 px screen width
* Category bar should be sticky or scroll independently
