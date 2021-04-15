package com.lei.admin.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.*;
import com.lei.admin.entity.FileInfo;
import com.lei.admin.mapper.FileInfoMapper;
import com.lei.admin.service.IOSService;
import com.lei.admin.utils.OSSUtils;
import com.lei.admin.vo.OSSFileVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

/**
 * @Author LEI
 * @Date 2021/3/15 10:29
 * @Description TODO
 */
@Service
public class OSServiceImpl implements IOSService {

    @Autowired
    private FileInfoMapper fileInfoMapper;

    @Override
    public String uploadOSSFile(OSSFileVO ossFileVO) throws IOException {
        MultipartFile file = ossFileVO.getFile();
        String userName = ossFileVO.getUserName();
        Integer classificationId = ossFileVO.getClassificationId();
        String bucketName = "test-0230";
        String objectName = Objects.requireNonNull(file.getContentType()).replaceAll("/", "") + "/" + file.getOriginalFilename();
        OSS ossClient = OSSUtils.getOssClient();
        // 创建InitiateMultipartUploadRequest对象。
        InitiateMultipartUploadRequest request = new InitiateMultipartUploadRequest(bucketName, objectName);

        // 如果需要在初始化分片时设置文件存储类型，请参考以下示例代码。
        // ObjectMetadata metadata = new ObjectMetadata();
        // metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
        // request.setObjectMetadata(metadata);

        // 初始化分片。
        InitiateMultipartUploadResult upresult = ossClient.initiateMultipartUpload(request);
        // 返回uploadId，它是分片上传事件的唯一标识，您可以根据这个uploadId发起相关的操作，如取消分片上传、查询分片上传等。
        String uploadId = upresult.getUploadId();

        // partETags是PartETag的集合。PartETag由分片的ETag和分片号组成。
        List<PartETag> partETags = new ArrayList<>();
        // 计算文件有多少个分片。
        final long partSize = 1024 * 1024L;   // 1MB
        final MultipartFile sampleFile = ossFileVO.getFile();
        long fileLength = sampleFile.getSize();
        int partCount = (int) (fileLength / partSize);
        if (fileLength % partSize != 0) {
            partCount++;
        }
        // 遍历分片上传。
        for (int i = 0; i < partCount; i++) {
            long startPos = i * partSize;
            long curPartSize = (i + 1 == partCount) ? (fileLength - startPos) : partSize;
            InputStream instream = sampleFile.getInputStream();
            // 跳过已经上传的分片。
            instream.skip(startPos);
            UploadPartRequest uploadPartRequest = new UploadPartRequest();
            uploadPartRequest.setBucketName(bucketName);
            uploadPartRequest.setKey(objectName);
            uploadPartRequest.setUploadId(uploadId);
            uploadPartRequest.setInputStream(instream);
            // 设置分片大小。除了最后一个分片没有大小限制，其他的分片最小为100 KB。
            uploadPartRequest.setPartSize(curPartSize);
            // 设置分片号。每一个上传的分片都有一个分片号，取值范围是1~10000，如果超出这个范围，OSS将返回InvalidArgument的错误码。
            uploadPartRequest.setPartNumber(i + 1);
            // 每个分片不需要按顺序上传，甚至可以在不同客户端上传，OSS会按照分片号排序组成完整的文件。
            UploadPartResult uploadPartResult = ossClient.uploadPart(uploadPartRequest);
            // 每次上传分片之后，OSS的返回结果包含PartETag。PartETag将被保存在partETags中。
            partETags.add(uploadPartResult.getPartETag());
        }


        // 创建CompleteMultipartUploadRequest对象。
        // 在执行完成分片上传操作时，需要提供所有有效的partETags。OSS收到提交的partETags后，会逐一验证每个分片的有效性。当所有的数据分片验证通过后，OSS将把这些分片组合成一个完整的文件。
        CompleteMultipartUploadRequest completeMultipartUploadRequest =
                new CompleteMultipartUploadRequest(bucketName, objectName, uploadId, partETags);

        // 如果需要在完成文件上传的同时设置文件访问权限，请参考以下示例代码。
        // completeMultipartUploadRequest.setObjectACL(CannedAccessControlList.PublicRead);

        // 完成上传。
        CompleteMultipartUploadResult completeMultipartUploadResult = ossClient.completeMultipartUpload(completeMultipartUploadRequest);

        // 关闭OSSClient。
        ossClient.shutdown();
        String url = "https://" + bucketName + ".oss-cn-beijing.aliyuncs.com/" + objectName;
        FileInfo fileInfo = new FileInfo();
        fileInfo.setFileName(file.getOriginalFilename());
        fileInfo.setIdentifier(uploadId);
        fileInfo.setTotalSize((int) file.getSize());
        fileInfo.setLocation(url);
        fileInfo.setFileType(file.getContentType());
        fileInfo.setClassificationId(classificationId);
        fileInfo.setUploadUser(userName);
        fileInfo.setUploadTime(new Date());
        fileInfoMapper.insert(fileInfo);
        return url;
    }

    @Override
    public String uploadPhoto(MultipartFile file) {
        OSS ossClient = OSSUtils.getOssClient();
        String bucketName = "bysj01";
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String fileName = "photo" + "/" + uuid + file.getOriginalFilename();
        String url = "";
        try {
            InputStream inputStream = file.getInputStream();
            ossClient.putObject(bucketName, fileName, inputStream);
            ossClient.shutdown();
            url = "https://" + bucketName + ".oss-cn-beijing.aliyuncs.com/" + fileName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }
}
