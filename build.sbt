def scala212 = "2.12.12"
inThisBuild(
  List(
    organization := "org.scalameta",
    homepage := Some(url("https://github.com/scalameta/svm-subs")),
    licenses := List(
      "Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")
    ),
    developers := List(
      Developer(
        "olafurpg",
        "Ólafur Páll Geirsson",
        "olafurpg@gmail.com",
        url("https://geirsson.com")
      )
    ),
    scalaVersion := scala212,
    crossScalaVersions := List(scala212)
  )
)

crossScalaVersions := Nil
skip in publish := true

lazy val subs = project
  .in(file("svm-subs"))
  .settings(
    moduleName := "svm-subs",
    crossVersion := CrossVersion.disabled,
    libraryDependencies += "org.graalvm.nativeimage" % "svm" % "20.2.0" % "compile-internal",
    sources in (Compile, doc) := Seq.empty,
    javaHome in Compile := {
      // force javac to fork by setting javaHome to workaround https://github.com/sbt/zinc/issues/520
      val home = file(sys.props("java.home"))
      val actualHome =
        if (System.getProperty("java.version").startsWith("1.8"))
          home.getParentFile
        else home
      Some(actualHome)
    }
  )
