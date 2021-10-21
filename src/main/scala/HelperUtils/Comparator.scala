package HelperUtils

import org.apache.hadoop.io.{IntWritable, WritableComparable, WritableComparator}

class Comparator extends WritableComparator(classOf[IntWritable], true) {
  @SuppressWarnings(Array("rawtypes"))
  override def compare(a: WritableComparable[_], b: WritableComparable[_]): Int = {

    val key1: IntWritable = a.asInstanceOf[IntWritable]
    val key2: IntWritable = b.asInstanceOf[IntWritable]

    -1 * key1.compareTo(key2)
  }
}
