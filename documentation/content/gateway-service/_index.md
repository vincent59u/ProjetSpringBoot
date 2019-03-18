---
title: "Gateway-service"
weight: 7
alwaysopen : false
---

## Principes

Le gateway-service est le point d'entrée de l'application. Il reçoit toutes les requêtes des utilisateurs et les rediriges ensuite vers les services concernés. Il permet également de faire la vérification du token d'autorisation. J'ai utilisé zuul pour créer les routes de mon application. Il suffit ensuite de configurer l'application comme dans cet exemple :

![routes](../images/gateway-service/capture0.png?width=30pc)

## Exemples

L'exemple est le même que pour la partie précédente sur oauth-service. Dans cet exemple je montrais comment récupérer la liste des tâches en passant par ce service d'entrée.