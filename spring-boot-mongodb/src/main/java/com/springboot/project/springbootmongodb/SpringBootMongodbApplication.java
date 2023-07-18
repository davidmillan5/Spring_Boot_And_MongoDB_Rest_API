package com.springboot.project.springbootmongodb;

import com.springboot.project.springbootmongodb.domain.Address;
import com.springboot.project.springbootmongodb.domain.Gender;
import com.springboot.project.springbootmongodb.domain.Student;
import com.springboot.project.springbootmongodb.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class SpringBootMongodbApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootMongodbApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(StudentRepository repository, MongoTemplate mongoTemplate){
		return args -> {
			Address address = new Address(
					"Colombia",
					"050025",
					"Medellin"
			);
			String email = "lcosorio@gmail.com";
			Student student = new Student(
					"Laurinha", "Osorio",
					email, Gender.FEMALE, address,
					List.of("Computer Science","Maths","Data Structures","Algorithms"),
					BigDecimal.TEN,
					LocalDateTime.now()
			);

			// usingMongoTemplateAndQuery(repository, mongoTemplate, email, student);

			repository.findStudentByEmail(email)
					.ifPresentOrElse(s ->{
						System.out.println(s + " already exists");
					},()->{
						System.out.println("Inserting student "+ student);
						repository.insert(student);
					});

		};
	}

	private static void  usingMongoTemplateAndQuery(StudentRepository repository, MongoTemplate mongoTemplate, String email, Student student) {
		Query query = new Query();
		query.addCriteria(Criteria.where("email").is(email));

		List<Student> students = mongoTemplate.find(query, Student.class);

		if(students.size() > 1){
			throw new IllegalStateException("found many students with email "+ email);
		}

		if(students.isEmpty()){
			System.out.println("Inserting student "+ student);
			repository.insert(student);
		}else{
			System.out.println(student + " already exists");
		}
	}

}
