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
class Mapper_Job3 {}
object Mapper_Job3 {

  @throws[Exception]
  def runJob3(args: Array[String]): Unit = {
    val conf3 = new Configuration
    conf3.set("mapreduce.output.textoutputformat.separator", ",")
    val jobName3 = "MapReduceJob3"
    val job3 = Job.getInstance(conf3, jobName3)
    job3.setJarByClass(this.getClass)
    job3.setOutputKeyClass(classOf[Text])
    job3.setOutputValueClass(classOf[IntWritable])
    job3.setMapOutputKeyClass(classOf[Text])
    job3.setMapOutputValueClass(classOf[IntWritable])
    job3.setMapperClass(classOf[Mapper_Job3.Mapper_3])
    job3.setReducerClass(classOf[Mapper_Job3.Reducer_3])
    job3.setInputFormatClass(classOf[TextInputFormat])
    job3.setOutputFormatClass(classOf[TextOutputFormat[Text, Text]])
    FileInputFormat.addInputPath(job3, new Path(args(0)))
    FileOutputFormat.setOutputPath(job3, new Path(args(1)))
    //val outputDir4_initial = output.replace("(jobName)", jobName4_initial)
    //FileOutputFormat.setOutputPath(job4_initial, new Path(outputDir4_initial))
    System.exit(if (job3.waitForCompletion(true)) 0 else 1)
  }

  class Mapper_3 extends Mapper[LongWritable, Text, Text, IntWritable] {
    private val one_job3: IntWritable = new IntWritable(1)
    private val word_job3: Text = new Text
    val logger = CreateLogger(classOf[Mapper_3])
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

  class Reducer_3 extends Reducer[Text, IntWritable, Text, IntWritable] {

    private val result = new IntWritable(1)
    val logger = CreateLogger(classOf[Reducer_3])

    override def reduce(key: Text, values: lang.Iterable[IntWritable], context: Reducer[Text, IntWritable, Text, IntWritable]#Context): Unit = {
      {
        val sum = values.asScala.foldLeft(0)(_ + _.get)
        context.write(key, new IntWritable(sum))
      }
    }
  }
}
