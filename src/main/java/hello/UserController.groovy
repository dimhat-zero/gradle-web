package hello

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

/**
 * Created by think on 2016/8/31.
 */
@RestController
@EnableAutoConfiguration
@RequestMapping("/user")
class UserController {

    @RequestMapping(method=[RequestMethod.GET])
    def get(Long id){
        id ? id:"";
    }

    public static void main(String[] args){
        SpringApplication.run UserController,args
    }
}
