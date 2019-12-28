CREATE TABLE watchlist (
    user_id uuid UNIQUE PRIMARY KEY,
    version integer NOT NULL,
    contents jsonb NOT NULL
);
