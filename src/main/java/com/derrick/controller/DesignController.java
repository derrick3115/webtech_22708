package com.derrick.controller;

import com.derrick.model.Desing;
import com.derrick.services.DesignService;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.derrick.services.DatabasePDFService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@Controller
public class DesignController {

    @Autowired
    DesignService designService;


    @GetMapping("/home")
    public String homePage(Model model){
        return findPaginated(1,model);
    }

    @GetMapping ("/search-page")
    public String searchMethod(Model model){
        Desing student =new Desing();
       model.addAttribute("search",student);
        return "index";
    }

    @PostMapping("/search_one")
    public String getEmployee(@ModelAttribute("search") Desing student,Model model){
        model.addAttribute("search", designService.findDesign(student.getOrderId()));
        return "searching";
    }


    @GetMapping("/employee_page")
    public String getEmployee(Model model){
        Desing stud = new Desing();
        model.addAttribute("design",stud);
        return "employee";
    }

    @GetMapping("/registration")
    public String registerStudentPage(Model model){
        Desing stud = new Desing();
        model.addAttribute("design", stud);
        return "registration-page";
    }
    @GetMapping("/search")
    public String searchOrder(Model model){
        model.addAttribute("search",new Desing());
        return "findOne";
    }

    @PostMapping("/findOrder")
    public String getOrder(@ModelAttribute("search") Desing desing, Model model){
        Desing desing1=designService.findDesign(desing.getOrderId());
        if (desing1!=null){
            model.addAttribute("search",desing1);
            return "findOne";
        }else {
            model.addAttribute("error","order wa not found");
            return "findOne";
        }

    }


    @PostMapping("/emp")
    public String registerEmp(@ModelAttribute("design") Desing theDesign) throws ParseException {

        Desing savedStudent = designService.saveDesign(theDesign);
        if(savedStudent != null){
            return "redirect:/employee_page?success";
        }
        return "redirect:/employee_page?error";
    }


    @PostMapping("/register")
    public String registerDesignInDb(@ModelAttribute("design") Desing theDesign) throws ParseException {

        Desing savedDesign = designService.saveDesign(theDesign);
        if(savedDesign != null){
            return "redirect:/registration?success";
        }
        return "redirect:/registration?error";
    }

    @GetMapping("/home/edit/{designID}")
    public String editDesign(@PathVariable String designID, Model model){
        Long id=Long.parseLong(designID);
        Desing st=new Desing();
     model.addAttribute("design", designService.findDesign(id));
     return "edit-design";
    }

    @PostMapping("/home/{designID}")
    public String updateDesign(@PathVariable String designID, @ModelAttribute("design") Desing desing, Model model){
        Desing exitingDesign= designService.findDesign(desing.getOrderId());
        exitingDesign.setOrderId(desing.getOrderId());
        exitingDesign.setfName(desing.getfName());
        exitingDesign.setlName(desing.getlName());
        exitingDesign.setYard(desing.getYard());
        exitingDesign.setOrderDate(desing.getOrderDate());
        exitingDesign.setPicture(desing.getPicture());
        designService.updateDesign(exitingDesign);
        return "redirect:/home";
    }
    @GetMapping ("/home/{designID}")
    public String deleteDesign(@PathVariable String designID){
        Long id=Long.parseLong(designID);

        designService.deleteDesign(id);
   return "redirect:/home";
    }

    @GetMapping("/exportCsv")
    public void exportCSV(HttpServletResponse response)
            throws Exception {

        //set file name and content type
        String filename = "Volunteer-data.csv";

        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + filename + "\"");
        //create a csv writer
        StatefulBeanToCsv<Desing> writer = new StatefulBeanToCsvBuilder<Desing>(response.getWriter())
                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).withSeparator(CSVWriter.DEFAULT_SEPARATOR).withOrderedResults(false)
                .build();
        //write all employees data to csv file
        writer.write(designService.getAllDesigns());

    }

    @GetMapping(value = "/exportPdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> volunteerReport()  throws IOException {
        List<Desing> volunteers = (List<Desing>) designService.getAllDesigns();

        ByteArrayInputStream bis = DatabasePDFService.employeePDFReport(volunteers);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=VolunteerReport.pdf");

        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }


    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,Model model){
        int pageSize=5;
        Page<Desing> page=designService.pagenateStudent(pageNo,pageSize);
        List<Desing> studentList=page.getContent();
        model.addAttribute("currentPage",pageNo);
        model.addAttribute("totalPage",page.getTotalPages());
        model.addAttribute("totalItems",page.getTotalElements());
        model.addAttribute("listStudents",studentList);
        return "home-page";
    }

}
