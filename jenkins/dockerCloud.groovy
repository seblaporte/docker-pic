
import jenkins.model.Jenkins

//configure cloud stack
import com.nirima.jenkins.plugins.docker.DockerCloud
import io.jenkins.docker.client.DockerAPI
import org.jenkinsci.plugins.docker.commons.credentials.DockerServerEndpoint


if (!Jenkins.instance.clouds.getByName('docker-local')) {
    DockerCloud dockerCloud = new DockerCloud(
            'docker-local',
            new DockerAPI(
                    new DockerServerEndpoint('tcp://172.18.0.1:4243',''),
                    5,
                    15,
                    '',
                    ''
            ),
            []
    )
    dockerCloud.setContainerCap(10)
    Jenkins.instance.clouds.add(dockerCloud);
    Jenkins.instance.save()
    println 'Configured docker cloud.'
}
