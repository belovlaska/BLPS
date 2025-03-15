BEGIN;


-- Вспомогательный тип для ролей пользователей (enum Role)
CREATE TYPE user_role AS ENUM ('ROLE_USER', 'ROLE_ADMIN', 'ROLE_FINANCIER');

-- Таблица пользователей системы
CREATE TABLE users (
  id SERIAL PRIMARY KEY,                                      -- Уникальный идентификатор пользователя
  username VARCHAR(255) NOT NULL UNIQUE,
  details  INTEGER NOT NULL ,-- Логин пользователя (уникальный)
  password_hash VARCHAR(128) NOT NULL UNIQUE,                 -- Хэш пароля (используется SHA-512)
  role user_role NOT NULL,                                    -- Роль пользователя: 'ROLE_USER', 'ROLE_ADMIN'
  created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()  -- Дата создания пользователя
);

-- Таблица для запросов на получение прав администратора
CREATE TABLE admin_requests (
  id SERIAL PRIMARY KEY,
  user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
  request_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
  approved_by INTEGER REFERENCES users(id),
  approval_date TIMESTAMP WITH TIME ZONE
);

-- Таблица локаций
CREATE TABLE locations (
  id SERIAL PRIMARY KEY,                                           -- Уникальный идентификатор местоположения
  x DOUBLE PRECISION,                                              -- Координата X
  y FLOAT,                                                         -- Координата Y
  z FLOAT,                                                         -- Координата Z
  created_by INTEGER NOT NULL REFERENCES users(id),                -- Идентификатор пользователя, создавшего местоположение
  created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),      -- Время создания персоны
  updated_by INTEGER REFERENCES users(id),                         -- Идентификатор пользователя, последнего обновившего местоположение
  updated_at TIMESTAMP WITH TIME ZONE                              -- Время последнего обновления
);

-- Таблица координат
CREATE TABLE coordinates (
  id SERIAL PRIMARY KEY,                                           -- Уникальный идентификатор координат
  x FLOAT NOT NULL CHECK (x <= 903),                               -- Координата X (максимум 903)
  y FLOAT NOT NULL CHECK (y >= -901),
  created_by INTEGER NOT NULL REFERENCES users(id),                -- Идентификатор пользователя, создавшего координату
  created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),      -- Время создания персоны
  updated_by INTEGER REFERENCES users(id),                         -- Идентификатор пользователя, последнего обновившего координату
  updated_at TIMESTAMP WITH TIME ZONE                              -- Время последнего обновления
);

CREATE TABLE disciplines (
  id SERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL CHECK (name <> ''),
  labs_count BIGINT NOT NULL,
  created_by INTEGER NOT NULL REFERENCES users(id),
  created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
  updated_by INTEGER REFERENCES users(id),
  updated_at TIMESTAMP WITH TIME ZONE
);

-- Таблица персон
CREATE TABLE people (
  id SERIAL PRIMARY KEY,                                           -- Уникальный идентификатор персонажа
  name VARCHAR(255) NOT NULL CHECK (name <> ''),                   -- Имя персонажа (не может быть пустым)
  eye_color color NOT NULL,                                        -- Цвет глаз
  hair_color color,                                                -- Цвет волос (может быть NULL)
  location_id INTEGER NOT NULL
    REFERENCES locations(id) ON DELETE RESTRICT,                   -- Идентификатор местоположения (ссылка на таблицу location)
  height BIGINT NOT NULL CHECK (height > 0),                       -- Рост персонажа (больше 0)
  nationality country,                                             -- Национальность
  created_by INTEGER NOT NULL REFERENCES users(id),                -- Идентификатор пользователя, создавшего персону
  created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),      -- Время создания персоны
  updated_by INTEGER REFERENCES users(id),                         -- Идентификатор пользователя, последнего обновившего персону
  updated_at TIMESTAMP WITH TIME ZONE                              -- Время последнего обновления
);

-- Таблица драконов
CREATE TABLE labworks (
  id SERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL CHECK (name <> ''),
  coordinates_id INTEGER NOT NULL
    REFERENCES coordinates(id) ON DELETE RESTRICT,
  description VARCHAR(5000) CHECK (description IS NULL OR description <> ''),
  difficulty difficulty NOT NULL,
  discipline_id INTEGER REFERENCES disciplines(id) ON DELETE SET NULL,
  minimal_point INTEGER CHECK (minimal_point IS NULL OR minimal_point > 0),
  author_id INTEGER REFERENCES people(id) ON DELETE SET NULL,
  created_by INTEGER NOT NULL REFERENCES users(id),
  created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
  updated_by INTEGER REFERENCES users(id),
  updated_at TIMESTAMP WITH TIME ZONE
);

-- Индексы для оптимизации поиска и выборок
CREATE INDEX idx_labwork_minimal_point ON labworks(minimal_point);
CREATE INDEX idx_labwork_name ON labworks(name);
CREATE INDEX idx_discipline_labs_count ON disciplines(labs_count);

COMMIT;
