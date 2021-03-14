package com.lei.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lei.admin.entity.ChunkInfo;
import com.lei.admin.entity.FileInfo;
import com.lei.admin.mapper.ChunkInfoMapper;
import com.lei.admin.mapper.FileInfoMapper;
import com.lei.admin.service.IFileInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lei.admin.utils.FileInfoUtils;
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
import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
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
        QueryWrapper<ChunkInfo> wrapper = new QueryWrapper<>();
        wrapper.and(i -> i.eq("identifier",fileInfo.getIdentifier()).eq("filename",fileInfo.getFileName()));
        chunkInfoMapper.delete(wrapper);
        //文件合并成功后，保存记录
        if (fileSuccess == HttpServletResponse.SC_OK) {
            fileInfoMapper.insert(fileInfo);
            response.setStatus(HttpServletResponse.SC_OK);
        }
        //如果已经存在，则判断是否是同一个项目，同一个项目不用新增记录，否则新增
        else if (fileSuccess == HttpServletResponse.SC_MULTIPLE_CHOICES) {
            QueryWrapper<FileInfo> wrapper1 = new QueryWrapper<>();
            wrapper1.and(i -> i.eq("identifier",fileInfo.getIdentifier()).eq("file_name",fileInfo.getFileName()));
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
    public PageUtils<FileInfo> findFileList(Integer pageNum, Integer pageSize, FileInfoVO fileInfoVO) {
        String fileName = fileInfoVO.getFileName();
        String fileType = fileInfoVO.getFileType();
        Integer classificationId = fileInfoVO.getClassificationId();
        Integer totalSize = fileInfoVO.getTotalSize();
        Date beginDate = fileInfoVO.getBeginDate();
        Date endDate = fileInfoVO.getEndDate();
        List<FileInfo> fileInfos = fileInfoMapper.findFileList(fileName,fileType,classificationId,totalSize,beginDate,endDate);
        PageUtils<FileInfo> info = new PageUtils<>(pageNum,pageSize);
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
        File file = new File(fileInfo.getLocation()+File.separator+fileInfo.getFileName());
        String newFileName = "";
        try {
            File newFile = new File("D:\\GraduationProject\\StageOne\\knowledge-back\\upload\\preview");//转换之后文件生成的地址
            if (!newFile.exists()) {
                newFile.mkdirs();
            }
            String str = fileInfo.getFileName();
            int i = str.indexOf(".");
            newFileName = str.substring(0,i) + ".pdf";
            System.out.println(newFileName);
            String path = "D:\\GraduationProject\\StageOne\\knowledge-back\\upload\\preview"+File.separator+newFileName;
            //文件转化
            converter.convert(file).to(new File(path)).execute();
            //使用response,将pdf文件以流的方式发送的前段
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newFileName;
    }
}
