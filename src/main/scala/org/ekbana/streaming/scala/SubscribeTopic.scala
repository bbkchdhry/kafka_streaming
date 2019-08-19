//package org.ekbana.streaming.scala
//
//import org.apache.spark.sql.SparkSession
//
//object SubscribeTopic {
//
//  def cleanTopicMessages(args: Array[String]): Unit = {
//    val spark = SparkSession
//      .builder
//      .appName("Spark-Kafka-Integration")
//      .master("local")
//      .getOrCreate()
//
//    val df = spark
//      .readStream
//      .format("kafka")
//      .option("kafka.bootstrap.servers", "10.10.5.30:9092")
//      .option("subscribe", "hgi_classDetails")
//      .load()
//
//    df.writeStream
//      .format("console")
//      .option("truncate","false")
//      .start()
//      .awaitTermination()
//
//  }
//}
