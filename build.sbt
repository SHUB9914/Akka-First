name := "Akka-FirstAssignment"
version := "1.0"

scalaVersion := "2.11.8"


val akkaVersion = "2.4.17"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion ,
  "com.typesafe.akka" % "akka-http-experimental_2.11" % "2.4.11.2" ,
  "com.typesafe.akka" % "akka-http-spray-json-experimental_2.11" % "2.4.11.2"
)

