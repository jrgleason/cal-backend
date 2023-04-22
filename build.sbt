/*
  General Scala attributes
 */
scalaVersion := "3.2.2"

/*
  General project attributes
 */
organization := "com.secondave"
name := "CalBackend"
version := "0.0.1-SNAPSHOT"
description := "Backend service for my scheduling system"
organizationHomepage := Some(url("http://secondave.net"))

/*
  Project dependencies
 */
libraryDependencies ++= Seq(
  "org.springframework.boot" % "spring-boot-starter-web" % "3.0.6",
  "org.springframework.boot" % "spring-boot-starter-security" % "3.0.6",
  "com.auth0" % "auth0" % "2.1.0",
  "com.auth0" % "java-jwt" % "4.4.0",
  "com.google.auth" % "google-auth-library-oauth2-http" % "1.3.0",
  "com.google.api-client" % "google-api-client" % "2.2.0",
  "com.google.apis" % "google-api-services-calendar" % "v3-rev20220715-2.0.0",
)

/*
  Packaging plugin
 */

// enable the Java app packaging archetype and Ash script (for Alpine Linux, doesn't have Bash)
//enablePlugins(JavaAppPackaging, AshScriptPlugin)

// set the main entrypoint to the application that is used in startup scripts
//mainClass in Compile := Some("de.codecentric.microservice.MyServiceApplication")

// the Docker image to base on (alpine is smaller than the debian based one (120 vs 650 MB)
//dockerBaseImage := "openjdk:8-jre-alpine"

// creates tag 'latest' as well when publishing
//dockerUpdateLatest := true