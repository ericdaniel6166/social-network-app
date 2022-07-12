BEGIN TRANSACTION
if not exists(select 1 from app_user_role where app_user_id = 1 and app_role_id = 1)
insert app_user_role(app_user_id, app_role_id)
values (1, 1)

COMMIT TRANSACTION
