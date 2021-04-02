SELECT t.tag_name name,COUNT(*) value FROM tinymce_tag tt JOIN tag t ON tt.tag_id = t.id WHERE tinymce_id IN
    (SELECT id FROM tinymce WHERE is_article = 1)
GROUP BY tag_id
