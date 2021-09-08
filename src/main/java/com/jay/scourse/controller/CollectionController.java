package com.jay.scourse.controller;


import com.jay.scourse.entity.Collection;
import com.jay.scourse.entity.User;
import com.jay.scourse.entity.UserType;
import com.jay.scourse.exception.GlobalException;
import com.jay.scourse.service.ICollectionService;
import com.jay.scourse.vo.CommonResult;
import com.jay.scourse.vo.CommonResultEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jay
 * @since 2021-08-28
 */
@RestController
@RequestMapping("/collection")
public class CollectionController {

    private final ICollectionService collectionService;

    @Autowired
    public CollectionController(ICollectionService collectionService) {
        this.collectionService = collectionService;
    }

    @PostMapping("/add")
    public CommonResult addCollection(User user, @RequestBody Collection collection){
        // 检查用户操作权限
        if(user.getUserType() != UserType.TEACHER){
            throw new GlobalException(CommonResultEnum.UNAUTHORIZED_OPERATION_ERROR);
        }
        //  设置题目集冗余字段 教师id
        collection.setTeacherId(user.getId());
        collectionService.save(collection);
        return CommonResult.success(CommonResultEnum.INSERT_SUCCESS, collection);
    }

    @GetMapping("/list")
    public CommonResult getCollections(User user,
                                       @RequestParam("pageNum") Integer pageNum,
                                       @RequestParam("pageSize") Integer pageSize){
        // 查询该用户发布的题集
        return collectionService.getCollectionList(user, pageNum, pageSize);
    }

    @PostMapping("/update")
    public CommonResult updateCollection(User user, @RequestBody Collection collection){
        // 验证用户操作权限
        if(user.getUserType() != UserType.TEACHER || !user.getId().equals(collection.getTeacherId())){
            throw new GlobalException(CommonResultEnum.UNAUTHORIZED_OPERATION_ERROR);
        }

        collectionService.updateById(collection);
        return CommonResult.success(CommonResultEnum.MODIFICATION_SUCCESS, collection);
    }
}
