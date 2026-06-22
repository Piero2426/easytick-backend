INSERT INTO event_categories (name) VALUES
('Conciertos'),
('Teatro'),
('Deportes'),
('Tecnología'),
('Educación'),
('Cine'),
('Festivales'),
('Exposiciones'),
('Comedia'),
('Moda'),
('Gastronomía'),
('Literatura'),
('Danza'),
('Salud & Bienestar'),
('Networking');

INSERT INTO events (title, description, event_date, location, price, capacity, available_slots, organizer_id, status, category_id) VALUES
('Concierto de Rock', 'Bandas locales y nacionales.', '2026-03-15 20:00:00', 'Auditorio Central', 50.00, 300, 300, 1, 'ACTIVE', 1),
('Obra de Teatro Clásico', 'Versión moderna de Hamlet.', '2026-03-20 19:00:00', 'Teatro Municipal', 30.00, 150, 150, 2, 'ACTIVE', 2),
('Maratón 5K Ciudad', 'Evento deportivo para todos.', '2026-04-01 07:00:00', 'Parque Central', 10.00, 500, 500, 3, 'ACTIVE', 3),
('Hackathon Tech', 'Competencia de programación.', '2026-03-25 09:00:00', 'Universidad Tecnológica', 0.00, 100, 100, 4, 'ACTIVE', 4),
('Curso de Fotografía', 'Aprende técnicas básicas.', '2026-03-18 16:00:00', 'Centro Cultural', 25.00, 40, 40, 5, 'ACTIVE', 5),
('Cine al Aire Libre', 'Proyección de películas clásicas.', '2026-03-22 20:00:00', 'Plaza Mayor', 15.00, 200, 200, 6, 'ACTIVE', 6),
('Festival de Jazz', 'Jazz internacional y nacional.', '2026-04-05 18:00:00', 'Auditorio Jazz', 60.00, 250, 250, 1, 'ACTIVE', 7),
('Exposición de Arte Moderno', 'Obras de artistas emergentes.', '2026-03-28 10:00:00', 'Galería Central', 20.00, 100, 100, 2, 'ACTIVE', 8),
('Stand Up Comedy Night', 'Comediantes locales.', '2026-04-02 21:00:00', 'Teatro Comedia', 35.00, 120, 120, 3, 'ACTIVE', 9),
('Desfile de Moda Primavera', 'Colecciones 2026.', '2026-03-30 17:00:00', 'Centro de Convenciones', 40.00, 80, 80, 4, 'ACTIVE', 10),
('Festival Gastronómico', 'Degustación de comida local.', '2026-04-10 12:00:00', 'Plaza Gastronómica', 20.00, 150, 150, 5, 'ACTIVE', 11),
('Feria del Libro', 'Autores y editoriales presentes.', '2026-03-27 10:00:00', 'Centro Cultural', 5.00, 200, 200, 6, 'ACTIVE', 12),
('Clase de Ballet', 'Taller para principiantes.', '2026-03-26 15:00:00', 'Academia de Danza', 30.00, 50, 50, 2, 'ACTIVE', 13),
('Yoga y Meditación', 'Sesión de bienestar integral.', '2026-03-29 08:00:00', 'Parque Wellness', 10.00, 60, 60, 3, 'ACTIVE', 14),
('Networking Empresarial', 'Conecta con profesionales.', '2026-04-07 18:00:00', 'Centro de Negocios', 0.00, 100, 100, 4, 'ACTIVE', 15);
-----------------------------------------------------
