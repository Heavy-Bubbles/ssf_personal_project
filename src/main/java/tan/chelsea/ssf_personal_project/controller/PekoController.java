package tan.chelsea.ssf_personal_project.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import tan.chelsea.ssf_personal_project.model.FoodImage;
import tan.chelsea.ssf_personal_project.service.RandomFoodGenerator;

// denotes that this class is a controller 
@Controller
@RequestMapping (path = "/")
public class PekoController {

    // inject service class dependency
    @Autowired
    RandomFoodGenerator randomFoodGeneratorService;

    @GetMapping
    public String getHomePage(){
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

    
}
