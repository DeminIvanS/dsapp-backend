-- ============================================================================
-- 1. НЕЗАВИСИМЫЕ ТАБЛИЦЫ (Создаются в первую очередь)
-- ============================================================================

-- Учетные записи
CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       login VARCHAR(100) NOT NULL UNIQUE,
                       password_hash VARCHAR(255) NOT NULL,
                       role VARCHAR(20) NOT NULL,               -- 'ADMIN', 'TEACHER', 'STUDENT'
                       active BOOLEAN NOT NULL DEFAULT TRUE,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Филиалы школы
CREATE TABLE branches (
                          id SERIAL PRIMARY KEY,
                          name VARCHAR(100) NOT NULL,
                          address VARCHAR(255) NOT NULL,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Прайс-лист услуг и абонементов
CREATE TABLE price_list_items (
                                  id SERIAL PRIMARY KEY,
                                  name VARCHAR(255) NOT NULL,
                                  price NUMERIC(10, 2) NOT NULL,
                                  valid_from DATE NOT NULL,
                                  valid_to DATE,                           -- null означает бессрочно
                                  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ============================================================================
-- 2. ТАБЛИЦЫ СВЯЗАННЫЕ С USERS И ВНУТРЕННЕЙ СТРУКТУРОЙ
-- ============================================================================

-- Преподаватели
CREATE TABLE teachers (
                          id SERIAL PRIMARY KEY,
                          user_id INT NOT NULL UNIQUE,
                          first_name VARCHAR(100) NOT NULL,
                          last_name VARCHAR(100) NOT NULL,
                          patronymic VARCHAR(100),
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          CONSTRAINT fk_teachers_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Студенты
CREATE TABLE students (
                          id SERIAL PRIMARY KEY,
                          user_id INT NOT NULL UNIQUE,
                          first_name VARCHAR(100) NOT NULL,
                          last_name VARCHAR(100) NOT NULL,
                          patronymic VARCHAR(100),
                          birth_date DATE NOT NULL,
                          parent_name VARCHAR(255) NOT NULL,
                          phone VARCHAR(20) NOT NULL,
                          referral_source VARCHAR(255),
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          CONSTRAINT fk_students_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Танцевальные залы в филиалах
CREATE TABLE halls (
                       id SERIAL PRIMARY KEY,
                       branch_id INT NOT NULL,
                       name VARCHAR(100) NOT NULL,
                       description VARCHAR(255),
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       CONSTRAINT fk_halls_branch FOREIGN KEY (branch_id) REFERENCES branches(id) ON DELETE RESTRICT
);

-- ============================================================================
-- 3. ГРУППЫ И РАСПИСАНИЕ
-- ============================================================================

-- Танцевальные группы
CREATE TABLE groups (
                        id SERIAL PRIMARY KEY,
                        branch_id INT NOT NULL,
                        teacher_id INT NOT NULL,
                        name VARCHAR(100) NOT NULL,
                        age_range VARCHAR(50),
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        CONSTRAINT fk_groups_branch FOREIGN KEY (branch_id) REFERENCES branches(id) ON DELETE RESTRICT,
                        CONSTRAINT fk_groups_teacher FOREIGN KEY (teacher_id) REFERENCES teachers(id) ON DELETE RESTRICT
);

-- Состав групп
CREATE TABLE student_groups (
                                student_id INT NOT NULL,
                                group_id INT NOT NULL,
                                PRIMARY KEY (student_id, group_id),
                                CONSTRAINT fk_sg_student FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE,
                                CONSTRAINT fk_sg_group FOREIGN KEY (group_id) REFERENCES groups(id) ON DELETE CASCADE
);

-- Шаблоны для автоматической генерации расписания
CREATE TABLE schedule_templates (
                                    id SERIAL PRIMARY KEY,
                                    group_id INT NOT NULL,
                                    teacher_id INT NOT NULL,
                                    hall_id INT NOT NULL,
                                    day_of_week INT NOT NULL,                -- 1=Пн, 7=Вс
                                    start_time TIME NOT NULL,
                                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                    CONSTRAINT fk_st_group FOREIGN KEY (group_id) REFERENCES groups(id) ON DELETE RESTRICT,
                                    CONSTRAINT fk_st_teacher FOREIGN KEY (teacher_id) REFERENCES teachers(id) ON DELETE RESTRICT,
                                    CONSTRAINT fk_st_hall FOREIGN KEY (hall_id) REFERENCES halls(id) ON DELETE RESTRICT
);

-- Конкретные занятия (Уроки в календаре)
CREATE TABLE classes (
                         id SERIAL PRIMARY KEY,
                         template_id INT,
                         group_id INT NOT NULL,
                         teacher_id INT NOT NULL,
                         hall_id INT NOT NULL,
                         class_date DATE NOT NULL,
                         start_time TIME NOT NULL,
                         cancelled BOOLEAN NOT NULL DEFAULT FALSE,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         CONSTRAINT fk_classes_template FOREIGN KEY (template_id) REFERENCES schedule_templates(id) ON DELETE SET NULL,
                         CONSTRAINT fk_classes_group FOREIGN KEY (group_id) REFERENCES groups(id) ON DELETE RESTRICT,
                         CONSTRAINT fk_classes_teacher FOREIGN KEY (teacher_id) REFERENCES teachers(id) ON DELETE RESTRICT,
                         CONSTRAINT fk_classes_hall FOREIGN KEY (hall_id) REFERENCES halls(id) ON DELETE RESTRICT
);

-- Посещаемость конкретного урока
CREATE TABLE attendance (
                            id SERIAL PRIMARY KEY,
                            class_id INT NOT NULL,
                            student_id INT NOT NULL,
                            present BOOLEAN NOT NULL DEFAULT TRUE,
                            comment VARCHAR(255),
                            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            CONSTRAINT unq_class_student UNIQUE (class_id, student_id), -- Чтобы дважды не отметить одного ребенка на уроке
                            CONSTRAINT fk_attendance_class FOREIGN KEY (class_id) REFERENCES classes(id) ON DELETE CASCADE,
                            CONSTRAINT fk_attendance_student FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE
);

-- ============================================================================
-- 4. ФИНАНСОВЫЙ БЛОК (БИЛЛИНГ)
-- ============================================================================

-- Начисления долгов / Счета за абонементы
CREATE TABLE charges (
                         id SERIAL PRIMARY KEY,
                         student_id INT NOT NULL,
                         price_list_item_id INT NOT NULL,
                         amount NUMERIC(10, 2) NOT NULL,
                         paid BOOLEAN NOT NULL DEFAULT FALSE,
                         created_by INT NOT NULL,                 -- user_id администратора
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         CONSTRAINT fk_charges_student FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE RESTRICT,
                         CONSTRAINT fk_charges_item FOREIGN KEY (price_list_item_id) REFERENCES price_list_items(id) ON DELETE RESTRICT,
                         CONSTRAINT fk_charges_admin FOREIGN KEY (created_by) REFERENCES users(id) ON DELETE RESTRICT
);

-- Платежи
CREATE TABLE payments (
                          id SERIAL PRIMARY KEY,
                          student_id INT NOT NULL,
                          amount NUMERIC(10, 2) NOT NULL,
                          payment_date DATE NOT NULL DEFAULT CURRENT_DATE,
                          received_by INT NOT NULL,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          CONSTRAINT fk_payments_student FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE RESTRICT,
                          CONSTRAINT fk_payments_admin FOREIGN KEY (received_by) REFERENCES users(id) ON DELETE RESTRICT
);

-- Связующая таблица Платежи <-> Начисления
CREATE TABLE payment_charges (
                                 payment_id INT NOT NULL,
                                 charge_id INT NOT NULL,
                                 PRIMARY KEY (payment_id, charge_id),
                                 CONSTRAINT fk_pc_payment FOREIGN KEY (payment_id) REFERENCES payments(id) ON DELETE CASCADE,
                                 CONSTRAINT fk_pc_charge FOREIGN KEY (charge_id) REFERENCES charges(id) ON DELETE RESTRICT
);
