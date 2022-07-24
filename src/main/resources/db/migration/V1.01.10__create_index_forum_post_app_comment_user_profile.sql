BEGIN TRANSACTION

drop index if exists idx_is_active on app_comment

CREATE INDEX idx_is_active
ON app_comment (is_active)
include (created_by, created_date, last_modified_by, last_modified_date, content, username, post_id)

drop index if exists idx_post_id on app_comment

CREATE INDEX idx_post_id
ON app_comment (post_id)
include (created_by, created_date, last_modified_by, last_modified_date, content, username, is_active)

drop index if exists idx_username on app_comment

CREATE INDEX idx_username
ON app_comment (username)
include (created_by, created_date, last_modified_by, last_modified_date, content, is_active, post_id)
------
drop index if exists idx_is_active on post

CREATE INDEX idx_is_active
ON post (is_active)
include (created_by, created_date, last_modified_by, last_modified_date, content, name, username, forum_id)

drop index if exists idx_forum_id on post

CREATE INDEX idx_forum_id
ON post (forum_id)
include (created_by, created_date, last_modified_by, last_modified_date, content, name, is_active, username)

drop index if exists idx_username on post

CREATE INDEX idx_username
ON post (username)
include (created_by, created_date, last_modified_by, last_modified_date, content, name, is_active, forum_id)
------
drop index if exists idx_is_active on forum

CREATE INDEX idx_is_active
ON forum (is_active)
include (created_by, created_date, last_modified_by, last_modified_date, description, name, username)

drop index if exists idx_username on forum

CREATE INDEX idx_username
ON forum (username)
include (created_by, created_date, last_modified_by, last_modified_date, description, name, is_active)
------
drop index if exists idx_username on user_profile

CREATE UNIQUE INDEX idx_username
ON user_profile (username)
include (created_by, created_date, last_modified_by, last_modified_date, address, birthday, full_name)
COMMIT TRANSACTION