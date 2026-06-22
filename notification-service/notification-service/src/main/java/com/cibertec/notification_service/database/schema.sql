

CREATE TABLE notifications (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT NOT NULL,
  type VARCHAR(50),
  subject VARCHAR(150),
  message TEXT,
  status VARCHAR(50),
  sent_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE notification_queue (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  event_type VARCHAR(100),
  payload TEXT,
  processed BOOLEAN DEFAULT false
);