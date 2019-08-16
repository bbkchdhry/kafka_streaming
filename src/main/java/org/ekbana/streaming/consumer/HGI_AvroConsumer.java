package org.ekbana.streaming.consumer;

import com.sun.xml.internal.ws.encoding.soap.DeserializationException;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class HGI_AvroConsumer {

    private final static String BOOTSTRAP_SERVERS = "10.10.5.30:9092,10.10.5.31:9092,10.10.5.32:9092,10.10.5.33:9092";
    private final static String TOPIC = "hgi_classDetails";
    private final static String GROUP_ID = "hgi";
    private final static String SCHEMA_URL = "http://10.10.5.30:8081";

    private static Consumer<String, GenericRecord> createConsumer() {
        Properties properties = new Properties();
        // normal consumer
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);

        // avro part (deserializer)
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class.getName());
        properties.setProperty(KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG, SCHEMA_URL);

        // Will cause exception: Error deserializing key/value
//        properties.setProperty(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, "true");

        return new KafkaConsumer<>(properties);
    }

    public static void main(String[] args) {

        final Consumer<String, GenericRecord> consumer = createConsumer();
        consumer.subscribe(Collections.singletonList(TOPIC));

        try {

            while (true) {

                ConsumerRecords<String, GenericRecord> records = null;
                try {
                    records = consumer.poll(Duration.ofSeconds(5));
                } catch (DeserializationException e) {
                    System.out.println("exception");
                    String s = e.getMessage().split("Error deserializing key/value for partition ")[1].split(". If needed, please seek past the record to continue consumption.")[0];
                    String topics = s.split("-")[0];
                    int offset = Integer.valueOf(s.split("offset ")[1]);
                    int partition = Integer.valueOf(s.split("-")[1].split(" at")[0]);

                    TopicPartition topicPartition = new TopicPartition(topics, partition);
                    //log.info("Skipping " + topic + "-" + partition + " offset " + offset);
                    consumer.seek(topicPartition, offset + 1);
                }
                if (records != null) {
                    if (records.count() == 0) {
                        System.out.println("Waiting for records....");
                    } else records.forEach(record -> {
                        GenericRecord genericRecord = record.value();
                        System.out.printf("%s \n", genericRecord);
                    });
                }
                consumer.commitAsync();
            }
        }catch (Exception e){
            System.out.println(e.getLocalizedMessage());
            consumer.close();
        }
    }
}