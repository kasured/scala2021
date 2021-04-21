name := "scala2021"

Compile / mainClass := Some("scala2021.erusak.task01.Hola")

scalaVersion := "2.13.5"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.7" % "test"
libraryDependencies += "org.scalacheck" %% "scalacheck" % "1.14.1" % "test"
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3"
libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.9.3"

Compile / scalacOptions ++= Seq("-encoding", "UTF-8", "-target:jvm-1.8",
  "-deprecation", "-feature", "-unchecked",
  "-Xlog-reflective-calls",
  "-Xlint",
  "-Xfatal-warnings"
)