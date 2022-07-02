BEGIN TRANSACTION
    if not exists(select 1 from app_role where role_name = 'ROLE_USER')
insert app_role(created_by, last_modified_by, created_date, last_modified_date, role_name)
values ('social_network_app_v1', 'social_network_app_v1', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP,
        'ROLE_USER')

COMMIT TRANSACTION