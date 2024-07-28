package response;

public class ProductResponse {
    public String title;
    public String description;
    public String price;
    public String condition;
    public String time;
    public String userName;

    public ProductResponse(String title ,String description, String price, String condition, String time, String userName) {
        this.title= title;
        this.description = description;
        this.price = price;
        this.condition = condition;
        this.time = time;
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "ProductResponse{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", price='" + price + '\'' +
                ", condition='" + condition + '\'' +
                ", time='" + time + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
