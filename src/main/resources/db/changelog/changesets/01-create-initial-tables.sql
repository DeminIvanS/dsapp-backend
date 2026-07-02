--liquibase formatted sql
drop table if exists users,
branches,
price_list_items,
teachers,
students,
halls,
groups,
student_groups,
schedule_templates,
classes,
attendance,
charges,
payments,
payment_charges;

CREATE TABLE if not exists users (
                       id BIGSERIAL PRIMARY KEY,
                       login VARCHAR(100) NOT NULL UNIQUE,
                       password_hash VARCHAR(255) NOT NULL,
                       role VARCHAR(20) NOT NULL,
                       is_active BOOLEAN NOT NULL DEFAULT TRUE,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE if not exists branches (
                          id BIGSERIAL PRIMARY KEY,
                          name VARCHAR(100) NOT NULL,
                          address VARCHAR(255) NOT NULL,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE if not exists price_list_items (
                                  id BIGSERIAL PRIMARY KEY,
                                  name VARCHAR(255) NOT NULL,
                                  price NUMERIC(10, 2) NOT NULL,
                                  valid_from DATE NOT NULL,
                                  valid_to DATE,
                                  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE if not exists teachers (
                          id BIGSERIAL PRIMARY KEY,
                          user_id INT NOT NULL UNIQUE,
                          first_name VARCHAR(100) NOT NULL,
                          last_name VARCHAR(100) NOT NULL,
                          patronymic VARCHAR(100),
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          CONSTRAINT fk_teachers_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE if not exists students (
                          id BIGSERIAL PRIMARY KEY,
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

CREATE TABLE if not exists halls (
                       id BIGSERIAL PRIMARY KEY,
                       branch_id INT NOT NULL,
                       name VARCHAR(100) NOT NULL,
                       description VARCHAR(255),
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       CONSTRAINT fk_halls_branch FOREIGN KEY (branch_id) REFERENCES branches(id) ON DELETE RESTRICT
);

CREATE TABLE if not exists groups (
                        id BIGSERIAL PRIMARY KEY,
                        branch_id INT NOT NULL,
                        teacher_id INT NOT NULL,
                        name VARCHAR(100) NOT NULL,
                        age_range VARCHAR(50),
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        CONSTRAINT fk_groups_branch FOREIGN KEY (branch_id) REFERENCES branches(id) ON DELETE RESTRICT,
                        CONSTRAINT fk_groups_teacher FOREIGN KEY (teacher_id) REFERENCES teachers(id) ON DELETE RESTRICT
);

CREATE TABLE if not exists student_groups (
                                student_id INT NOT NULL,
                                group_id INT NOT NULL,
                                PRIMARY KEY (student_id, group_id),
                                CONSTRAINT fk_sg_student FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE,
                                CONSTRAINT fk_sg_group FOREIGN KEY (group_id) REFERENCES groups(id) ON DELETE CASCADE
);

CREATE TABLE if not exists schedule_templates (
                                    id BIGSERIAL PRIMARY KEY,
                                    group_id INT NOT NULL,
                                    teacher_id INT NOT NULL,
                                    hall_id INT NOT NULL,
                                    day_of_week VARCHAR(15) NOT NULL,
                                    start_time TIME NOT NULL,
                                    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                    CONSTRAINT fk_st_group FOREIGN KEY (group_id) REFERENCES groups(id) ON DELETE RESTRICT,
                                    CONSTRAINT fk_st_teacher FOREIGN KEY (teacher_id) REFERENCES teachers(id) ON DELETE RESTRICT,
                                    CONSTRAINT fk_st_hall FOREIGN KEY (hall_id) REFERENCES halls(id) ON DELETE RESTRICT
);

CREATE TABLE if not exists classes (
                         id BIGSERIAL PRIMARY KEY,
                         template_id INT,
                         group_id INT NOT NULL,
                         teacher_id INT NOT NULL,
                         hall_id INT NOT NULL,
                         class_date DATE NOT NULL,
                         start_time TIME NOT NULL,
                         is_cancelled BOOLEAN NOT NULL DEFAULT FALSE,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         CONSTRAINT fk_classes_template FOREIGN KEY (template_id) REFERENCES schedule_templates(id) ON DELETE SET NULL,
                         CONSTRAINT fk_classes_group FOREIGN KEY (group_id) REFERENCES groups(id) ON DELETE RESTRICT,
                         CONSTRAINT fk_classes_teacher FOREIGN KEY (teacher_id) REFERENCES teachers(id) ON DELETE RESTRICT,
                         CONSTRAINT fk_classes_hall FOREIGN KEY (hall_id) REFERENCES halls(id) ON DELETE RESTRICT
);

CREATE TABLE if not exists attendance (
                            id BIGSERIAL PRIMARY KEY,
                            class_id INT NOT NULL,
                            student_id INT NOT NULL,
                            is_present BOOLEAN NOT NULL DEFAULT TRUE,
                            comment VARCHAR(255),
                            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            CONSTRAINT unq_class_student UNIQUE (class_id, student_id),
                            CONSTRAINT fk_attendance_class FOREIGN KEY (class_id) REFERENCES classes(id) ON DELETE CASCADE,
                            CONSTRAINT fk_attendance_student FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE
);

CREATE TABLE if not exists charges (
                         id BIGSERIAL PRIMARY KEY,
                         student_id INT NOT NULL,
                         price_list_item_id INT NOT NULL,
                         amount NUMERIC(10, 2) NOT NULL,
                         is_paid BOOLEAN NOT NULL DEFAULT FALSE,
                         created_by INT NOT NULL,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         CONSTRAINT fk_charges_student FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE RESTRICT,
                         CONSTRAINT fk_charges_item FOREIGN KEY (price_list_item_id) REFERENCES price_list_items(id) ON DELETE RESTRICT,
                         CONSTRAINT fk_charges_admin FOREIGN KEY (created_by) REFERENCES users(id) ON DELETE RESTRICT
);

CREATE TABLE if not exists payments (
                          id BIGSERIAL PRIMARY KEY,
                          student_id INT NOT NULL,
                          amount NUMERIC(10, 2) NOT NULL,
                          payment_date DATE NOT NULL DEFAULT CURRENT_DATE,
                          received_by INT NOT NULL,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          CONSTRAINT fk_payments_student FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE RESTRICT,
                          CONSTRAINT fk_payments_admin FOREIGN KEY (received_by) REFERENCES users(id) ON DELETE RESTRICT
);

CREATE TABLE if not exists payment_charges (
                                 payment_id INT NOT NULL,
                                 charge_id INT NOT NULL,
                                 PRIMARY KEY (payment_id, charge_id),
                                 CONSTRAINT fk_pc_payment FOREIGN KEY (payment_id) REFERENCES payments(id) ON DELETE CASCADE,
                                 CONSTRAINT fk_pc_charge FOREIGN KEY (charge_id) REFERENCES charges(id) ON DELETE RESTRICT
);