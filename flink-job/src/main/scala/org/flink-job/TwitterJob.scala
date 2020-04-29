package org.flink-job


import java.util.Properties
import org.apache.flink.streaming.connectors.twitter.TwitterSource
import com.twitter.hbc.core.endpoint.{StatusesFilterEndpoint, StreamingEndpoint, Location}

import scala.collection.JavaConverters._

import org.apache.flink.api.scala._
import org.apache.flink.api.common.functions.FlatMapFunction
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.core.fs.FileSystem.WriteMode

import scala.collection.mutable.ListBuffer

object TwitterJob {
  def main(args: Array[String]) {

    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.getConfig.setGlobalJobParameters(params)

    val p = new Properties()
    p.setProperty(TwitterSource.CONSUMER_KEY, "")
    p.setProperty(TwitterSource.CONSUMER_SECRET, "")
    p.setProperty(TwitterSource.TOKEN, "")
    p.setProperty(TwitterSource.TOKEN_SECRET, "")

    val source = new TwitterSource(p)
    val ep = new FilterEndpoint()

    source.setCustomEndpointInitializer(ep)

    val streamSource = env.addSource(source)

    env.execute()
  }
}

private class FilterEndpoint extends TwitterSource.EndpointInitializer with Serializable {
  @Override
  def createEndpoint(): StreamingEndpoint {
    val endpoint = new StatusesFilterEndpoint()
    val chicago = new Location(new Location.Coordinate(-86.0, 41.0), new Location.Coordinate(-87.0, 42.0))

    endpoint.locations(List(chicago).asJava)
    endpoint.trackTerms(List("COVID", "pandemic", "coronavirus").asJava)
    return endpoint
  }
}