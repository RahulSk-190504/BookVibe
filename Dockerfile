FROM tomcat:9.0-jdk17

# Remove default Tomcat apps
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy WAR into Tomcat
COPY deploy/BookVibe.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080
