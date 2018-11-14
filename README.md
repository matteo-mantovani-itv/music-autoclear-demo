# music-autoclear-demo
Proof of concept demo for the AutoClear project


To run this locally:

1. Run the docker-devup.sh script which will provide a postgresql database
2. Open the autoclear-demo-parent/pom.xml file in intellij
3. Right click on DominoBackendApplication and run it, this will fail but add the following to the run configuration program arguments:

db migrate ../autoclear-demo-backend/src/main/resources/app-conf.yaml

4. Re-run it, that should now just work - creating the db schema for the demo via liquibase

5. Copy that configuration and change the program arguments to be:

server ../autoclear-demo-backend/src/main/resources/app-conf.yaml

6. Running that will run the dropwizard backend - available at http://127.0.0.1:9200

7. Create a new run configuration of type npm -> run -> serve ... and just run it, that will start the angular frontend (which will dynamically update upon changes)
