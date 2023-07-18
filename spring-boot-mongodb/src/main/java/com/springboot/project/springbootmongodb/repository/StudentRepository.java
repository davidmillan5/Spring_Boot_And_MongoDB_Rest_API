package com.springboot.project.springbootmongodb.repository;

import com.springboot.project.springbootmongodb.domain.Student;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface StudentRepository extends MongoRepository<Student,String> {
    Optional<Student> findStudentByEmail(String email);

}
