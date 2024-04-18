package br.com.restapi.services;

import java.util.List;

import br.com.restapi.dtos.StudentDTO;

public interface StudentService {

    StudentDTO save(StudentDTO student);

    StudentDTO getByID(long studentID);

    boolean deleteByID(long studentID);

    List<StudentDTO> getAll();
}
