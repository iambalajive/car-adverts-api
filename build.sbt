name := "car-adverts-api"

version := "0.1"

scalaVersion := "2.12.8"


lazy val app = (project in file("."))
  .settings(
//    libraryDependencies ++= serverDeps
  ).enablePlugins(JavaAppPackaging)