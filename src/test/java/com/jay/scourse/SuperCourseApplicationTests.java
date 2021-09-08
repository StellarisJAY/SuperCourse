package com.jay.scourse;

import com.jay.scourse.util.CsvUtil;
import com.jay.scourse.util.Md5Util;
import com.jay.scourse.util.VideoStorageUtil;
import com.jay.scourse.vo.QuestionVO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@SpringBootTest
class SuperCourseApplicationTests {

    @Test
    void testMd5() throws Exception {
        String s = Md5Util.md5("12345678");
        System.out.println(s);
    }

    @Test
    void testUploadAuth() throws Exception {
        VideoStorageUtil.uploadVideo("test2", "/test.mp4");
    }

    @Test
    void testCsvUtil() throws FileNotFoundException {
        File file = new File("D:/questions.csv");
        FileInputStream inputStream = new FileInputStream(file);
        try {
            List<QuestionVO> questionVOS = CsvUtil.parseQuestion(inputStream, false);
            System.out.println(questionVOS.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
