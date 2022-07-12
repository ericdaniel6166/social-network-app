BEGIN TRANSACTION

SET IDENTITY_INSERT app_role ON

if not exists(select 1 from app_role where role_name = 'ROLE_ADMIN')
insert app_role(id, created_by, last_modified_by, created_date, last_modified_date, role_name)
values (1,  'social_network_app_v1', 'social_network_app_v1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,
        'ROLE_ADMIN')

if not exists(select 1 from app_role where role_name = 'ROLE_MODERATOR')
insert app_role(id, created_by, last_modified_by, created_date, last_modified_date, role_name)
values (2,  'social_network_app_v1', 'social_network_app_v1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,
        'ROLE_MODERATOR')

if not exists(select 1 from app_role where role_name = 'ROLE_USER')
insert app_role(id, created_by, last_modified_by, created_date, last_modified_date, role_name)
values (3, 'social_network_app_v1', 'social_network_app_v1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,
        'ROLE_USER')

SET IDENTITY_INSERT app_role OFF

COMMIT TRANSACTION