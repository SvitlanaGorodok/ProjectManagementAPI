package api.projectmanagement.controller;

import api.projectmanagement.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@RequestMapping("/employees")
@RestController
public class EmployeeController {
    private final EmployeeService employeeService;
    @GetMapping("")
    public ModelAndView showAll() {
        ModelAndView model = new ModelAndView("employees/showall");
        model.addObject("employees", employeeService.findAll());
        return model;
    }
}
