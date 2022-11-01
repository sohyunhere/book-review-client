package com.example.bookreviewclient.controller;

import com.example.bookreviewclient.dto.AttachedFileDto;
import com.example.bookreviewclient.model.AttachedFile;
import com.example.bookreviewclient.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@Slf4j
public class UploadController {

    @Autowired
    private WebClient webClient;
    private final FileService fileService;
    @Value("${com.example.upload.path}") // application.properties의 변수
    private String uploadPath;//이미지 파일 경로

    @Value("${com.example.upload.filepath}")
    private String filePath;//첨부 파일 경로


    //위지윅 에디터 이미지 업로드
    @PostMapping("/board/image/editorUpload")
    public String uploadImage(@RequestParam("image") MultipartFile upload) throws IOException {
        //랜덤 문자 생성
        UUID uid = UUID.randomUUID();

        String fileName = upload.getOriginalFilename();//파일 이름 가져오기
        String toastUploadPath = uid + "_" + fileName;

        File file = new File(uploadPath, toastUploadPath);
        upload.transferTo(file);

        log.info("toastUploadPath : "+toastUploadPath);
        return toastUploadPath;
    }

    //첨부파일 업로드
    @PostMapping("/board/fileUpload")
    public String uploadFile(@RequestParam("uploadfile") MultipartFile uploadFile, @RequestParam("postId") long postId) throws IOException {

        //랜덤 문자 생성
        UUID uid = UUID.randomUUID();
        String fileName = uploadFile.getOriginalFilename();//파일 이름 가져오기\
        String savedFileName = uid + "_" + fileName;

        File file = new File(filePath, savedFileName);
        uploadFile.transferTo(file);

        AttachedFileDto dto = new AttachedFileDto();
        dto.setName(fileName);
        dto.setPostId(postId);
        dto.setPath(filePath + savedFileName);

        String fileId = webClient.post()
                .uri("/board/fileUpload")
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(String.class)
                .flux()
                .toStream()
                .findFirst().orElse(null);

        return fileId;
    }

    @GetMapping("/filedownload/{idx}")
    public void downloadFile(@PathVariable("idx") Long fileId, HttpServletResponse response) throws Exception {
        AttachedFile f = webClient.get()
                .uri("/filedownload/{idx}", fileId)
                .retrieve()
                .bodyToMono(AttachedFile.class)
                .flux()
                .toStream()
                .findFirst().orElse(null);

        fileService.downloadFile(f, response);
    }
}
