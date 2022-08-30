package com.farshmaker.security_demo.student;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {

  private static final List<Student> STUDENTS = Arrays.asList(
      new Student(1, "James Bond"),
      new Student(2, "Maria Jones"),
      new Student(3, "Anna Smith")
  );

  @GetMapping("/{studentId}")
  public Student getStudent(@PathVariable Integer studentId) {
    return STUDENTS.stream()
        .filter(student -> studentId.equals(student.getId()))
        .findFirst()
        .orElseThrow(() -> new IllegalStateException("Student " + studentId + "does not exists"));
  }

  @GetMapping("/test")
  public String getStudent() {
    final Map<Object, String> treeSet = new HashMap<>();
    final Object o = new Object();
    final Object o2 = new Object();

    treeSet.put(o, "Averchenko");
    treeSet.put(o2, "Averchenko");
//    treeSet.put("Temp", "Averchenko");
//    treeSet.put("Yegor", "aver");
//    treeSet.put("qwerty", "ytrewq");
    return treeSet.toString();
  }
}
