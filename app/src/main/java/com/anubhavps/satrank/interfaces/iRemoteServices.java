package com.anubhavps.satrank.interfaces;

import com.anubhavps.satrank.models.Marks;

public interface iRemoteServices {

    //takes the data to be inserted (as a object)
    void insertScore(Marks marks,iResponseResult responseResult);


    //takes the student name and the new data to be updated (as a object)
    void updateScore(Marks marks,iResponseResult responseResult);


    //takes the student name to delete the score
    void deleteScore(String name,iResponseResult responseResult);


    //takes no input , fetches all scores as array list
    void getAllScores(iResponseResult responseResult,iJsonResult jsonResult);


    //takes the student name who's data needs to be fetched
    void getScore(String name,iResponseResult responseResult,iJsonResult jsonResult);

    //gets the rank of the student with the student name
    void getRank(String name,String score,iResponseResult responseResult,iJsonRankResult jsonRankResult);


}
