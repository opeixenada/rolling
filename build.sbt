name := "Rolling"

version := "1.0"

scalaVersion := "2.12.3"

mainClass in (Compile, run) := Some("RollingWindowProcessor")

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= Seq(
  "org.scalactic" %% "scalactic" % "3.0.3",
  "org.scalatest" %% "scalatest" % "3.0.3" % "test")