package com.github.guoyjalihy.logging;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.File;
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
    @Value("${logging.file.path}")
    public String filePath = "";

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
        model.addAttribute("text","text");
        return "logging";
    }

    private void iteratorFile(File file,List<String> fileList,String rootPath){
        File[] files = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            if(files[i].isDirectory()){
                rootPath = files[i].getPath();
                iteratorFile(files[i],fileList,rootPath);
            }else{
                String filePath = rootPath + "\\" + files[i].getName();
                fileList.add(filePath);
            }
        }
    }
}
