//package org.ekbana.streaming.producer;
//
//import java.io.IOException;
//import java.util.UUID;
//
//// Handle starting producer or consumer
//public class Run {
//    public static void cleanTopicMessages(String[] args) throws IOException {
//        if(args.length < 3) {
//            usage();
//        }
//
//        String topicName = args[1];
//        switch(args[0].toLowerCase()) {
//            case "producer":
//                Producer.produce(topicName);
//                break;
//            case "consumer":
//                // Either a groupId was passed in, or we need a random one
//                String groupId;
//                if(args.length == 4) {
//                    groupId = args[3];
//                } else {
//                    groupId = UUID.randomUUID().toString();
//                }
//                Consumer.consume(brokers, groupId, topicName);
//                break;
//            default:
//                usage();
//        }
//        System.exit(0);
//    }
//    // Display usage
//    public static void usage() {
//        System.out.println("Usage:");
//        System.out.println("kafka-0.0.1.jar <producer|consumer>  brokerhosts [groupid]");
//        System.exit(1);
//    }
//}
