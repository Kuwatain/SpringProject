package com.example.kuwait;

import com.example.kuwait.domain.Message;
import com.example.kuwait.repos.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class BasicHttpServer {

    @Autowired
    private MessageRepo messageRepo;

    @GetMapping("/")
    public String greeting(String name, Map<String,Object> model) {
        return "greeting";
    }

    @GetMapping("/main")
    public String main(Map<String,Object> model) {
        Iterable<Message> messages = messageRepo.findAll();

        model.put("messages",messages);

        return "main";
    }

    @PostMapping("/main")
    public String add(@RequestParam String text,
                      @RequestParam String tag,
                      Map<String,Object> model
    ) {
        Message message = new Message(text,tag);
        messageRepo.save(message);

        Iterable<Message> messages = messageRepo.findAll();

        model.put("messages",messages);

        return "main";
    }

    @PostMapping("filter")
    public String filter(@RequestParam(defaultValue = "") String filter,
                         Map<String,Object> model
    ) {
        Iterable<Message> messages;
        messages = filter == null || filter.isEmpty() ?
                messageRepo.findAll() : messageRepo.findByTag(filter);

        model.put("messages", messages);
        return "main";
    }

    @PostMapping("deleteById")
    public String deleteById(@RequestParam int id, Map<String, Object> model){

        try {
            messageRepo.deleteById(id);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        Iterable<Message> messages = messageRepo.findAll();

        model.put("messages", messages);

        return "main";
    }

    @PostMapping("deleteAll")
    public String deleteAll(Map<String, Object> model){
        messageRepo.deleteAll();
        Iterable<Message> messages = messageRepo.findAll();

        model.put("messages", messages);

        return "main";
    }
}
