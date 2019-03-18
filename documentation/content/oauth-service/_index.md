---
title: "Oauth-service"
weight: 6
alwaysopen : false
---

## Principes

Le service Oauth permet de donner l'autorisation à un utilisateur d'accéder à l'API. Ce service permet de la génération de token afin que l'utilisateur puisse prouver qu'il est autorisé à manipuler les différentes ressources. Pour obtenir un token, l'utilisateur doit fournir les clefs de l'API ainsi que l'identifiant et le mot de passe d'un utilisateur ayant des autorisations. 

{{% notice tip %}}
Ce service contient une base de données H2 qui contient un utilisateur. Il aurait été intéressant d'utiliser les profils des personnes du personnes-service.
{{% /notice %}}

## Exemples

{{% notice tip %}}
Voici la liste des informations pour obtenir un token d'autorisation :<br>
- Client ID = 8c3a4ad0-7ad0-4acc-b462-38329eee5c14<br>
- Client Secret = 72dbb01f-2f80-4381-8232-85b43e49eb2a<br>
- Username = matthieu<br>
- Password = sesame
{{% /notice %}}

Pour cet exemple, je suis passé par le gateway-service qui est décrit dans la prochaine partie. En faisant une requête GET pour récupérer les tâches, on obtient un message d'erreur nous indiquant que nous n'avons pas l'autorisation.

![refus](../images/oauth-service/capture0.png)

Pour obtenir la liste des tâches, il faut désormais détenir le token d'autorisation. Il faut donc configurer la partie 'Authorization' dans Postman :

![authorization](../images/oauth-service/capture1.png)

Cette fonctionnalité permet d'obtenir le token d'autorisation. Ce token permet donc de déverrouiller l'accès à l'API comme le montre la capture d'écran suivante :

![token](../images/oauth-service/capture2.png)

Comme on peut le voir, ce service permet donc de sécuriser l'application.