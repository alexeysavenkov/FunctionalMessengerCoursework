package playUtils

import javax.inject.Inject

import play.api.{Configuration, Play}

object Config {
 private val config = Play.current.configuration.underlying

 def getStr(key: String): String = config.getString(key)
}
