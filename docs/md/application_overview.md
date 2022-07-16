##### [Back to index](/README.md)

##### Authorization
1. Role Hierarchy:
    - ROLE_ADMIN > ROLE_MODERATOR > ROLE_USER
        
2. Details:     
    - "/admin/**": admin api
        - ROLE_ADMIN
    - "/account/updateRole/**": update role
        - user can't set role to himself
        - ROLE_ROOT_ADMIN can: 
            - set any roles to any accounts
        - ROLE_ADMIN can:
            - set role less or equal ROLE_MODERATOR to less or equal ROLE_MODERATOR account
        - less or equal ROLE_MODERATOR:
            - Forbidden
    - GET "/comment?search=isActive=bool=false",
    "/forum?search=isActive=bool=false",
    "/post?search=isActive=bool=false":
    read inactive comment, forum, post
    - POST, PUT, DELETE "/forum/**" : create/update/delete forum
        - ROLE_MODERATOR
    - PUT, DELETE "/user/**": update/delete user api
        - ROLE_USER and username match with user   
        - ROLE_MODERATOR
    - PUT, DELETE, "/post/**", "/comment/**": update/delete post,comment
        - ROLE_USER and username match with post/comment created by
        - ROLE_MODERATOR
    - POST "/comment/**","/post/**": create post/comment
        - ROLE_USER
    - "/auth/**": sign up, verify account, sign in
    - GET "/comment/**","/forum/**","/post/**": read active comment, forum, post
        - permit all    
            
        
        
    