BEGIN;

CREATE TABLE IF NOT EXISTS categories (
id SERIAL PRIMARY KEY,
name TEXT NOT NULL UNIQUE,
parent_id INT REFERENCES categories(id) ON DELETE SET NULL
);

-- Root categories
INSERT INTO categories (name) VALUES
('Sports'),
('Music'),
('Gaming'),
('Outdoor'),
('Creativity'),
('Food'),
('Tech'),
('Social')
ON CONFLICT (name) DO NOTHING;

-- Subcategories (use SELECT to resolve parent by name)
INSERT INTO categories (name, parent_id) VALUES
 ('Football',        (SELECT id FROM categories WHERE name = 'Sports')),
 ('Basketball',      (SELECT id FROM categories WHERE name = 'Sports')),
 ('Tennis',          (SELECT id FROM categories WHERE name = 'Sports')),
 ('Running',         (SELECT id FROM categories WHERE name = 'Sports')),
 ('Cycling',         (SELECT id FROM categories WHERE name = 'Sports')),

 ('Playing',         (SELECT id FROM categories WHERE name = 'Music')),
 ('Singing',         (SELECT id FROM categories WHERE name = 'Music')),
 ('Concerts',        (SELECT id FROM categories WHERE name = 'Music')),

 ('Video Games',     (SELECT id FROM categories WHERE name = 'Gaming')),
 ('Board Games',     (SELECT id FROM categories WHERE name = 'Gaming')),
 ('Tabletop RPG',    (SELECT id FROM categories WHERE name = 'Gaming')),

 ('Hiking',          (SELECT id FROM categories WHERE name = 'Outdoor')),
 ('Camping',         (SELECT id FROM categories WHERE name = 'Outdoor')),
 ('Fishing',         (SELECT id FROM categories WHERE name = 'Outdoor')),

 ('Drawing',         (SELECT id FROM categories WHERE name = 'Creativity')),
 ('Painting',        (SELECT id FROM categories WHERE name = 'Creativity')),
 ('Photography',     (SELECT id FROM categories WHERE name = 'Creativity')),
 ('DIY & Crafts',    (SELECT id FROM categories WHERE name = 'Creativity')),

 ('Cooking',         (SELECT id FROM categories WHERE name = 'Food')),
 ('Baking',          (SELECT id FROM categories WHERE name = 'Food')),
 ('Coffee & Cafés',  (SELECT id FROM categories WHERE name = 'Food')),

 ('Programming',     (SELECT id FROM categories WHERE name = 'Tech')),
 ('Robotics',        (SELECT id FROM categories WHERE name = 'Tech')),
 ('Game Development',(SELECT id FROM categories WHERE name = 'Tech')),

 ('Language Exchange',(SELECT id FROM categories WHERE name = 'Social')),
 ('Volunteering',     (SELECT id FROM categories WHERE name = 'Social')),
 ('Networking',       (SELECT id FROM categories WHERE name = 'Social')),
 ('Book Clubs',       (SELECT id FROM categories WHERE name = 'Social'))
ON CONFLICT (name) DO NOTHING;

COMMIT;