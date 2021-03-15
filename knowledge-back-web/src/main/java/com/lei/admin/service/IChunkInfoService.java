package com.lei.admin.service;

import com.lei.admin.entity.ChunkInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lei.admin.entity.Tinymce;
import com.lei.admin.vo.ChunkResult;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author GuanyuLei
 * @since 2021-03-06
 */
public interface IChunkInfoService extends IService<ChunkInfo> {
    /**
     * 校验当前文件
     *
     * @param chunkInfo
     * @param response
     * @return 秒传？续传？新传？
     */
    ChunkResult checkChunkState(ChunkInfo chunkInfo, HttpServletResponse response);

    /**
     * 上传文件
     *
     * @param chunk
     * @return
     */
    Integer uploadFile(ChunkInfo chunk);
}
