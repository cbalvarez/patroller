addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.7.4")

addSbtPlugin("com.typesafe.sbteclipse" % "sbteclipse-plugin" % "3.0.0")

addSbtPlugin("com.despegar.sbt" % "plugins" % "0.13")

addSbtPlugin("com.earldouglas" % "xsbt-web-plugin" % "0.7.0")

addSbtPlugin("com.despegar.sbt" %% "madonna" % "0.0.25")

addSbtPlugin("com.github.gseitz" % "sbt-release" % "0.8.5")

resolvers += "Despegar Releases" at "http://nexus.despegar.it/nexus/content/groups/public-release/"

scalacOptions := Seq("-deprecation")
