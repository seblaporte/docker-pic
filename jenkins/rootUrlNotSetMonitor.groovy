import jenkins.model.Jenkins
import jenkins.diagnostics.RootUrlNotSetMonitor

Jenkins.instance.administrativeMonitors.each {
    monitor ->
        if(monitor instanceof RootUrlNotSetMonitor) {
            monitor.disable(true)
        }
}
Jenkins.instance.save()