-- 1. Configuramos Booking Service
USE booking_service_db;
-- Aseguramos que el estado 'CREATED' sea permitido
ALTER TABLE booking_history MODIFY status ENUM('CANCELLED','CONFIRMED','PENDING','CREATED');

INSERT INTO bookings (user_id, event_id, quantity, status, total_price, created_at, updated_at) VALUES 
(1,1,2,'CONFIRMED',240.00, NOW(), NOW()),
(2,2,1,'CONFIRMED',200.00, NOW(), NOW()),
(3,3,3,'PENDING',150.00, NOW(), NOW()),
(4,4,1,'CONFIRMED',80.00, NOW(), NOW()),
(5,5,2,'CONFIRMED',180.00, NOW(), NOW()),
(6,6,4,'PENDING',240.00, NOW(), NOW()),
(7,7,2,'CONFIRMED',60.00, NOW(), NOW()),
(8,8,1,'CONFIRMED',70.00, NOW(), NOW()),
(9,9,1,'CONFIRMED',250.00, NOW(), NOW()),
(10,10,2,'PENDING',200.00, NOW(), NOW());

INSERT INTO booking_history (booking_id, status, changed_at) VALUES 
(1,'CREATED', NOW()),(2,'CREATED', NOW()),(3,'CREATED', NOW()),(4,'CREATED', NOW()),(5,'CREATED', NOW()),
(6,'CREATED', NOW()),(7,'CREATED', NOW()),(8,'CREATED', NOW()),(9,'CREATED', NOW()),(10,'CREATED', NOW());

-- 2. Configuramos Event Service
USE event_service_db;
INSERT INTO event_categories (name) VALUES 
('Conciertos'),('Teatro'),('Deportes'),('Tecnología'),('Educación'),('Cine'),('Festivales'),('Exposiciones'),('Comedia'),('Moda'),('Gastronomía'),('Literatura'),('Danza'),('Salud & Bienestar'),('Networking');

INSERT INTO events (title, description, event_date, location, price, capacity, available_slots, organizer_id, event_status, category_id, created_at) VALUES 
('Concierto de Rock', 'Bandas locales y nacionales.', '2026-03-15 20:00:00', 'Auditorio Central', 50.00, 300, 300, 1, 'ACTIVE', 1, NOW()),
('Obra de Teatro Clásico', 'Versión moderna de Hamlet.', '2026-03-20 19:00:00', 'Teatro Municipal', 30.00, 150, 150, 2, 'ACTIVE', 2, NOW()),
('Maratón 5K Ciudad', 'Evento deportivo para todos.', '2026-04-01 07:00:00', 'Parque Central', 10.00, 500, 500, 3, 'ACTIVE', 3, NOW()),
('Hackathon Tech', 'Competencia de programación.', '2026-03-25 09:00:00', 'Universidad Tecnológica', 0.00, 100, 100, 4, 'ACTIVE', 4, NOW()),
('Curso de Fotografía', 'Aprende técnicas básicas.', '2026-03-18 16:00:00', 'Centro Cultural', 25.00, 40, 40, 5, 'ACTIVE', 5, NOW()),
('Cine al Aire Libre', 'Proyección de películas clásicas.', '2026-03-22 20:00:00', 'Plaza Mayor', 15.00, 200, 200, 6, 'ACTIVE', 6, NOW()),
('Festival de Jazz', 'Jazz internacional y nacional.', '2026-04-05 18:00:00', 'Auditorio Jazz', 60.00, 250, 250, 1, 'ACTIVE', 7, NOW()),
('Exposición de Arte Moderno', 'Obras de artistas emergentes.', '2026-03-28 10:00:00', 'Galería Central', 20.00, 100, 100, 2, 'ACTIVE', 8, NOW()),
('Stand Up Comedy Night', 'Comediantes locales.', '2026-04-02 21:00:00', 'Teatro Comedia', 35.00, 120, 120, 3, 'ACTIVE', 9, NOW()),
('Desfile de Moda Primavera', 'Colecciones 2026.', '2026-03-30 17:00:00', 'Centro de Convenciones', 40.00, 80, 80, 4, 'ACTIVE', 10, NOW()),
('Festival Gastronómico', 'Degustación de comida local.', '2026-04-10 12:00:00', 'Plaza Gastronómica', 20.00, 150, 150, 5, 'ACTIVE', 11, NOW()),
('Feria del Libro', 'Autores y editoriales presentes.', '2026-03-27 10:00:00', 'Centro Cultural', 5.00, 200, 200, 6, 'ACTIVE', 12, NOW()),
('Clase de Ballet', 'Taller para principiantes.', '2026-03-26 15:00:00', 'Academia de Danza', 30.00, 50, 50, 2, 'ACTIVE', 13, NOW()),
('Yoga y Meditación', 'Sesión de bienestar integral.', '2026-03-29 08:00:00', 'Parque Wellness', 10.00, 60, 60, 3, 'ACTIVE', 14, NOW()),
('Networking Empresarial', 'Conecta con profesionales.', '2026-04-07 18:00:00', 'Centro de Negocios', 0.00, 100, 100, 4, 'ACTIVE', 15, NOW());

