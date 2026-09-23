PRAGMA foreign key=ON;
CREATE TABLE IF NOT EXISTS users (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    login TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS fichiers (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nom TEXT NOT NULL,
    droits TEXT NOT NULL,
    proprietaire_id INTEGER NOT NULL,

    FOREIGN KEY (proprietaire_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS logs (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    user_id INTEGER NOT NULL,
    fichier_id INTEGER NOT NULL,
    action TEXT NOT NULL,
    resultat TEXT NOT NULL,
    date TEXT NOT NULL,

    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (fichier_id) REFERENCES fichiers(id)
);