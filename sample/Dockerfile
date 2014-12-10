# DOCKER-VERSION 1.3.2
FROM ubuntu:14.04
MAINTAINER Antoine Hars <ahars@ippon.fr>

# make sure the package repository is up to date
RUN echo "deb http://archive.ubuntu.com/ubuntu trusty main universe" > /etc/apt/sources.list
RUN apt-get -y update

# install python-software-properties (so you can do add-apt-repository)
RUN DEBIAN_FRONTEND=noninteractive apt-get install -y -q python-software-properties software-properties-common

# install oracle java from PPA & other tools
RUN add-apt-repository ppa:webupd8team/java -y
RUN echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | /usr/bin/debconf-set-selections
RUN apt-get update && apt-get install -y \
    sudo \
    git \
    maven \
    oracle-java8-installer \
    && apt-get clean

# Set oracle java as the default java
RUN update-java-alternatives -s java-8-oracle
RUN echo "export JAVA_HOME=/usr/lib/jvm/java-8-oracle" >> ~/.bashrc

# install the spark-jhipster
RUN git clone https://github.com/ahars/spark-jhipster.git
RUN cd spark-jhipster && \
    mvn package

EXPOSE 8080

CMD java -jar spark-jhipster/target/spark-jhipster-0.0.1-SNAPSHOT.war
