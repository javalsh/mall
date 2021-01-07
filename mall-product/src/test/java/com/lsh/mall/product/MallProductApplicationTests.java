package com.lsh.mall.product;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import com.lsh.mall.product.entity.BrandEntity;
import com.lsh.mall.product.service.BrandService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayInputStream;
import java.io.File;

@SpringBootTest
class MallProductApplicationTests {

	@Autowired
	BrandService brandService;

	@Test
	void contextLoads() {
	}

	@Test
	void saveTest(){
		BrandEntity brandEntity = new BrandEntity();
		brandEntity.setName("华为");
		brandEntity.setDescript("华为还行");
		brandService.save(brandEntity);
		System.out.println("保存成功");
	}
	@Test
	void fileUpload(){
		// Endpoint以杭州为例，其它Region请按实际情况填写。
		String endpoint = "oss-cn-beijing.aliyuncs.com";
		// 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
		String accessKeyId = "LTAI4Fy54YRxxxKRhq4pDx6v";
		String accessKeySecret = "LurTLijn6cOA1xRbnVZorPtfr4Pzn6";

		// 创建OSSClient实例。
		OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

		// 创建PutObjectRequest对象。
		PutObjectRequest putObjectRequest = new PutObjectRequest("lsh-mall", "02.jpg", new File("E:\\Pictures\\2020\\002TLsr9ly1gm7gx28hw6j60u00zs7wh02.jpg"));

	// 如果需要上传时设置存储类型与访问权限，请参考以下示例代码。
	// ObjectMetadata metadata = new ObjectMetadata();
	// metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
	// metadata.setObjectAcl(CannedAccessControlList.Private);
	// putObjectRequest.setMetadata(metadata);

	// 上传文件。
		ossClient.putObject(putObjectRequest);

	// 关闭OSSClient。
		ossClient.shutdown();
	}
}
