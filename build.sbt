name := "scalac_r"

version := "1.0"

scalaVersion := "2.11.6"

libraryDependencies += "org.scalatest" % "scalatest_2.11" % "2.2.1" % "test"

libraryDependencies += "com.github.scopt" %% "scopt" % "3.3.0"

resolvers += Resolver.sonatypeRepo("public")

assemblyJarName in assembly := "chess.jar"

mainClass in assembly := Some("chess.App")

scalacOptions ++= Seq("-deprecation")