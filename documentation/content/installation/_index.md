---
title: "Installation"
weight: 1
alwaysopen : false
---

## Prérequis 

Pour lancer tous les services de mon projet, il vous suffit d'avoir docker d'installé ainsi qu'une connexion internet afin de télécharger les images nécessaires (Java, postgres, mongoDB, RabittMQ).

## Téléchargement du code source

Il faut tout d'abord télécharger le dossier nommé 'livraison' sur mon dépôt [GitHub](.). Ce dossier contient le fichier docker-compose.yml ainsi qu'un sous dossier contenant un dockerfile ainsi que le .jar pour chaque microservice de l'application.

## Lancement de tous les services

Pour lancer les microservices il vous suffit d'ouvrir un terminal à la racine du dossier précédement téléchargé. Tous les services se lancent avec les trois commandes suivantes :
<ul>
    <li>`docker-compose -f docker-compose-mini.yml up -d --build` ou `docker-compose -f docker-compose-full.yml up -d --build`</li>
    <li>`docker exec taches-service-postgres psql -d postgres -U postgres -a -q -w -f /docker-entrypoint-initdb.d/init.sql`</li>
    <li>`docker exec personnes-service-mongodb mongo localhost:27017/mongodb /docker-entrypoint-initdb.d/init.js`</li>
</ul>

J'ai réalisé deux fichier docker-compose distinct pour une question de rapidité. Le fichier docker-compose-mini.yml ne contient que les services vitaux à l'application (taches-service, personnes-service, eureka-service, etc..). Le fichier docker-compose-full.yml lance les services du précédent fichier et lance les services de monitoring.

{{% notice tip %}}
Les deux dernières commandes permettent d'executer des scripts de peuplement des différentes bases de données.
{{% /notice %}}

## Vérification

Le lancement de tous les services peut prendre quelques minutes (environ 2-3 minutes sur ma machine). Pour vérifier que ceux-ci soiet bien lancés, vous pouvez regarder l'état des containers docker avec la commande `docker ps`. Vérifier ensuite que la liste des containers ci-contre soient UP (ici avec le fichier docker-compose-mini.yml).

![docker](../images/installation/capture0.png)

Vous pouvez également vous rendre à l'adresse [localhost:8761](http://localhost:8761) et vérifier que les services (taches-service et personnes-service) soient enregistrés dans eureka comme sur l'image suivante :

![eureka](../images/installation/capture1.png)

Si tout s'est bien déroulé vous pouvez utiliser l'application.