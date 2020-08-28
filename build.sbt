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
    scalaVersion := scala213
  )
)

def scala212 = "2.12.12"
def scala213 = "2.13.3"
def scalaVersions = List(scala212, scala213)
lazy val svmVersion = Def.setting {
  dynverGitDescribeOutput.value.get.previousVersion
  // previousStableVersion.value.getOrElse {
    // sys.error("missing git tag. To fix this problem, run: git fetch --tags")
    // "20.1.0"
  // }
}

crossScalaVersions := Nil
skip in publish := true

lazy val subs = project
  .in(file("svm-subs"))
  .settings(
    moduleName := "svm-subs",
    crossScalaVersions := scalaVersions,
    libraryDependencies += "org.graalvm.nativeimage" % "svm" % svmVersion.value % "compile-internal",
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
