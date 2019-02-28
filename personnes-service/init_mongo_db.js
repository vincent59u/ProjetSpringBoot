db.personnes.drop();
db.personnes.insertMany([
    {"id": "425e7701-02c6-4de3-9333-a2459eece1c8", "nom": "GASIOROWSKI", "prenom": "Loic", "email": "loic.gasiorowski@gmail.com", "mdp": "motdepasse", "date_naissance": new Date("1996-07-31"), "commune": "Moyeuvre-grande", "codepostal": "57250"},
    {"id": "de7f2052-4961-4b4f-938c-3cd12clz9f82", "nom": "GRANDJEAN", "prenom": "Rudy", "email": "rudy.grandjean@gmail.com", "mdp": "motdepasse", "date_naissance": new Date("1996-06-15"), "commune": "Laneuveville", "codepostal": "54410"},
    {"id": "e60fe9dd-24c5-41b3-8379-ab7172c2ad16", "nom": "VINCENT", "prenom": "Matthieu", "email": "matthieu.vincent@gmail.com", "mdp": "motdepasse", "date_naissance": new Date("1995-03-31"), "commune": "Rupt-sur-Moselle", "codepostal": "88360"},
    {"id": "0e9d45d5-40c6-4b90-bbee-65147e31c9a2", "nom": "DESSI", "prenom": "Maxime", "email": "maxime.dessi@gmail.com", "mdp": "motdepasse", "date_naissance": new Date("1996-07-27"), "commune": "Nancy", "codepostal": "54000"}
]);