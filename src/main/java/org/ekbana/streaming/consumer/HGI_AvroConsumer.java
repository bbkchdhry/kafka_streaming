package org.ekbana.streaming.consumer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.sun.xml.internal.ws.encoding.soap.DeserializationException;
import io.confluent.kafka.serializers.AbstractKafkaAvroSerDeConfig;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.ekbana.streaming.model.ClassDetails;
import org.ekbana.streaming.producer.Producer;

import java.io.IOException;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class HGI_AvroConsumer {

    private final static String BOOTSTRAP_SERVERS = "10.10.5.30:9092,10.10.5.31:9092,10.10.5.32:9092,10.10.5.33:9092";
    private final static String TOPIC = "hgi_classDetails";
    private final static String GROUP_ID = "hgi";
    private final static String SCHEMA_URL = "http://10.10.5.30:8081";

    // Useful in removing nulls from serialized Json string
//    public static String JsonNullRegEx = "[\"][a-zA-Z0-9_]*[\"]:null[ ]*[,]|[\"][a-zA-Z0-9_]*[\"]:0[ ]*[,]?";
    public static String JsonNullZeroReg = "[\"][a-zA-Z0-9_]*[\"]:(?:null|0)[ ]*[,]?";

    private String producerTopicName;

    public HGI_AvroConsumer(String producerTopicName){
        this.producerTopicName = producerTopicName;
    }

    private HGI_AvroConsumer(){

    }

    private static Consumer<Long, GenericRecord> createConsumer() {
        Properties properties = new Properties();
        // normal consumer
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);

        // avro part (deserializer)
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, KafkaAvroDeserializer.class.getName());
        properties.setProperty(KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG, SCHEMA_URL);

//      Will cause exception: Error deserializing key/value if set to true
        properties.setProperty(KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG, "false");

        return new KafkaConsumer<>(properties);
    }

    public void cleanTopicMessages() {

        final Consumer<Long, GenericRecord> consumer = createConsumer();
        consumer.subscribe(Collections.singletonList(TOPIC));

        try {

            while (true) {

                ConsumerRecords<Long, GenericRecord> records = null;
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
                    } else {

                        records.forEach(record -> {

//                            System.out.printf("Original Records: \n%s \n\n", record.value());

                            Gson gson = new Gson();
                            ClassDetails classDetails = gson.fromJson(record.value().toString(), ClassDetails.class);
                            String data = classDetails.getAllData().toString().replaceAll(JsonNullZeroReg, "");
                            System.out.printf("ClassDetails: \n%s \n\n", data);


                        if(producerTopicName != null){
                            // Produce data to new topic
                            try {
                                Producer.produce(producerTopicName, data, BOOTSTRAP_SERVERS);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        });
                    }
                }
                consumer.commitAsync();
            }
        }catch (Exception e){
            System.out.println(e.getLocalizedMessage());
            consumer.close();
        }
    }

    public static void main(String[] args) {
//        new HGI_AvroConsumer().cleanTopicMessages();
        String producerTopicName = "hgi_classDetails_2";
        new HGI_AvroConsumer(producerTopicName).cleanTopicMessages();
    }
}