SELECT
    t.id,
    t.title,
    t.classification_id,
    t.summary,
    t.create_time,
    t.content,
    su.username,
    su.avatar,
    fc.click_count
FROM
    tinymce t
        JOIN sys_user su ON t.write_user = su.username
        JOIN fiery_count fc ON t.id = fc.resource_id
WHERE
        t.is_article = 1
  AND t.state = 1
