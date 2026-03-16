BEGIN;
INSERT INTO categories (name) VALUES
    ('sports'),
    ('music'),
    ('gaming'),
    ('outdoor'),
    ('creativity'),
    ('food'),
    ('tech'),
    ('social');

INSERT INTO categories (name, parent_category_id) VALUES
     ('Football',        (SELECT id FROM categories WHERE name = 'sports')),
     ('Basketball',      (SELECT id FROM categories WHERE name = 'sports')),
     ('Tennis',          (SELECT id FROM categories WHERE name = 'sports')),
     ('Running',         (SELECT id FROM categories WHERE name = 'sports')),
     ('Cycling',         (SELECT id FROM categories WHERE name = 'sports')),

     ('Playing',         (SELECT id FROM categories WHERE name = 'music')),
     ('Singing',         (SELECT id FROM categories WHERE name = 'music')),
     ('Concerts',        (SELECT id FROM categories WHERE name = 'music')),

     ('Video Games',     (SELECT id FROM categories WHERE name = 'gaming')),
     ('Board Games',     (SELECT id FROM categories WHERE name = 'gaming')),
     ('Tabletop RPG',    (SELECT id FROM categories WHERE name = 'gaming')),

     ('Hiking',          (SELECT id FROM categories WHERE name = 'outdoor')),
     ('Camping',         (SELECT id FROM categories WHERE name = 'outdoor')),
     ('Fishing',         (SELECT id FROM categories WHERE name = 'outdoor')),

     ('Drawing',         (SELECT id FROM categories WHERE name = 'creativity')),
     ('Painting',        (SELECT id FROM categories WHERE name = 'creativity')),
     ('Photography',     (SELECT id FROM categories WHERE name = 'creativity')),
     ('DIY & Crafts',    (SELECT id FROM categories WHERE name = 'creativity')),

     ('Cooking',         (SELECT id FROM categories WHERE name = 'food')),
     ('Baking',          (SELECT id FROM categories WHERE name = 'food')),
     ('Coffee & Cafés',  (SELECT id FROM categories WHERE name = 'food')),

     ('Programming',     (SELECT id FROM categories WHERE name = 'tech')),
     ('Robotics',        (SELECT id FROM categories WHERE name = 'tech')),
     ('Game Development',(SELECT id FROM categories WHERE name = 'tech')),

     ('Language Exchange',(SELECT id FROM categories WHERE name = 'social')),
     ('Volunteering',     (SELECT id FROM categories WHERE name = 'social')),
     ('Networking',       (SELECT id FROM categories WHERE name = 'social')),
     ('Book Clubs',       (SELECT id FROM categories WHERE name = 'social'));

COMMIT;