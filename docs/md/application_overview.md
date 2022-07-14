- Authorization
    - Role Hierarchy:
        - ROLE_ADMIN > ROLE_MODERATOR > ROLE_USER
    - "/auth/**" 
    - GET "/common/**","/comment/**","/forum/**","/post/**"
        - permit all
    - "/admin/**"
        - ROLE_ADMIN
    - POST "/forum/**"
        - ROLE_MODERATOR
    - POST "/**"
        - ROLE_USER