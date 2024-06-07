ALTER TABLE Profile
DROP CONSTRAINT profile_user_id_fk;

ALTER TABLE Profile
ADD CONSTRAINT profile_user_id_fk
FOREIGN KEY (user_id)
REFERENCES User(id)
ON DELETE CASCADE;