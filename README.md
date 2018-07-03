# pulsar-mqtt-source

pulsar-mqtt-source subscribes to MQTT topics and publishes all received messages into an Apache Pulsar topic.

**Warning**: pulsar-mqtt-source is not yet functional.


## Usage

Run the Docker image published at https://hub.docker.com/r/hsldevcom/pulsar-mqtt-source .


## Configuration

pulsar-mqtt-source can be configured with a file and/or environment variables.

If a configuration file is intended to be used, its path must be set in the environment variable `CONFIG_PATH`.
The reference configuration can be found from the file `src/main/resources/application.conf` in the git repository.
The configuration file must follow one of the formats allowed by [lightbend/config](https://github.com/lightbend/config/).

The values of the environment variables override any conflicting values read from the configuration file.
The names of the environment variables and how they match with the reference configuration can be found from the file `src/main/resources/environment.conf` in the git repository.
