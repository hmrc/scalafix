import sbt._
import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._
/* scalafmt: { maxColumn = 120 }*/

object Dependencies {

  val scalametaV = "2.0.0-M1"
  val metaconfigV = "0.5.0"

  var testClasspath: String = "empty"
  def scalahost: ModuleID = "org.scalameta" % "scalahost" % scalametaV cross CrossVersion.full
  def scalahostSbt: ModuleID = "org.scalameta" % "sbt-scalahost" % scalametaV
  def metaconfig: ModuleID = "com.geirsson" %% "metaconfig-typesafe-config" % metaconfigV
  def ammonite = "com.lihaoyi" %% "ammonite-ops" % "0.9.0"
  def googleDiff = "com.googlecode.java-diff-utils" % "diffutils" % "1.3.0"

  def scalameta = Def.setting("org.scalameta" %%% "contrib" % scalametaV)
  def scalatest = Def.setting("org.scalatest" %%% "scalatest" % "3.0.0")
}
