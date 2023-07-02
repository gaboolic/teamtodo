package tk.gbl.service;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Date: 2015/4/27
 * Time: 20:38
 *
 * @author Tian.Dong
 */
@Service
public class UploadService {
  public String uploadFile(MultipartFile upload) {
    if (upload.isEmpty()) {
      return null;
    }
    HttpServletRequest request =
        ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    String path = request.getSession().getServletContext().getRealPath("/upload/file");
    String fileName = UUID.randomUUID().toString();
    String file = upload.getOriginalFilename();
    fileName += file.substring(file.lastIndexOf("."));

    try {
      FileUtils.copyInputStreamToFile(upload.getInputStream(), new File(path, fileName));
    } catch (IOException e) {
      e.printStackTrace();
    }

    String filePath = "/upload/file/" + fileName;
    String msg = null;
    msg = "{\"success\":\"" + true + "\"," +
        "\"file_path\":\"" + filePath + "\"," +
        "\"file_name\":\"" + upload.getOriginalFilename() + "\"" +
        "}";
    return msg;
  }
}
