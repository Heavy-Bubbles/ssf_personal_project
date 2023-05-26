package tan.chelsea.ssf_personal_project.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import tan.chelsea.ssf_personal_project.model.Contact;
import tan.chelsea.ssf_personal_project.model.FoodImage;
import tan.chelsea.ssf_personal_project.repository.ContactRedis;
import tan.chelsea.ssf_personal_project.service.RandomFoodGenerator;

// denotes that this class is a controller 
@Controller
@RequestMapping (path = "/")
public class PekoController {

    // inject service class dependency
    @Autowired
    RandomFoodGenerator randomFoodGeneratorService;

    // inject redis
    @Autowired
    ContactRedis repository;

    @GetMapping
    public String getHomePage(Model model){
        // bind form to model
        model.addAttribute("contact", new Contact());
        // return morpeko.html template
        return "morpeko";
    }

    @GetMapping("/getfood")
    public String generateFood(Model model, HttpServletRequest request){

        // catching inputs other than numbers
        try{

        // fetch input parameter (convert String data from html form with name/id "number" to integer)
        int foodNumber = Integer.parseInt(request.getParameter("number"));

        // print result to terminal
        System.out.println("Input number is: " + foodNumber);

        // check if input is < 1 (try to replace this with validation)
        if (foodNumber < 0){

            // Initialize error message for negative food
            String negativeFood = "Invalid input! You cannot enter negative number of food.";
            // add error message as model. 1st parameter is attribute name, 2nd is object.
            model.addAttribute("negativeFood", negativeFood);
            // return to homepage with error message displayed
            return "morpeko";
           
        } 
        
        if (foodNumber == 0){
            return "nofood";
        }

        if (foodNumber > 10){
            return "toomuchfood";
        }

        // calling service method to populate image objects and create list of image objects
        List <FoodImage> finalFood = randomFoodGeneratorService.generateRandomFood(foodNumber);
        // Print out generated food list in terminal
        System.out.println(finalFood);
        // add generated food list as model
        model.addAttribute("finalFood", finalFood);
        // direct to page with generated results displayed
        return "displayfood";

        } catch (NumberFormatException nfe){

            // Initialize error message
            String errorMessage = "Invalid input! Please enter numbers only.";
            // add error message to the model. 1st parameter is attribute name, 2nd is object.
            model.addAttribute("errorMessage", errorMessage);
            // return to homepage with error message displayed
            return "morpeko";

        }

    }

    // submit donation information
    @PostMapping ("/donate")
    // @Valid for syntactic validation - Validate the data capture from the form by the model
    // BindingResult contains the validation results
    public String submitDonation(@Valid Contact contact, BindingResult bindingResult, Model model){


         // If there are validation errors, return to form and report errors
         if (bindingResult.hasErrors()){
            return "morpeko";
        }

        // check for other errors
        // custom data validation for semantic error (checking if email already exists)
        // can add multiple errors with the same name (err)
        if(!repository.isUniqueEmail(contact.getEmail())){
            ObjectError err = new ObjectError("globalError", "%s is already in use!".formatted(contact.getEmail()));
            bindingResult.addError(err);
            return "morpeko";
        }

        // save contact to redis 
        repository.saveContact(contact, model);
        // success message
        model.addAttribute("successMessage", "Donation saved successfully, with status code: " + HttpStatus.CREATED + ".");
        return "showdonation";
    }

    // show contact by inputting ID
    @GetMapping("/showdonation/{contactID}")
    public String getContactByID(Model model, @PathVariable String contactID){
        Contact contact = new Contact();
        // search for contact by id in redis
        contact = repository.getContactbyId(contactID);
        if (contact == null){
            model.addAttribute("noContact", "Contact not found!");
            return "error";
        }
        model.addAttribute("contact", contact);
        return "showdonation";
    }

    // show all contacts in redis
    @GetMapping(path = "/list")
    public String getAllDonations(Model model){
        List<Contact> donations = repository.getAllContacts();
        System.out.println(donations);
        model.addAttribute("donations", donations);
        return "donations";
    }

}
