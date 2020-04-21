package com.liu.movices.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@RestController
public class FileController {
    @RequestMapping( value = "/upload" ,method = RequestMethod.POST)
    public String upload(MultipartFile file, HttpServletRequest req) {
        String realPath = req.getSession().getServletContext().getRealPath("/uploadFile/");
        File folder = new File(realPath);
        if (!folder.isDirectory()) {
            folder.mkdirs();
        }
        String oldName = file.getOriginalFilename();
        try {
            file.transferTo(new File(folder,oldName));
            String filePath = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + "/uploadFile/" +  oldName;
            return filePath;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    @RequestMapping( value = "/download", method = RequestMethod.GET)
    public void download1(String url,HttpServletRequest req, HttpServletResponse response) throws Exception {
        // 文件地址，真实环境是存放在数据库中的
        String a = url.substring(33);
        String realPath = req.getSession().getServletContext().getRealPath("/uploadFile/");
        File file = new File(realPath+a);
        // 穿件输入对象
        FileInputStream fis = new FileInputStream(file);
        // 设置相关格式
        response.setContentType("application/force-download");
        // 设置下载后的文件名以及header
        response.addHeader("Content-disposition", "attachment;fileName=" +  new String( (file.getName()).getBytes("GBK"), "ISO8859-1"));
        // 创建输出对象
        OutputStream os = response.getOutputStream();

        // 常规操作
        byte[] buf = new byte[1024];
        int len = 0;
        while((len = fis.read(buf)) != -1) {
            os.write(buf, 0, len);
        }
        fis.close();
    }
}
