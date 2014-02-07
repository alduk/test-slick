name := "test-slick"

version := "1.0.1"

scalaVersion := "2.10.3"

scalacOptions += "-deprecation"

// scala-compiler is declared as an optional dependency by Slick.
// You need to add it explicitly to your own project if you want
// to use the direct embedding (as in SimpleExample.scala here).
libraryDependencies <+= (scalaVersion)("org.scala-lang" % "scala-compiler" % _)

// Use the right Slick version here:
libraryDependencies += "com.typesafe.slick" %% "slick-extensions" % "2.0.0"

resolvers += "Typesafe Releases" at "http://repo.typesafe.com/typesafe/maven-releases/"

libraryDependencies ++= List(
  "com.typesafe.slick" %% "slick" % "2.0.0",
  "org.slf4j" % "slf4j-nop" % "1.6.4",
  "com.h2database" % "h2" % "1.3.166"
) 
