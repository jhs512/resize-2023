package com.sbs.resize.standard.util;

import com.sbs.resize.base.app.AppConfig;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UtTest {
    @BeforeAll
    void init() {
        new File(AppConfig.getTempDirPath()).mkdirs();
    }

    @Test
    @DisplayName("URL을 통해서 파일 다운로드가 가능하다.")
    void t002() throws Exception {
        // 파일 다운로드
        String path = Ut.file.downloadFileByHttp("https://picsum.photos/id/237/200/300", AppConfig.getTempDirPath());

        // 파일경로가 존재한다면, 테스트를 통과했다는 의미
        assertThat(path).isNotBlank();

        // 다운로드 된 파일 삭제
        Ut.file.delete(path);
    }
}
