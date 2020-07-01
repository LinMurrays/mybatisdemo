package springdemo02.mybatisdemo;

import springdemo02.mybatisdemo.dataNconfig.bean.Department;
import springdemo02.mybatisdemo.dataNconfig.bean.Employee;
import springdemo02.mybatisdemo.dataNconfig.mapper.DepartmentMapper;
import springdemo02.mybatisdemo.dataNconfig.mapper.EmployeeMapper;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootTest
class MybatisdemoApplicationTests {

    @Autowired
    RedisTemplate<Object, Department> deptRedisTemplate;

    @Autowired
    RedisTemplate<Object, Employee> empRedisTemplate;

    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    DepartmentMapper departmentMapper;

    @Autowired
    DataSource dataSource;

    @Autowired
    StringRedisTemplate stringRedisTemplate;  //操作k-v都是字符串的

    @Autowired
    RedisTemplate redisTemplate;//k-v都是对象的

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Test
    void contextLoads() throws SQLException {
        System.out.println(dataSource.getConnection());

        Connection connection = dataSource.getConnection();
        System.out.println(connection);
        connection.close();
    }

    /**
     * Redis常见的五大数据类型
     * String（字符串）、List（列表）、Set（集合）、Hash（散列）、ZSet（有序集合）
     * stringRedisTemplate.opsForValue()[String（字符串）]
     * stringRedisTemplate.opsForList()[List（列表）]
     * stringRedisTemplate.opsForSet()[Set（集合）]
     * stringRedisTemplate.opsForHash()[Hash（散列）]
     * stringRedisTemplate.opsForZSet()[ZSet（有序集合）]
     */
    @Test
    public void test01() {
        //给redis中保存数据
        stringRedisTemplate.opsForValue().append("msg", "hello");
        String msg = stringRedisTemplate.opsForValue().get("msg");
        System.out.println(msg);

        stringRedisTemplate.opsForList().leftPush("mylist", "1");
        stringRedisTemplate.opsForList().leftPush("mylist", "2");
    }

    //测试保存对象
    @Test
    public void test02() {
        Employee empById = employeeMapper.getEmpById(1);

        Department department = departmentMapper.getDeptById(1);
        //默认如果保存对象，使用jdk序列化机制，序列化后的数据保存到redis中
        redisTemplate.opsForValue().set("emp-0222", empById);
        //1、将数据以json的方式保存
        //(1)自己将对象转为json
        //(2)redisTemplate默认的序列化规则；改变默认的序列化规则；
        empRedisTemplate.opsForValue().set("emp-01", empById);
        deptRedisTemplate.opsForValue().set("dept-lol", department);
    }



}
