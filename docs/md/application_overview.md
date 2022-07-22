##### [Back to README](/README.md)

##### Authorization
1. Role Hierarchy:
    - ROLE_ROOT_ADMIN > ROLE_ADMIN > ROLE_MODERATOR > ROLE_USER
        
##### Social Network App REST API

1. API
    - Account API
        - /account/**
            - has authority ROLE_USER at least
        - PUT /account/updateRole: Update role
            - has authority ROLE_ADMIN at least
            - user can't set role to himself 
            - user can't set role to account has already had that role
            - ROLE_ROOT_ADMIN can set role to any account except himself
            - ROLE_ADMIN can set role less or equal ROLE_MODERATOR to less or equal ROLE_MODERATOR account
        - POST /account/profile/{username}: Create or update user profile
    - Admin API
        - /admin/**
            - has authority ROLE_ADMIN at least
        - GET /admin/masterErrorMessage: Get all master error message
        - GET /admin/masterMessage: Get all master message
    - Auth API
        - /auth/**
            - permit all
        - POST /auth/signIn: sign in
        - POST /auth/signUp: sign up
        - GET /auth/verifyAccount/{token}: Verify account
    - Comment API
        - GET /comment/**:
            - permit all
        - POST /comment/**:
            - has authority ROLE_USER at least
        - PUT, DELETE /comment/**:
            - has authority ROLE_USER and username match with comment created by, OR has authority ROLE_MODERATOR at least
        - GET /comment: Get all comment
        - POST /comment: Create comment
        - PUT /comment/{id}: Update comment by id (Updating)
        - DELETE /comment/{id}: Delete comment by id
        - GET /comment/{id}: Get comment by id
        - GET /comment/createdBy/{username}: Get all comment created by username
        - GET /comment/post/{id}: Get all comment by post id
    - Post API
        - GET /post/**:
            - permit all
        - POST /post/**:
            - has authority ROLE_USER at least
        - PUT, DELETE /post/**:
            - has authority ROLE_USER and username match with post created by, OR has authority ROLE_MODERATOR at least
        - GET /post: Get all post
        - POST /post: Create post
        - PUT /post/{id}: Update post by id (Updating)
        - DELETE /post/{id}: Delete post by id
        - GET /post/{id}: Get post by id
        - GET /post/createdBy/{username}: Get all post created by username
        - GET /post/forum/{id}: Get all post by forum id
    - Forum API
        - GET /forum/**:
            - permit all
        - POST, PUT, DELETE /forum/**:
            - has authority ROLE_MODERATOR at least
        - GET /forum: Get all forum
        - POST /forum: Create forum
        - PUT /forum/{id}: Update forum by id (Updating)
        - DELETE /forum/{id}: Delete forum by id
        - GET /forum/{id}: Get forum by id
        - GET /forum/createdBy/{username}: Get all forum created by username (Updating)
2. Notice:
    - Search inactive resource (isActive is false)
        - has authority ROLE_MODERATOR at least
        - ex: /post?search=isActive=bool=false;name!=*red*
            - search posts which are inactive and post name dose not contain "red"
3. Complex search (using rsql-parser)
    - ex: /post?search=isActive=bool=false;name!=*red*
        - search posts which are: isActive is false AND post name dose not contain "red"
    - Syntax
        - Comparison Operator
            - =bool= : EQUAL (boolean)
            - ==: EQUAL/LIKE (string), EQUAL (number)
            - !=: NOT_EQUAL
            - =gt= Or >: GREATER_THAN 
            - =ge= Or >=: GREATER_THAN_OR_EQUAL
            - =lt= Or <: LESS_THAN 
            - =le= Or <=: LESS_THAN_OR_EQUAL
            - =in=: IN
            - =out=: OUT
        - Logical Operator
            - ; : AND
            - , : OR
        - Other:
            - *: %
                - ex: name==*red -> name LIKE '%red'
        
             
            
        
        
    