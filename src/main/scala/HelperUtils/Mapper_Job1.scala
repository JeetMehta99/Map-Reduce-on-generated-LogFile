package HelperUtils
import HelperUtils.CreateLogger
//import HelperUtils.Mapper_Job3.Mapper_3
import org.apache.hadoop.io.{IntWritable, LongWritable, Text}

import scala.io.Source.*
import scala.collection.mutable.ListBuffer
import org.joda.time.format.{DateTimeFormat, DateTimeFormatter}
import org.joda.time.{DateTime, *}
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}
import com.typesafe.config.ConfigFactory

import java.lang
import java.util.StringTokenizer
import org.apache.hadoop.mapreduce.lib.input.{FileInputFormat, TextInputFormat}
import org.apache.hadoop.mapreduce.lib.output.{FileOutputFormat, TextOutputFormat}
import org.apache.hadoop.mapreduce.{Job, Mapper, Reducer}

import scala.jdk.CollectionConverters.IterableHasAsScala

class Mapper_Job1 {}

object Mapper_Job1 {
  @throws[Exception]
  def runJob1(args: Array[String]): Unit = {
    val conf1 = new Configuration
    conf1.set("mapreduce.output.textoutputformat.separator", ",")
    val jobName1 = "MapReduceJob1"
    val job1 = Job.getInstance(conf1, jobName1)
    job1.setJarByClass(this.getClass)
    job1.setOutputKeyClass(classOf[Text])
    job1.setOutputValueClass(classOf[IntWritable])
    job1.setMapOutputKeyClass(classOf[Text])
    job1.setMapOutputValueClass(classOf[IntWritable])
    job1.setMapperClass(classOf[Mapper_Job1.Mapper_1])
    job1.setReducerClass(classOf[Mapper_Job1.Reducer_1])
    job1.setInputFormatClass(classOf[TextInputFormat])
    job1.setOutputFormatClass(classOf[TextOutputFormat[Text, Text]])
    FileInputFormat.addInputPath(job1, new Path(args(0)))
    FileOutputFormat.setOutputPath(job1, new Path(args(1)))
    System.exit(if (job1.waitForCompletion(true)) 0 else 1)
  }

  class Mapper_1 extends Mapper[LongWritable, Text, Text, IntWritable] {
    private val one_job1: IntWritable = new IntWritable(1)
    private val word_job1: Text = new Text
    val logger = CreateLogger(classOf[Mapper_1])
    val conf = ConfigFactory.load("application.conf")
    val pattern = (conf.getString("randomLogGenerator.Pattern")).r

    override def map(key: LongWritable, rowLine: Text, context: Mapper[LongWritable, Text, Text, IntWritable]#Context): Unit = {
      val read_line = rowLine.toString

      if (!read_line.isEmpty) {
        val separate: Array[String] = read_line.split(" - ")

        val log_splits: Array[String] = read_line.split(" ")
        val logtype = log_splits(2)

        val log_time = log_splits(0)
        val timeBins = (DateTimeFormat.forPattern("HH:mm:ss.SSS").parseDateTime(log_time).getMillis() / 1000) / 5

        val patternmatch = pattern.findAllIn(separate(1)).toString()

        if (!patternmatch.isEmpty) {
          val index_map1 = timeBins + "=" + logtype
          context.write(new Text(index_map1), new IntWritable(1))



        }
      }
    }
  }

  class Reducer_1 extends Reducer[Text, IntWritable, Text, IntWritable] {

    private val result1 = new IntWritable(1)
    val logger = CreateLogger(classOf[Reducer_1])

    override def reduce(key: Text, values: lang.Iterable[IntWritable], context: Reducer[Text, IntWritable, Text, IntWritable]#Context): Unit = {
      {
        context.write(key, result1)
      }
    }
  }
}
