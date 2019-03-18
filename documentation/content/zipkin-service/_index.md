---
title: "Zipkin-service"
weight: 10
alwaysopen : false
---

## Principes

Zipkin est un système de traçage distribué qui permet de résoudre les problème de latence dans les architectures microservices. Ce service propose une timeline avec les temps d'exécution pour chaque requête sur l'application.

{{% notice tip %}}
Ce service contient uniquement le jar fourni lors de l'exemple en cours. Rien de supplémentaire n'a été ajouté mis à part la dockerisation.
{{% /notice %}}

## Exemples

On peut voir, sur cette capture d'écran, toutes les opérations effectuées avec leurs temps d'exécution. Dans cet exemple, j'ai uniquement fait des requêtes sur l'ensemble des taches (GET /taches).

![GET all taches](../images/zipkin/capture0.png)

Dans la suite de l'exemple j'ai volontairement insérer un sleep de 10 secondes dans mon code afin de voir ce que cela donnait.

![GET all taches avec sleep(10)](../images/zipkin/capture1.png)

Si l'on clique sur la requête et que nous demandons plus de détail, on peut remarquer quelle est la méthode qui a mis le plus de temps à s'éxecuter (ici la méthode GET /taches contenant le sleep(10)).

![GET all taches avec sleep(10) detail](../images/zipkin/capture2.png)

Zipkin est donc un outil très puissant afin de traquer les problèmes de latence. Il est aussi très utile pour un processus d'amélioration des performances.