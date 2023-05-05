package api.projectmanagement.controller;

import api.projectmanagement.model.dto.EmployeeDto;
import api.projectmanagement.service.EmployeeLevelService;
import api.projectmanagement.service.EmployeeService;
import api.projectmanagement.service.PositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/employees")
@Controller
public class EmployeeController {
    private final EmployeeService employeeService;
    private final PositionService positionService;
    private final EmployeeLevelService levelService;
    @GetMapping("")
    public ModelAndView showAll() {
        ModelAndView model = new ModelAndView("employees/showall");
        model.addObject("employees", employeeService.findAllWithPositionDetails());
        return model;
    }

    @GetMapping("/create")
    public ModelAndView createForm(@RequestParam(name = "msg", required = false, defaultValue = "") String msg) {
        ModelAndView model = new ModelAndView("employees/create");
        model.addObject("msg", msg);
        model.addObject("positions", positionService.findAll());
        model.addObject("levels", levelService.findAll());
        return model;
    }

    @PostMapping("/create")
    public RedirectView create(@Validated @ModelAttribute("employeeDto") EmployeeDto employeeDto) {
        employeeService.save(employeeDto);
        RedirectView redirect = new RedirectView("/employees/create");
        redirect.addStaticAttribute("msg", "Employee successfully saved!");
        return redirect;
    }

    @GetMapping("/delete/{id}")
    public String deleteUserByIdForm(@PathVariable("id") UUID id) {
        employeeService.deleteById(id);
        return "redirect:/employees";
    }

}
