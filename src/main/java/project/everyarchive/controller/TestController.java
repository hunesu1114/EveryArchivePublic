package project.everyarchive.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class TestController {

    /**
     * cpu 부하 테스트
     */
    @GetMapping("/cpu")
    public String cpu() {
        log.info("CPU 부하 테스트");
        long val = 0;
        for (long i = 0; i < 100000000000L; i++) {
            val++;
        }
        return "ok val = " + val;
    }

    /**
     * JVM 메모리 부하 테스트
     */
    private List<String> list = new ArrayList<>();

    @GetMapping("/jvm")
    public String jvm() {
        log.info("JVM 메모리 부하 테스트");
        for (long i = 0; i < 100000000000L; i++) {
            list.add("hello jvm!" + i);
        }
        return "ok";
    }

    /**
     * 커넥션 고갈 테스트
     */
    @Autowired
    DataSource dataSource;

    @GetMapping("/conn")
    public String conn() throws SQLException {
        log.info("jdbc");
        Connection conn = dataSource.getConnection();
        log.info("connection info={}", conn);
        //conn.close();//커넥션을 닫지 않는다.
        return "ok";

    }
}
