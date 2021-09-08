package com.jay.scourse.util;

import com.jay.scourse.entity.Question;
import com.jay.scourse.vo.QuestionVO;

import java.io.*;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 * CSV文件解析工具
 * </p>
 *
 * @author Jay
 * @date 2021/8/30
 **/
public class CsvUtil {

    public static List<QuestionVO> parseQuestion(InputStream inputStream, boolean firstRowHeader) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        // 默认的header
        String[] headers = {"content", "type", "answers", "selectionA", "selectionB", "selectionC", "selectionD"};

        // 第一行作为header
        if(firstRowHeader){
            // 切分第一行
            String firstRow = reader.readLine();
            headers = firstRow.split(",");
        }

        String line;
        List<QuestionVO> result = new LinkedList<>();

        // 读取每一行
        while((line = reader.readLine()) != null){
            // 切分每一行
            String[] content = line.split(",");
            // 解析行内容，添加到list
            QuestionVO questionVO = buildQuestionVO(headers, content);
            if(questionVO != null){
                result.add(questionVO);
            }
        }
        return result;
    }

    /**
     * 解析content得到QuestionVO
     * @param fields fields数组
     * @param content fields内容数组
     * @return 如果解析出错或无法解析返回null
     */
    private static QuestionVO buildQuestionVO(String[] fields, String[] content)  {
        QuestionVO questionVO = new QuestionVO();
        for(int i = 0; i < content.length; i++){
            try{
                // type 字段
                if("type".equals(fields[i])){
                    // 获取整数值
                    int type = Integer.parseInt(content[i]);
                    if(type > 3 || type < 1){
                        return null;
                    }
                    questionVO.setType(type);

                }
                // answers 字段
                else if("answers".equals(fields[i])){
                    String[] answers = content[i].split(";");
                    questionVO.setAnswers(Arrays.asList(answers));
                }
                // String 类型field，直接使用反射设置
                else{
                    // 反射获取field
                    Field field = Question.class.getDeclaredField(fields[i]);
                    // 设置field访问权限
                    field.setAccessible(true);
                    // 设置questionVO对象的field
                    field.set(questionVO, content[i]);
                }
            }catch (NumberFormatException | NoSuchFieldException | IllegalAccessException e){
                e.printStackTrace();
                return null;
            }
        }
        return questionVO;
    }
}
