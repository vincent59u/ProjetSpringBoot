---
title: "Monitor-service"
weight: 8
alwaysopen : false
---

## Principes

Ce service de monitoring permet d'identifier si le circuit de l'application est ouvert ou non. Il donne également des informations sur la montée en charge du service ou encore sur le nombre de requêtes effectuées. Pour réaliser ce service, je me suis basé sur l'exemple vu en cours.

## Exemples

Voici l'interface du service de monitoring au lancement du taches-service :

![dashboard](../images/monitor-service/capture0.png)

Après l'exécution de quelques requêtes sur ce service, nous pouvons remarquer que le dashboard s'actualise en temps réel :

![dashboard](../images/monitor-service/capture1.png)

{{% notice tip %}}
On peut remarquer que le circuit est ouvert. Cela signifie qu'une des requêtes n'a pas eu de réponse. En l'occurrence, j'ai volontairement coupé le personnes-service pour cet exemple afin que le circuit soit ouvert.
{{% /notice %}}