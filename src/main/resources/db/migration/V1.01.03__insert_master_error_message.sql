BEGIN TRANSACTION
    if not exists(select 1 from master_error_message where error_code = 'ACCOUNT_ALREADY_ACTIVATED_ERROR')
        insert master_error_message(created_by, last_modified_by, created_date, last_modified_date, error_code,
                                    error_message)
        values ('social_network_app_v1', 'social_network_app_v1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,
                'ACCOUNT_ALREADY_ACTIVATED_ERROR',
                'Your account has been already activated.')

COMMIT TRANSACTION