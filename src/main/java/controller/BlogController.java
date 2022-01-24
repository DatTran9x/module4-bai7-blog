package controller;

import model.Blog;
import model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import service.IBlogService;
import service.ICategoryService;

import java.time.LocalDate;
import java.util.List;

@Controller
public class BlogController {
    @Autowired
    IBlogService blogService;

    @Autowired
    ICategoryService categoryService;

    @ModelAttribute("category")
    public List<Category> categoryList(){
        return categoryService.findAll();
    }

    @ModelAttribute("blog")
    public Blog blog(){
        return new Blog();
    }


    @GetMapping("/home")
    public ModelAndView homepage(@RequestParam(defaultValue = "0") int page){
        ModelAndView mav = new ModelAndView("homepage");
        mav.addObject("list",blogService.findAll(PageRequest.of(page,2, Sort.by("date"))));
        return mav;
    }

    @GetMapping("/create")
    public String createForm(){
        return "create";
    }

    @PostMapping("/create")
    public String create(Blog blog,int category_id){
        Category category = new Category();
        category.setId(category_id);
        blog.setCategory(category);
        blog.setDate(LocalDate.now());
        blogService.save(blog);
        return "redirect:/home";
    }

    @GetMapping("/details/{id}")
    public ModelAndView details(@PathVariable int id){
        Blog blog = blogService.findById(id);
        ModelAndView mav = new ModelAndView("details");
        mav.addObject("blog",blog);
        return mav;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editForm(@PathVariable int id){
        Blog blog = blogService.findById(id);
        ModelAndView mav = new ModelAndView("edit");
        mav.addObject("blog",blog);
        return mav;
    }

    @GetMapping("/search")
    public ModelAndView search(String search){
        Page<Blog> list = blogService.findByName(search);
        ModelAndView mav = new ModelAndView("homepage");
        mav.addObject("list",list);
        return mav;
    }
}
