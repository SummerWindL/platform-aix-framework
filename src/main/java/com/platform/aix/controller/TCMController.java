package com.platform.aix.controller;

import com.platform.aix.common.datacommon.db.domain.Prescription;
import com.platform.aix.common.datacommon.db.service.TCMService;
import org.elasticsearch.common.collect.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author fuyanliang
 * @version V1.0.0
 * @date 2025年03月24日 11:40
 * @Description 中医药方
 */
@RestController
@RequestMapping("/api/tcm")
public class TCMController {

    @Autowired
    private TCMService tcmService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = tcmService.storeFile(file);
        return ResponseEntity.ok(Map.of(
                "fileName", fileName,
                "filePath", "/api/tcm/download/" + fileName
        ));
    }

    @GetMapping
    public List<Prescription> getAllPrescriptions() {
        return tcmService.getAllPrescriptions();
    }

    @PostMapping
    public Prescription addPrescription(@RequestBody Prescription prescription) {
        return tcmService.savePrescription(prescription);
    }

    @PutMapping("/{id}")
    public Prescription updatePrescription(@PathVariable Long id, @RequestBody Prescription prescription) {
        return tcmService.updatePrescription(id, prescription);
    }

    @DeleteMapping
    public void deletePrescriptions(@RequestBody List<Long> ids) {
        tcmService.deletePrescriptions(ids);
    }

    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        Resource resource = tcmService.loadFile(fileName);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
