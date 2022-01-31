ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "2.13.7"
ThisBuild /scalacOptions := Seq(
  "-deprecation",
  "-feature",
  "-Xlint",
  "-Ywarn-dead-code"
)

lazy val root = (project in file("."))
  .settings(
    name := "ddd-aggregate-sample",
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "3.2.10" % Test
    )
  )
