BEGIN TRANSACTION
if not exists(select 1 from master_message where message_code = 'UPDATE_ROLE_SUCCESS')
insert master_message(created_by, last_modified_by, created_date, last_modified_date, message_code,
                      title, message)
values ('social_network_app_v1', 'social_network_app_v1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,
        'UPDATE_ROLE_SUCCESS', 'UPDATE ROLE SUCCESS',
        'You have updated role %s to user %s successfully')

COMMIT TRANSACTION