/*
   Disable Jenkins CLI.
   Copy script to $JENKINS_HOME/init.groovy.d/
   This init script for Jenkins fixes a zero day vulnerability.
   http://jenkins-ci.org/content/mitigating-unauthenticated-remote-code-execution-0-day-jenkins-cli
   https://github.com/jenkinsci-cert/SECURITY-218
   https://github.com/samrocketman/jenkins-script-console-scripts/blob/master/disable-jenkins-cli.groovy
 */

import jenkins.AgentProtocol
import jenkins.model.Jenkins
import hudson.model.RootAction

//determined if changes were made
configChanged = false

// disabled CLI access over TCP listener (separate port)
def p = AgentProtocol.all()
p.each { x ->
    if(x.name && x.name.contains("CLI")) {
        //println "remove ${x}"
        p.remove(x)
        configChanged = true
    }
}

// disable CLI access over /cli URL
def removal = { lst ->
    lst.each { x ->
        if(x.getClass().name.contains("CLIAction")) {
            //println "remove ${x}"
            lst.remove(x)
            configChanged = true
        }
    }
}
def j = Jenkins.instance;
removal(j.getExtensionList(RootAction.class))
removal(j.actions)

if(configChanged) {
    println 'Jenkins CLI has been disabled.'
} else {
    println 'Nothing changed. Jenkins CLI already disabled.'
}

Jenkins.instance.save()