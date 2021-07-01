package com.baizhi.mysqlredis.collctor;

import com.baizhi.mysqlredis.entity.Food;
import com.baizhi.mysqlredis.es.ProductRepository;
import com.baizhi.mysqlredis.service.WjbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("food")
public class FoodAction {

    @Resource
    private ProductRepository productRepository;

    @Autowired
    WjbService wjbService;
    ArrayList<Food> foodss = new ArrayList<>();
    @RequestMapping("all")
    public String queryAllw(HttpSession session){
        List<Food> list = wjbService.queryAll();
        session.setAttribute("list",list);
        return "redirect:/list.jsp";
    }
    @RequestMapping("alles")
    public String queryAlles(HttpSession session){
        List<Food> list = wjbService.queryAll();
        session.setAttribute("list",list);
        return "redirect:/index.jsp";
    }
    @RequestMapping("add")
    public String addApple(MultipartFile photos, HttpServletRequest request, Food food) throws IOException {
        String realPath = request.getRealPath("/upload");
        String filename = photos.getOriginalFilename();
        photos.transferTo(new File(realPath,filename));//文件上传
        food.setFoodSrc("upload/"+filename);
        wjbService.add(food);
        return "redirect:/food/all";
    }
    @RequestMapping("delete")
    public String addApple(Integer id,String foodSrc,HttpServletRequest request) throws IOException {
        String realPath = request.getSession().getServletContext().getRealPath("/upload");
        String[] split = foodSrc.split("/");
        new File(realPath+"\\"+"\\"+split[1]).delete();
        wjbService.delete(id);
        return "redirect:/food/all";
    }

    @RequestMapping("selectById")
    public String update(Integer id,HttpSession session) throws IOException {
        Food food = wjbService.selectById(id);
        session.setAttribute("list",food);
        return "update";
    }
    @RequestMapping("selectByIdEs")
    public String selectByIdEs(Integer id,HttpSession session) throws IOException {
        Food food = wjbService.selectById(id);
        session.setAttribute("list",food);
        return "detail";
    }
    @RequestMapping("update")
    public String updates(MultipartFile photos, HttpServletRequest request, Food food) throws IOException {
        String realPath = request.getRealPath("/upload");
        String filename = photos.getOriginalFilename();
        photos.transferTo(new File(realPath,filename));//文件上传
        food.setFoodSrc("upload/"+filename);
        wjbService.update(food);
        //es
        productRepository.save(food);
        return "redirect:/food/all";
    }
    @RequestMapping("deleteAllES")
    public String deleteEs() throws IOException {
        //根据所有索引
        productRepository.deleteAll();
        return null;
    }
    @RequestMapping("ques")
    public String queryEs() throws IOException {
        List<Food> list = wjbService.queryAll();
        for (Food food : list) {
            foodss.add(food);
            productRepository.saveAll(foodss);
        }
        return null;
    }

    @RequestMapping("pageAllES")
    public String pageAllES(HttpSession session,String message){
        List<Food> list = wjbService.queryEs(message);
        session.setAttribute("list",list);
        return "redirect:/index.jsp";
    }



}
