BEGIN TRANSACTION

    if not exists(select 1 from app_user where username = 'rootAdmin')
insert app_user(created_by, last_modified_by, created_date, last_modified_date, email, is_active, password, username, app_role_id)
values ('social_network_app_v1', 'social_network_app_v1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'rootAdmin@socialnetworkapp.com', 1,'$2a$10$y2Wsd4GRxdWPWBMfN8aOXOp8i3T4.0V2oEzMyfvQta62Ols8xjJkS','rootAdmin',1)

if not exists(select 1 from app_user where username = 'admin')
insert app_user(created_by, last_modified_by, created_date, last_modified_date, email, is_active, password, username, app_role_id)
values ('social_network_app_v1', 'social_network_app_v1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'admin@socialnetworkapp.com', 1,'$2a$10$y2Wsd4GRxdWPWBMfN8aOXOp8i3T4.0V2oEzMyfvQta62Ols8xjJkS','admin',2)

if not exists(select 1 from app_user where username = 'admin1')
insert app_user(created_by, last_modified_by, created_date, last_modified_date, email, is_active, password, username, app_role_id)
values ('social_network_app_v1', 'social_network_app_v1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'admin1@socialnetworkapp.com', 1,'$2a$10$y2Wsd4GRxdWPWBMfN8aOXOp8i3T4.0V2oEzMyfvQta62Ols8xjJkS','admin1',2)

if not exists(select 1 from app_user where username = 'moderator')
insert app_user(created_by, last_modified_by, created_date, last_modified_date, email, is_active, password, username, app_role_id)
values ('social_network_app_v1', 'social_network_app_v1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'moderator@socialnetworkapp.com', 1,'$2a$10$y2Wsd4GRxdWPWBMfN8aOXOp8i3T4.0V2oEzMyfvQta62Ols8xjJkS','moderator',3)

if not exists(select 1 from app_user where username = 'moderator1')
insert app_user(created_by, last_modified_by, created_date, last_modified_date, email, is_active, password, username, app_role_id)
values ('social_network_app_v1', 'social_network_app_v1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'moderator1@socialnetworkapp.com', 1,'$2a$10$y2Wsd4GRxdWPWBMfN8aOXOp8i3T4.0V2oEzMyfvQta62Ols8xjJkS','moderator1',3)

if not exists(select 1 from app_user where username = 'user')
insert app_user(created_by, last_modified_by, created_date, last_modified_date, email, is_active, password, username, app_role_id)
values ('social_network_app_v1', 'social_network_app_v1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'user@socialnetworkapp.com', 1,'$2a$10$y2Wsd4GRxdWPWBMfN8aOXOp8i3T4.0V2oEzMyfvQta62Ols8xjJkS','user',4)

if not exists(select 1 from app_user where username = 'user1')
insert app_user(created_by, last_modified_by, created_date, last_modified_date, email, is_active, password, username, app_role_id)
values ('social_network_app_v1', 'social_network_app_v1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'user1@socialnetworkapp.com', 1,'$2a$10$y2Wsd4GRxdWPWBMfN8aOXOp8i3T4.0V2oEzMyfvQta62Ols8xjJkS','user1',4)









COMMIT TRANSACTION
