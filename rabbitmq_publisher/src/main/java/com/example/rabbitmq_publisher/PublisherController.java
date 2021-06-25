package com.example.rabbitmq_publisher;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@RestController
public class PublisherController {

    private static final String EXCHANGE_NAME = "sample.exchange";

    private final RabbitTemplate rabbitTemplate;

    public PublisherController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }



    @PostMapping ("/{msg}")
    public String publish(@PathVariable String msg) {
        SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd");

        for(int i=1 ; i<31 ; i++){
            MsgBody msgBody = new MsgBody();
            msgBody.setMsg(msg+i);
            msgBody.setTime(date.format(new Date()));
            rabbitTemplate.convertAndSend(EXCHANGE_NAME, "sample.jwjin95.#", msgBody);
        }

        return "Success";
    }

    @GetMapping("/sample")
    public String publish() {
        SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd");
        MsgBody msgBody = new MsgBody("haha", date.format(new Date()));
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, "sample.jwjin95.#", msgBody);
        return "message sending!";
    }

//    @GetMapping("record")
//    public String getRecord() {
//
//    }


}