-- 3. Configuramos Payment Service
USE payment_service_db;
CREATE TABLE IF NOT EXISTS payments (id BIGINT AUTO_INCREMENT PRIMARY KEY, booking_id BIGINT NOT NULL, amount DECIMAL(10,2), payment_method VARCHAR(50), status VARCHAR(50), transaction_ref VARCHAR(100), created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP); 
CREATE TABLE IF NOT EXISTS notifications (id BIGINT AUTO_INCREMENT PRIMARY KEY, user_id BIGINT NOT NULL, type VARCHAR(50), subject VARCHAR(150), message TEXT, status VARCHAR(50), sent_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP); 
CREATE TABLE IF NOT EXISTS notification_queue (id BIGINT AUTO_INCREMENT PRIMARY KEY, event_type VARCHAR(100), payload TEXT, processed BOOLEAN DEFAULT false); 

-- 4. Configuramos Auth Service
USE auth_service_db; 
INSERT INTO users (name,email,password,role_type,enabled) VALUES 
('Ana Torres','ana@mail.com','$2a$10$7QJvQ4vQ1Q8Z8YzF3zQpEOTQ6vJzJ1y6RkWwM9zJXQmYkLkZp7G2K', 'CUSTOMER', true),
('Luis Perez','luis@mail.com','$2a$10$7QJvQ4vQ1Q8Z8YzF3zQpEOTQ6vJzJ1y6RkWwM9zJXQmYkLkZp7G2K', 'STAFF', true),
('Carlos Diaz','carlos@mail.com','$2a$10$7QJvQ4vQ1Q8Z8YzF3zQpEOTQ6vJzJ1y6RkWwM9zJXQmYkLkZp7G2K', 'ADMIN', true),
('Maria Lopez','maria@mail.com','$2a$10$7QJvQ4vQ1Q8Z8YzF3zQpEOTQ6vJzJ1y6RkWwM9zJXQmYkLkZp7G2K', 'CUSTOMER', true),
('Sofia Ramos','sofia@mail.com','$2a$10$7QJvQ4vQ1Q8Z8YzF3zQpEOTQ6vJzJ1y6RkWwM9zJXQmYkLkZp7G2K', 'CUSTOMER', true),
('Pedro Silva','pedro@mail.com','$2a$10$7QJvQ4vQ1Q8Z8YzF3zQpEOTQ6vJzJ1y6RkWwM9zJXQmYkLkZp7G2K', 'CUSTOMER', true),
('Valeria Cruz','valeria@mail.com','$2a$10$7QJvQ4vQ1Q8Z8YzF3zQpEOTQ6vJzJ1y6RkWwM9zJXQmYkLkZp7G2K', 'CUSTOMER', true),
('Jorge Mena','jorge@mail.com','$2a$10$7QJvQ4vQ1Q8Z8YzF3zQpEOTQ6vJzJ1y6RkWwM9zJXQmYkLkZp7G2K', 'CUSTOMER', true),
('Elena Ruiz','elena@mail.com','$2a$10$7QJvQ4vQ1Q8Z8YzF3zQpEOTQ6vJzJ1y6RkWwM9zJXQmYkLkZp7G2K', 'CUSTOMER', true),
('Diego Soto','diego@mail.com','$2a$10$7QJvQ4vQ1Q8Z8YzF3zQpEOTQ6vJzJ1y6RkWwM9zJXQmYkLkZp7G2K', 'ORGANIZER', true);