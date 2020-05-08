package community.provider;

import java.io.BufferedReader; 
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value; 
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;

import community.exception.CustomizErrorCode;
import community.exception.CustomizeException;


@Service
public class AliProvider {
	 
	 
	 @Value("${accessKeyId}")  
	private String accessKeyId;
	 
	 @Value("${accessKeySecret}")   
	private String accessKeySecret ;
 
	 @Value("${bucketName}") 
	private String bucketName; 
	 @Value("${endpoint}")   
	private String endpoint ;
	 @Value("${filedir}") 
	private String filedir ;
	
	private OSSClient ossClient;
	  
 

	/* 销毁 */
    public void destory() {
        ossClient.shutdown();
    }
 
    public void init() {
    	ossClient=(OSSClient) new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
    }

	public void del(String objectName) {     
		ossClient.deleteObject(bucketName, objectName); 
		destory();
					
	}
	
	public void downLoad(String objectName) throws IOException {   
		// 调用ossClient.getObject返回一个OSSObject实例，该实例包含文件内容及文件元信息。
		OSSObject ossObject = ossClient.getObject(bucketName, objectName);
		// 调用ossObject.getObjectContent获取文件输入流，可读取此输入流获取其内容。
		InputStream content = ossObject.getObjectContent();
		if (content != null) {
		    BufferedReader reader = new BufferedReader(new InputStreamReader(content));
		    while (true) {
		        String line = reader.readLine();
		        if (line == null) break;
		        System.out.println("\n" + line);
		    }
		    // 数据读取完成后，获取的流必须关闭，否则会造成连接泄漏，导致请求无连接可用，程序无法正常工作。
		    content.close();
		} 
		destory();
	}
	
	
	public void uploadImg2Oss(String url) throws Exception {
		File fileOnServer = new File(url);
		FileInputStream fin;
		try {
			fin = new FileInputStream(fileOnServer);
			String[] split = url.split("/");
			this.uploadFile2OSS(fin, split[split.length - 1]);
		} catch (FileNotFoundException e) {
			throw new CustomizeException(CustomizErrorCode.FILE_UPLOAD_FAIL);
		}
	}

	public String uploadImg2Oss(MultipartFile file) throws Exception {
		if (file.getSize() > 10 * 1024 * 1024) {
			throw new Exception("上传图片大小不能超过10M！");
		}
		String originalFilename = file.getOriginalFilename();
		System.out.println(originalFilename);
		String substring = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
		Random random = new Random();
		String name = random.nextInt(10000) + System.currentTimeMillis() + substring;
		try {
			InputStream inputStream = file.getInputStream();
			// System.out.println("文件名称="+name);
			this.uploadFile2OSS(inputStream, name);
			return name;
		} catch (Exception e) {
			throw new CustomizeException(CustomizErrorCode.FILE_UPLOAD_FAIL);
		}
	}

 /**
         * 获得图片路径
         *
         * @param fileUrl
         * @return
         */
	public String getImgUrl(String fileUrl) {
		System.out.println("fileUrl=" + fileUrl);
		if (!StringUtils.isEmpty(fileUrl)) {
			String[] split = fileUrl.split("/");
			return this.getUrl(this.filedir + split[split.length - 1]);
		}
		return null;
	}
 
 /**
         * 上传到OSS服务器 如果同名文件会覆盖服务器上的
         *
         * @param instream
         *            文件流
         * @param fileName
         *            文件名称 包括后缀名
         * @return 出错返回"" ,唯一MD5数字签名
         */
	public String uploadFile2OSS(InputStream instream, String fileName) {
		String ret = "";
		try {
			// 创建上传Object的Metadata
			ObjectMetadata objectMetadata = new ObjectMetadata();
			objectMetadata.setContentLength(instream.available());
			objectMetadata.setCacheControl("no-cache");
			objectMetadata.setHeader("Pragma", "no-cache");
			objectMetadata.setContentType(getcontentType(fileName.substring(fileName.lastIndexOf("."))));
			objectMetadata.setContentDisposition("inline;filename=" + fileName);
			// System.out.println("开始上传文件");
			// 上传文件
			PutObjectResult putResult = ossClient.putObject(bucketName, filedir + fileName, instream, objectMetadata);
			// PutObjectResult putResult = ossClient.putObject(bucketName,  fileName,
			// instream, objectMetadata);
			// System.out.println("上传成功");
			ret = putResult.getETag();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (instream != null) {
					instream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return ret;
	}
 
 /**
         * Description: 判断OSS服务文件上传时文件的contentType 
         *
         * @param filenameExtension 文件后缀
         * @return String
         */
	public static String getcontentType(String filenameExtension) {
		if (filenameExtension.equalsIgnoreCase("bmp")) {
			return "image/bmp";
		}
		if (filenameExtension.equalsIgnoreCase("gif")) {
			return "image/gif";
		}
		if (filenameExtension.equalsIgnoreCase("jpeg") || filenameExtension.equalsIgnoreCase("jpg")
				|| filenameExtension.equalsIgnoreCase("png")) {
			return "image/jpeg";
		}
		if (filenameExtension.equalsIgnoreCase("html")) {
			return "text/html";
		}
		if (filenameExtension.equalsIgnoreCase("txt")) {
			return "text/plain";
		}
		if (filenameExtension.equalsIgnoreCase("vsd")) {
			return "application/vnd.visio";
		}
		if (filenameExtension.equalsIgnoreCase("pptx") || filenameExtension.equalsIgnoreCase("ppt")) {
			return "application/vnd.ms-powerpoint";
		}
		if (filenameExtension.equalsIgnoreCase("docx") || filenameExtension.equalsIgnoreCase("doc")) {
			return "application/msword";
		}
		if (filenameExtension.equalsIgnoreCase("xml")) {
			return "text/xml";
		}
		return "image/jpg";
	}

/**
         * 获得url链接 
         *
         * @param key
         * @return
         */
	public String getUrl(String key) {
// 设置URL过期时间为10年 3600l* 1000*24*365*10  

		Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 10);
// 生成URL  
		URL url = ossClient.generatePresignedUrl(bucketName, key, expiration);

		if (url != null) { 
			return url.toString();
		}
		return null;
	}

}
