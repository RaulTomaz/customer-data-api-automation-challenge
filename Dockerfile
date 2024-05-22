FROM eclipse-temurin:17-jdk
LABEL authors="raulg"

WORKDIR /challenge/raul-tomaz-qa-test/

RUN apt-get update

RUN apt-get install -y maven

RUN apt-get install curl -y
RUN curl -o allure-commandline-2.17.3.tgz -Ls https://repo.maven.apache.org/maven2/io/qameta/allure/allure-commandline/2.17.3/allure-commandline-2.17.3.tgz
RUN tar -zxvf allure-commandline-2.17.3.tgz -C /opt/
RUN ln -s /opt/allure-2.17.3/bin/allure /usr/bin/allure
RUN rm -rf allure-commandline-2.17.3.tgz

COPY . /challenge/raul-tomaz-qa-test/

EXPOSE 4444