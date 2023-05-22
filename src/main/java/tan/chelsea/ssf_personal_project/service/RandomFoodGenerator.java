package tan.chelsea.ssf_personal_project.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import tan.chelsea.ssf_personal_project.model.FoodImage;

// denotes that this class is a service
@Service
public class RandomFoodGenerator {

    // method to generate number of food pictures based on user input
    public List<FoodImage> generateRandomFood (int foodNumber){

        // retrieve food images from food file and put them to ArrayList of FoodImages
        File imagesFile = new File("src/main/resources/static/images/food");
        File[] foodImageList = imagesFile.listFiles();

        List<FoodImage> totalFood = new ArrayList<>();

        for (File image: foodImageList){

            String imageName = image.getName();
            totalFood.add(new FoodImage(imageName.replaceAll(".jpg", "").trim(), "/images/food/"+imageName));

        }

        // generate different types of food based on the user input for food number
        List<FoodImage> generatedFood = new ArrayList<>();
        while (foodNumber != generatedFood.size()){
            Random random = new Random();
            int randomIndex = random.nextInt(totalFood.size());

            FoodImage generated = totalFood.get(randomIndex);

            if (!generatedFood.contains(generated)){
                generatedFood.add(generated);
            }     
        }
        return generatedFood;

        
    }
    
}
