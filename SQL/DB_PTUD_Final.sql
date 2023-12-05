Create Database DB_PTUD_Final
go 
use DB_PTUD_Final



create table NhaXuatBan(
	maNXB varchar(6) PRIMARY KEY,
	tenNXB varchar(15),
	diaChi varchar(50)

)

create table TheLoai(
	maTheLoai varchar(20)PRIMARY KEY,
	tenTheLoai varchar(15) not null,
)

create table TacGia(
	maTacGia varchar(5)PRIMARY KEY,
	tenTacGia varchar(15) not null,
)

CREATE TABLE KhachHang (
    maKH varchar(5) PRIMARY KEY,
	tenKH varchar(15) not NULL,
	ngaySinh DateTime not NULL,
	diaChi varchar(50) not null,
	sdt varchar(12) NOT NULL,
);

CREATE TABLE NhanVien (
    maNV varchar(5) PRIMARY KEY,
	hoTenNV varchar(50) NOT  NULL,
	ngaySinh DateTime NOT NULL,
	diaChi varchar(50) NOT NULL,
	sdt varchar(12) NOT NULL,
	chucVu varchar(15) NOT NULL,
	gioiTinh bit NOT NULL,
	CCCD nvarchar(20) NOT NULL,
	tinhTrang bit NOT NULL,
	lyDoNghiViec varchar(15) default '',
);


CREATE TABLE TaiKhoan (
   maNV varchar(5) not null,
   matKhau varchar(6) NOT NULL DEFAULT '1111',
   chucVu varchar(15) NOT NULL,
   trangThai bit not null default '0'
   constraint FK_MaNV Foreign key(maNV) references NhanVien(maNV)
)

GO



alter table TaiKhoan
add primary key (maNV)

go
CREATE TABLE NhaCungCap (
    maNCC varchar(6) PRIMARY KEY,
    tenNCC varchar(15) NOT NULL,
    sdt varchar(12) NOT NULL,
    diaChi varchar(50) NOT NULL,
	tinhTrang bit not null,  
);


CREATE TABLE Sach (
    
    maSach varchar(5) PRIMARY KEY,
    tenSach varchar(50) NOT NULL,
    maNCC varchar(6) NOT NULL,
    donGiaNhap Money NOT NULL,
	soLuong int not null,
	TheLoai varchar(20) not null,
	maNXB varchar(6) not null,
	maTacGia varchar(5) not null,
	hinhAnh NVARCHAR(MAX) not null,
	

	 Foreign key(maNCC) references NhaCungCap(maNCC),
	 Foreign key(TheLoai) references TheLoai(maTheLoai),
	 Foreign key(maNXB) references NhaXuatBan(maNXB),
	 Foreign key(maTacGia) references TacGia(maTacGia),
   
);


CREATE TABLE HoaDon (
    maHD VARCHAR(5),
	maNV varchar(5),
	maKH varchar(5) ,
    ngayLap DateTime NOT NULL default Getdate(),
    
	VAT float not null,
	maQuay varchar(3) not null,
	
	uuDai float not null,
	tongTien float not null,

	Primary key(maHD),
	FOREIGN KEY(maNV) REFERENCES NhanVien(maNV),
	FOREIGN KEY(maKH) REFERENCES KhachHang(maKH),
);



CREATE TABLE CTHoaDon(
   maHD VARCHAR(5),
   maSach varchar(5) NOT NULL ,
   tenSach varchar(50) NOT NULL,
   soLuong INT NOT NULL,
   donGia money not null,
   thanhTien money not null,
   Foreign key(maHD) references HoaDon(maHD),
   Foreign key(maSach) references Sach(maSach),
) 




go
create trigger create_account
on NhanVien After insert 
as 
begin
	Declare @maNhanVien nvarchar(6) , @chucVu nvarchar(15)
	Set @maNhanVien = (SELECT maNV from inserted)
	Set @chucVu = (Select  chucVu from inserted )
	insert into TaiKhoan(maNV ,chucVu)
	Values (@maNhanVien , @chucVu)
end


insert TheLoai(maTheLoai , tenTheLoai)
values ('VanHoc' , 'VanHoc')

insert TacGia(maTacGia , tenTacGia)
values ('TG001' , 'Hai')

insert into Nh
insert NhaXuatBan(maNXB , tenNXB , diaChi)
values('NXB001' , 'Kim Dong' , '12 Nguyen Van Bao')
insert into TaiKhoan values ('NV002','1111','NhanVien','0');
select * from NhanVien
insert NhanVien(maNV, hoTenNV , ngaySinh , diaChi , sdt , chucVu , gioiTinh,CCCD , tinhTrang)
values('NV003' , 'Gia Thuan' , '2023/11/06' , 'HCM' , '096123' , 'QuanLy' , 'true' , '046123' ,'true')