package br.com.restapi.services;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.restapi.dtos.StudentDTO;
import br.com.restapi.entity.Student;
import br.com.restapi.repositories.StudentRepo;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepo studentRepo;

    public StudentServiceImpl(StudentRepo studentRepo) {
        this.studentRepo = studentRepo;
    }

    @Override
    public StudentDTO save(StudentDTO studentDTO) {
        Optional<Student> existingStudent = studentRepo.findById(studentDTO.getId());
        Student student;

        if (existingStudent.isPresent()) {
            student = existingStudent.get();
            updateNonNullProperties(studentDTO, student);
            return convertToDTO(studentRepo.save(student));
        }
        student = convertToEntity(studentDTO);
        return convertToDTO(studentRepo.save(student));
    }

    @Override
    public StudentDTO getByID(long studentID) {
        return studentRepo.findById(studentID).
                map(this::convertToDTO)
                .orElse(null);
    }

    @Override
    public List<StudentDTO> getAll() {
        return studentRepo.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteByID(long studentID) {

        if (studentRepo.existsById(studentID)) {
            studentRepo.deleteById(studentID);
            return true;
        }
        return false;
    }

    private StudentDTO convertToDTO(Student student) {
        return new StudentDTO(student.getId(), student.getFirstName(), student.getLastName(), student.getEmail(),
                student.getAccountCreatedTime(), student.getAccountLastUpdatedTime(),
                student.getBirthDate().format(DateTimeFormatter.ISO_LOCAL_DATE), student.getAge());
    }

    private Student convertToEntity(StudentDTO studentDTO) {
        return new Student(studentDTO.getId(), studentDTO.getFirstName(), studentDTO.getLastName(),
                studentDTO.getEmail(), studentDTO.getDob());
    }

    private void updateNonNullProperties(StudentDTO dto, Student student) {
        if (dto.getFirstName() != null) {
            student.setFirstName(dto.getFirstName());
        }
        if (dto.getLastName() != null) {
            student.setLastName(dto.getLastName());
        }
        if (dto.getEmail() != null) {
            student.setEmail(dto.getEmail());
        }

        System.out.println();

        student.setAccountCreatedTime(student.getAccountCreatedTime());

    }

}
