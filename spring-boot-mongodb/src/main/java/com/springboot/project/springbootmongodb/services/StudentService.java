package com.springboot.project.springbootmongodb.services;

import com.springboot.project.springbootmongodb.domain.Student;
import com.springboot.project.springbootmongodb.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class StudentService {

    private final StudentRepository studentRepository;
    public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }
}
