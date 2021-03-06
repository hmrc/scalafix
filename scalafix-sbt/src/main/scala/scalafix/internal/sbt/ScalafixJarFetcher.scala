package scalafix.internal.sbt

import sbt.File

object ScalafixJarFetcher {
  def fetchJars(org: String, artifact: String, version: String): List[File] =
    this.synchronized {
      import coursier._
      val start = Resolution(Set(Dependency(Module(org, artifact), version)))
      val repositories = Seq(
        Cache.ivy2Local,
        MavenRepository("https://repo1.maven.org/maven2")
      )
      val fetch = Fetch.from(repositories, Cache.fetch())
      val resolution = start.process.run(fetch).unsafePerformSync
      val errors = resolution.metadataErrors
      if (errors.nonEmpty) {
        sys.error(errors.mkString("\n"))
      }
      val localArtifacts = scalaz.concurrent.Task
        .gatherUnordered(
          resolution.artifacts.map(Cache.file(_).run)
        )
        .unsafePerformSync
        .map(_.toEither)
      val failures = localArtifacts.collect { case Left(e) => e }
      if (failures.nonEmpty) {
        sys.error(failures.mkString("\n"))
      } else {
        val jars = localArtifacts.collect {
          case Right(file) if file.getName.endsWith(".jar") =>
            file
        }
        jars
      }
    }

}
