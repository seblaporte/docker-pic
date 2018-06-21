
import jenkins.model.Jenkins

//configure cloud stack
import com.nirima.jenkins.plugins.docker.DockerCloud
import com.nirima.jenkins.plugins.docker.DockerTemplate
import io.jenkins.docker.client.DockerAPI
import net.sf.json.JSONArray
import net.sf.json.JSONObject
import org.jenkinsci.plugins.docker.commons.credentials.DockerServerEndpoint
import org.kohsuke.stapler.StaplerRequest

JSONArray<JSONObject> docker_settings = new JSONArray<JSONObject>()
docker_settings.addAll([
        [
                name             : 'docker-local',
                serverUrl        : 'tcp://172.18.0.1:4243',
                containerCap     : 10,
                connectionTimeout: 5,
                readTimeout      : 15,
                credentialsId    : '',
                version          : '',
                templates        : []
        ]
])


def bindJSONToList(Class type, Object src) {
    if (type == DockerTemplate) {
        ArrayList<DockerTemplate> r = new ArrayList<DockerTemplate>();
        if (src instanceof JSONObject) {
            JSONObject temp = (JSONObject) src;
            r.add(
                    new DockerTemplate(temp.optString('image'),
                            temp.optString('labelString'),
                            temp.optString('remoteFs'),
                            temp.optString('remoteFsMapping'),
                            temp.optString('credentialsId'),
                            temp.optString('idleTerminationMinutes'),
                            temp.optString('sshLaunchTimeoutMinutes'),
                            temp.optString('jvmOptions'),
                            temp.optString('javaPath'),
                            temp.optInt('memoryLimit'),
                            temp.optInt('cpuShares'),
                            temp.optString('prefixStartSlaveCmd'),
                            temp.optString('suffixStartSlaveCmd'),
                            temp.optString('instanceCapStr'),
                            temp.optString('dnsString'),
                            temp.optString('dockerCommand', '/sbin/my_init'),
                            temp.optString('volumesString'),
                            temp.optString('volumesFromString'),
                            temp.optString('environmentsString'),
                            temp.optString('lxcConfString'),
                            temp.optString('hostname'),
                            temp.optString('bindPorts'),
                            temp.optBoolean('bindAllPorts'),
                            temp.optBoolean('privileged'),
                            temp.optBoolean('tty'),
                            temp.optString('macAddress')
                    )
            );
        }
        if (src instanceof JSONArray) {
            JSONArray json_array = (JSONArray) src;
            for (Object o : json_array) {
                if (o instanceof JSONObject) {
                    JSONObject temp = (JSONObject) o;
                    r.add(
                            new DockerTemplate(temp.optString('image'),
                                    temp.optString('labelString'),
                                    temp.optString('remoteFs'),
                                    temp.optString('remoteFsMapping'),
                                    temp.optString('credentialsId'),
                                    temp.optString('idleTerminationMinutes'),
                                    temp.optString('sshLaunchTimeoutMinutes'),
                                    temp.optString('jvmOptions'),
                                    temp.optString('javaPath'),
                                    temp.optInt('memoryLimit'),
                                    temp.optInt('cpuShares'),
                                    temp.optString('prefixStartSlaveCmd'),
                                    temp.optString('suffixStartSlaveCmd'),
                                    temp.optString('instanceCapStr'),
                                    temp.optString('dnsString'),
                                    temp.optString('dockerCommand'),
                                    temp.optString('volumesString'),
                                    temp.optString('volumesFromString'),
                                    temp.optString('environmentsString'),
                                    temp.optString('lxcConfString'),
                                    temp.optString('hostname'),
                                    temp.optString('bindPorts'),
                                    temp.optBoolean('bindAllPorts'),
                                    temp.optBoolean('privileged'),
                                    temp.optBoolean('tty'),
                                    temp.optString('macAddress')
                            )
                    );
                }
            }
        }
        return r;
    }
    if (type == DockerCloud) {
        ArrayList<DockerCloud> r = new ArrayList<DockerCloud>();
        if (src instanceof JSONObject) {
            JSONObject temp = (JSONObject) src;
            if (!Jenkins.instance.clouds.getByName(temp.optString('name'))) {
                DockerCloud dockerCloud = new DockerCloud(
                        temp.optString('name'),
                        new DockerAPI(
                                new DockerServerEndpoint(temp.optString('serverUrl'),temp.optString('credentialsId')),
                                temp.optInt('connectTimeout', 5),
                                temp.optInt('readTimeout', 15),
                                '',
                                temp.optString('version')
                        ),
                        bindJSONToList(DockerTemplate.class, temp.optJSONArray('templates'))
                )
                dockerCloud.setContainerCap(temp.optInt('containerCap',10))
                r.add(dockerCloud);
            }
        }
        if (src instanceof JSONArray) {
            JSONArray json_array = (JSONArray) src;
            for (Object o : json_array) {
                if (o instanceof JSONObject) {
                    JSONObject temp = (JSONObject) o;
                    if (!Jenkins.instance.clouds.getByName(temp.optString('name'))) {

                        DockerCloud dockerCloud = new DockerCloud(
                                temp.optString('name'),
                                new DockerAPI(
                                    new DockerServerEndpoint(temp.optString('serverUrl'),temp.optString('credentialsId')),
                                    temp.optInt('connectTimeout', 5),
                                    temp.optInt('readTimeout', 15),
                                    '',
                                    temp.optString('version')
                                ),
                                bindJSONToList(DockerTemplate.class, temp.optJSONArray('templates'))
                        )

                        dockerCloud.setContainerCap(temp.optInt('containerCap',10))
                        r.add(dockerCloud);
                    }
                }
            }
        }
        return r;
    }
}


def req = [
        bindJSONToList: { Class type, Object src ->
            bindJSONToList(type, src)
        }
] as org.kohsuke.stapler.StaplerRequest


ArrayList<DockerCloud> clouds = new ArrayList<DockerCloud>();
clouds = req.bindJSONToList(DockerCloud.class, docker_settings)
if (clouds.size() > 0) {
    Jenkins.instance.clouds.addAll(clouds)
    println 'Configured docker cloud.'
}