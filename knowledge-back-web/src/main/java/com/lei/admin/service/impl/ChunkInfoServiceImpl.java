package com.lei.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lei.admin.entity.ChunkInfo;
import com.lei.admin.mapper.ChunkInfoMapper;
import com.lei.admin.service.IChunkInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lei.admin.utils.FileInfoUtils;
import com.lei.admin.vo.ChunkResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
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
public class ChunkInfoServiceImpl extends ServiceImpl<ChunkInfoMapper, ChunkInfo> implements IChunkInfoService {

    @Autowired
    private ChunkInfoMapper chunkInfoMapper;
    @Value("D:\\GraduationProject\\StageOne\\knowledge-back\\upload")
    private String uploadFolder;

    @Override
    public ChunkResult checkChunkState(ChunkInfo chunkInfo, HttpServletResponse response) {
        ChunkResult chunkResult = new ChunkResult();
        String file = uploadFolder + File.separator + chunkInfo.getIdentifier() + File.separator + chunkInfo.getFilename();
        if(FileInfoUtils.fileExists(file)) {
            chunkResult.setSkipUpload(true);
            chunkResult.setLocation(file);
            response.setStatus(HttpServletResponse.SC_OK); // 200
            chunkResult.setMessage("完整文件已存在，直接跳过上传，实现秒传");
            return chunkResult;
        }
        QueryWrapper<ChunkInfo> wrapper = new QueryWrapper<>();
        wrapper.and(i -> i.eq("identifier",chunkInfo.getIdentifier()).eq("filename",chunkInfo.getFilename()));
        List<ChunkInfo> chunkInfos = chunkInfoMapper.selectList(wrapper);
        // 从数据库中获取已经存储的chunkNumber集合，返回给前端
        ArrayList<Integer> list = new ArrayList<>();
        for (ChunkInfo info : chunkInfos) {
            list.add(info.getChunkNumber());
        }
        if (list.size() > 0) {
            chunkResult.setSkipUpload(false);
            chunkResult.setUploadedChunks(list);
            response.setStatus(HttpServletResponse.SC_OK);
            chunkResult.setMessage("部分文件块已存在，继续上传剩余文件块，实现断点续传");
            return chunkResult;
        }
        return chunkResult;
    }

    @Override
    public Integer uploadFile(ChunkInfo chunk) {
        Integer apiRlt = HttpServletResponse.SC_OK;
        MultipartFile file = chunk.getFile();
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(FileInfoUtils.generatePath(uploadFolder, chunk));
            Files.write(path, bytes);
            chunk.setFileType(file.getContentType());
            if(chunkInfoMapper.insert(chunk) < 0){
                apiRlt = HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE; // 415
            }
        } catch (IOException e) {
            apiRlt = HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE;
        }
        return apiRlt;
    }
}
