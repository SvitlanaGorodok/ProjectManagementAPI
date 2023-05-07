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

import java.util.List;
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
        model.addObject("employees", employeeService.findAll());
        return model;
    }

    @GetMapping("/create")
    public ModelAndView createForm() {
        ModelAndView model = new ModelAndView("employees/create");
        model.addObject("positions", positionService.findAll());
        model.addObject("levels", levelService.findAll());
        model.addObject("emails", List.of("lukrainka@gmail.com", "test@gmail.com"));
        return model;
    }

    @PostMapping("/create")
    public RedirectView create(@Validated @ModelAttribute("employeeDto") EmployeeDto employeeDto) {
        employeeService.save(employeeDto);
        return new RedirectView("/employees");
    }

    @GetMapping("/delete/{id}")
    public RedirectView delete(@PathVariable("id") UUID id) {
        employeeService.deleteById(id);
        return new RedirectView("/employees");
    }

    @GetMapping("/update/{id}")
    public ModelAndView updateForm(@PathVariable("id") UUID id) {
        ModelAndView model = new ModelAndView("employees/update");
        model.addObject("employee", employeeService.findById(id));
        model.addObject("positions", positionService.findAll());
        model.addObject("levels", levelService.findAll());
        return model;
    }

    @PostMapping("/update")
    public RedirectView update(@Validated @ModelAttribute("employeeDto") EmployeeDto employeeDto) {
        employeeService.save(employeeDto);
        return new RedirectView("/employees");
    }

}
