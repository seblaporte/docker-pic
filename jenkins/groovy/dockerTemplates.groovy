import jenkins.model.Jenkins

import com.nirima.jenkins.plugins.docker.DockerCloud
import com.nirima.jenkins.plugins.docker.DockerTemplate
import com.nirima.jenkins.plugins.docker.DockerTemplateBase

import io.jenkins.docker.connector.DockerComputerAttachConnector

DockerCloud cloud = Jenkins.instance.clouds.getByName('docker-local')
if (cloud) {
    /**
     * Definition du slave Nodejs
     */

    DockerTemplate dockerTemplate = new DockerTemplate(
            new DockerTemplateBase('lydobnk/nodejs-slave'),
            new DockerComputerAttachConnector(),
            'nodejs-slave', '', '')
    dockerTemplate.setRemoveVolumes(true)
    cloud.addTemplate(dockerTemplate)
    Jenkins.instance.save()
    println 'nodejs-slave set'
}