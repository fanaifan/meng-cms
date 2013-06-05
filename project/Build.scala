import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "meng-cms"
  val appVersion      = "1.0"

  val appDependencies = Seq(
    // Add your project dependencies here,
    javaCore,
    javaJdbc,
    javaEbean,
    //"commons-codec" % "commons-codec" % "1.8",
    "org.apache.commons" % "commons-email" % "1.3.1",
    "mysql" % "mysql-connector-java" % "5.1.18"
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here      
  )

}
