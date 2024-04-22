package com.liuwei.user.utils;

import io.minio.*;
import io.minio.http.Method;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

public class MinioUtils {
    private static MinioClient minioClient;


    static {
       try{
           minioClient =  MinioClient.builder()
                   .endpoint("http://localhost:9000")
                   .credentials("cat-protection-system-user", "12345678")
                   .build();
       } catch (Exception e){
           e.printStackTrace();
       }
    }
    /**
     * 初始化方法
     * @param endpoint
     * @param accessKey
     * @param secretKey
     */
    public static void init(String endpoint, String accessKey, String secretKey){
        try{
            minioClient = MinioClient.builder()
                    .endpoint(endpoint)
                    .credentials(accessKey, secretKey)
                    .build();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 上传文件
     * @param bucketName
     * @param objectName
     * @param filePath
     * @return
     * @throws Exception
     */
    public static ObjectWriteResponse upload(String bucketName, String objectName, String filePath) throws Exception{
        ObjectWriteResponse objectWriteResponse = minioClient.uploadObject(
                UploadObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .filename(filePath)
                        .contentType("application/octet-stream")
                        .build()
        );
        return objectWriteResponse;
    }

    public static ObjectWriteResponse upload(String bucketName, String objectName, long objectSize,
                                             InputStream inputStream) throws Exception{

        ObjectWriteResponse objectWriteResponse = minioClient.putObject(PutObjectArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .stream(inputStream, objectSize, 1024 * 1024 * 5)
                .contentType("application/octet-stream")
                .build());
        return objectWriteResponse;
    }



    /**
     * 下载文件
     * @param bucketName
     * @param objectName
     * @param filePath
     * @param overwrite
     * @throws Exception
     */

    public static void download(String bucketName, String objectName, String filePath, boolean overwrite)
            throws Exception{
        minioClient.downloadObject(
                DownloadObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .filename(filePath)
                        .overwrite(overwrite)
                        .build()
        );
    }

    /**
     * 下载文件
     * @param bucketName
     * @param objectName
     * @param filePath
     * @throws Exception
     */

    public static void download(String bucketName, String objectName, String filePath) throws Exception{
        minioClient.downloadObject(
                DownloadObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .filename(filePath)
                        .build()
        );
    }

    /**
     * 删除文件
     * @param bucketName
     * @param objectName
     * @throws Exception
     */

    public static void removeObject(String bucketName, String objectName) throws Exception{
        minioClient.removeObject(
                RemoveObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .build()
        );
    }

    /**
     * 获取文件信息
     * @param bucketName
     * @param objectName
     * @return
     * @throws Exception
     */

    public static GetObjectResponse getObjectInfo(String bucketName, String objectName) throws Exception{
        GetObjectResponse object = minioClient.getObject(GetObjectArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .build());
        return object;
    }

    /**
     * 根据路径获取文件
     * @param bucketName
     * @param objectName
     * @param duration
     * @param timeUnit
     * @return
     * @throws Exception
     */

    public static String getPresignedUrl(String bucketName, String objectName, int duration, TimeUnit timeUnit)
            throws Exception{
        String url = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                .expiry(duration, timeUnit)
                .method(Method.GET)
                .object(objectName)
                .bucket(bucketName)
                .build());
        return url;
    }

    /**
     * 根据路径获取文件
     * @param bucketName
     * @param objectName
     * @param duration
     * @param timeUnit
     * @param method
     * @return
     * @throws Exception
     */

    public static String getPresignedUrl(String bucketName, String objectName, int duration, TimeUnit timeUnit, Method method) throws Exception{
        String url = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                .expiry(duration, timeUnit)
                .method(method)
                .object(objectName)
                .bucket(bucketName)
                .build());
        return url;
    }

    /**
     * 获取上传链接
     * @param bucketName
     * @param objectName
     * @param duration
     * @param timeUnit
     * @return
     * @throws Exception
     */
    public static String getUploadUrl(String bucketName, String objectName, int duration, TimeUnit timeUnit) throws Exception{
        String url = minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                .expiry(duration, timeUnit)
                .method(Method.PUT)
                .object(objectName)
                .bucket(bucketName)
                .build());
        return url;
    }





}
