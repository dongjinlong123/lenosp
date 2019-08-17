package com.len.controller;

import com.alibaba.fastjson.JSONObject;
import com.len.base.BaseController;
import com.len.common.FileImageManagerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;


/**
 * 文本编辑器控制类
 * 
 * @Description: 	
 * @author djl
 * @date 2018年7月8日 下午12:20:36 
 * @version V1.0
 */
@Controller
@Slf4j
@RequestMapping("/editor/upload")
public class KindEditorController extends BaseController {

	@Value("${lenosp.imagePath}")
	private String filePath;
	@Value("${lenosp.rootUrl}")
	private String rootUrl;


	@ResponseBody
	@RequestMapping(value = "/file", method = RequestMethod.POST)
	public String file(HttpServletRequest request) throws Exception {
		String url = rootUrl;
		JSONObject obj = new JSONObject();
		if (request instanceof MultipartRequest) {
			MultipartRequest m = (MultipartRequest) request;
			Iterator<String> iterator = m.getFileNames();

			while (iterator.hasNext()) {
				String fileName = iterator.next();
				List<MultipartFile> files = m.getFiles(fileName);
				for (MultipartFile f : files) {
					try {
						String file = filePath+f.getOriginalFilename();
						url += f.getOriginalFilename();
						FileOutputStream fos= new FileOutputStream(file);
						fos.write(f.getBytes());
						fos.close();
					} catch (IOException e) {
						log.info("上传失败" + e.fillInStackTrace());
					}
				}

			}
		}
		//编辑器返回值
		obj.put("error", 0);
		obj.put("url",url);
		//md返回值
		obj.put("success", 0);
		obj.put("message", "传成功");
		return obj.toJSONString();
	}

	@ResponseBody
	@RequestMapping(value = "/downfile", method = RequestMethod.GET)
	public String downfile(HttpServletRequest req, HttpServletResponse rep) {

		return FileImageManagerUtil.downfile(req, rep,filePath,rootUrl);
	}

}