package com.geekaca.mall.controller.admin;

import com.geekaca.mall.controller.param.GoodsParam;
import com.geekaca.mall.domain.GoodsInfo;
import com.geekaca.mall.service.HomePageService;
import com.geekaca.mall.utils.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@RestController
@RequestMapping("/manage-api/v1")
public class CreateItemController {

    @Autowired
    private HomePageService homePageService;

    @PostMapping("/upload/file")
    @ResponseBody
    public Result upload(HttpServletRequest httpServletRequest, @RequestParam("file") MultipartFile file) throws URISyntaxException {
        //获取文件名
        String fileName = file.getOriginalFilename();
        //"."最后一次出现的位置
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //生成文件名称通用方法
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        Random r = new Random();
        StringBuilder tempName = new StringBuilder();
        tempName.append(sdf.format(new Date())).append(r.nextInt(100)).append(suffixName);
        String newFileName = tempName.toString();
        File fileDirectory = new File(Code.UPLOAD_PATH);
        //创建文件
        File destFile = new File(Code.UPLOAD_PATH + newFileName);
        try {
            if (!fileDirectory.exists()) {
                if (!fileDirectory.mkdir()) {
                    throw new IOException("文件夹创建失败，路径为：" + fileDirectory);
                }
            }
            file.transferTo(destFile);//把上传的文件保存到指定位置
            //上传图片成功后返回访问这个图片的url地址，前端收到后继续进行文件的新增
            //获取服务器IP(http://localhost:8080 + /upload)
            Object url = MyBlogUtils.getHost(new URI(httpServletRequest.getRequestURL() + "")) + "/upload/" + newFileName;
            return ResultGenerator.genSuccessResult(url);
        } catch (IOException e) {
            e.printStackTrace();
            return ResultGenerator.genFailResult("文件上传失败");
        }
    }

    @ApiOperation(value = "商品列表", notes = "查询所有商品")
    @RequestMapping(value = "/goods/list", method = RequestMethod.GET)
    public Result list(@RequestParam(required = false) @ApiParam(value = "页码") Integer pageNumber,
                       @RequestParam(required = false) @ApiParam(value = "每页条数") Integer pageSize,
                       @RequestParam(required = false) @ApiParam(value = "商品名称") String goodsName,
                       @RequestParam(required = false) @ApiParam(value = "上架状态 0-上架 1-下架") Integer goodsSellStatus) {
        if (pageNumber == null || pageNumber < 0){
            pageNumber = 1;
        }
        if (pageSize == null || pageSize < 0){
            pageSize = 10;
        }
        GoodsParam goodsParam = new GoodsParam(goodsName, goodsSellStatus, pageNumber, pageSize);
        PageResult pageResult = homePageService.getGoodsList(goodsParam);
        if (pageResult != null){
            return ResultGenerator.genSuccessResult(pageResult);
        }else{
            return ResultGenerator.genFailResult("没有找到相关商品");
        }
    }

    @PostMapping("/goods")
    public Result add(@RequestBody GoodsInfo goodsInfo){
        int added = homePageService.addGoods(goodsInfo);
        if (added > 0){
            return ResultGenerator.genSuccessResult("增加成功");
        }else{
            return ResultGenerator.genFailResult("增加失败");
        }
    }
}
