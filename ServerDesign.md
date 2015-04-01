# Model #
```

Errand 
- int errandID
- List<Answer> answers;
- blah blah
- setAnswers(List<list> answers)

User
- String id
- double reliability
- int balance;
- int disposablebalance;

Answer
- int id
- int errandID
- String userID
- Date timestamp
- double longitude
- double latitude
- int pointsRewarded

Response
- String status
- String message
```

# Data Access Objects #
```
interface ErrandDAO {

Errand getErrand(int id);
List<Errand> getErrands_byLocation(x, y, range)
List<Errand> getErrands_createdByUser(String user);
List<Errand> getErrands_performedByUser(String user);

}

interface AnswerDAO {

List<Answers> getAnswers_byErrand(int errandID)
saveAnswer(Answer answer) //if id = NULL create 

}


interface UserDAO {
 User getUser(String id)
}
```

# Implementation of DAO #
```
ErrandDAOImpl implements ErrandDAO {

  private Hashtable<Integer id, Errand errand> errands;

}
```

# XMLWriter #
```
writeXML(List<Errand> Errands)
writeXML(Errand errand)
writeXML(User user)
writeXML(Response response)
```