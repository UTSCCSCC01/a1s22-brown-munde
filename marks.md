# Assignment 1 Marks  

*Group:* brown-munde  
*Student 1:*  shar1497  
*Student 2:*  omachiyu  

## REST API Correctness  
- 45 test cases passed from .robot file  

Your Mark:  32 / 45  

## CI/CD  
- Number of unique test cases written by the group  
Your Mark:  16 / 16  

## DAGGER   
- Correct Inject Statements  
- Correct initalization in App.java\  n- Only has and uses one context  
- Having proper module classes  
- Proper provides for provideDriver (check exists, annotation and return)  
- Proper provides for provide neo4jDAO (check exists, annotation and return)  
- Proper provides for server (check exists, annotation and return)  

Your Mark:  10 / 16  

---------------------------  
Remark Comments: No mark change -- Same results after running auto-marker several times. After some investigation, I found that most provides methods in Dagger modules didnt have a public scope (the default scope is at the package level https://docs.oracle.com/javase/tutorial/java/javaOO/accesscontrol.html) 

*Total:*  58.0/77

