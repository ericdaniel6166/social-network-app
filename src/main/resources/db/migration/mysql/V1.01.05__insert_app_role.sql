START TRANSACTION;

insert into app_role
(created_by, last_modified_by, created_date, last_modified_date, role_name)
values ('social_network_app_v1', 'social_network_app_v1', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(),
        'ROLE_USER')
ON DUPLICATE KEY UPDATE last_modified_date = CURRENT_TIMESTAMP();

COMMIT;