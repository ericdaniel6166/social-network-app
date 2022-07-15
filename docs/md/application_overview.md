##### Authorization
1. Role Hierarchy:
    - ROLE_ADMIN > ROLE_MODERATOR > ROLE_USER
        
2. Details:     
    - "/auth/**" 
    - "/admin/**": admin api
            - ROLE_ADMIN
    - GET "/comment/**","/forum/**","/post/**": read active comment, forum, post
        - permit all    
    - GET "/comment?search=isActive=bool=false",
    <br>"/forum?search=isActive=bool=false",
    <br>"/post?search=isActive=bool=false":
    <br> read inactive comment, forum, post
        - ROLE_MODERATOR
    - POST, PUT, DELETE "/forum/**" : create/update/delete forum
    - PUT, DELETE "/user/**": update/delete user api
        - ROLE_USER and username match with user   
        - ROLE_MODERATOR
    - PUT, DELETE, "/post/**", "/comment/**": update/delete post,comment
        - ROLE_USER and username match with post/comment created by
        - ROLE_MODERATOR
    - POST "/comment/**","/post/**": create post/comment
        - ROLE_USER
        
    