
import sbt._
import Keys._

object Dependencies {
  
  val scalatest = "org.scalatest" %% "scalatest" % "3.0.5" % Test

  val dropwizardTest = "io.dropwizard" % "dropwizard-testing" % "1.3.8" % Test

  val mockito = "org.mockito" % "mockito-all" % "1.8.4" % Test

  val grizzly ="org.glassfish.jersey.test-framework.providers" % "jersey-test-framework-provider-grizzly2" % "2.25.1" % Test

  val h2 =  "com.h2database" % "h2" % "1.4.197" % Test

  
//  dao
  val postgres = "postgresql" % "postgresql" % "9.1-901-1.jdbc4"
  val slick =   "com.typesafe.slick" %% "slick" % "3.2.3"
  val flyway = "org.flywaydb" % "flyway-core" % "5.2.4"



  // dropwizard
  val dropwizardScala = "com.datasift.dropwizard.scala" %% "dropwizard-scala-core" % "1.3.7-1"
  val dropwizardguicer = "ru.vyarus" % "dropwizard-guicey" % "4.2.2"


  val dropwizard = Seq(dropwizardScala,dropwizardguicer)
  
  val dao = Seq(postgres,slick,flyway)

  val testDeps = Seq(scalatest,dropwizardTest,mockito,grizzly,h2)
  
  
}
