-- Create table
CREATE TABLE IF NOT EXISTS player (
    id              UUID PRIMARY KEY,
    name            VARCHAR(255)    NOT NULL,
    email           VARCHAR(255)    NOT NULL UNIQUE,
    date_of_birth   DATE            NOT NULL,
    registered_date DATE            NOT NULL,
    goals           INTEGER         NOT NULL,
    assists         INTEGER         NOT NULL,
    position        VARCHAR(255)    NOT NULL,
    jersey_number   INTEGER         NOT NULL,
    team            VARCHAR(255)    NOT NULL
    );

-- Insert dummy players
-- Player 1
INSERT INTO player (id, name, email, date_of_birth, registered_date, goals, assists, position, jersey_number, team)
SELECT '123e4567-e89b-12d3-a456-426614174001', 'Mikael Lundqvist', 'mikael.lundqvist@floorball.com', '1998-03-15', '2024-01-10', 45, 30, 'Forward', 11, 'Helsinki Hawks'
    WHERE NOT EXISTS (SELECT 1 FROM player WHERE id = '123e4567-e89b-12d3-a456-426614174001');

-- Player 2
INSERT INTO player (id, name, email, date_of_birth, registered_date, goals, assists, position, jersey_number, team)
SELECT '123e4567-e89b-12d3-a456-426614174002', 'Tomás Novák', 'tomas.novak@floorball.com', '2000-07-22', '2024-01-10', 28, 52, 'Defender', 4, 'Helsinki Hawks'
    WHERE NOT EXISTS (SELECT 1 FROM player WHERE id = '123e4567-e89b-12d3-a456-426614174002');

-- Player 3
INSERT INTO player (id, name, email, date_of_birth, registered_date, goals, assists, position, jersey_number, team)
SELECT '123e4567-e89b-12d3-a456-426614174003', 'Janne Korhonen', 'janne.korhonen@floorball.com', '1995-11-08', '2024-01-10', 12, 18, 'Goalkeeper', 33, 'Helsinki Hawks'
    WHERE NOT EXISTS (SELECT 1 FROM player WHERE id = '123e4567-e89b-12d3-a456-426614174003');

