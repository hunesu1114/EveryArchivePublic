package project.everyarchive.service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import project.everyarchive.dto.FilesDto;
import project.everyarchive.entity.DataType;
import project.everyarchive.entity.Files;
import project.everyarchive.entity.Member;
import project.everyarchive.repository.FilesRepository;
import project.everyarchive.repository.QFilesRepository;
import project.everyarchive.util.s3.S3Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FilesService {

    private final FilesRepository filesRepository;
    private final QFilesRepository qFilesRepository;
    private final S3Util s3Util;

    @Transactional
    public void uploadFile(MultipartFile multipartFile, String dirName, HttpServletRequest request) throws IOException {
        Map<String, Object> fileInfo = s3Util.uploadFile(multipartFile, dirName, request);
        Files files = Files.builder()
                .originalFilename((String) fileInfo.get("originalFilename"))
                .filePath((String) fileInfo.get("filePath"))
                .dataType((DataType) fileInfo.get("dataType"))
                .size((int)fileInfo.get("size"))
                .member((Member) fileInfo.get("member"))
                .build();
        filesRepository.save(files);
    }

    @Transactional(readOnly = true)
    public List<FilesDto> findAllByType(DataType dataType) {
        List<FilesDto> responseList = new ArrayList<>();

        List<Files> entities = qFilesRepository.findAllByType(dataType);
        for (Files entity : entities) {
            FilesDto filesDto = new FilesDto();
            filesDto.setId(entity.getId());
            filesDto.setFilePath(entity.getFilePath());
            filesDto.setOriginalFilename(entity.getOriginalFilename());
            filesDto.setDataType(entity.getDataType());
            filesDto.setSize(entity.getSize());
            filesDto.setMemberId(entity.getMember().getId());
            responseList.add(filesDto);
        }
        return responseList;
    }

    @Transactional
    public void deleteFile(Long filesId) {
        Files entity = qFilesRepository.findById(filesId);
        s3Util.deleteFile(entity.getFilePath());
        filesRepository.deleteById(filesId);
    }

    public UrlResource downloadFile(Long filesId) {
        Files entity = qFilesRepository.findById(filesId);
        return s3Util.downloadFile(entity.getFilePath());
    }

    public String getOriginalFilename(Long filesId) {
        return qFilesRepository.findOriginalFilename(filesId);
    }

    public FilesDto findById(Long filesId) {
        Files entity = qFilesRepository.findById(filesId);
        FilesDto responseDto = new FilesDto();
        responseDto.setId(entity.getId());
        responseDto.setSize(entity.getSize());
        responseDto.setFilePath(entity.getFilePath());
        responseDto.setOriginalFilename(entity.getOriginalFilename());
        responseDto.setMemberId(entity.getMember().getId());
        responseDto.setDataType(entity.getDataType());

        return responseDto;
    }
}
