package org.dimhat.hello.controller;

import org.dimhat.hello.model.Hello;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.atomic.AtomicLong;

@Controller
public class HelloController {
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    @ResponseBody
    public Hello greeting(@RequestParam(value = "name", required = false, defaultValue = "World") String name) {
        System.out.println("==== in greeting ====");
        return new Hello(counter.incrementAndGet(),
                String.format(template, name));
    }
}