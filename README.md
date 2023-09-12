# Reddit-Clone

 - Frontend - Angular / Backend - Java
    
    This project is currently Work in Progress. As name suggests, I try to recreate my own version of Reddit with multiple features.
    My aim is to build stable and solid back-end first, then I'll move to the frontend.
    
    Estimated 90% done of the Backend-part
    
    Features:
     - Spring Boot app, REST API, JPA, Hibernate, Spring Security, Mailtrap, Thymeleaf implementation
     - Junit / Mockito [unit/integration testing]
     - JWT based authentication / authorization
     - User can't bruteforce login details. After 10 failed attempts user / bot gets blocked from the server based on his IP address for 24 hours
     - Post / Comment System (create, edit, up/downvote, delete) [Post -> Comment @OneToMany]
     - Fully implemented Roles / Privileges => Admin can grant/strip roles from users (such as Moderator) => protected endpoint from JWT
     only accessible with ROLE_ADMIN
     - ROLES and Privileges are automatically set-up when starting an application
     - Posts / Comments can be edited only by their owners / moderators (ROLE_MODERATOR) / admins (ROLE_ADMIN)
     - Posts / Comments can be deleted only by their owners / admins (ROLE_ADMIN)
     - User registration => data validation (username, special requirements for password (lower case, upper case, digit), valid email address)
     - User has automatically granted ROLE_USER after successfull registration
     - User can have 2FA enabled -> if so, QR code is generated and sent to him, he scans it and sends back numeric code as a response
     - Fully implemented email service => Emails are HTML format and are automatically generated based on user's data in the database
     - Emails serve as way to verify email adress to gain full access to application + as a backend in case of lost password
     - If user lost his password he sends request -> back on email is sent a randomly generated numeric code which user need to provide before
     reseting his password
     - User can also upload custom profile picture which is compressed on the server and then saved into file system
     - filenames of profile pictures are randomly generated and everytime check if the same name doesn't exists otherwise they would be overwritten
     - User has @ManyToMany with roles / privileges
     - Almost all of the endpoints automatically extract user based on the accessToken and dont rely on Dto's that heavily
     - All endpoints need to be authenticated apart from AuthController API (for registering, login, reseting password etc.)
     - When user fetches posts + comments => profile pictures of their owners are taken from FileSystem, converted into byte Array -> base 64
     and sent within the response so they can be encoded back to image in the frontend
     - With the posts / comments comes also reaction field => defined as -1, 0 or 1 to describe how user reacted to specific post/ comment 
     (downvote, didnt react, upvote)
     - Also with every post / comment are sent two Sets of Users who upvoted / downvoted specific post / comment
     - User's have karma based on their entire performance from interactions (based on upvotes/downvotes from their posts / comments)
     - Also every Post / Comment has reputation (the same concept as above)
     - User's can't spam downvotes / upvotes. They can vote only once

    What's missing to fully complete the BACKEND part ?
     As for right now, these are things that miss and I would like to implement them:
      - Sub-Reddits(Forums)
      - Nested comment system (this will be a big one)
      - 100% coverage from unit / integration tests

    After finishing this I'll move to the FRONTEND part and will connect all of the features to create fully working Full-Stack application.
