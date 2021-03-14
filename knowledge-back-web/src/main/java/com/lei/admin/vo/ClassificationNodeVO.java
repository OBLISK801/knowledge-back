package com.lei.admin.vo;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author LEI
 * @Date 2021/2/25 22:51
 * @Description TODO
 */
@Data
public class ClassificationNodeVO {
    private Integer id;

    private String classificationName;

    private String classificationIntroduction;

    private Integer parentId;

    List<ClassificationNodeVO> children = new ArrayList<>();
}
