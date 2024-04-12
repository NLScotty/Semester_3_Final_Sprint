package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import models.Recommendation;

public class RecommendationDao {
    
    /** 
     * Uploads given reccomendation to the database
     * @param recommendation
     * @return boolean success of the operation 
     */
    public boolean createRecommendation(Recommendation recommendation) {
        String queryString = "INSERT INTO Recommendations (user_id, recommendation_text, date) VALUES (?, ?, ?)";
        boolean inserted = false;
        try{
            Connection con = DatabaseConnection.getCon();
            PreparedStatement preparedStatement = con.prepareStatement(queryString);
            preparedStatement.setInt(1, recommendation.getUserId());
            preparedStatement.setString(2, recommendation.getRecommendationText());
            preparedStatement.setDate(3, recommendation.getDate());
            inserted = preparedStatement.executeUpdate() != 0;
        }catch(Exception e){
            e.printStackTrace();
        }
        return inserted;
    }

    /** Returns all recommendations from the database
     * @return ArrayList<Recommendations> list of recommendations
     */
    public ArrayList<Recommendation> getRecommendation(){
        String queryString = "SELECT * FROM Recommendations";
        ArrayList<Recommendation> recommendation = new ArrayList<>();
        try {
            Connection con = DatabaseConnection.getCon();
            PreparedStatement queryStatement = con.prepareStatement(queryString);
            ResultSet result = queryStatement.executeQuery();
            while(result.next()){
                recommendation.add(new Recommendation(result.getInt("recommendation_id"), result.getInt("user_id"), result.getString("recommendation_text"), result.getDate("date")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return recommendation;
    }

    /** Returns singular reccomendation by id.
     * @param id of reccomendation
     * @return Reccommendation desired reccomendation
     */
    public Recommendation getRecommendationById(int id) { //get recommendation by id from database 
        String queryString = "SELECT * FROM Recommendations WHERE recommendation_id = ?";
        Recommendation recommendation = null;
        try {
            Connection con = DatabaseConnection.getCon();
            PreparedStatement queryStatement = con.prepareStatement(queryString);
            queryStatement.setInt(1, id);
            ResultSet result = queryStatement.executeQuery();
            result.next();
            recommendation = new Recommendation(result.getInt("recommendation_id"), result.getInt("user_id"), result.getString("recommendation_text"), result.getDate("date"));
        }catch(Exception e){
            e.printStackTrace();;
        }
        return recommendation;
    }

    /** Returns all recommendations under user id
     * @param user_id of user
     * @return ArrayList<Recommendations> list of reccomendations of user id
     */
    public ArrayList<Recommendation> getRecommendationByUserId(int user_id){
      String queryString = "SELECT * FROM Recommendations WHERE user_id = ?";
      ArrayList<Recommendation> recommendations = new ArrayList<>();
      try {
          Connection con = DatabaseConnection.getCon();
          PreparedStatement queryStatement = con.prepareStatement(queryString);
          queryStatement.setInt(1, user_id);
          ResultSet result = queryStatement.executeQuery();
          while(result.next()){
              recommendations.add(new Recommendation(result.getInt("recommendation_id"), result.getInt("user_id"), result.getString("recommendation_text"), result.getDate("date")));
          }
      }catch(Exception e){
          e.printStackTrace();
      }
      return recommendations;
    }
    
    /** Updates a recommendation. returns true if operation was sucessful
     * @param recommendation
     * @return boolean success of operation
     */
    public boolean updateRecommendation(Recommendation recommendation) {
        String queryString = "UPDATE Recommendations SET user_id = ?, recommendation_text = ?, date = ? WHERE recommendation_id = ?";
        boolean updated = false;
        try{
            Connection con = DatabaseConnection.getCon();
            PreparedStatement preparedStatement = con.prepareStatement(queryString);
            preparedStatement.setInt(1, recommendation.getUserId());
            preparedStatement.setString(2, recommendation.getRecommendationText());
            preparedStatement.setDate(3, recommendation.getDate());
            preparedStatement.setInt(4, recommendation.getId());
            updated = preparedStatement.executeUpdate() != 0;
        }catch(Exception e){
            e.printStackTrace();
        }
        return updated;
    }

    /** deletes a reccomendation on the database. returns true if operation was sucessful
     * @param id of recommendation
     * @return boolean success of operation
     */
    public boolean deleteRecommendation(int id) { // delete recommendation from the database 
        String queryString = "DELETE FROM Recommendations WHERE recommendation_id = ?";
        boolean deleted = false;
        try{
            Connection con = DatabaseConnection.getCon();
            PreparedStatement preparedStatement = con.prepareStatement(queryString);
            preparedStatement.setInt(1, id);
            deleted = preparedStatement.executeUpdate() != 0;
        }catch(Exception e){
            e.printStackTrace();
        }
        return deleted;
    }
}
