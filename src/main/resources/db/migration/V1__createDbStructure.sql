CREATE TABLE recipe (
                        id SERIAL PRIMARY KEY,
                        title VARCHAR(50) NOT NULL,
                        servings INTEGER,
                        instructions TEXT NOT NULL,
                        is_vegetarian BOOLEAN,
                        UNIQUE (title)
);
CREATE TABLE ingredient (
                            id SERIAL PRIMARY KEY,
                            name VARCHAR(255) NOT NULL,
                            UNIQUE (name)
);
CREATE TABLE recipe_ingredient (
                                   recipe_id BIGINT,
                                   ingredient_id BIGINT,
                                   PRIMARY KEY (recipe_id, ingredient_id),
                                   FOREIGN KEY (recipe_id) REFERENCES recipe(id),
                                   FOREIGN KEY (ingredient_id) REFERENCES ingredient(id)
);
