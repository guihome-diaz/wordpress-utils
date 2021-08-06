-- Replace the prefix "baby_" by your own, if any

select * from baby_users bu ;

-- See who published articles
SELECT distinct post_author FROM baby_posts;

-- find posts' authors details
select * from baby_users bu
    where id IN (SELECT distinct post_author FROM baby_posts);

-- user metdata
select * from baby_users users
      left join baby_usermeta usermeta on usermeta.user_id = users.ID
    where users.id IN (SELECT distinct post_author FROM baby_posts)
    order by users.user_login, usermeta.meta_key;

select distinct meta_key from baby_usermeta usermeta order by meta_key;