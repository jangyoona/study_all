package com.demoweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/demo")
public class DemoController {

    @GetMapping("/image-upload")
    public String showImageUploadForm() {
        return "demo/image-upload";
    }

    @PostMapping(path = {"/image-upload"}, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String uploadImage(MultipartFile attach) {
        try {
            String currentDir = System.getProperty("user.dir"); // user.dir? 현재 프로그램이 실행이 시작된 경로
            File uploadDir = new File(currentDir, "upload");
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            System.out.println(". ----------------------> " + uploadDir.getAbsolutePath());
            attach.transferTo((new File(uploadDir, attach.getOriginalFilename())));

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Add file path to the model
        return String.format("{\"imageUrl\":\"%s\"}", "/demo/uploads/" + attach.getOriginalFilename());
    }
}
