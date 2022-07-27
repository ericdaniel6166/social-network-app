BEGIN TRANSACTION
if not exists(select 1 from master_error_message where error_code = 'SEND_MAIL_ERROR')
    insert master_error_message(created_by, last_modified_by, created_date, last_modified_date, error_code,
                                error_message)
    values ('social_network_app_v1', 'social_network_app_v1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,
            'SEND_MAIL_ERROR',
            'Error sending email to %s.')

if not exists(select 1 from master_error_message where error_code = 'USERNAME_EXISTED_ERROR')
    insert master_error_message(created_by, last_modified_by, created_date, last_modified_date, error_code,
                                error_message)
    values ('social_network_app_v1', 'social_network_app_v1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,
            'USERNAME_EXISTED_ERROR',
            'This username has already been taken.\nPlease try another.')

if not exists(select 1 from master_error_message where error_code = 'EMAIL_EXISTED_ERROR')
    insert master_error_message(created_by, last_modified_by, created_date, last_modified_date, error_code,
                                error_message)
    values ('social_network_app_v1', 'social_network_app_v1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,
            'EMAIL_EXISTED_ERROR',
            'This email has already been taken.\nPlease try another.')

if not exists(select 1 from master_error_message where error_code = 'ACCOUNT_ALREADY_ACTIVATED_ERROR')
    insert master_error_message(created_by, last_modified_by, created_date, last_modified_date, error_code,
                                error_message)
    values ('social_network_app_v1', 'social_network_app_v1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,
            'ACCOUNT_ALREADY_ACTIVATED_ERROR',
            'Your account has been already activated.')
COMMIT TRANSACTION