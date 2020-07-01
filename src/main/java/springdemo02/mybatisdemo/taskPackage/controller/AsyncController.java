package springdemo02.mybatisdemo.taskPackage.controller;


import springdemo02.mybatisdemo.taskPackage.service.AsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AsyncController {

    @Autowired
    AsyncService asyncService;

    @GetMapping("/task/hello")
    public String hello(){
        asyncService.hello();
        return "success";
    }
}