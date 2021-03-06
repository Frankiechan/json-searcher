name := "json-searcher"

version := "1.0"

scalaVersion := "2.12.7"


val scalaTestVersion = "3.0.4"
val circeVersion = "0.10.1"
val catsVersion = "1.1.0"
val scalaCheckVersion = "1.13.5"
val attoVersion = "0.6.5"

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-effect" % catsVersion,
  "io.circe" %% "circe-parser" % circeVersion,
  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-testing" % circeVersion,
  "io.circe" %% "circe-literal" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "com.github.gvolpe" %% "console4cats" % "0.6.0",
  "org.scalatest" %% "scalatest" % scalaTestVersion % Test,
  "org.scalacheck" %% "scalacheck" % scalaCheckVersion % Test
)

dependencyOverrides ++= Seq(
  "org.typelevel" % "cats-core_2.12" % "1.5.0",
  "org.typelevel" % "cats-kernel_2.12" % "1.5.0",
  "org.typelevel" % "cats-macros_2.12" % "1.5.0"
)


scalacOptions ++= Seq(
  "-Ypartial-unification",
  "-Yno-adapted-args",
  "-Ywarn-numeric-widen",
  "-Ywarn-unused-import",
  "-Ywarn-numeric-widen",
  "-Ywarn-value-discard",
  "-Ypartial-unification",
  "-deprecation",
  "-unchecked",
  "-feature",
  "-Xfuture",
  "-Xlint",
  "-encoding", "UTF-8",
  "-language:existentials",
  "-language:higherKinds",
  "-language:reflectiveCalls",
  "-language:implicitConversions")

javacOptions ++= Seq(
  "-source", "1.8",
  "-target", "1.8"
)

scalacOptions in Test ++= Seq("-Yrangepos")

scalacOptions in Test --= Seq(
  "-Ywarn-value-discard",
  "-Ywarn-numeric-widen"
)

test in assembly := {}

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", _@_*) => MergeStrategy.discard
  case _ => MergeStrategy.first
}

assemblyOutputPath in assembly := new File("target/app.jar")




