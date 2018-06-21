FROM jenkins/jenkins:lts

ENV JAVA_OPTS="-Djenkins.install.runSetupWizard=false"

COPY security.groovy /usr/share/jenkins/ref/init.groovy.d/security.groovy

COPY cli.groovy /usr/share/jenkins/ref/init.groovy.d/cli.groovy

COPY csrf.groovy /usr/share/jenkins/ref/init.groovy.d/csrf.groovy

COPY rootUrlNotSetMonitor.groovy /usr/share/jenkins/ref/init.groovy.d/rootUrlNotSetMonitor.groovy

COPY plugins.txt /usr/share/jenkins/ref/plugins.txt
RUN /usr/local/bin/install-plugins.sh < /usr/share/jenkins/ref/plugins.txt

ENV JENKINS_OPTS="--webroot=/var/cache/jenkins/war"
USER root
RUN mkdir /var/cache/jenkins
RUN chown -R jenkins:jenkins /var/cache/jenkins

USER jenkins