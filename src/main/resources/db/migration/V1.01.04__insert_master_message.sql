BEGIN TRANSACTION
if not exists(select 1 from master_message where message_code = 'SIGN_UP_SUCCESS')
insert master_message(created_by, last_modified_by, created_date, last_modified_date, message_code,
                      title, message)
values ('social_network_app_v1', 'social_network_app_v1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,
        'SIGN_UP_SUCCESS', 'VERIFICATION LINK SENT',
        'Hi %s, we''ve sent an email to %s. Please click on the link given in email to verify your account.\nThe link in the email will expire in 24 hours.')

if not exists(select 1 from master_message where message_code = 'VERIFY_ACCOUNT_SUCCESS')
insert master_message(created_by, last_modified_by, created_date, last_modified_date, message_code,
                      title, message)
values ('social_network_app_v1', 'social_network_app_v1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,
        'VERIFY_ACCOUNT_SUCCESS', 'VERIFICATION SUCCESS',
        'Hi %s, your account has been verified.')
COMMIT TRANSACTION