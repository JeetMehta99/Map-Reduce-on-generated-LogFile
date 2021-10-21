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
//import scala.jdk.CollectionConverters.IterableHasAsScala
//Job4
class Mapper_Job4{}

object Mapper_Job4 {

  @throws[Exception]
  def runJob4(args: Array[String]): Unit = {
    val conf4_initial = new Configuration
    conf4_initial.set("mapreduce.output.textoutputformat.separator", ",")
    val jobName4_initial = "MapReduceJob4"
    val job4_initial = Job.getInstance(conf4_initial, jobName4_initial)
    job4_initial.setJarByClass(this.getClass)
    job4_initial.setOutputKeyClass(classOf[Text])
    job4_initial.setOutputValueClass(classOf[IntWritable])
    job4_initial.setMapOutputKeyClass(classOf[Text])
    job4_initial.setMapOutputValueClass(classOf[IntWritable])
    job4_initial.setMapperClass(classOf[Mapper_Job4.Mapper_4])
    job4_initial.setReducerClass(classOf[Mapper_Job4.Reducer_4])
    job4_initial.setInputFormatClass(classOf[TextInputFormat])
    job4_initial.setOutputFormatClass(classOf[TextOutputFormat[Text, Text]])
    FileInputFormat.addInputPath(job4_initial, new Path(args(0)))
    FileOutputFormat.setOutputPath(job4_initial, new Path(args(1)))
    //val outputDir4_initial = output.replace("(jobName)", jobName4_initial)
    //FileOutputFormat.setOutputPath(job4_initial, new Path(outputDir4_initial))
    job4_initial.waitForCompletion(true)

    //final_exec
    val conf = new Configuration
    conf.set("mapreduce.output.textoutputformat.separator", ",")
    val jobName = "MapReduceJob4_Exec"
    val job = Job.getInstance(conf, jobName)
    job.setJarByClass(this.getClass)
    job.setOutputKeyClass(classOf[Text])
    job.setOutputValueClass(classOf[IntWritable])
    job.setMapOutputKeyClass(classOf[Text])
    job.setMapOutputValueClass(classOf[IntWritable])
    job.setMapperClass(classOf[Job4_exec.Mapper_Job4])
    job.setReducerClass(classOf[Job4_exec.Reducer_Job4])
    job.setInputFormatClass(classOf[TextInputFormat])
    job.setOutputFormatClass(classOf[TextOutputFormat[Text, Text]])
    FileInputFormat.addInputPath(job, new Path(args(1)))
    FileOutputFormat.setOutputPath(job, new Path(args(2)))
    //val outputDir = output.replace("(jobName)", jobName)

    System.exit(if (job.waitForCompletion(true)) 0 else 1)
  }

  class Mapper_4 extends Mapper[LongWritable, Text, Text, IntWritable] {
    private val one_job4: IntWritable = new IntWritable(1)
    private val word_job4: Text = new Text
    val logger = CreateLogger(classOf[Mapper_4])
    val conf = ConfigFactory.load("application.conf")
    val pattern = (conf.getString("randomLogGenerator.Pattern")).r

    override def map(key: LongWritable, rowLine: Text, context: Mapper[LongWritable, Text, Text, IntWritable]#Context): Unit = {
      val read_line = rowLine.toString
      if (!read_line.isEmpty) {
        val separate: Array[String] = read_line.split(" - ")
        val log_errtype: Array[String] = read_line.split(" ")
        val logtype = log_errtype(2)
        val patternmatch = pattern.findAllIn(separate(1)).toString()

        //val pattern_match = pattern.finalaall(separate(1)).toString
        if (!patternmatch.isEmpty) {
          val index_map4 = logtype + "=" + patternmatch.length()
          context.write(new Text(index_map4), new IntWritable(1))
        }
      }
    }
  }

  class Reducer_4 extends Reducer[Text, IntWritable, Text, IntWritable] {

    private val par_result = new IntWritable(1)
    val logger = CreateLogger(classOf[Reducer_4])

    override def reduce(key: Text, values: lang.Iterable[IntWritable], context: Reducer[Text, IntWritable, Text, IntWritable]#Context): Unit = {
      {
        context.write(key, par_result)

      }
    }
  }
}







