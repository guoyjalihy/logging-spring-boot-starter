package com.github.guoyjalihy.logging;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Auther: guoyanjun
 * @Date: 2021/06/04 09:44
 */
@Controller
@RequestMapping(value = "logging")
public class LoggingController {

    private String filePath;
    public LoggingController(String filePath){
        this.filePath = filePath;
    }

    @RequestMapping(value = "files",method = RequestMethod.GET)
    public String fileNames(Model model){
        File file = new File(filePath);
        if (!file.isDirectory()){
            throw new RuntimeException(filePath + "不是文件夹，请配置为日志文件所在的根路径。");
        }
        List<String> fileList = new ArrayList<>();
        String rootPath = filePath;
        iteratorFile(file,fileList,rootPath);
        model.addAttribute("files",fileList);
        return "fileSelect";
    }

    private void iteratorFile(File file,List<String> fileList,String rootPath){
        File[] files = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            if(files[i].isDirectory()){
                rootPath = files[i].getPath();
                iteratorFile(files[i],fileList,rootPath);
            }else{
                String filePath = rootPath + "/" + files[i].getName();
                fileList.add(filePath);
            }
        }
    }


    @RequestMapping(value = "showLog",method = RequestMethod.GET)
    public String showLog(){
        return "showLog";
    }

    @RequestMapping(value = "download",method = RequestMethod.GET)
    public void  download(HttpServletResponse response , Model model, @RequestParam String fileName) {
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        byte[] buff = new byte[1024];
        //创建缓冲输入流
        BufferedInputStream bis = null;
        OutputStream outputStream = null;

        try {
            outputStream = response.getOutputStream();

            //这个路径为待下载文件的路径
            bis = new BufferedInputStream(new FileInputStream(new File(fileName)));
            int read = bis.read(buff);

            //通过while循环写入到指定了的文件夹中
            while (read != -1) {
                outputStream.write(buff, 0, buff.length);
                outputStream.flush();
                read = bis.read(buff);
            }
        } catch ( IOException e ) {
            e.printStackTrace();
            //出现异常返回给页面失败的信息
            model.addAttribute("result","下载失败");
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
