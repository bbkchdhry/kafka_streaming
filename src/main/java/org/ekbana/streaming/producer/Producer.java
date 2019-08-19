package org.ekbana.streaming.producer;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.protocol.types.Field;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.ekbana.streaming.model.ClassDetails;
import org.json.JSONObject;

import java.util.Properties;
import java.io.IOException;

public class Producer {

    public static void produce(String topicName, String data, String bootstrap_servers) throws IOException
    {

        // Set properties used to configure the producer
        Properties properties = new Properties();
        // Set the brokers (bootstrap servers)
        properties.setProperty("bootstrap.servers", bootstrap_servers);
        properties.setProperty("transforms", "flatten");
        properties.setProperty("transforms.flatten.type", "org.apache.kafka.connect.transforms.Flatten$Value");
        properties.setProperty("transforms.flatten.delimiter", ".");

        // Set how to serialize key/value pairs
        properties.setProperty("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        properties.setProperty("value.serializer","org.apache.kafka.connect.json.JsonSerializer");

        // specify the protocol for SSL Encryption This is needed for secure clusters
        //properties.setProperty(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SASL_PLAINTEXT");

        KafkaProducer producer = new KafkaProducer(properties);
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode jsonNode = objectMapper.valueToTree(data);
            ProducerRecord<String, JsonNode> rec = new ProducerRecord<>(topicName, jsonNode);
            System.out.printf("record to produce: \n%s \n\n", rec);

            producer.send(rec);

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            producer.close();
        }
    }
}
