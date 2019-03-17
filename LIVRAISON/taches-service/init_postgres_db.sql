TRUNCATE TABLE tache CASCADE;

INSERT INTO tache(id, nom, responsable_id, date_debut, date_echeance, etat) VALUES('de7d9052-4961-4b4f-938a-3cd12cbe1f82', 'tache1', '425e7701-02c6-4de3-9333-a2459eece1c8', NOW(), '2019-05-30', '1');
INSERT INTO tache_participants_id VALUES ('de7d9052-4961-4b4f-938a-3cd12cbe1f82', 'de7f2052-4961-4b4f-938c-3cd12clz9f82');
INSERT INTO tache_participants_id VALUES ('de7d9052-4961-4b4f-938a-3cd12cbe1f82', 'e60fe9dd-24c5-41b3-8379-ab7172c2ad16');

INSERT INTO tache(id, nom, responsable_id, date_debut, date_echeance, etat) VALUES('c5f14e96-4cbb-48f3-9cb6-aa387474dfc4', 'tache2', 'de7f2052-4961-4b4f-938c-3cd12clz9f82', NOW(), '2020-01-03', '0');

INSERT INTO tache(id, nom, responsable_id, date_debut, date_echeance, etat) VALUES('2864f0e9-1f8e-40d0-909a-d92b1cb5e71f', 'tache3', '0e9d45d5-40c6-4b90-bbee-65147e31c9a2', NOW(), '2021-08-09', '2');
INSERT INTO tache_participants_id VALUES ('2864f0e9-1f8e-40d0-909a-d92b1cb5e71f', 'e60fe9dd-24c5-41b3-8379-ab7172c2ad16');

INSERT INTO tache(id, nom, responsable_id, date_debut, date_echeance, etat) VALUES('63ecf6fa-15bf-4ee3-bdb1-af0e587de075', 'tache4', '0e9d45d5-40c6-4b90-bbee-65147e31c9a2', NOW(), '2030-12-19', '3');
INSERT INTO tache_participants_id VALUES ('63ecf6fa-15bf-4ee3-bdb1-af0e587de075', 'de7f2052-4961-4b4f-938c-3cd12clz9f82');
INSERT INTO tache_participants_id VALUES ('63ecf6fa-15bf-4ee3-bdb1-af0e587de075', 'e60fe9dd-24c5-41b3-8379-ab7172c2ad16');
INSERT INTO tache_participants_id VALUES ('63ecf6fa-15bf-4ee3-bdb1-af0e587de075', '425e7701-02c6-4de3-9333-a2459eece1c8');

INSERT INTO tache(id, nom, responsable_id, date_debut, date_echeance, etat) VALUES('d7314ba3-7150-47ee-b7e2-0a843074bff6', 'tache5', 'de7f2052-4961-4b4f-938c-3cd12clz9f82', NOW(), '2019-07-28', '0');

INSERT INTO tache(id, nom, responsable_id, date_debut, date_echeance, etat) VALUES('e0b75740-7e49-4824-91d0-3bdbd0a1f179', 'tache6', '425e7701-02c6-4de3-9333-a2459eece1c8', NOW(), '2022-01-01', '1');
INSERT INTO tache_participants_id VALUES ('e0b75740-7e49-4824-91d0-3bdbd0a1f179', '0e9d45d5-40c6-4b90-bbee-65147e31c9a2');

INSERT INTO tache(id, nom, responsable_id, date_debut, date_echeance, etat) VALUES('339fa25f-1b6a-4767-a5aa-b4160ece98f6', 'tache7', 'e60fe9dd-24c5-41b3-8379-ab7172c2ad16', NOW(), '2019-10-13', '1');
INSERT INTO tache_participants_id VALUES ('339fa25f-1b6a-4767-a5aa-b4160ece98f6', '425e7701-02c6-4de3-9333-a2459eece1c8');