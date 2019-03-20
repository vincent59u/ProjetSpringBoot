---
title: "RabbitMQ"
weight: 11
alwaysopen : false
---

## Principes

Ce service ne contient que l'image docker de rabbitMQ. Comme vu en cours, cela permet de récupérer les informations postées par les utilisateurs si le taches-service est indisponible. Cela donne l'illusion à l'utilisateur que le service fonctionne correctement. Les données sont ensuite sauvegardées lorsque le service est de nouveau opérationnel. 