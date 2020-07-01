package springdemo02.mybatisdemo.cachePackage.controller;

import springdemo02.mybatisdemo.cachePackage.service.EmployeeService;
import springdemo02.mybatisdemo.dataNconfig.bean.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @GetMapping("/cache/emp/{id}")
    public Employee getEmployee(@PathVariable("id") Integer id){

        return employeeService.getEmployee(id);

    }

    @GetMapping("/cache/emp")
    public Employee update(Employee employee){
        Employee emp = employeeService.updateEmp(employee);

        return emp;
    }

    @GetMapping("/delemp")
    public String deleteEmp(Integer id){
        employeeService.deleteEmp(id);
        return "success";
    }

    @GetMapping("/emp/lastname/{lastName}")
    public Employee getEmpByLastName(@PathVariable("lastName") String lastName){
        return employeeService.getEmpByLastName(lastName);
    }

    /**
     *
     * @param lastName
     * @return
     * http://localhost:8080/emp/lastnames?lastName=tom
     * 这个方法这样取不了lastName的值，因为后面没有带参数，不能@PathVariable("lastName") 这样取值
     * 这个方法实际上是错误的，应该将方法参数改为：Employee employee
     * 这样才能用：http://localhost:8080/emp/lastnames?lastName=tom  才能取到lastName的值，而且其他值为null
     *上面就只传一个参数，/emp/lastname/{lastName}，所以需要@PathVariable("lastName")这样子取值
     *
     */
    @GetMapping("/emp/lastnames")
    public Employee getEmpByLastNames(@PathVariable("lastName") String lastName){
        return employeeService.getEmpByLastName(lastName);
    }
}
