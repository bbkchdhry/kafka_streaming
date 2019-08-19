package org.ekbana.streaming.producer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.codehaus.jackson.map.SerializationConfig;
import org.ekbana.streaming.consumer.CustomSerializer;
import org.ekbana.streaming.model.ClassDetails;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Properties;

@JsonInclude( JsonInclude.Include.NON_EMPTY )
public class Producer {

    public static void produce(String topicName, ClassDetails data, String bootstrap_servers) throws IOException
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
//        properties.setProperty("value.serializer","org.ekbana.streaming.consumer.CustomSerializer");
        properties.setProperty("value.serializer","org.apache.kafka.connect.json.JsonSerializer");

        // specify the protocol for SSL Encryption This is needed for secure clusters
        //properties.setProperty(CommonClientConfigs.SECURITY_PROTOCOL_CONFIG, "SASL_PLAINTEXT");

        System.out.printf("ClassDetailJSON: \n%s\n\n", data.getFilteredClassDetailJSON());

        KafkaProducer producer = new KafkaProducer(properties);
        ObjectMapper objectMapper = new ObjectMapper();

//        System.out.printf("ObjectMapper: \n%s \n\n", objectMapper.writeValueAsString(data));
        try {
            JsonNode jsonNode = objectMapper.valueToTree(data);
            System.out.printf("JsonNode: \n%s \n\n", jsonNode.toString());
//            ProducerRecord<String, ClassDetails> rec = new ProducerRecord<>(topicName, data);
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
