package HelperUtils

import HelperUtils.CreateLogger
import org.apache.hadoop.io.{IntWritable, LongWritable, Text}
import scala.io.Source.*
import scala.collection.mutable.ListBuffer
import org.joda.time.format.{DateTimeFormat, DateTimeFormatter}
import org.joda.time.{DateTime, *}
import com.typesafe.config.ConfigFactory
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{Path, FileSystem}
import java.lang
import java.util.StringTokenizer
import org.apache.hadoop.mapreduce.lib.input.{FileInputFormat, TextInputFormat}
import org.apache.hadoop.mapreduce.lib.output.{FileOutputFormat, TextOutputFormat}
import org.apache.hadoop.mapreduce.{Job, Mapper, Reducer}
import scala.jdk.CollectionConverters.IterableHasAsScala

object Job2_exec {

  class Mapper_Job2 extends Mapper[LongWritable, Text, IntWritable, Text] {
    val logger = CreateLogger(classOf[Mapper_Job2])
    val conf = ConfigFactory.load("application.conf")
    val pattern = (conf.getString("randomLogGenerator.Pattern")).r
    override def map(key: LongWritable, rowLine: Text, context: Mapper[LongWritable, Text, IntWritable, Text]#Context): Unit = {
      val read_line = rowLine.toString
      if (!read_line.isEmpty) {
        val key2 = read_line.split(",")  //Separate the (key, value) and this key is the input to second Mapper
        context.write(new IntWritable(key2(1).toInt), new Text(key2(0)))
      }
    }
  }
  class Reducer_Job2 extends Reducer[IntWritable, Text, IntWritable, Text] {
    override def reduce(key: IntWritable, values: java.lang.Iterable[IntWritable], context: Reducer[IntWritable, Text, IntWritable, Text]#Context): Unit = {
      
      val value1 = values.asScala.foldLeft(",")(_ + _).substring(1)
      context.write(key, new Text(value1))
    }
  }

}