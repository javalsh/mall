package com.lsh.mall.thirdparty;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@SpringBootTest
class MallThirdPartyApplicationTests {

	@Test
	void contextLoads() {
	}
	@Autowired
	OSSClient ossClient;

	@Test
	public void testUpload() throws FileNotFoundException {

		//上传文件流。
		InputStream inputStream = new FileInputStream("E:\\Pictures\\2020\\002TLsr9ly1gm7gx28wx9j60u00zse8102.jpg");
		ossClient.putObject("lsh-mall", "5.jpg", inputStream);

		// 关闭OSSClient。
		ossClient.shutdown();
		System.out.println("上传成功.");
	}

}
