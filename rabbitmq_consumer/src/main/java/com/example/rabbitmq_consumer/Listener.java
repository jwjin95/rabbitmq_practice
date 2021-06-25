package com.example.rabbitmq_consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.json.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.json.JsonParser;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

@Component
public class Listener {

    private static final Logger log = LoggerFactory.getLogger(Listener.class);





    @RabbitListener(queues = "sample.queue", concurrency = "2")
    public void receiveMessage(final Message message) throws JsonProcessingException {
        log.info(message.toString());
        String threadName = Thread.currentThread().getName();
        int threadNum = threadName.charAt(threadName.length()-1)-'1';


        String body = new String(message.getBody());
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> map = mapper.readValue(body, Map.class);
        try {
            File resource_A = new ClassPathResource("A.txt").getFile();
            File resource_B = new ClassPathResource("B.txt").getFile();

            FileWriter fwA = new FileWriter(resource_A.getAbsoluteFile(), true);
            FileWriter fwB = new FileWriter(resource_B.getAbsoluteFile(), true);

            BufferedWriter bwA = new BufferedWriter(fwA);
            BufferedWriter bwB = new BufferedWriter(fwB);

//            log.info(resource_A.getAbsolutePath());
//            log.info(Integer.toString(threadNum));

            BufferedWriter[] writers = {bwA, bwB};


            writers[threadNum].write(map.get("msg") + " " + map.get("time") + "\n");
            bwA.close();
            bwB.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        System.out.println(map.get("msg"));

        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
