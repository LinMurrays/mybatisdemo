package springdemo02.mybatisdemo.cachePackage.controller;


import springdemo02.mybatisdemo.cachePackage.service.DeptService;
import springdemo02.mybatisdemo.dataNconfig.bean.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeptsController {

    @Autowired
    DeptService deptService;

    @GetMapping("/cache/dept/{id}")
    public Department getDept(@PathVariable("id") Integer id){
        return deptService.getDeptById(id);
    }
}
