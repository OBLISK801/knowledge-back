package com.lei.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.lei.admin.entity.ChunkInfo;
import com.lei.admin.entity.FileInfo;
import com.lei.admin.mapper.ChunkInfoMapper;
import com.lei.admin.mapper.FileInfoMapper;
import com.lei.admin.service.IFileInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lei.admin.utils.FileInfoUtils;
import com.lei.admin.vo.DownLoadVO;
import com.lei.admin.vo.FileInfoVO;
import com.lei.error.SystemCodeEnum;
import com.lei.error.SystemException;
import com.lei.utils.PageUtils;
import org.apache.commons.io.IOUtils;
import org.jodconverter.DocumentConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author GuanyuLei
 * @since 2021-03-06
 */
@Service
public class FileInfoServiceImpl extends ServiceImpl<FileInfoMapper, FileInfo> implements IFileInfoService {

    @Autowired
    private ChunkInfoMapper chunkInfoMapper;
    @Autowired
    private FileInfoMapper fileInfoMapper;
    @Autowired
    private DocumentConverter converter;

    @Value("D:\\GraduationProject\\StageOne\\knowledge-back\\upload")
    private String uploadFolder;

    @Override
    public HttpServletResponse mergeFile(FileInfo fileInfo, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE); //415
        //进行文件的合并操作
        String filename = fileInfo.getFileName();
        String file = uploadFolder + File.separator + fileInfo.getIdentifier() + File.separator + filename;
        String folder = uploadFolder + File.separator + fileInfo.getIdentifier();
        Integer fileSuccess = FileInfoUtils.merge(file, folder, filename);
        fileInfo.setLocation(folder);
        fileInfo.setIsPublic(0);
        QueryWrapper<ChunkInfo> wrapper = new QueryWrapper<>();
        wrapper.and(i -> i.eq("identifier", fileInfo.getIdentifier()).eq("filename", fileInfo.getFileName()));
        chunkInfoMapper.delete(wrapper);
        //文件合并成功后，保存记录
        if (fileSuccess == HttpServletResponse.SC_OK) {
            fileInfoMapper.insert(fileInfo);
            if ("application/msword".equals(fileInfo.getFileType()) ||
                    "application/vnd.openxmlformats-officedocument.presentationml.presentation".equals(fileInfo.getFileType()) ||
                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet".equals(fileInfo.getFileType())) {
                // 插入后直接返回自增id
                this.preview(fileInfo.getId());
            }
            if ("application/pdf".equals(fileInfo.getFileType())) {
                try {
                    FileInputStream fileInputStream = new FileInputStream(file);
                    FileOutputStream fileOutputStream = new FileOutputStream(
                            "D:\\GraduationProject\\StageOne\\knowledge-back\\upload\\preview"+File.separator+filename);
                    IOUtils.copy(fileInputStream, fileOutputStream);
                    IOUtils.closeQuietly(fileInputStream);
                    IOUtils.closeQuietly(fileOutputStream);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            response.setStatus(HttpServletResponse.SC_OK);
        }
        //如果已经存在，则判断是否是同一个项目，同一个项目不用新增记录，否则新增
        else if (fileSuccess == HttpServletResponse.SC_MULTIPLE_CHOICES) {
            QueryWrapper<FileInfo> wrapper1 = new QueryWrapper<>();
            wrapper1.and(i -> i.eq("identifier", fileInfo.getIdentifier())
                    .eq("file_name", fileInfo.getFileName())
                    .eq("upload_user", fileInfo.getUploadUser()));
            List<FileInfo> tfList = fileInfoMapper.selectList(wrapper1);
            if (tfList.size() == 0) {
                fileInfoMapper.insert(fileInfo);
                response.setStatus(HttpServletResponse.SC_MULTIPLE_CHOICES); //300
            } else {
                for (FileInfo info : tfList) {
                    if (info.getClassificationId().equals(fileInfo.getClassificationId())) {
                        QueryWrapper<FileInfo> wrapper2 = new QueryWrapper<>();
                        wrapper2.eq("id", info.getId());
                        fileInfoMapper.delete(wrapper2);
                        fileInfoMapper.insert(fileInfo);
                        response.setStatus(HttpServletResponse.SC_MULTIPLE_CHOICES);
                    }
                }
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400
        }
        return response;
    }

    @Override
    public PageUtils<FileInfo> findFileList(Integer pageNum, Integer pageSize, String username, FileInfoVO fileInfoVO) {
        String fileName = fileInfoVO.getFileName();
        String fileType = fileInfoVO.getFileType();
        Integer classificationId = fileInfoVO.getClassificationId();
        Integer totalSize = fileInfoVO.getTotalSize();
        Date beginDate = fileInfoVO.getBeginDate();
        Date endDate = fileInfoVO.getEndDate();
        Integer isPublic = fileInfoVO.getIsPublic();
        List<FileInfo> fileInfos = fileInfoMapper.findFileList(fileName, fileType, classificationId, totalSize, beginDate, endDate, username, isPublic);
        PageUtils<FileInfo> info = new PageUtils<>(pageNum, pageSize);
        info.doPage(fileInfos);
        return info;
    }

    @Override
    public void deleteFile(FileInfo fileInfo) throws SystemException {
        FileInfo file = fileInfoMapper.selectById(fileInfo.getId());
        if (file == null) {
            throw new SystemException(SystemCodeEnum.PARAMETER_ERROR, "要删除的文件不存在");
        }
        fileInfoMapper.deleteById(fileInfo.getId());
    }

    @Override
    public FileInfo findById(Integer id) {
        return fileInfoMapper.selectById(id);
    }

    @Override
    public String preview(Integer id) {
        FileInfo fileInfo = this.findById(id);
        // 获得上传的文件
        File file = new File(fileInfo.getLocation() + File.separator + fileInfo.getFileName());
        String newFileName = "";
        try {
            File newFile = new File("D:\\GraduationProject\\StageOne\\knowledge-back\\upload\\preview");//转换之后文件生成的地址
            if (!newFile.exists()) {
                newFile.mkdirs();
            }
            String str = fileInfo.getFileName();
            int i = str.indexOf(".");
            newFileName = str.substring(0, i) + ".pdf";
            System.out.println(newFileName);
            String path = "D:\\GraduationProject\\StageOne\\knowledge-back\\upload\\preview" + File.separator + newFileName;
            // 转换过的文件不需要再转换
            File fileExist = new File(path);
            if (!fileExist.exists()) {
                System.out.println("开始转化");
                //文件转化
                converter.convert(file).to(new File(path)).execute();
            } else {
                System.out.println("转换文件已经存在，直接返回");
            }

            //使用response,将pdf文件以流的方式发送的前段
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newFileName;
    }

    @Override
    public void publicMyFile(Integer id) {
        FileInfo fileInfo = fileInfoMapper.selectById(id);
        fileInfo.setIsPublic(1);
        UpdateWrapper<FileInfo> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", id);
        fileInfoMapper.update(fileInfo, wrapper);
    }

    @Override
    public PageUtils<FileInfo> findPublicFile(Integer pageNum, Integer pageSize) {
        QueryWrapper<FileInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("is_public", 1);
        List<FileInfo> fileInfos = fileInfoMapper.selectList(wrapper);
        PageUtils<FileInfo> info = new PageUtils<>(pageNum, pageSize);
        info.doPage(fileInfos);
        return info;
    }

    @Override
    public PageUtils<FileInfo> findByTagId(Integer id, Integer pageNum, Integer pageSize) {
        List<FileInfo> fileInfos = fileInfoMapper.findByTagId(id);
        PageUtils<FileInfo> info = new PageUtils<>(pageNum, pageSize);
        info.doPage(fileInfos);
        return info;
    }

    @Override
    public PageUtils<FileInfo> findByClassId(Integer id, Integer pageNum, Integer pageSize) {
        QueryWrapper<FileInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("classification_id",id).eq("is_public",1);
        List<FileInfo> fileInfos = fileInfoMapper.selectList(wrapper);
        PageUtils<FileInfo> info = new PageUtils<>(pageNum, pageSize);
        info.doPage(fileInfos);
        return info;
    }

    @Override
    public PageUtils<DownLoadVO> findByUser(Integer pageNum, Integer pageSize, String username) {
        List<DownLoadVO> downLoadVOS = fileInfoMapper.findByUser(username);
        PageUtils<DownLoadVO> info = new PageUtils<>(pageNum,pageSize);
        info.doPage(downLoadVOS);
        return info;
    }
}
