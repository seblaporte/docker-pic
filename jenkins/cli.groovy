import jenkins.model.Jenkins


Jenkins.instance.getDescriptor("jenkins.CLI").get().setEnabled(false)
Set<String> agentProtocolsList =  ['JNLP4-connect']
Jenkins.instance.setAgentProtocols(agentProtocolsList)
Jenkins.instance.save()