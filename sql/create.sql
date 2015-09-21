create table log_report ( id int not null auto_increment, host varchar(200) not null, ip varchar(15) not null, vm varchar(200) not null, local_port int not null, remote_port int not null, ts_reported timestamp not null default '0000-00-00', status int not null, ts_inserted timestamp not null default current_timestamp, primary key (id )); 

create table allowed_dest ( id int(11) not null auto_increment, dst varchar(100) not null, dst_type varchar(100), date_added timestamp not null, report_id int not null, reason varchar(200), primary key(id))
