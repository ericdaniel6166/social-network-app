START TRANSACTION;

insert into master_error_message
(created_by, last_modified_by, created_date, last_modified_date, error_code, error_message)
values ('social_network_app_v1', 'social_network_app_v1', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'USERNAME_EXISTS_ERROR',
        'This username has already been taken.\nPlease try another.')
ON DUPLICATE KEY UPDATE last_modified_date = CURRENT_TIMESTAMP();

insert into master_error_message
(created_by, last_modified_by, created_date, last_modified_date, error_code, error_message)
values ('social_network_app_v1', 'social_network_app_v1', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'EMAIL_EXISTS_ERROR',
        'This email has already been taken.\nPlease try another.')
ON DUPLICATE KEY UPDATE last_modified_date = CURRENT_TIMESTAMP();

COMMIT;