package project.everyarchive.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.everyarchive.dto.FilesDto;
import project.everyarchive.entity.DataType;
import project.everyarchive.entity.Role;
import project.everyarchive.service.FilesService;
import project.everyarchive.util.session_login.SessionAuthCheckUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/dataArchive")
public class DataController {

    private final SessionAuthCheckUtil authCheckUtil;
    private final FilesService filesService;

    @GetMapping("/select")
    public String selectPage(HttpServletRequest request) {
        Role role = authCheckUtil.checkSessionAuth(request);
        if (role != Role.ADMIN) {
            return "404";
        }
        return "data_archive/select_page";
    }

    @GetMapping("/file")
    public String filePage(HttpServletRequest request) {
        Role role = authCheckUtil.checkSessionAuth(request);
        if (role != Role.ADMIN) {
            return "404";
        }
        return "data_archive/file";
    }

    @GetMapping("/photo")
    public String photoPage(HttpServletRequest request) {
        Role role = authCheckUtil.checkSessionAuth(request);
        if (role != Role.ADMIN) {
            return "404";
        }
        return "data_archive/photo";
    }

    @GetMapping("/video")
    public String videoPage(HttpServletRequest request) {
        Role role = authCheckUtil.checkSessionAuth(request);
        if (role != Role.ADMIN) {
            return "404";
        }
        return "data_archive/video";
    }


    /**
     * ==================== file upload & download APIs ===================
     */
    @ResponseBody
    @GetMapping("/file/getList")
    public List<FilesDto> getList(@RequestParam("type") String type, HttpServletRequest request) {
        Role role = authCheckUtil.checkSessionAuth(request);
        if (role != Role.ADMIN) {
            return null;
        }
        if (type.equals("file")) {
            return filesService.findAllByType(DataType.FILE);
        } else if (type.equals("photo")) {
            return filesService.findAllByType(DataType.PHOTO);
        } else {
            return filesService.findAllByType(DataType.VIDEO);
        }
    }

    @ResponseBody
    @PostMapping("/file/upload")
    public String fileUpload(@RequestParam("file") MultipartFile multipartFile, @RequestParam("type") String type, HttpServletRequest request) {
        Role role = authCheckUtil.checkSessionAuth(request);
        if (role != Role.ADMIN) {
            return "Unauthorized Access";
        }
        try {
            if (type.equals("file")) {
                filesService.uploadFile(multipartFile, "file/", request);
            } else if (type.equals("photo")) {
                filesService.uploadFile(multipartFile, "photo/", request);
            } else {
                filesService.uploadFile(multipartFile, "video/", request);
            }

            return "Uploaded Successfully!";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @ResponseBody
    @PostMapping("/file/delete")
    public String fileDelete(@RequestParam Long filesId, HttpServletRequest request) {
        Role role = authCheckUtil.checkSessionAuth(request);
        if (role != Role.ADMIN) {
            return "Unauthorized Access";
        }
        try {
            filesService.deleteFile(filesId);
            return "File Deleted Successfully";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @ResponseBody
    @GetMapping("/file/download")
    public ResponseEntity<UrlResource> downloadFile(@RequestParam Long filesId, HttpServletRequest request) throws UnsupportedEncodingException {
        Role role = authCheckUtil.checkSessionAuth(request);
        if (role != Role.ADMIN) {
            return ResponseEntity.badRequest().body(null);
        }
        String contentDisposition = "attachment; filename=\"" + URLEncoder.encode(filesService.getOriginalFilename(filesId), StandardCharsets.UTF_8) + "\"";

        UrlResource urlResource = filesService.downloadFile(filesId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(urlResource);
    }

    @ResponseBody
    @GetMapping("/file/getInfoById")
    public FilesDto getInfoById(@RequestParam Long filesId) {
        return filesService.findById(filesId);
    }
}
