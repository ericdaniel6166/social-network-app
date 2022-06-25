BEGIN TRANSACTION
    use social_network_app;
    if not exists(select 1 from master_error_message where error_code = 'SEND_MAIL_ERROR')
        insert master_error_message(created_by, last_modified_by, created_date, last_modified_date, error_code,
                                    error_message)
        values ('social_network_app_v1', 'social_network_app_v1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,
                'SEND_MAIL_ERROR',
                'Error sending email to %s')
COMMIT TRANSACTION