package com.zhangzc.blog.blogadmin.Controller;


import com.zhangzc.blog.blogadmin.Pojo.Vo.AddCategoryReqVO;
import com.zhangzc.blog.blogadmin.Pojo.Vo.FindCategoryPageListReqVO;
import com.zhangzc.blog.blogadmin.Service.AdminCategoryService;
import com.zhangzc.blog.blogcommon.Aop.ApiOperationLog;
import com.zhangzc.blog.blogcommon.Utils.PageResult;
import com.zhangzc.blog.blogcommon.Utils.R;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminCategoryController {
    private final AdminCategoryService adminCategoryService;

    private final AdminCategoryService categoryService;
    @PostMapping("/category/add")
    @ApiOperationLog(description = "添加分类")
    public R addCategory(@RequestBody @Validated AddCategoryReqVO addCategoryReqVO) {
        return adminCategoryService.addCategory(addCategoryReqVO);
    }



    @PostMapping("/category/list")
    @ApiOperationLog(description = "分类分页数据获取")
    public PageResult findCategoryList(@RequestBody @Validated FindCategoryPageListReqVO findCategoryPageListReqVO) {
        return categoryService.findCategoryList(findCategoryPageListReqVO);
    }

    @PostMapping("/category/delete")
    @ApiOperationLog(description = "删除分类分页数据")
    public R deleteCategoryById(@RequestBody Long id){
        return categoryService.deleteCategoryById(id);
    }

    @PostMapping("/category/select/list")
    @ApiOperationLog(description = "分类 Select 下拉列表数据获取")
    public R findCategorySelectList() {
        return categoryService.findCategorySelectList();
    }

}
