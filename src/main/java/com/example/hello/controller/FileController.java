package com.example.hello.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.hello.common.Result;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;

/**
 * 文件上传控制器
 */
@RestController
public class FileController {

    @Value("${minio.endpoint}")
    private String endpoint; // This is the configured endpoint (e.g., https://minio-api.doitapp.top)

    @Value("${minio.accessKey}")
    private String accessKey;

    @Value("${minio.secretKey}")
    private String secretKey;

    @Value("${minio.bucketName}")
    private String bucketName;

    /**
     * 文件上传
     * @param file 上传的文件
     * @return 文件的访问路径
     */
    @PostMapping("/api/upload")
    public Result<String> upload(@RequestParam("file") MultipartFile file) {
        try {
            // 获取文件名
            String originalFilename = file.getOriginalFilename();
            // 生成随机文件名
            String fileName = UUID.randomUUID().toString() + 
                originalFilename.substring(originalFilename.lastIndexOf("."));

            // 创建MinIO客户端，使用配置文件中注入的外部端点地址 (endpoint)
            MinioClient minioClient = MinioClient.builder()
                .endpoint(endpoint) // Use the configured endpoint for connection
                .credentials(accessKey, secretKey)
                .build();

            // 检查存储桶是否存在
            boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!bucketExists) {
                // 创建存储桶
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }

            // 上传文件
            minioClient.putObject(
                PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build()
            );

            // 构建文件访问路径（使用配置中注入的 endpoint 地址）
            String fileUrl = endpoint + "/" + bucketName + "/" + fileName;

            // 返回结果
            return Result.success(fileUrl);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error("文件上传失败：" + e.getMessage());
        }
    }
} 