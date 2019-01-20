
import sbt._
import Keys._

object Dependencies {
  
  val scalatest = "org.scalatest" %% "scalatest" % "3.0.5" % Test

  val dropwizardTest = "io.dropwizard" % "dropwizard-testing" % "1.3.8" % Test

  val mockito = "org.mockito" % "mockito-all" % "1.8.4" % Test

  val grizzly ="org.glassfish.jersey.test-framework.providers" % "jersey-test-framework-provider-grizzly2" % "2.25.1" % Test

  val h2 =  "com.h2database" % "h2" % "1.4.197" % Test

  val testDeps = Seq(scalatest,dropwizardTest,mockito,grizzly,h2)
  
  

  val applicationDependencies = testDeps

}