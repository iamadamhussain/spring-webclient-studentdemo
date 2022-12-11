package com.adam.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import com.adam.domain.Students;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
public class StudentClientController {

    WebClient webClient = WebClient.create("http://localhost:8080");

    @GetMapping("/client/retrieve")
    public Flux<Students> getAllStudentsUsingRetrieve(){

        return webClient.get().uri("/v1/students")
                .retrieve()
                .bodyToFlux(Students.class)
                .log("Students in Client Project retrieve : ");
    }


    @GetMapping("/client/retrieve/singlestudent")
    public Mono<Students> getOneItemUsingRetrieve(@PathVariable String id){

        return webClient.get().uri("/v1/students/{id}",id)
                .retrieve()
                .bodyToMono(Students.class)
                .log("Students in Client Project retrieve single Item : ");
    }

  

    @PostMapping("/client/createStudent")
    public Mono<Students> createItem(@RequestBody Students student){
    	student.setRegisteredOn(System.currentTimeMillis());
    	student.setStatus(1);
        Mono<Students> itemMono = Mono.just(student);
       return webClient.post().uri("/v1/savestudent")
                .contentType(MediaType.APPLICATION_JSON)
                .body(itemMono, Students.class)
                .retrieve()
                .bodyToMono(Students.class)
                .log("Created item is : ");

    }

    @DeleteMapping("/client/deletestudent/{id}")
    public Mono<Void> deleteItem(@PathVariable String id){

        return webClient.delete().uri("/v1/deletestudent/{id}",id)
                .retrieve()
                .bodyToMono(Void.class)
                .log("Deleted Student is");
    }





    @PutMapping("/client/updateStudent/{id}")
    public Mono<Students> updateItem(@PathVariable String id,
                                 @RequestBody Students item){

        Mono<Students> itemBody = Mono.just(item);

        return webClient.put().uri("/v1/updateStudent/{id}",id)
                .body(itemBody, Students.class)
                .retrieve()
                .bodyToMono(Students.class)
                .log("Updated Item is : ");
    }



}
