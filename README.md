# Watchlist

* App that provides a way to keep track of media consumption across several categories
* Available as a web app backed by server-side DB or electron app backed by local storage

# TODOs

## DB Schema

* Need to decide:
  * Store everything in a big jsonb?
  * Normalize and store in child tables?

## Service

* Implement server-side saving
    * when signing in, if local data is not empty and server-side data exists and does not match local data (check 
    timestamp), prompt user if they would like to keep local data or server data.

## UI

* Sorting by column
* Colors for status
* Colors for score
* Filters
* Implement New Category button
* Dark mode
* Score pips look bad between 1200 and 1250 px screen width
