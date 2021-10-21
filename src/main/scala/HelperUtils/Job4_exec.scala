package HelperUtils


import HelperUtils.CreateLogger
import org.apache.hadoop.io.{IntWritable, LongWritable, Text}
import scala.io.Source.*
import scala.collection.mutable.ListBuffer
import org.joda.time.format.{DateTimeFormat, DateTimeFormatter}
import org.joda.time.{DateTime, *}
//import org.apache.hadoop.mapred.Mapper
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{Path, FileSystem}
import java.lang
import java.util.StringTokenizer
import com.typesafe.config.ConfigFactory
import org.apache.hadoop.mapreduce.lib.input.{FileInputFormat, TextInputFormat}
import org.apache.hadoop.mapreduce.lib.output.{FileOutputFormat, TextOutputFormat}
import org.apache.hadoop.mapreduce.{Job, Mapper, Reducer}
import scala.jdk.CollectionConverters.IterableHasAsScala
//Job4
object Job4_exec {
  
  class Mapper_Job4 extends Mapper[LongWritable, Text, Text, IntWritable] {
    //    private val one_job4: IntWritable = new IntWritable(1)
    //    private val word_job4: Text = new Text
    val logger = CreateLogger(classOf[Mapper_Job4])
    val conf = ConfigFactory.load("application.conf")
    val pattern = (conf.getString("randomLogGenerator.Pattern")).r

    override def map(key: LongWritable, rowLine: Text, context: Mapper[LongWritable, Text, Text, IntWritable]#Context): Unit = {
      val read_line = rowLine.toString
      if (!read_line.isEmpty) {
        val key4: Array[String] = read_line.split(",")
        val log_errtype: Array[String] = key4(0).split("=")
        context.write(new Text(log_errtype(0)), new IntWritable(log_errtype(1).toInt))
      }
    }
  }

  class Reducer_Job4 extends Reducer[Text, IntWritable, Text, IntWritable] {

    private val result = new IntWritable(1)
    val logger = CreateLogger(classOf[Reducer_Job4])

    override def reduce(key: Text, values: lang.Iterable[IntWritable], context: Reducer[Text, IntWritable, Text, IntWritable]#Context): Unit = {
      {
        var max_value = 0
        for (value <- values.asScala) {
          if (value.get > max_value) max_value = value.get
        }
        logger.info(s"Key is ${key}, value is ${max_value}")
        context.write(key, new IntWritable(max_value))
      }
    }
  }
}








