package HelperUtils

import HelperUtils.CreateLogger
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

class Mapper_Job2 {}

object Mapper_Job2
{
  @throws[Exception]
  def runJob2(args: Array[String]): Unit = {
    val conf2_initial = new Configuration
    conf2_initial.set("mapreduce.output.textoutputformat.separator", ",")
    val jobName2_initial = "MapReduceJob2"
    val job2_initial = Job.getInstance(conf2_initial, jobName2_initial)
    job2_initial.setJarByClass(this.getClass)
    job2_initial.setOutputKeyClass(classOf[Text])
    job2_initial.setOutputValueClass(classOf[IntWritable])
    job2_initial.setMapOutputKeyClass(classOf[Text])
    job2_initial.setMapOutputValueClass(classOf[IntWritable])
    job2_initial.setMapperClass(classOf[Mapper_Job2.Mapper_2])
    job2_initial.setReducerClass(classOf[Mapper_Job2.Reducer_2])
    job2_initial.setInputFormatClass(classOf[TextInputFormat])
    job2_initial.setOutputFormatClass(classOf[TextOutputFormat[Text, Text]])
    FileInputFormat.addInputPath(job2_initial, new Path(args(0)))
    FileOutputFormat.setOutputPath(job2_initial, new Path(args(1)))
    job2_initial.waitForCompletion(true)

    //final_exec
    val conf2 = new Configuration
    conf2.set("mapreduce.output.textoutputformat.separator", ",")
    val jobName2 = "MapReduceJob2_Exec"

    val job2 = Job.getInstance(conf2, jobName2)
    job2.setJarByClass(this.getClass)
    job2.setOutputKeyClass(classOf[IntWritable])
    job2.setOutputValueClass(classOf[Text])
    job2.setMapOutputKeyClass(classOf[IntWritable])
    job2.setMapOutputValueClass(classOf[Text])
    job2.setMapperClass(classOf[Job2_exec.Mapper_Job2])
    job2.setReducerClass(classOf[Job2_exec.Reducer_Job2])
    job2.setInputFormatClass(classOf[TextInputFormat])
    job2.setOutputFormatClass(classOf[TextOutputFormat[Text, Text]])
    FileInputFormat.addInputPath(job2, new Path(args(1)))
    FileOutputFormat.setOutputPath(job2, new Path(args(2)))
    job2.setSortComparatorClass(classOf[Comparator])

    System.exit(if (job2.waitForCompletion(true)) 0 else 1)
  }
  class Mapper_2 extends Mapper[LongWritable, Text, Text, IntWritable]
  {
    private val one_job1: IntWritable = new IntWritable(1)
    private val word_job1: Text = new Text
    val logger = CreateLogger(classOf[Mapper_2])
    val conf = ConfigFactory.load("application.conf")
    val pattern = (conf.getString("randomLogGenerator.Pattern")).r
    override def map(key: LongWritable, rowLine: Text, context: Mapper[LongWritable, Text, Text, IntWritable]#Context): Unit =
    {
      val read_line = rowLine.toString
      if (!read_line.isEmpty)
      {
        val separate: Array[String] = read_line.split(" - ")

        val log_splits: Array[String] = read_line.split(" ")
        val logtype = log_splits(2)

        val log_time = log_splits(0)
        val timeBins = (DateTimeFormat.forPattern("HH:mm:ss.SSS").parseDateTime(log_time).getMillis() / 1000) / 5

        val patternmatch = pattern.findAllIn(separate(1)).toString()

        if (patternmatch.nonEmpty && logtype == "ERROR")
        {
          context.write(new Text(timeBins.toString), new IntWritable(1))
        }

      }
    }
  }
  class Reducer_2 extends Reducer[Text, IntWritable, Text, IntWritable] {

    private val result2 = new IntWritable(1)
    val logger = CreateLogger(classOf[Reducer_2])

    override def reduce(key: Text, values: lang.Iterable[IntWritable], context: Reducer[Text, IntWritable, Text, IntWritable]#Context): Unit = {
      {
        val sum = values.asScala.foldLeft(0)(_ + _.get)
        context.write(key, new IntWritable(sum))
      }
    }
  }
}

