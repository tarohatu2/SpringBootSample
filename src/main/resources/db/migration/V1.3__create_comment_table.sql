CREATE TABLE IF NOT EXISTS Comment (
  id INT(11) NOT NULL AUTO_INCREMENT,
  title VARCHAR(50) DEFAULT NULL,
  created_datetime DATETIME NOT NULL,
  updated_datetime DATETIME NOT NULL,
  PRIMARY KEY(id)
);

ALTER TABLE Comment ADD COLUMN user_id INT NOT NULL FIRST;

ALTER TABLE Comment
ADD CONSTRAINT comment_user_id_fk
FOREIGN KEY (user_id)
REFERENCES User(id);

ALTER TABLE Comment ADD COLUMN article_id INT NOT NULL FIRST;

ALTER TABLE Comment
ADD CONSTRAINT comment_article_id_fk
FOREIGN KEY (article_id)
REFERENCES Article(id);