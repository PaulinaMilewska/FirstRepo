package spring.employees;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import spring.foo.Foo;
import spring.mail.EmailExecutor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller
public class EmpController {
    public List<Emp> list = new ArrayList<>();


    public EmpController() {
        list = new ArrayList<>();
        list.add(new Emp( "Janek", 120000, "Radom", "pkozlowska.pw@gmail.com"));
        list.add(new Emp("Zosia", 9000, "Makowiec", "pkozlowska.pw@gmail.com"));
        list.add(new Emp("Marek", 10000, "Warszawa", "pkozlowska.pw@gmail.com"));
        list.add(new Emp("Krysytna", 13000, "Ryzowice", "pkozlowska.pw@gmail.com"));
    }

    @RequestMapping("/")
    public String indexGet() {
        return "emp/index";
    }

    @RequestMapping(value = "/delete_emp", method = RequestMethod.POST)
    public ModelAndView delete(@RequestParam(value = "emp_id") String emp_id) {
        list.remove(getEmployeesById(Integer.parseInt(emp_id)));
        return new ModelAndView("redirect:/viewemp");
    }

    private Emp getEmployeesById(@RequestParam int id){
        return list.stream().filter(f->f.getId() == id).findFirst().get();
    }

    @RequestMapping(value = "/edit_emp")
    public ModelAndView editing(@RequestParam(value = "emp_id") String emp_id) {
        Emp emp = getEmployeesById(Integer.parseInt(emp_id));
        return new ModelAndView("emp/empform", "emp", emp);
    }

    @RequestMapping(value = "/empform")
    public ModelAndView showForm() {
        return new ModelAndView("emp/empform", "emp", new Emp());
    }

    @RequestMapping(value = "/save_emp")
    public ModelAndView save(@ModelAttribute(value = "emp") Emp emp) {

       if (emp.getId() < 1){
           emp.setId(Emp.index++);
           System.out.printf("Adding the new employee");
           emp.setId(list.size()+1);
           list.add(emp);
       } else {
           Emp empTemp = getEmployeesById(emp.getId());
           empTemp.setName(emp.getName());
           empTemp.setSalary(emp.getSalary());
           empTemp.setDesignation(emp.getDesignation());
       }
        EmailExecutor.sendMail("pkozlowska.pw@gmail.com");
        return new ModelAndView("redirect:/viewemp");
    }

    @RequestMapping("/viewemp")
    public ModelAndView viewemp(Model model) {
        return new ModelAndView("emp/viewemp", "list", list);
    }

    @RequestMapping("/look")
    public ModelAndView look(Model model1) {
        System.out.println("Test");
        return new ModelAndView("emp/show");
    }

    @RequestMapping(value = "/show_text")
    public ModelAndView show(@ModelAttribute(value = "emp") Emp emp) {
        return new ModelAndView("redirect:/look");
    }

}