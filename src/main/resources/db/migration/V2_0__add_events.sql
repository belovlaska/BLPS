BEGIN;

-- Вспомогательный тип для типов событий
CREATE TYPE event_type AS ENUM ('CREATE', 'UPDATE', 'DELETE');

-- Вспомогательный тип для типов сущностей
CREATE TYPE resource_type AS ENUM (
  'applications', 'coordinates', 'disciplines', 'people', 'locations', 'admin-requests'
);

-- Таблица событий
CREATE TABLE events (
  id SERIAL PRIMARY KEY,
  resource_id INTEGER NOT NULL,
  resource_type resource_type NOT NULL,
  type event_type NOT NULL,
  created_by INTEGER REFERENCES users(id),
  created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);

-- Индексы для оптимизации поиска и выборок
CREATE INDEX idx_events_resource ON events(resource_id, resource_type);
CREATE INDEX idx_events_created_at ON events(created_at);

-- Ограничение внешних ключей для таблиц
ALTER TABLE events ADD CONSTRAINT fk_created_by FOREIGN KEY (created_by) REFERENCES users(id);

COMMIT;
