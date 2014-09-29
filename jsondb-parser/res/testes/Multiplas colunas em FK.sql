-- Extraído de http://stackoverflow.com/questions/12573099/foreign-key-with-multiple-columns-from-different-tables
-- Pode ser necessário adaptar o código antes de testar

-- Nothing special here.
create table animal_types (
  animal_type varchar(15) primary key
);

create table animals (
  name varchar(15) primary key,
  animal_type varchar(15) not null references animal_types (animal_type),
  -- This constraint lets us work around SQL's lack of assertions in this case.
  unique (name, animal_type)
);

-- Nothing special here.
create table animal_food_types (
  animal_type varchar(15) not null references animal_types (animal_type),
  food_type varchar(15) not null,
  primary key (animal_type, food_type)
);

-- Overlapping foreign key constraints.
create table animals_preferred_food (
  animal_name varchar(15) not null,
  -- This column is necessary to implement your requirement. 
  animal_type varchar(15) not null,
  pref_food varchar(10) not null,
  primary key (animal_name, pref_food),
  -- This foreign key constraint requires a unique constraint on these
  -- two columns in "animals".
  foreign key (animal_name, animal_type) 
    references animals (animal_name, animal_type),
  -- Since the animal_type column is now in this table, this constraint
  -- is simple.
  foreign key (animal_type, pref_food) 
    references animal_food_types (animal_type, food_type)
);