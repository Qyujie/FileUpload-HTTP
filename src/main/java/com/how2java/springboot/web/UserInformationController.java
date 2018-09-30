package com.how2java.springboot.web;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UserInformationController {
	final String uplaodPath = "D:/uploadFiles/";
	public long lensize = 0;

	@RequestMapping("/")
	public String userInformation(Model m) {
		File uplaod = new File(uplaodPath);
		uplaod.mkdirs();
		return "fileIO";
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public String upload(HttpServletRequest req, @RequestParam("file") MultipartFile file, Model m) {
		try {
			String fileName = file.getOriginalFilename();
			String destFileName = uplaodPath + fileName;
			File destFile = new File(destFileName);
			destFile.getParentFile().mkdirs();
			//file.transferTo(destFile);
			
			InputStream inputStream = file.getInputStream();
			OutputStream outputStream = new FileOutputStream(destFile);
			BufferedInputStream bis = new BufferedInputStream(inputStream);
			BufferedOutputStream bos = new BufferedOutputStream(outputStream);
			byte[] b = new byte[1024];
			int len = 0;
			long fileSize = file.getSize();
			lensize = 0;

			Thread t1 = new Thread() {
				public void run() {
					while (true) {
						if (lensize==fileSize) {
							break;
						}
						try {
							System.out.println(lensize);
							sleep(500);
						} catch (InterruptedException e) {
							// TODO 自动生成的 catch 块
							e.printStackTrace();
						}
					}
				}
			};
			t1.start();
			while ((len = bis.read(b)) != -1) {
				bos.write(b, 0, len);
				lensize += len;
			}
			bis.close();
			bos.close();

			System.out.println("fileSize:" + fileSize);
			System.out.println("lensize:" + lensize);

			// String oldfilePath =
			// userInformationMapper.getUserInformation(id).getHead_portrait();
			// File oldFile = new File(uplaodPath+oldfilePath);
			// oldFile.delete();

			return fileName;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return "上传失败," + e.getMessage();
		} catch (IOException e) {
			e.printStackTrace();
			return "上传失败," + e.getMessage();
		}
	}
}
