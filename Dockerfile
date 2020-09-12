FROM jboss/wildfly

MAINTAINER ron4uk
RUN /opt/jboss/wildfly/bin/add-user.sh --silent=true admin "admin" ManagementRealm
RUN mkdir -p /opt/jboss/wildfly/standalone/deployments/

COPY /target/eCareAd-1.0-SNAPSHOT.war /opt/jboss/wildfly/standalone/deployments/
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-c","standalone-full.xml","-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]
EXPOSE 9000 9990  8080
