-- Supplemental file used only when "spring.jpa.hibernate.ddl-auto" is set to "create" or "create-drop"
--

-- Link comments and users
ALTER TABLE comments ADD CONSTRAINT comments_fk_users FOREIGN KEY (user_id) REFERENCES users(ID);

-- Link terms to post
ALTER TABLE term_relationships ADD CONSTRAINT term_relations_fk_posts FOREIGN KEY (object_id) REFERENCES posts(ID);
-- Link terms to taxonomy
ALTER TABLE term_relationships ADD CONSTRAINT term_relations_fk_taxonomy FOREIGN KEY (term_taxonomy_id) REFERENCES term_taxonomy(term_id);

commit;

