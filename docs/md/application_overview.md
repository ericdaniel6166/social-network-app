1. Authorization
    - Role Hierarchy:
        - ROLE_ADMIN > ROLE_MODERATOR > ROLE_USER
        
    - Details   
    
    
    - "/auth/**" 
    - GET "/common/**","/comment/**","/forum/**","/post/**"
        - permit all
        
        
    - "/admin/**"
        - ROLE_ADMIN
        
    
    - POST "/forum/**"
        - ROLE_MODERATOR
        
    
    - DELETE "/post/**", "/comment/**"
        - ROLE_USER and username match with post/comment created by
        - ROLE_MODERATOR
        
    
    - POST "/comment/**","/post/**"
        - ROLE_USER
        
    