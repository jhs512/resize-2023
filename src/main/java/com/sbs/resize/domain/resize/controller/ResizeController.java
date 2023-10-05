package com.sbs.resize.domain.resize.controller;

import com.sbs.resize.domain.genFile.entity.GenFile;
import com.sbs.resize.domain.genFile.service.GenFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@RequestMapping("/resize")
public class ResizeController {
    private final GenFileService genFileService;

    @GetMapping("")
    @ResponseBody
    public String resize(String url, int width) {
        return genFileService.findByOriginUrl(url)
                .map(genFile -> {
                    return "이미 존재";
                })
                .orElseGet(() -> {
                    GenFile genFile = genFileService.save(url);

                    return "방금 다운로드";
                });
    }
}
