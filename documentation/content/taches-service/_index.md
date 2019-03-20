---
title: "Taches-service"
weight: 4
alwaysopen : false
---

## Technologies

Le microservice taches-sevice a été écrit en java avec le framework Spring Boot. La base de données est une base postgres. Je me suis inspiré de l'exemple du cours que nous avions vu pour mettre en place ce service.

## Objectifs

L'objectif principal est de gérer des tâches représentées par un certain nombre d'attribut. Ce service communique également avec le personnes-service afin de récupérer les informations du responsable et des participants.

## Entity

L'entity 'Tache' permet de représenter au sein du programme les tâches qui seront manipulées. Elles sont représentées avec les attributs suivants : id, nom, id du responsable, id des participants, date de début, date d'échéance et l'etat. Comme pour l'entity 'Personne' du personnes-service j'ai défini des contraintes avec les annotations fournies.

![annotations](../images/taches-service/capture0.png?width=20pc)

Il est également possible de définir ses propres annotations. Par exemple la date d'échéance ne doit pas être inférieur à la date de début qui a toujours la valeur de la date courante. Pour cela j'ai écrit une annotation nommée "ConsistentDate".

![interface](../images/taches-service/capture1.png?width=40pc)
![validator](../images/taches-service/capture2.png?width=40pc)

Il faut ensuite ajouter l'annotation @ConsistentDate au niveau de l'entité Tache. Pour cet exemple, j'ai retiré l'annotation sur l'attribut date_echeance afin de pouvoir volontairement insérer une date passée.

![verification](../images/taches-service/capture3.png?width=25pc)
![verification](../images/taches-service/capture4.png)

{{% notice tip %}}
Cependant, je n'ai pas utilisé mon annotation custom car la date de début est égale a date.Now(). Il suffit juste de poser l'annotation @Past sur la date d'échéance afin que celle-ci soit automatiquement supérieur à la date de début.
{{% /notice %}}

## Boundary

Comme pour personnes-service, j'ai redéfini les méthodes GET, POST, PUT, DELETE pour ajouter la couche métier qui est gérée par HATEOAS. J'ai donc géré toutes les contraintes suivantes :
<ul>
    <li>Etat de la tâche à la création en fonction du nombre de participant</li>
    <li>Passage à l'état en_cours lorsqu'on ajoute un participant</li>
    <li>Ajout et suppression de participant si la tâche n'est pas achevée</li>
    <li>Passage d'une tâche à l'état achevée si la date_echeance n'est pas passée</li>
    <li>Vérification que les personnes existent avant l'ajout à une tâche</li>
</ul>

Dans la capture d'écran ci-dessous, on peut voir le code qui permet de gérer l'état d'une tâche lors de la création en fonction de si elle possède au moins un participant.

![hateoas](../images/taches-service/capture5.png?width=40pc)
![post hateoas](../images/taches-service/capture6.png?width=25pc)

On peut voir que la tâche est dans l'état créée car elle ne possède pas de participant.
![vérification hateoas](../images/taches-service/capture7.png?width=40pc)

Je poste ensuite un participant à cette tâche (Methode POST sur l'Url http://localhost:8180/taches/2378e1c2-10a9-4a55-b7ca-6e58224d6791/participant/e60fe9dd-24c5-41b3-8379-ab7172c2ad16).
La tâche passe automatiquement dans l'état en_cours.
![vérification hateoas](../images/taches-service/capture8.png?width=40pc)

Vous pouvez voir toute l'implémentation des règles métiers dans la classe TacheRepresentation.java du taches-service. Pour finir, la problématique principale était d'intégrer l'implémentation d'un ResourceProcessor pour pouvoir gérer le client REST personnes-service. Pour cela je me suis inspiré de l'exemple du cours et vous pouvez consulter ce code dans le taches-service.

## Documentation

Une documentation générée par Swagger2 est disponible à l'adresse [localhost:8180/swagger-ui.html](http://localhost:8180/swagger-ui.html).

![documentation](../images/taches-service/capture9.png)

Il est possible de personnaliser et ajouter davantage de contenu à la documentation. J'ai préféré me concentrer sur les autres aspects du projet.

## Tests

J'ai également réalisé des tests unitaires comme vu en cours afin de tester rapidement les différentes méthodes de l'API et de valider les contraintes métiers.

![tests unitaires](../images/taches-service/capture10.png)

{{% notice tip %}}
Sur la capture d'écran, on remarque que certains tests ne sont pas passés mais cela est normal car le taches-service ne se connecte pas à Eureka lors du lancement de la classe de test et par conséquent ne peut pas récupérer les informations du feign client (personnes-service).
{{% /notice %}}