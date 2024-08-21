package likelion.edu.vn.health_care.controller;

import likelion.edu.vn.health_care.entity.MedicalRecordEntity;
import likelion.edu.vn.health_care.model.dto.ResultPaginationDTO;
import likelion.edu.vn.health_care.service.MedicalRecordService;
import likelion.edu.vn.health_care.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/medical-records")
@CrossOrigin
public class MedicalRecordController {
    @Autowired
    private MedicalRecordService medicalRecordService;

    @GetMapping
    public ResponseEntity<Object> getAllRecords(
            @RequestParam("current") Optional<String> currentOptional,
            @RequestParam("pageSize") Optional<String> pageSizeOptional) {

        try {
            int current = currentOptional.filter(s -> !s.isEmpty()).map(Integer::parseInt).orElse(1);
            int pageSize = pageSizeOptional.filter(s -> !s.isEmpty()).map(Integer::parseInt).orElse(10);

            Pageable pageable = PageRequest.of(current - 1, pageSize);
            return ResponseHandler.generateResponse(HttpStatus.OK, false, "Records retrieved successfully", this.medicalRecordService.handleGetAll(pageable));
        } catch (Exception e) {
            return ResponseHandler.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, true, e.getMessage(), null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getRecordById(@PathVariable Integer id) {
        try {
            Optional<MedicalRecordEntity> record = medicalRecordService.findById(id);
            if (record.isPresent()) {
                return ResponseHandler.generateResponse(HttpStatus.OK, false, "Record retrieved successfully", record.get());
            } else {
                return ResponseHandler.generateResponse(HttpStatus.NOT_FOUND, true, "Record not found", null);
            }
        } catch (Exception e) {
            return ResponseHandler.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, true, e.getMessage(), null);
        }
    }

    @PostMapping
    public ResponseEntity<Object> createRecord(@RequestBody MedicalRecordEntity medicalRecordEntity) {
        try {
            MedicalRecordEntity createdRecord = medicalRecordService.create(medicalRecordEntity);
            return ResponseHandler.generateResponse(HttpStatus.CREATED, false, "Record created successfully", createdRecord);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true, e.getMessage(), null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateRecord(@PathVariable Integer id, @RequestBody MedicalRecordEntity medicalRecordEntity) {
        try {
            medicalRecordEntity.setId(id);
            MedicalRecordEntity updatedRecord = medicalRecordService.update(medicalRecordEntity);
            return ResponseHandler.generateResponse(HttpStatus.OK, false, "Record updated successfully", updatedRecord);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true, e.getMessage(), null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteMedicalRecord(@PathVariable Integer id) {
        try {
            medicalRecordService.deleteById(id);
            return ResponseHandler.generateResponse(HttpStatus.OK, false, "Record deleted successfully", null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse(HttpStatus.BAD_REQUEST, true, e.getMessage(), null);
        }
    }
}
