<<<<<<< HEAD
name := "AdvWebProject"
=======
name := "play2torial"
>>>>>>> 44691b7985ab60f68709bd25ec14fb3be81c3330

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  cache,
  javaJdbc,
  javaWs,
  javaJpa.exclude("org.hibernate.javax.persistence", "hibernate-jpa-2.0-api"),
  "org.hibernate" % "hibernate-entitymanager"      % "4.3.10.Final", // replace by your jpa implementation
  "mysql" % "mysql-connector-java"                 % "5.1.40",
  "javax.inject" % "javax.inject"                  % "1",
  "org.springframework" % "spring-aop"             % "4.1.6.RELEASE",
  "org.springframework" % "spring-beans"           % "4.1.6.RELEASE",
  "org.springframework" % "spring-context"         % "4.1.6.RELEASE",
  "org.springframework" % "spring-core"            % "4.1.6.RELEASE",
  "org.springframework" % "spring-jdbc"            % "4.1.6.RELEASE",
  "org.springframework" % "spring-orm"             % "4.1.6.RELEASE",
  "org.springframework" % "spring-tx"              % "4.1.6.RELEASE"
)

libraryDependencies+= "org.webjars" % "jquery" %"1.11.2"

libraryDependencies += "org.webjars" % "bootstrap" % "2.1.1"


PlayKeys.playWatchService := play.sbtplugin.run.PlayWatchService.sbt(pollInterval.value)