package com.lei.admin.converter;

import com.lei.admin.entity.Classification;
import com.lei.admin.vo.ClassificationNodeVO;
import com.lei.system.entity.Menu;
import com.lei.system.vo.MenuNodeVO;
import com.lei.system.vo.MenuVO;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author LEI
 * @Date 2021/2/25 23:05
 * @Description TODO
 */
public class ClassificationConverter {
    public static List<ClassificationNodeVO> converterToALLClassificationNodeVO(List<Classification> classifications) {
        List<ClassificationNodeVO> classificationNodeVOS = new ArrayList<>();
        if (!CollectionUtils.isEmpty(classifications)) {
            for (Classification classification : classifications) {
                ClassificationNodeVO classificationNodeVO = new ClassificationNodeVO();
                BeanUtils.copyProperties(classification, classificationNodeVO);
                classificationNodeVOS.add(classificationNodeVO);
            }
        }
        return classificationNodeVOS;
    }

    public static ClassificationNodeVO converterToClassificationNodeVO(Classification classification) {
        ClassificationNodeVO classificationNodeVO = new ClassificationNodeVO();
        if (classification != null) {
            BeanUtils.copyProperties(classification, classificationNodeVO);
        }
        return classificationNodeVO;
    }
}
