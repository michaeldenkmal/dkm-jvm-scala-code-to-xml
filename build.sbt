ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.13"

lazy val root = (project in file("."))
  .settings(
    name := "scalaCodeToJson"
  )

libraryDependencies += "org.scalameta" %% "scalameta" % "4.5.0"
// https://mvnrepository.com/artifact/org.dom4j/dom4j
libraryDependencies += "org.dom4j" % "dom4j" % "2.1.4"

//libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "2.2.0"
// https://mvnrepository.com/artifact/junit/junit
libraryDependencies += "junit" % "junit" % "4.12" % Test
