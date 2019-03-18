---
title: "Spring-boot-admin"
weight: 9
alwaysopen : false
---

## Principes

Spring-boot-admin est un dashboard assez graphique qui permet de monitorer l'ensemble des services d'une architecture orientée microservice. Il permet de consulter, pour chaque service, l'état des instances (up ou down). Ce service est très utile car il contient également les logs de chacun des services.

## Exemples

Pour cet exemple, j'ai volontairement stopper le monitor-service afin de créer des erreurs dans taches-service et personnes-service.

![dashboard](../images/spring-boot-admin-service/capture0.png)

Si on clique sur l'un des deux services, spring boot admin nous indique le ou les motifs d'erreur. Dans notre cas l'erreur concerne le monitor-service qui est introuvable.

![erreur](../images/spring-boot-admin-service/capture1.png)