package com.farshmaker.security_demo.student;

import org.apache.juli.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/management/api/v1/students")
public class StudentManagementController {

  private static final Logger log = LoggerFactory.getLogger(StudentManagementController.class.getSimpleName());

  private static final List<Student> STUDENTS = Arrays.asList(
      new Student(1, "James Bond"),
      new Student(2, "Maria Jones"),
      new Student(3, "Anna Smith")
  );

  @GetMapping
  public List<Student> getAllStudents() {
    return STUDENTS;
  }

  @PostMapping
  public void registerNewStudent(@RequestBody final Student student) {
    log.info(student.toString());
  }

  @DeleteMapping("{studentId}")
  public void deleteStudent(@PathVariable final Integer studentId) {
    log.info(studentId.toString());
  }

  @PutMapping("{studentId}")
  public void updateStudent(@PathVariable final Integer studentId,
                            @RequestBody final Student student) {
    log.info(String.format("%d, %s", studentId, student));
  }
}
