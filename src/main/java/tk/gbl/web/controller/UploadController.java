package tk.gbl.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import tk.gbl.service.UploadService;

import javax.annotation.Resource;

/**
 * Date: 2015/4/27
 * Time: 20:36
 *
 * @author Tian.Dong
 */
@Controller
@RequestMapping("upload")
public class UploadController {
  @Resource
  UploadService uploadService;

  @ResponseBody
  @RequestMapping("file")
  public String uploadFile(@RequestParam MultipartFile fileData){
    String ret = uploadService.uploadFile(fileData);
    return ret;
  }
}
