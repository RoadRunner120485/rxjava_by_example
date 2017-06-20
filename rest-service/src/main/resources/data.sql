INSERT INTO customer (id, first_name, last_name) VALUES ('ad34f2', 'Hans', 'Wurst');
INSERT INTO customer (id, first_name, last_name) VALUES ('be3490', 'Hermann', 'Huhn');

INSERT INTO car (id, customer_id, brand, model) VALUES ('e1cac1', 'ad34f2', 'Volkswagen', 'Passat');
INSERT INTO car (id, customer_id, brand, model) VALUES ('e1cac2', 'ad34f2', 'BMW', '520d');
INSERT INTO car (id, customer_id, brand, model) VALUES ('e1cac3', 'be3490', 'Volkswagen', 'Passat');

INSERT INTO logbook (id, car_id, customer_id, name) VALUES ('abba11', 'e1cac1', 'ad34f2', 'Hans Wurst - Passt blau');
INSERT INTO logbook (id, car_id, customer_id, name) VALUES ('abba12', 'e1cac2', 'ad34f2', 'Hans Wurst - 520d');
INSERT INTO logbook (id, car_id, customer_id, name) VALUES ('abba13', 'e1cac3', 'be3490', 'Hermann Huhn - Passat');

INSERT INTO log_record(id, logbook_id, car_id, customer_id, start_mileage, end_mileage, start_timestamp, end_timestamp)
  VALUES ('ffff01', 'abba11', 'e1cac3', 'be3490', 61040, 61140,  {ts '2017-06-01 10:47:52.69'},  {ts '2017-06-01 12:13:52.69'});
INSERT INTO log_record(id, logbook_id, car_id, customer_id, start_mileage, end_mileage, start_timestamp, end_timestamp)
  VALUES ('ffff02', 'abba11', 'e1cac3', 'be3490', 61140, 61290, {ts '2017-06-02 15:22:52.69'}, {ts '2017-06-02 17:13:52.69'});
INSERT INTO log_record(id, logbook_id, car_id, customer_id, start_mileage, end_mileage, start_timestamp, end_timestamp)
  VALUES ('ffff03', 'abba11', 'e1cac3', 'be3490', 61290, 61320, {ts '2017-06-02 18:19:52.69'}, {ts '2017-06-02 18:44:52.69'});
INSERT INTO log_record(id, logbook_id, car_id, customer_id, start_mileage, end_mileage, start_timestamp, end_timestamp)
  VALUES ('ffff04', 'abba11', 'e1cac3', 'be3490', 61320, 61334, {ts '2017-06-04 14:51:52.69'}, {ts '2017-06-04 15:06:52.69'});

INSERT INTO log_record(id, logbook_id, car_id, customer_id, start_mileage, end_mileage, start_timestamp, end_timestamp)
  VALUES ('ffff11', 'abba12', 'e1cac2', 'ad34f2', 61040, 61140,  {ts '2017-06-01 10:47:52.69'},  {ts '2017-06-01 12:13:52.69'});
INSERT INTO log_record(id, logbook_id, car_id, customer_id, start_mileage, end_mileage, start_timestamp, end_timestamp)
  VALUES ('ffff12', 'abba12', 'e1cac2', 'ad34f2', 61140, 61290, {ts '2017-06-02 15:22:52.69'}, {ts '2017-06-02 17:13:52.69'});
INSERT INTO log_record(id, logbook_id, car_id, customer_id, start_mileage, end_mileage, start_timestamp, end_timestamp)
  VALUES ('ffff13', 'abba12', 'e1cac2', 'ad34f2', 61290, 61320, {ts '2017-06-02 18:19:52.69'}, {ts '2017-06-02 18:44:52.69'});
INSERT INTO log_record(id, logbook_id, car_id, customer_id, start_mileage, end_mileage, start_timestamp, end_timestamp)
  VALUES ('ffff14', 'abba12', 'e1cac2', 'ad34f2', 61320, 61334, {ts '2017-06-04 14:51:52.69'}, {ts '2017-06-04 15:06:52.69'});

INSERT INTO log_record(id, logbook_id, car_id, customer_id, start_mileage, end_mileage, start_timestamp, end_timestamp)
  VALUES ('ffff21', 'abba13', 'e1cac1', 'ad34f2', 61040, 61140,  {ts '2017-06-01 10:47:52.69'},  {ts '2017-06-01 12:13:52.69'});
INSERT INTO log_record(id, logbook_id, car_id, customer_id, start_mileage, end_mileage, start_timestamp, end_timestamp)
  VALUES ('ffff22', 'abba13', 'e1cac1', 'ad34f2', 61140, 61290, {ts '2017-06-02 15:22:52.69'}, {ts '2017-06-02 17:13:52.69'});
INSERT INTO log_record(id, logbook_id, car_id, customer_id, start_mileage, end_mileage, start_timestamp, end_timestamp)
  VALUES ('ffff23', 'abba13', 'e1cac1', 'ad34f2', 61290, 61320, {ts '2017-06-02 18:19:52.69'}, {ts '2017-06-02 18:44:52.69'});
INSERT INTO log_record(id, logbook_id, car_id, customer_id, start_mileage, end_mileage, start_timestamp, end_timestamp)
  VALUES ('ffff24', 'abba13', 'e1cac1', 'ad34f2', 61320, 61334, {ts '2017-06-04 14:51:52.69'}, {ts '2017-06-04 15:06:52.69'});



