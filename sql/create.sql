create table log_report ( id int not null auto_increment, host varchar(200) not null, src_ip varchar(15) not null, dst_ip varchar(15) not null, vm varchar(200) not null, src_port int not null, dst_port int not null, ts_reported timestamp not null default '0000-00-00', status int not null, ts_inserted timestamp not null default current_timestamp, allowed_rule int(11), traffic_type varchar(200), primary key (id )); 

create table allowed_dest ( id int(11) not null auto_increment, dst varchar(100) not null, dst_type varchar(100), date_added timestamp not null, report_id int not null, reason varchar(200), primary key(id))

create table trace_question (id int(11) not null auto_increment, host varchar(200) not null, date_question timestamp, answer varchar(100), primary key(id) )
