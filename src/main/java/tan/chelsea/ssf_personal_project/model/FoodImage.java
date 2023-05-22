package tan.chelsea.ssf_personal_project.model;

// to populate food image data
// technically no need the name as it is not used 
public class FoodImage {

    
    private String name;
    private String path;

    public FoodImage(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public FoodImage() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "FoodImage [name=" + name + ", path=" + path + "]";
    }

    
}
