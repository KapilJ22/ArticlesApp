# ArticleApp - REST APIs for creating articles, adding comments to them and liking comments  
# ToDo - Front-End, liking articles and few other misc items  
 
This app is developed using Spring Boot, HSQLDB(In-Memory Database), JPA and Hibernate, Oauth2

• Users interact with Article services to create new Articles.   
• Each Article contains a set of Comments that are provided during Article creation.  
• Voting is restricted on a single Comment.  
• Participants can cast any number of Likes.  


# Steps to Setup
1. Clone the application - git clone https://github.com/drone-cloud/ArticleApp.git
2. Run the app using maven without packaging -
    mvn spring-boot:run   
The app will start running at http://localhost:8080.  
Swagger doumentation: http://localhost:8080/swagger-ui/index.html

# Explore Rest APIs


### Try it out with no authentication
http://localhost:8080/v2/Articles  
Swagger: http://localhost:8080/api-docs?group=v2


### Try it out with basic authentication
http://localhost:8080/v3/Articles 
( with a sample username=mickey,password=cheese)  
Swagger:http://localhost:8080/api-docs?group=v3


### Try it out Oauth2 APIs:
http://localhost:8080/oauth2/v3/Articles 


• The client is already registered with the authorization server, generate a token for the user mickey by running the following curl command:  
    curl -u quickpolliOSClient:top_secret -X POST http://localhost:8080/oauth/token -H "Accept:application/json" -d "username=mickey&password=cheese&grant_type=password"  
    The command creates a POST request to the /oauth/token endpoint and passes the client credentials for HTTP Basic authentication. 

• The POST request payload contains the resources user’s username and password and the requested grant type. On receiving the request, the authorization server will generate the following response with an access token:  
    {"access_token":"77ed953e-b3b6-4ea1-820e-2e9acc702293","token_type":"bearer",
    "expires_in":42874,"scope":"read write"}

• The following cURL command requests the /oauth2/v3/Articles resource using the token that we
obtained in the previous step:  
    curl http://localhost:8080/oauth2/v3/Articles -H "Authorization: Bearer 77ed953e-b3b6-4ea1-820e-2e9acc702293"
