START TRANSACTION;

insert into master_message
(created_by, last_modified_by, created_date, last_modified_date, message_code, title, message)
values ('social_network_app_v1', 'social_network_app_v1', CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP(), 'SIGN_UP_SUCCESS',
        'VERIFICATION LINK SENT','Hi %s, we''ve sent an email to %s. Please click on the link given in email to verify your account.\nThe link in the email will expire in 24 hours.')
ON DUPLICATE KEY UPDATE last_modified_date = CURRENT_TIMESTAMP();

COMMIT;