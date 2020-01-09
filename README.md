# Watchlist

* App that provides a way to keep track of media consumption across several categories
* Available as a web app backed by server-side DB or electron app backed by local storage

# TODOs
## DB Schema

* Need to decide:
  * Store everything in a big jsonb?
  * Normalize and store in child tables?

## Auth

* Need to decide:
  * Auth project provides a login kui component
    * Pros: Simple, integrated in SPA
    * Cons: Might be harder to customize login per app
    * Component could make calls directly to auth API
      * Requires a static context for auth app
    * Watchlist could provide API routes for login to call
      * Requires watchlist service to proxy these requests to auth service (which could send password over plaintext)
    * Context data items could be a map of name to context path. Watchlist page would then provide a context-watchlist 
    and context-auth
      * Requires duplicating the context path configuration
  * Auth has it's own frontend. To login, watchlist links to auth page with redirect param.
    * Pros: Apps are isolated, contexts are still fully configurable
    * Cons: Have to figure out how to redirect back
    * Could open a new modal window for login page so that a redirect isn't necessary
      * Need to detect when window closes from watchlist to update state
