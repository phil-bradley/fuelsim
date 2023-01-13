/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  PBradley
 * Created: 28 Feb 2022
 */

insert into grade values (1, 'Unleaded', 1448);
insert into grade values (2, 'Diesel', 1349);
insert into grade values (3, 'Super Unleaded', 1539);

insert into tank (id, capacity_Litres, grade_id, TEMPERATURE_CELCIUS, VOLUME_LITRES) values (1, 20000, 1, 23, 20000);
insert into tank (id, capacity_Litres, grade_id, TEMPERATURE_CELCIUS, VOLUME_LITRES) values (2, 25000, 2, 22, 12000);
insert into tank (id, capacity_Litres, grade_id, TEMPERATURE_CELCIUS, VOLUME_LITRES) values (3, 15000, 3, 23, 11000);

insert into pump values (1, 'IDL');
insert into pump values (2, 'IDL');
insert into pump values (3, 'IDL');
insert into pump values (4, 'IDL');