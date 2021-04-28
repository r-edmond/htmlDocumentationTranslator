name := "html-converter"

version := "0.1"

scalaVersion := "2.13.5"

idePackagePrefix := Some("org.nu.htmlDocumentation")

libraryDependencies += "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.2"
// https://mvnrepository.com/artifact/org.scala-lang.modules/scala-xml
libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "2.0.0-M1"
