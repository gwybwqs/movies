package com.liu.movices;

import com.liu.movices.dao.AdminUserDao;
import com.liu.movices.dao.MoviesDao;
import com.liu.movices.entity.Movies;
import org.apache.http.entity.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@SpringBootTest
public class UpText {
    @Autowired
    AdminUserDao adminUserDao;
    @Autowired
    MoviesDao moviesDao;

    @Test
    public void fun() throws Exception {
        InputStream inputStream = new FileInputStream("F:/2.xlsx");

        List<List<Object>> dataList = ExcelUtils.readAllExcel(inputStream);
        Object o1;
        Object o2;
        Object o3;
        Object o4;
        Object o5;
        Movies movies;
        for (List<Object> row : dataList) {
            o1 = row.get(0);
            o2 = row.get(1);
            o3 = row.get(2);
            o4 = row.get(3);
            o5 = row.get(4);
            movies = moviesDao.findByName((String)o1);
            if (movies == null){
               try {
                   movies = new Movies();
                   movies.setName((String)o1);
                   movies.setPerformer((String)o2);
                   movies.setType((String)o2);
                   movies.setInformation((String)o3);
                   movies.setMsg(fun((String)o4));
                   movies.setUrl((String)o5);
                   moviesDao.save(movies);
               }catch (Exception e){
                   continue;
               }
            }

        }

    }
    public String fun (String fileName) {
        //File类型转换MultipartFile代码
        File file = new File(fileName);
        String realPath = "F:/IdeaProjects/movices/src/main/webapp/uploadFile/";
        File folder = new File(realPath);
        if (!folder.isDirectory()) {
            folder.mkdirs();
        }
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            MultipartFile multipartFile = new MockMultipartFile(file.getName(), file.getName(),
                    ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
            String oldName = multipartFile.getOriginalFilename();
            multipartFile.transferTo(new File(folder,oldName));
            String filePath = "http://localhost:8080/uploadFile/"+ oldName;
            return filePath;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "失败";
    }

}
