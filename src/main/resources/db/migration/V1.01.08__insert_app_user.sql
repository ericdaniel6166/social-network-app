BEGIN TRANSACTION

SET IDENTITY_INSERT app_user ON

if not exists(select 1 from app_user where username = 'admin')
insert app_user(id, created_by, last_modified_by, created_date, last_modified_date, email, is_active, password, username)
values (1, 'social_network_app_v1', 'social_network_app_v1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 'admin@socialnetworkapp.com', 1,'$2a$10$y2Wsd4GRxdWPWBMfN8aOXOp8i3T4.0V2oEzMyfvQta62Ols8xjJkS','admin')

SET IDENTITY_INSERT app_user OFF

COMMIT TRANSACTION
