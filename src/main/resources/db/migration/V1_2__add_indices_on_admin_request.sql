BEGIN;

-- Время создания запроса
ALTER TABLE admin_requests ADD created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW();

-- Индекс по пользователю, подавшему запрос
CREATE INDEX idx_admin_requests_user_id ON admin_requests(user_id);

-- Индекс по статусу запроса
CREATE INDEX idx_admin_requests_status ON admin_requests(status);

COMMIT;
