package com.lei.admin.utils;

import com.lei.admin.entity.ChunkInfo;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.*;

/**
 * @Author LEI
 * @Date 2021/3/6 14:10
 * @Description TODO
 */
public class FileInfoUtils {

    public static boolean fileExists(String file) {
        boolean fileExists;
        Path path = Paths.get(file);
        fileExists = Files.exists(path, LinkOption.NOFOLLOW_LINKS);
        return fileExists;
    }

    public static String generatePath(String uploadFolder, ChunkInfo chunk) {
        StringBuilder sb = new StringBuilder();
        sb.append(uploadFolder).append("/").append(chunk.getIdentifier());
        //判断uploadFolder/identifier 路径是否存在，不存在则创建
        if (!Files.isWritable(Paths.get(sb.toString()))) {
            try {
                Files.createDirectories(Paths.get(sb.toString()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.append("/")
                .append(chunk.getFilename())
                .append("-")
                .append(chunk.getChunkNumber()).toString();
    }


    public static Integer merge(String file, String folder, String filename) {
        //默认合并成功
        Integer rlt = HttpServletResponse.SC_OK;
        try {
            //先判断文件是否存在
            if (fileExists(file)) {
                //文件已存在
                rlt = HttpServletResponse.SC_MULTIPLE_CHOICES;
            } else {
                //不存在的话，进行合并
                Files.createFile(Paths.get(file));
                Files.list(Paths.get(folder))
                        .filter(path -> !path.getFileName().toString().equals(filename))
                        .sorted((o1, o2) -> {
                            String p1 = o1.getFileName().toString();
                            String p2 = o2.getFileName().toString();
                            int i1 = p1.lastIndexOf("-");
                            int i2 = p2.lastIndexOf("-");
                            return Integer.valueOf(p2.substring(i2)).compareTo(Integer.valueOf(p1.substring(i1)));
                        })
                        .forEach(path -> {
                            try {
                                //以追加的形式写入文件
                                Files.write(Paths.get(file), Files.readAllBytes(path), StandardOpenOption.APPEND);
                                //合并后删除该块
                                Files.delete(path);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });

            }
        } catch (IOException e) {
            e.printStackTrace();
            //合并失败
            rlt = HttpServletResponse.SC_BAD_REQUEST;
        }
        return rlt;
    }

}
