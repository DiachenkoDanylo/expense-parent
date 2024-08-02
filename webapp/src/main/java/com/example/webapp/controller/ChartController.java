package com.example.webapp.controller;

import com.example.webapp.model.DateForm;
import com.example.webapp.model.ExpenseDTO;
import com.example.webapp.service.*;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

//import java.sql.Date;
import java.util.List;

/*  expense-parent
    03.07.2024
    @author DiachenkoDanylo
*/
@Controller
@RequestMapping("/chart")
@AllArgsConstructor
public class ChartController {


    private final ExpenseService expenseService;
    private final CategoryService categoryService;
    private final DataChartService dataChartService;
    private final ChartServiceImplLinear chartServiceImplLinear;
    private final ChartServiceImplCircle chartServiceImplCircle;
    private final ChartServiceImplColumn chartServiceImplColumn;

    @GetMapping("/")
    public String chartPage(Model model,
                       @AuthenticationPrincipal OAuth2User oAuth2User) {

        return "/chart/mainChartPage";
    }

    @GetMapping("/linear")
    public String showChartForm(Model model,
                                @AuthenticationPrincipal OAuth2User oAuth2User) {
        model.addAttribute("dateRange", new DateForm(null,null,null));
        model.addAttribute("categories",categoryService.getCategoriesByClientUsername(oAuth2User));
        model.addAttribute("chart","linear");
        return "chart/chartForm";
    }

    @PostMapping("/linear")
    public String linearData(Model model,
                             @AuthenticationPrincipal OAuth2User oAuth2User,
                             @RequestParam(value = "startDate") String startDate,
                             @RequestParam(value = "endDate") String endDate,
                             @RequestParam(value = "category", defaultValue = "0") int category) {
        List<ExpenseDTO> list = dataChartService.getDataByDate(oAuth2User, startDate, endDate, category);
        model.addAttribute("chartData",chartServiceImplLinear.toChartData(list));
        return "/chart/linear";
    }

    @GetMapping("/circle")
    public String showChartFormCircle(Model model,
                                @AuthenticationPrincipal OAuth2User oAuth2User) {
        model.addAttribute("dateRange", new DateForm(null,null,null));
        model.addAttribute("categories",categoryService.getCategoriesByClientUsername(oAuth2User));
        model.addAttribute("chart","circle");
        return "chart/chartForm";
    }

    @PostMapping("/circle")
    public String circleData(Model model,
                             @AuthenticationPrincipal OAuth2User oAuth2User,
                             @RequestParam(value = "startDate") String startDate,
                             @RequestParam(value = "endDate") String endDate,
                             @RequestParam(value = "category",required = false) int category) {
        List<ExpenseDTO> list = dataChartService.getDataByDate(oAuth2User, startDate, endDate, category);
        model.addAttribute("chartData",chartServiceImplCircle.toChartData(list));
        return "/chart/circle";
    }

    @GetMapping("/column")
    public String columnPage(Model model,
                             @AuthenticationPrincipal OAuth2User oAuth2User) {
        model.addAttribute("dateRange", new DateForm(null,null,null));
        model.addAttribute("categories",categoryService.getCategoriesByClientUsername(oAuth2User));
        model.addAttribute("chart","column");
        return "/chart/chartForm";
    }

    @PostMapping("/column")
    public String columnData(Model model,
                             @AuthenticationPrincipal OAuth2User oAuth2User,
                             @RequestParam(value = "startDate") String startDate,
                             @RequestParam(value = "endDate") String endDate,
                             @RequestParam(value = "category",required = false) int category ) {
        List<ExpenseDTO> list = dataChartService.getDataByDate(oAuth2User, startDate, endDate, category);
        model.addAttribute("chartData",chartServiceImplColumn.toChartData(list));
        return "/chart/column";
    }



}
