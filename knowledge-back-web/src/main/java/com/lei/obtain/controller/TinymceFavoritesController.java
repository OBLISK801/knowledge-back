package com.lei.obtain.controller;


import com.lei.obtain.entity.TinymceFavorites;
import com.lei.obtain.service.ITinymceFavoritesService;
import com.lei.obtain.vo.FavoriteArticleVO;
import com.lei.obtain.vo.TinymceFavoritesVO;
import com.lei.response.ResponseModel;
import com.lei.utils.PageUtils;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author GuanyuLei
 * @since 2021-04-04
 */
@RestController
@RequestMapping("/obtain/favorites")
public class TinymceFavoritesController {

    @Autowired
    private ITinymceFavoritesService tinymceFavoritesService;

    @PostMapping("/favoriteArticle")
    @ApiOperation("收藏文章")
    public ResponseModel favoriteArticle(@RequestBody TinymceFavoritesVO favoritesVO) {
        tinymceFavoritesService.favoriteArticle(favoritesVO);
        return ResponseModel.success();
    }

    @PostMapping("/unFavoriteArticle")
    @ApiOperation("取消收藏文章")
    public ResponseModel unFavoriteArticle(@RequestBody TinymceFavoritesVO favoritesVO) {
        tinymceFavoritesService.unFavoriteArticle(favoritesVO);
        return ResponseModel.success();
    }

    @PostMapping("/isFavoriteArticle")
    @ApiOperation("判断是否收藏文章")
    public ResponseModel<Boolean> isFavoriteArticle(@RequestBody TinymceFavoritesVO favoritesVO) {
        TinymceFavorites tinymceFavorites = tinymceFavoritesService.isFavoriteArticle(favoritesVO);
        if (tinymceFavorites != null) {
            return ResponseModel.success(true);
        } else {
            return ResponseModel.success(false);
        }
    }

    @GetMapping("/listAll")
    @ApiOperation("获取我的收藏")
    public ResponseModel<PageUtils<FavoriteArticleVO>> listAll(@RequestParam("pageNum") Integer pageNum,
                                                               @RequestParam("pageSize") Integer pageSize,
                                                               @RequestParam("username") String username) {
        PageUtils<FavoriteArticleVO> result = tinymceFavoritesService.listAll(pageNum, pageSize, username);
        return ResponseModel.success(result);
    }

    @GetMapping("/getNum")
    public ResponseModel<Integer> getNum(@RequestParam("tinymceId")Integer tinymceId) {
        Integer num = tinymceFavoritesService.getNum(tinymceId);
        if (num == null) {
            return ResponseModel.success(0);
        }
        return ResponseModel.success(num);
    }
}

