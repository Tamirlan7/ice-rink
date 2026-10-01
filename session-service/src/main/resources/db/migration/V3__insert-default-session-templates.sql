-- V3__insert-default-session-templates.sql

INSERT INTO t_session_template (day_of_week, start_time, end_time, allowed_people_quantity, is_active)
VALUES
    -- будни: два сеанса после обеда
    ('MONDAY',    '15:00', '18:00', 300, TRUE),
    ('MONDAY',    '18:00', '21:00', 300, TRUE),
    ('TUESDAY',   '15:00', '18:00', 300, TRUE),
    ('TUESDAY',   '18:00', '21:00', 300, TRUE),
    ('WEDNESDAY', '15:00', '18:00', 300, TRUE),
    ('WEDNESDAY', '18:00', '21:00', 300, TRUE),
    ('THURSDAY',  '15:00', '18:00', 300, TRUE),
    ('THURSDAY',  '18:00', '21:00', 300, TRUE),
    ('FRIDAY',    '15:00', '18:00', 300, TRUE),
    ('FRIDAY',    '18:00', '21:00', 300, TRUE),
    -- выходные: три сеанса с утра
    ('SATURDAY',  '10:00', '13:00', 300, TRUE),
    ('SATURDAY',  '13:00', '16:00', 300, TRUE),
    ('SATURDAY',  '16:00', '19:00', 300, TRUE),
    ('SUNDAY',    '10:00', '13:00', 300, TRUE),
    ('SUNDAY',    '13:00', '16:00', 300, TRUE),
    ('SUNDAY',    '16:00', '19:00', 300, TRUE);