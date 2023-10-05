package com.sbs.resize.domain.resize.controller;

import com.sbs.resize.domain.genFile.service.GenFileService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Rollback(value = false)
@ActiveProfiles("test")
public class ResizeControllerTest {
    @Autowired
    private GenFileService genFileService;

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("/resize -> ResizeController::resize 메서드가 실행")
    void t001() throws Exception {
        // WHEN
        ResultActions resultActions = mvc
                .perform(get("/resize?url=https://picsum.photos/id/237/200/300&width=200"))
                .andDo(print());

        // THEN
        resultActions
                .andExpect(handler().handlerType(ResizeController.class))
                .andExpect(handler().methodName("resize"))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @DisplayName("/resize -> ResizeController::resize 메서드가 실행")
    void t002() throws Exception {
        String originUrl = "https://picsum.photos/id/237/200/300";

        // WHEN
        ResultActions resultActions = mvc
                .perform(get("/resize?url=https://picsum.photos/id/237/200/300&width=200"))
                .andDo(print());

        genFileService.findByOriginUrl(originUrl);
    }
}
