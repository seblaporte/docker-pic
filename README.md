# docker-pic
Conteneurisation d'une plateforme d'intégration continue

### Jenkins

L'initialisation du compte administrateur s'appuie sur les infos contenues dans docker secret.
avant le premier démarrage du container il faut créer dans docker secret le user et son mdp.

```bash

echo "myuser" | docker secret create jenkins-user -
echo "mypassword" | docker secret create jenkins-pass -

```

### Déploiement de la stack

* fichier pic-stack.yml  
  Ce fichier décrit l'ensemble des services qui seront déployés.
  * jenkins
  * sonarqube
  * nexus  
  * traefik
  * ... 