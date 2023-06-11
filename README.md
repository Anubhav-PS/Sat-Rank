# Sat-Rank
Display SAT rank  


The functions of this app

->Insert SAT Score of students along with the student details
->Update Details of students
->Delete SAT Score of student
->Fetch all the SAT Scores
->Get SAT Score of student by name


The student's name is the unique indetifier or the primary key


The API Created are as below (The api are created in node.js and deployed to google cloud using the google cloud function tool)
The github link for the backend -

* To add or insert sat score 
  https://us-central1-sat-mark.cloudfunctions.net/addScore
  
* To update sat score
    https://us-central1-sat-mark.cloudfunctions.net/updateScore
    
* To delete sat score
    https://us-central1-sat-mark.cloudfunctions.net/deleteScore
    
* To get the sat score of individual student by name
    https://us-central1-sat-mark.cloudfunctions.net/getScore

* To get the all the sat score of all students
    https://us-central1-sat-mark.cloudfunctions.net/getAllScore
    
* To get the rank of the student by student name
    https://us-central1-sat-mark.cloudfunctions.net/getRank
    
    
Client App Project Structure

![image](https://github.com/Anubhav-PS/Sat-Rank/assets/74093939/b7bac05f-e09d-4c36-825b-071bf559f83c)

Client App Screenshots

The pass or fail of student is caculated in backend after the score is uploaded. In the client app it is displayed as green or red .
Green represents passs
Red respresents fail

![add score](https://github.com/Anubhav-PS/Sat-Rank/assets/74093939/0f6eaf44-8a47-42ef-a4b9-72ef56c164ba)
![operations](https://github.com/Anubhav-PS/Sat-Rank/assets/74093939/39be09de-9a07-4bf1-ba72-6ae572f509e6)
![rank](https://github.com/Anubhav-PS/Sat-Rank/assets/74093939/cd5df527-b68b-4cc2-b54e-52b311fee29f)
![update](https://github.com/Anubhav-PS/Sat-Rank/assets/74093939/186683a9-1b09-426c-8b80-5b02c3f424d0)
![all score](https://github.com/Anubhav-PS/Sat-Rank/assets/74093939/8d594f02-1aa6-4121-bfd2-e2d830cb3e6a)



