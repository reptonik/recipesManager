-- Inserting data into the 'recipe' table
INSERT INTO recipe (title, servings, instructions, is_vegetarian)
VALUES ('Spaghetti Bolognese', 4, 'Cook spaghetti and prepare Bolognese sauce.', false),
       ('Vegetarian Pizza', 2, 'Prepare pizza dough and top with various vegetables.', true),
       ('Chicken Curry', 6, 'Cook chicken with curry spices and serve with rice.', false);

-- Inserting data into the 'ingredient' table
INSERT INTO ingredient (title)
VALUES ('Spaghetti'),
       ('Beef'),
       ('Tomato Sauce'),
       ('Cheese'),
       ('Bell Pepper'),
       ('Chicken'),
       ('Curry Powder'),
       ('Rice');

-- Inserting data into the 'recipe_ingredient' table
INSERT INTO recipe_ingredient (recipe_id, ingredient_id)
VALUES (1, 1), -- Spaghetti Bolognese: Spaghetti
       (1, 2), -- Spaghetti Bolognese: Beef
       (1, 3), -- Spaghetti Bolognese: Tomato Sauce
       (2, 1), -- Vegetarian Pizza: Spaghetti (just kidding, it's actually Tomato Sauce!)
       (2, 4), -- Vegetarian Pizza: Cheese
       (2, 5), -- Vegetarian Pizza: Bell Pepper
       (3, 6), -- Chicken Curry: Chicken
       (3, 7), -- Chicken Curry: Curry Powder
       (3, 8); -- Chicken Curry: Rice
