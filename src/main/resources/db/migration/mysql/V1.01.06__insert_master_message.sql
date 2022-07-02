START TRANSACTION;

insert into master_message
(created_by, last_modified_by, created_date, last_modified_date, message_code, title, message)
values ('social_network_app_v1', 'social_network_app_v1', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'CREATE_FORUM_SUCCESS', 'CREATE FORUM SUCCESS',
        'You have create forum "%s" successfully')
ON DUPLICATE KEY UPDATE last_modified_date = CURRENT_TIMESTAMP();

COMMIT;
