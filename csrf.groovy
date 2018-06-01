import jenkins.model.Jenkins
import hudson.security.csrf.DefaultCrumbIssuer

/**
 * Activation de la protection CSRF
 */
Jenkins.instance.setCrumbIssuer(new DefaultCrumbIssuer(true))
Jenkins.instance.save()