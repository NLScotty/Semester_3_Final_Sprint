package models;

import java.sql.Date;

public class Recommendation {
    private int id;
    private int userId;
    private String recommendationText;
    private Date date;

    public Recommendation(int id, int userId, String recommendationText, Date date){
        this.id = id;
        this.userId = userId;
        this.recommendationText = recommendationText;
        this.date = date;
    }

    public int getId() {
        return id;
    }
    public int getUserId() {
        return userId;
    }
    public String getRecommendationText() {
        return recommendationText;
    }
    public Date getDate() {
        return date;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public void setRecommendationText(String recommendationText) {
        this.recommendationText = recommendationText;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Reccomendation_id: "+id+", User_Id: "+userId+", Reccomendation_Text: "+recommendationText+", Date: "+date;
    }

}
