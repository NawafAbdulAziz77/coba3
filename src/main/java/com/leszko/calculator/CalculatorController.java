package com.leszko.calculator; 
import java.time.Instant;
import java.sql.Timestamp;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.RequestParam; 
import org.springframework.web.bind.annotation.RestController; 
import java.util.List;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
 
@RestController 
class CalculatorController { 
     @Autowired 
     private Calculator calculator; 
     @Autowired 
     private CalculationRepository calculationRepository;
     @Autowired 
     private JdbcTemplate jdbcTemplate; 
 
     @RequestMapping("/sum") 
     String sum(@RequestParam("a") Integer a,  
                @RequestParam("b") Integer b) { 
          String result = String.valueOf(calculator.sum(a, b)); 
          calculationRepository.save(new Calculation(a.toString(), b.toString(), result, Timestamp.from(Instant.now()))); 
          return result;
     } 

     @GetMapping("/tables")
     public List<Map<String, Object>> getTables() {
         try {
             return jdbcTemplate.queryForList(
                 "SELECT * FROM CALCULATION"
             );
         } catch (Exception e) {
             e.printStackTrace(); // biar nongol di Jenkins log
             return List.of(Map.of("error", e.getMessage()));
         }
}
} 
