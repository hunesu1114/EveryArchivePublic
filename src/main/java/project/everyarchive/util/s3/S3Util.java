package project.everyarchive.util.s3;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import project.everyarchive.entity.DataType;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class S3Util {

    private final AmazonS3Client amazonS3Client;

    private final String bucket = "everyarchive";

    public Map<String, Object> uploadFile(MultipartFile multipartFile, String dirName, HttpServletRequest request) throws IOException {
        Map<String, Object> responseMap = new HashMap<>();
        String originalFilename = multipartFile.getOriginalFilename();
        String fullPath = dirName + originalFilename;

        responseMap.put("member", request.getSession().getAttribute("member"));
        responseMap.put("originalFilename", originalFilename);
        responseMap.put("filePath", fullPath);
        responseMap.put("size", Integer.parseInt(Long.toString(multipartFile.getSize() / (1024 * 1024))));
        if (dirName.contains("file")) {
            responseMap.put("dataType", DataType.FILE);
        } else if (dirName.contains("photo")) {
            responseMap.put("dataType", DataType.PHOTO);
        } else {
            responseMap.put("dataType", DataType.VIDEO);
        }

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setContentType(multipartFile.getContentType());

        amazonS3Client.putObject(bucket, fullPath, multipartFile.getInputStream(), objectMetadata);
        return responseMap;
    }

    public UrlResource downloadFile(String filePath) {
        return new UrlResource(amazonS3Client.getUrl(bucket, filePath));
    }

    public void deleteFile(String filePath) {
        amazonS3Client.deleteObject(bucket, filePath);
    }


}
