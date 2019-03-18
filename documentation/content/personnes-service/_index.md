---
title: "Personnes-service"
weight: 3
alwaysopen : false
---

## Technologies

Le microservice personnes-sevice a été écrit en java avec le framework Spring Boot. La base de données est une base orientée 'documents' avec MongoDB. J'ai fais ce choix pour montrer qu'il est possible de faire communiquer des services entre eux même si ces derniers ont des technologies différentes. De plus, nous avions eu un cours sur MongoDB l'année passée en M1.

## Objectifs

Le but du personnes-service est de gérer différents profils utilisateurs et de mettre ces fiches à la disposition du taches-service qui permet d'associer une tache à différents utilisateurs (responsable et participant).

## Entity

L'entity 'Personne' permet de déclarer les différents attributs d'une personne. Chaque utilisateur est représenté par la liste suivante : id, nom, prénom, email, mot de passe, date de naissance, commune et code postal.

Spring fourni un ensemble d'annotation qui permet de poser des contraintes sur les attributs d'une personne.

![annotations](../images/personnes-service/capture0.png?width=20pc)

On peut voir, par exemple, l'annotation @Email qui permet de vérifier que la chaine de caractère soit bien un email ou encore l'annotation @Past qui permet de valider le fait que la date de naissance soit bien dans le passé.

## Boundary

Comme vu en cours, j'ai préféré redéfinir les méthodes GET, POST, PUT et DELETE dans une classe PersonneRepresentation afin de maitrisé toutes les opérations. C'est dans cette classe que l'on utilise l'annotation @Valid (dans la signature des méthodes) qui permet de valider les données d'une personne en fonction des contraintes posées dans l'entity.

![annotation valide](../images/personnes-service/capture1.png)

Pour tester ce système de validation fourni par Spring, nous essayons d'insérer un utilisateur avec une date de naissance dans le future.

![post](../images/personnes-service/capture2.png?width=20pc)
![resultat](../images/personnes-service/capture3.png)

On remarque donc que les annotations permettent une validation rapide des données afin de garantir leurs intégrité.

## Documentation

Une documentation générée par Swagger2 est disponible à l'adresse [localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html).

![documentation](../images/personnes-service/capture4.png)

Il est possible de personnaliser et ajouter d'avantage de contenu à la documentation. J'ai préféré me concentrer sur les autres aspects du projet.

## Tests

J'ai également réaliser des tests unitaires comme vu en cours afin de tester rapidement les différentes méthode de l'API.

![tests unitaire](../images/personnes-service/capture5.png)