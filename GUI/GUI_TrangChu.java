package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import ConnectDB.ConnectDB;
import DAO.TaiKhoan_Dao;
import GUI.GUI_NhaCungCap;
import GUI.GUI_NhanVien;
import entity.TaiKhoan;

import javax.swing.UIManager;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import java.awt.SystemColor;
import java.awt.Window;
import java.awt.Button;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;

public class GUI_TrangChu extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTable table;
	private JTextField textField_4;
	private Color normalColor;
	private Color pressedColor;
	private TaiKhoan_Dao tk_dao = new TaiKhoan_Dao();
	List<TaiKhoan> dstk = new ArrayList<TaiKhoan>();
	private Window frame = new JFrame();
	private String maNV = "user";
	private String hoTenNhanVien = "User Name";
	private String chucVu = "Eror";
	private static int flagDangXuat = -1;
	private boolean laPhimF1 = false;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				
				
				 
				try {
					GUI_TrangChu frameTrangChu = new GUI_TrangChu();
					frameTrangChu.setLocationRelativeTo(null);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI_TrangChu() {
		
		ConnectDB.getInstance().connect();
		setTitle("Trang chủ");
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		
		setBounds(100, 100, 1150, 680);
		contentPane = new JPanel();
		contentPane.setForeground(new Color(234, 198, 150));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		
//		Load Các thông thông tin của nhân viên sử dụng hệ thống
		dstk = tk_dao.getAllTaiKhoan();
		for (TaiKhoan taiKhoan : dstk) {
			if(taiKhoan.isTrangThai() == true) {
				maNV = taiKhoan.getMaNV();
				hoTenNhanVien = taiKhoan.getHoTenNhanVien();
				chucVu = taiKhoan.getChucVu();
			}
		}
		
		
		
//		Sự kiện đóng chương trình KHI ấn nút x để thoát chương trình thì nó sẽ tự động đăng xuất
		this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int choice = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn thoát không?", "Xác nhận thoát", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                	tk_dao.dangXuat(maNV);
                    System.exit(0);
                }
            }
        });
		
		
		
		
		this.addKeyListener(new KeyAdapter() {
			 public void keyPressed(KeyEvent e) {
	                if (e.getKeyCode() == KeyEvent.VK_F1) { //F1: Khách Hàng  
	                	GUI_KhacHang guiKhachHang = new GUI_KhacHang();
	               	 	guiKhachHang.setVisible(true);
	               	 	dispose();
	               	 	
	                }else if(e.getKeyCode() == KeyEvent.VK_F2) { //F2 : Sách 
	                	GUI_Sach guiSach = new GUI_Sach();
	                	guiSach.setVisible(true);
	                	dispose();
	                }else if(e.getKeyCode() == KeyEvent.VK_F3) { //F3 : Tác Giả 
	                	GUI_TacGia guiTacGia = new GUI_TacGia();
	                	guiTacGia.setVisible(true);
	                	dispose();
	                }else if(e.getKeyCode() == KeyEvent.VK_F4) { //F4 : Nhà Xuất Bản 
	                	GUI_NhaXuatBan guiNXB = new GUI_NhaXuatBan();
	                	guiNXB.setVisible(true);
	                	dispose();
	                }else if(e.getKeyCode() == KeyEvent.VK_F5) { //F5 : Thể Loại 
	                	GUI_TheLoai theLoai = new GUI_TheLoai();
	                	theLoai.setVisible(true);
	                	dispose();
	                }else if(e.getKeyCode() == KeyEvent.VK_F6) { //F6 : Tạo Hóa Đơn 
	                	GUI_TaoHoaDon guiTaoHD = new GUI_TaoHoaDon();
	                	guiTaoHD.setVisible(true);
	                	dispose();
	                }else if(e.getKeyCode() == KeyEvent.VK_F7) { //F7 : Xem Chi Tiết Hóa Đơn
	                	GUI_XemChiTietHoaDon guiChiTietHD = new GUI_XemChiTietHoaDon();
	                	guiChiTietHD.setVisible(true);
	                	dispose();
	                }else if(e.getKeyCode() == KeyEvent.VK_F8) { //F8 : Quản Lý Nhân Viên 
	                	if(chucVu.equals("Nhân Viên Bán Hàng")) {
	                		JOptionPane.showMessageDialog(null, "Bạn Không Có Quyền Truy Cập Vào Chức Năng Này", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
	                	}else {
	                		GUI_NhanVien guiNhanVien = new GUI_NhanVien();
		                	guiNhanVien.setVisible(true);
		                	dispose();
	                	}
	                	
	                	
	                }else if(e.getKeyCode() == KeyEvent.VK_F9) { //F9 : Xem Thông Tin Nhân Viên Bị Xóa 
	                	if(chucVu.equals("Nhân Viên Bán Hàng")) {
	                		JOptionPane.showMessageDialog(null, "Bạn Không Có Quyền Truy Cập Vào Chức Năng Này", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
	                	}else {
	                		GUI_XemTTNVbixoa guiNhanVienBiXoa = new GUI_XemTTNVbixoa();
		                	guiNhanVienBiXoa.setVisible(true);
		                	dispose();
	                	}
	                	
	                	
	                }else if(e.getKeyCode() == KeyEvent.VK_F10) { //F10 : Nhà Cung Cấp 
	                	if(chucVu.equals("Nhân Viên Bán Hàng")) {
	                		JOptionPane.showMessageDialog(null, "Bạn Không Có Quyền Truy Cập Vào Chức Năng Này", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
	                	}else {
	                		GUI_NhaCungCap guiNCC = new GUI_NhaCungCap();
		                	guiNCC.setVisible(true);
		                	dispose();;
	                	}
	                	
	                	
	                }else if(e.getKeyCode() == KeyEvent.VK_F11) { //F11 : Hướng Dẫn Sử Dụng 
	                	try {
							Desktop.getDesktop().browse(new URL("https://hieunhan1919.github.io/ptudwebhelp/").toURI());
						} catch (IOException | URISyntaxException e1) {
							e1.printStackTrace();
						}
	                	
//	                	dispose();
	                	
	                }else if(e.getKeyCode() == KeyEvent.VK_F12) { //F12 Đổi Mật Khẩu
	                	GUI_DoiMK guiDoiMK = new GUI_DoiMK();
	                	guiDoiMK.setVisible(true);
	                	dispose();
	                }else if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
	                	GUI_TrangChu guiTrangChu = new GUI_TrangChu();
	                	guiTrangChu.setVisible(true);
	                	dispose();
	                }
	         }
			
		});
		
		

		this.setFocusable(true); // Để bắt sự kiện từ frame
		this.requestFocus(); // Tập trung vào frame để nhận sự kiện từ bàn phím
		
		
		
		
		
		
		
		
		
		
		
	
		
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(17, 57, 70));
		panel.setBounds(0, 0, 165, 58);
		contentPane.add(panel);
		
		 JLabel lblNewLabel_1 = new JLabel("");
		 panel.add(lblNewLabel_1);
		 lblNewLabel_1.setForeground(new Color(255, 255, 255));
		 lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 32));
		 lblNewLabel_1.setBackground(new Color(17, 57, 70));
		 lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblNewLabel = new JLabel("Trang chủ");
		lblNewLabel.setForeground(new Color(234, 198, 150));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 35));
		lblNewLabel.setBounds(475, -10, 203, 68);
		contentPane.add(lblNewLabel);
		
		JLabel lblTrietly = new JLabel("GIÁ TRỊ THẬT CỦA MỘT CUỐN SÁCH KHÔNG THỂ NẰM HẾT Ở TRANG BÌA");
		lblTrietly.setHorizontalAlignment(SwingConstants.CENTER);
		lblTrietly.setForeground(new Color(234, 198, 150));
		lblTrietly.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblTrietly.setBounds(0, 114, 1143, 68);
		contentPane.add(lblTrietly);
		
		JButton btnKhachHang = new JButton("Khách hàng");
		btnKhachHang.setVerticalAlignment(SwingConstants.BOTTOM);
		btnKhachHang.setHorizontalAlignment(SwingConstants.LEFT);
		btnKhachHang.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/khachhang_img.png")));
		btnKhachHang.setForeground(new Color(255, 255, 255));
		btnKhachHang.setBackground(new Color(17, 57, 70));
		btnKhachHang.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnKhachHang.setBounds(0, 59, 165, 58);
		btnKhachHang.setContentAreaFilled(false);
		btnKhachHang.setFocusPainted(false);
		btnKhachHang.setOpaque(true);
		contentPane.add(btnKhachHang);
		
		btnKhachHang.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseEntered(MouseEvent e) {
		    	btnKhachHang.setBackground(new Color(195,129,84));
		    }

		    @Override
		    public void mouseExited(MouseEvent e) {
		    	btnKhachHang.setBackground(new Color(17, 57, 70));
		    }
		});
		
		
	       JPopupMenu popupMenu = new JPopupMenu();
	        JMenuItem menuItemThem = new JMenuItem("QL Khách Hàng (F1)");
	        menuItemThem.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/add_img.png")));
	        menuItemThem.setForeground(new Color(255, 255, 255)); // Set text color
	        menuItemThem.setBackground(new Color(136, 74, 57)); // Set background color
	        menuItemThem.setFont(new Font("Tahoma", Font.PLAIN, 14)); // Set font
	        menuItemThem.setPreferredSize(new Dimension(163, 58));
	        menuItemThem.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					GUI_KhacHang guiKH = new GUI_KhacHang();
					guiKH.setVisible(true);
					guiKH.setLocationRelativeTo(null);
		            dispose();

				}
			});
	        popupMenu.add(menuItemThem);
	        
//	        JMenuItem menuItemCapNhat = new JMenuItem("Cập nhật khách hàng");
//	        menuItemCapNhat.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/updated_img.png")));
//	        menuItemCapNhat.setForeground(new Color(255, 255, 255));
//	        menuItemCapNhat.setBackground(new Color(136, 74, 57));
//	        menuItemCapNhat.setFont(new Font("Tahoma", Font.PLAIN, 12));
//	        menuItemCapNhat.setPreferredSize(new Dimension(163, 58));
//	        menuItemCapNhat.addActionListener(new ActionListener() {
//				
//				@Override
//				public void actionPerformed(ActionEvent e) {
//					GUI_KhacHang guiKH = new GUI_KhacHang();
//					guiKH.setVisible(true);
//					guiKH.setLocationRelativeTo(null);
//		            dispose();
//
//				}
//			});
//	        popupMenu.add(menuItemCapNhat);
//	        
//	        JMenuItem menuItemTimKiem = new JMenuItem("Tìm khách hàng");
//	        menuItemTimKiem.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/loupe_img.png")));
//	        menuItemTimKiem.setForeground(new Color(255, 255, 255));
//	        menuItemTimKiem.setBackground(new Color(136, 74, 57));
//	        menuItemTimKiem.setFont(new Font("Tahoma", Font.PLAIN, 14));
//	        menuItemTimKiem.setPreferredSize(new Dimension(163, 58));
//	        menuItemTimKiem.addActionListener(new ActionListener() {
//				
//				@Override
//				public void actionPerformed(ActionEvent e) {
//					GUI_KhacHang guiKH = new GUI_KhacHang();
//					guiKH.setVisible(true);
//					guiKH.setLocationRelativeTo(null);
//		            dispose();
//
//				}
//			});
//	        popupMenu.add(menuItemTimKiem);


	        btnKhachHang.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                // Show the pop-up menu when the "Khách hàng" button is clicked
	                popupMenu.show(btnKhachHang, 0, btnKhachHang.getHeight());
	            }
	        });

	        menuItemThem.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                // Add your logic for "Thêm" here
	            }
	        });

//	        menuItemCapNhat.addActionListener(new ActionListener() {
//	            public void actionPerformed(ActionEvent e) {
//	                // Add your logic for "Cập nhật" here
//	            }
//	        });
//
//	        menuItemTimKiem.addActionListener(new ActionListener() {
//	            public void actionPerformed(ActionEvent e) {
//	                // Add your logic for "tìm kiếm" here
//	            }
//	        });
		
	        JButton btnSach = new JButton("Sách\r\n");
			btnSach.setHorizontalAlignment(SwingConstants.LEFT);
			btnSach.setVerticalAlignment(SwingConstants.TOP);
			btnSach.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/sach_img.png")));
			btnSach.setForeground(new Color(255, 255, 255));
			btnSach.setBackground(new Color(17, 57, 70));
			btnSach.setFont(new Font("Tahoma", Font.PLAIN, 14));
			btnSach.setBounds(164, 59, 159, 58);
			btnSach.setContentAreaFilled(false);
			btnSach.setFocusPainted(false);
			btnSach.setOpaque(true);
			contentPane.add(btnSach);
			
			btnSach.addMouseListener(new MouseAdapter() {
			    @Override
			    public void mouseEntered(MouseEvent e) {
			    	btnSach.setBackground(new Color(195,129,84));
			    }

			    @Override
			    public void mouseExited(MouseEvent e) {
			    	btnSach.setBackground(new Color(17, 57, 70));
			    }
			});
			
			
		     JPopupMenu popupMenuSach = new JPopupMenu();
		        JMenuItem menuItemThemSach = new JMenuItem("QL Sách (F2)");
		        menuItemThemSach.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/add_img.png")));
		        menuItemThemSach.setForeground(new Color(255, 255, 255)); 
		        menuItemThemSach.setBackground(new Color(136, 74, 57)); 
		        menuItemThemSach.setFont(new Font("Tahoma", Font.PLAIN, 14)); 
		        menuItemThemSach.setPreferredSize(new Dimension(157, 58));
		        menuItemThemSach.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						GUI_Sach gui_Sach = new GUI_Sach();
						gui_Sach.setVisible(true);
			            dispose();

					}
				});
		        popupMenuSach.add(menuItemThemSach);
		        
		        JMenuItem menuItemThemTg = new JMenuItem("QL Tác giả (F3)");
		        menuItemThemTg.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/add_img.png")));
		        menuItemThemTg.setForeground(new Color(255, 255, 255)); 
		        menuItemThemTg.setBackground(new Color(136, 74, 57));
		        menuItemThemTg.setFont(new Font("Tahoma", Font.PLAIN, 14)); 
		        menuItemThemTg.setPreferredSize(new Dimension(157, 58));
		        menuItemThemTg.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						GUI_TacGia gui_Tg = new GUI_TacGia();
						gui_Tg.setVisible(true);
			            dispose();

					}
				});
		        popupMenuSach.add(menuItemThemTg);
		        
		        JMenuItem menuItemThemNXB = new JMenuItem("QL Nhà Xuất Bản (F4)");
		        menuItemThemNXB.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/add_img.png")));
		        menuItemThemNXB.setForeground(new Color(255, 255, 255)); 
		        menuItemThemNXB.setBackground(new Color(136, 74, 57)); 
		        menuItemThemNXB.setFont(new Font("Tahoma", Font.PLAIN, 12)); 
		        menuItemThemNXB.setPreferredSize(new Dimension(157, 58));
		        menuItemThemNXB.addActionListener(new ActionListener() {
		  				public void actionPerformed(ActionEvent e) {
		  					GUI_NhaXuatBan gui_NXB = new GUI_NhaXuatBan();
		  					gui_NXB.setVisible(true);
				            dispose();

		  				}
		  			});
		        popupMenuSach.add(menuItemThemNXB);
		        
		        JMenuItem menuItemThemTL = new JMenuItem("QL Thể Loại (F5)");
		        menuItemThemTL.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/add_img.png")));
		        menuItemThemTL.setForeground(new Color(255, 255, 255)); 
		        menuItemThemTL.setBackground(new Color(136, 74, 57)); 
		        menuItemThemTL.setFont(new Font("Tahoma", Font.PLAIN, 14)); 
		        menuItemThemTL.setPreferredSize(new Dimension(157, 58));
		        menuItemThemTL.addActionListener(new ActionListener() {
		  				public void actionPerformed(ActionEvent e) {
		  					GUI_TheLoai gui_TheLoai = new GUI_TheLoai();
		  					gui_TheLoai.setVisible(true);
				            dispose();

		  				}
		  			});
		        popupMenuSach.add(menuItemThemTL);
		        
		        btnSach.addActionListener(new ActionListener() {
		        	  public void actionPerformed(ActionEvent e) {
			                
		        		  popupMenuSach.show(btnSach, 0, btnSach.getHeight());
			            }
			        });
		        menuItemThemSach.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		               
		            }
		        });
		        popupMenuSach.add(menuItemThemSach);

		      
		       
		        menuItemThemTg.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		              
		            }
		        });
		        popupMenuSach.add(menuItemThemTg);

		      
		        
		        menuItemThemNXB.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		            
		            }
		        });
		        popupMenuSach.add(menuItemThemNXB);

		       
		       
		        menuItemThemTL.addActionListener(new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		             
		            }
		        });
		        popupMenuSach.add(menuItemThemTL);

		       
		        btnSach.setComponentPopupMenu(popupMenuSach);
	   

		
		
		
		JButton btnHoaDon = new JButton("Hóa đơn");
		btnHoaDon.setHorizontalAlignment(SwingConstants.LEFT);
		btnHoaDon.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/bill_imng.png")));
		btnHoaDon.setForeground(new Color(255, 255, 255));
		btnHoaDon.setBackground(new Color(17, 57, 70));
		btnHoaDon.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnHoaDon.addActionListener(new ActionListener() {	
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnHoaDon.setBounds(322, 59, 165, 58);
		btnHoaDon.setContentAreaFilled(false);
		btnHoaDon.setFocusPainted(false);
		btnHoaDon.setOpaque(true);
		contentPane.add(btnHoaDon);
		
		btnHoaDon.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseEntered(MouseEvent e) {
		    	btnHoaDon.setBackground(new Color(195,129,84));
		    }

		    @Override
		    public void mouseExited(MouseEvent e) {
		    	btnHoaDon.setBackground(new Color(17, 57, 70));
		    }
		});
		
		JPopupMenu popupMenuHoaDon = new JPopupMenu();
		JMenuItem menuItemTaoHoaDon = new JMenuItem("Tạo hóa đơn (F6)");
		menuItemTaoHoaDon.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/add_img.png")));
		menuItemTaoHoaDon.setForeground(new Color(255, 255, 255));
		menuItemTaoHoaDon.setBackground(new Color(136, 74, 57));
		menuItemTaoHoaDon.setFont(new Font("Tahoma", Font.PLAIN, 14));
		menuItemTaoHoaDon.setPreferredSize(new Dimension(163, 58));
		menuItemTaoHoaDon.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				GUI_TaoHoaDon guiHoaDon = new GUI_TaoHoaDon();
				guiHoaDon.setVisible(true);
				dispose();
				
			}
		});
		popupMenuHoaDon.add(menuItemTaoHoaDon);

		
		JMenuItem menuItemXemChiTietHoaDon = new JMenuItem("Xem chi tiết hóa đơn (F7)");
		menuItemXemChiTietHoaDon.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/payment_img.png")));
		menuItemXemChiTietHoaDon.setForeground(new Color(255, 255, 255));
		menuItemXemChiTietHoaDon.setBackground(new Color(136, 74, 57));
		menuItemXemChiTietHoaDon.setFont(new Font("Tahoma", Font.PLAIN, 12));
		menuItemXemChiTietHoaDon.setPreferredSize(new Dimension(163, 58));
		menuItemXemChiTietHoaDon.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				GUI_XemChiTietHoaDon guiqlyHoaDon = new GUI_XemChiTietHoaDon();
				guiqlyHoaDon.setVisible(true);
				dispose();
				
			}
		});
		popupMenuHoaDon.add(menuItemXemChiTietHoaDon);



		btnHoaDon.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		     
		        popupMenuHoaDon.show(btnHoaDon, 0, btnHoaDon.getHeight());
		    }
		});

		menuItemTaoHoaDon.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		      
		    }
		});

		menuItemXemChiTietHoaDon.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		   
		    }
		});
		
		JButton btnNhanVIen = new JButton("Nhân viên");
		btnNhanVIen.setHorizontalAlignment(SwingConstants.LEFT);
		btnNhanVIen.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/nhanvien_img.png")));
		btnNhanVIen.setForeground(new Color(255, 255, 255));
		btnNhanVIen.setBackground(new Color(17, 57, 70));
		btnNhanVIen.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		btnNhanVIen.setBounds(486, 59, 165, 58);
		btnNhanVIen.setContentAreaFilled(false);
		btnNhanVIen.setFocusPainted(false);
		btnNhanVIen.setOpaque(true);
		contentPane.add(btnNhanVIen);
		
		btnNhanVIen.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseEntered(MouseEvent e) {
		    	btnNhanVIen.setBackground(new Color(195,129,84));
		    }

		    @Override
		    public void mouseExited(MouseEvent e) {
		    	btnNhanVIen.setBackground(new Color(17, 57, 70));
		    }
		});
		
		
		JPopupMenu popupMenuNhanVien = new JPopupMenu();
		JMenuItem menuItemThemNhanVien = new JMenuItem("QL Nhân Viên (F8)");
		menuItemThemNhanVien.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/add_img.png")));
		menuItemThemNhanVien.setForeground(new Color(255, 255, 255));
		menuItemThemNhanVien.setBackground(new Color(136, 74, 57));
		menuItemThemNhanVien.setFont(new Font("Tahoma", Font.PLAIN, 14));
		menuItemThemNhanVien.setPreferredSize(new Dimension(163, 58));
		menuItemThemNhanVien.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(chucVu.trim().equals("Nhân Viên Bán Hàng")) {
					JOptionPane.showMessageDialog(null, "Bạn Không Có Quyền Truy Cập Vào Chức Năng Này", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
				}else {
					GUI_NhanVien guiNhanVien = new GUI_NhanVien();
					guiNhanVien.setVisible(true);
					dispose();
				}
				
			}

		});
		popupMenuNhanVien.add(menuItemThemNhanVien);

		
//		JMenuItem menuItemCapNhatNhanVien = new JMenuItem("Cập nhật nhân viên");
//		menuItemCapNhatNhanVien.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/updated_img.png")));
//		menuItemCapNhatNhanVien.setForeground(new Color(255, 255, 255));
//		menuItemCapNhatNhanVien.setBackground(new Color(136, 74, 57));
//		menuItemCapNhatNhanVien.setFont(new Font("Tahoma", Font.PLAIN, 12));
//		menuItemCapNhatNhanVien.setPreferredSize(new Dimension(163, 58));
//		menuItemCapNhatNhanVien.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				if(chucVu.trim().equals("Nhân Viên Bán Hàng")) {
//					JOptionPane.showMessageDialog(null, "Bạn Không Có Quyền Truy Cập Vào Chức Năng Này", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
//				}else {
//					GUI_NhanVien guiNhanVien = new GUI_NhanVien();
//					guiNhanVien.setVisible(true);
//					dispose();
//				}
//				
//			}
//
//		});
//		popupMenuNhanVien.add(menuItemCapNhatNhanVien);
//
//		
//		JMenuItem menuItemTimKiemNhanVien = new JMenuItem("Tìm nhân viên");
//		menuItemTimKiemNhanVien.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/loupe_img.png")));
//		menuItemTimKiemNhanVien.setForeground(new Color(255, 255, 255));
//		menuItemTimKiemNhanVien.setBackground(new Color(136, 74, 57));
//		menuItemTimKiemNhanVien.setFont(new Font("Tahoma", Font.PLAIN, 14));
//		menuItemTimKiemNhanVien.setPreferredSize(new Dimension(163, 58));
//		menuItemTimKiemNhanVien.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				if(chucVu.trim().equals("Nhân Viên Bán Hàng")) {
//					JOptionPane.showMessageDialog(null, "Bạn Không Có Quyền Truy Cập Vào Chức Năng Này", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
//				}else {
//					GUI_NhanVien guiNhanVien = new GUI_NhanVien();
//					guiNhanVien.setVisible(true);
//					dispose();
//				}
//				
//			}
//
//		});
//		popupMenuNhanVien.add(menuItemTimKiemNhanVien);
//
//		
//		JMenuItem menuItemXoaNhanVien = new JMenuItem("Xóa nhân viên");
//		menuItemXoaNhanVien.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/delete_img.png")));
//		menuItemXoaNhanVien.setForeground(new Color(255, 255, 255));
//		menuItemXoaNhanVien.setBackground(new Color(136, 74, 57));
//		menuItemXoaNhanVien.setFont(new Font("Tahoma", Font.PLAIN, 14));
//		menuItemXoaNhanVien.setPreferredSize(new Dimension(163, 58));
//		menuItemXoaNhanVien.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				if(chucVu.trim().equals("Nhân Viên Bán Hàng")) {
//					JOptionPane.showMessageDialog(null, "Bạn Không Có Quyền Truy Cập Vào Chức Năng Này", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
//				}else {
//					GUI_NhanVien guiNhanVien = new GUI_NhanVien();
//					guiNhanVien.setVisible(true);
//					dispose();
//				}
//				
//			}
//
//		});
//		
//		
//		
//		popupMenuNhanVien.add(menuItemXoaNhanVien);

	
		JMenuItem menuItemXemNhanVienBiXoa = new JMenuItem("Xem nhân viên bị xóa (F9)");
		menuItemXemNhanVienBiXoa.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/erase_img.png")));
		menuItemXemNhanVienBiXoa.setForeground(new Color(255, 255, 255));
		menuItemXemNhanVienBiXoa.setBackground(new Color(136, 74, 57));
		menuItemXemNhanVienBiXoa.setFont(new Font("Tahoma", Font.PLAIN, 12));
		menuItemXemNhanVienBiXoa.setPreferredSize(new Dimension(163, 58));
		popupMenuNhanVien.add(menuItemXemNhanVienBiXoa);

		menuItemXemNhanVienBiXoa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chucVu.trim().equals("Nhân Viên Bán Hàng")) {
					JOptionPane.showMessageDialog(null, "Bạn Không Có Quyền Truy Cập Vào Chức Năng Này", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
				}else {
					GUI_XemTTNVbixoa guiNhanVienBiXoa = new GUI_XemTTNVbixoa();
					guiNhanVienBiXoa.setVisible(true);
					dispose();
				}
				
			}
		});
		btnNhanVIen.setComponentPopupMenu(popupMenuNhanVien);

		
		btnNhanVIen.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(chucVu.trim().equals("Nhân Viên Bán Hàng")) {
					JOptionPane.showMessageDialog(null, "Bạn Không Có Quyền Truy Cập Vào Chức Năng Này", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
				}else {
					GUI_NhanVien guiNhanVien = new GUI_NhanVien();
					popupMenuNhanVien.show(btnNhanVIen, 0, btnNhanVIen.getHeight());
					guiNhanVien.setVisible(true);
					dispose();
				}
				
			}
			
		   
		});

		menuItemThemNhanVien.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		       
		    }
		});

		

		menuItemXemNhanVienBiXoa.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		  
		    }
		});
		
		JButton btnNhaCungCap = new JButton("Nhà cung cấp");
		btnNhaCungCap.setHorizontalAlignment(SwingConstants.LEFT);
		btnNhaCungCap.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/ncc_img.png")));
		btnNhaCungCap.setForeground(new Color(255, 255, 255));
		btnNhaCungCap.setBackground(new Color(17, 57, 70));
		btnNhaCungCap.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnNhaCungCap.setBounds(650, 59, 165, 58);
		btnNhaCungCap.setContentAreaFilled(false);
		btnNhaCungCap.setFocusPainted(false);
		btnNhaCungCap.setOpaque(true);
		contentPane.add(btnNhaCungCap);
		
		btnNhaCungCap.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseEntered(MouseEvent e) {
		    	btnNhaCungCap.setBackground(new Color(195,129,84));
		    }

		    @Override
		    public void mouseExited(MouseEvent e) {
		    	btnNhaCungCap.setBackground(new Color(17, 57, 70));
		    }
		});
		
		
		
		JPopupMenu popupMenuNCC = new JPopupMenu();
		JMenuItem menuItemThemNCC = new JMenuItem("QL NCC (F10)");
		menuItemThemNCC.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/add_img.png")));
		menuItemThemNCC.setForeground(new Color(255, 255, 255));
		menuItemThemNCC.setBackground(new Color(136, 74, 57));
		menuItemThemNCC.setFont(new Font("Tahoma", Font.PLAIN, 14));
		menuItemThemNCC.setPreferredSize(new Dimension(163, 58));
		menuItemThemNCC.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				GUI_NhaCungCap guiNhaCungCap = new GUI_NhaCungCap();
				guiNhaCungCap.setVisible(true);
				dispose();
				
			}
		});
		popupMenuNCC.add(menuItemThemNCC);

		
//		JMenuItem menuItemCapNhatNCC = new JMenuItem("Cập nhật NCC");
//		menuItemCapNhatNCC.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/updated_img.png")));
//		menuItemCapNhatNCC.setForeground(new Color(255, 255, 255));
//		menuItemCapNhatNCC.setBackground(new Color(136, 74, 57));
//		menuItemCapNhatNCC.setFont(new Font("Tahoma", Font.PLAIN, 14));
//		menuItemCapNhatNCC.setPreferredSize(new Dimension(163, 58));
//		menuItemCapNhatNCC.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				GUI_NhaCungCap guiNhaCungCap = new GUI_NhaCungCap();
//				guiNhaCungCap.setVisible(true);
//				dispose();
//				
//			}
//		});
//		popupMenuNCC.add(menuItemCapNhatNCC);
//
//	
//		JMenuItem menuItemTimKiemNCC = new JMenuItem("Tìm NCC");
//		menuItemTimKiemNCC.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/loupe_img.png")));
//		menuItemTimKiemNCC.setForeground(new Color(255, 255, 255));
//		menuItemTimKiemNCC.setBackground(new Color(136, 74, 57));
//		menuItemTimKiemNCC.setFont(new Font("Tahoma", Font.PLAIN, 14));
//		menuItemTimKiemNCC.setPreferredSize(new Dimension(163, 58));
//		menuItemTimKiemNCC.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				GUI_NhaCungCap guiNhaCungCap = new GUI_NhaCungCap();
//				guiNhaCungCap.setVisible(true);
//				dispose();
//				
//			}
//		});
//		popupMenuNCC.add(menuItemTimKiemNCC);

		
		btnNhaCungCap.setComponentPopupMenu(popupMenuNCC);

	
		btnNhaCungCap.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if(chucVu.trim().equals("Nhân Viên Bán Hàng")) {
					JOptionPane.showMessageDialog(null, "Bạn Không Có Quyền Truy Cập Vào Chức Năng Này", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
				}else {
					GUI_NhaCungCap guiNCC = new GUI_NhaCungCap();
					guiNCC.setVisible(true);
					 popupMenuNCC.show(btnNhaCungCap, 0, btnNhaCungCap.getHeight());
					dispose();
				}
				
			}

		   
		});

		menuItemThemNCC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chucVu.trim().equals("Nhân Viên Bán Hàng")) {
					JOptionPane.showMessageDialog(null, "Bạn Không Có Quyền Truy Cập Vào Chức Năng Này", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
				}else {
					GUI_NhaCungCap guiNCC = new GUI_NhaCungCap();
					guiNCC.setVisible(true);
					dispose();
				}
				
			}

		});

//		menuItemCapNhatNCC.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				if(chucVu.trim().equals("Nhân Viên Bán Hàng")) {
//					JOptionPane.showMessageDialog(null, "Bạn Không Có Quyền Truy Cập Vào Chức Năng Này", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
//				}else {
//					GUI_NhaCungCap guiNCC = new GUI_NhaCungCap();
//					guiNCC.setVisible(true);
//					dispose();
//				}
//				
//			}
//
//		});
//
//		menuItemTimKiemNCC.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				if(chucVu.trim().equals("Nhân Viên Bán Hàng")) {
//					JOptionPane.showMessageDialog(null, "Bạn Không Có Quyền Truy Cập Vào Chức Năng Này", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
//				}else {
//					GUI_NhaCungCap guiNCC = new GUI_NhaCungCap();
//					guiNCC.setVisible(true);
//					dispose();
//				}
//				
//			}
//
//		});
		
		JButton btnTroGiup = new JButton("Trợ giúp");
		btnTroGiup.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/help_img.png")));
		btnTroGiup.setHorizontalAlignment(SwingConstants.LEFT);
		btnTroGiup.setForeground(new Color(255, 255, 255));
		btnTroGiup.setBackground(new Color(17, 57, 70));
		btnTroGiup.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnTroGiup.setBounds(814, 59, 165, 58);
		btnTroGiup.setContentAreaFilled(false);
		btnTroGiup.setFocusPainted(false);
		btnTroGiup.setOpaque(true);
		contentPane.add(btnTroGiup);
		
		btnTroGiup.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseEntered(MouseEvent e) {
		    	btnTroGiup.setBackground(new Color(195,129,84));
		    }

		    @Override
		    public void mouseExited(MouseEvent e) {
		    	btnTroGiup.setBackground(new Color(17, 57, 70));
		    }
		});
		
		JPopupMenu popupMenuTrGip = new JPopupMenu();

	
		JMenuItem menuItemHuongDan = new JMenuItem("Hướng dẫn sử dụng (F11)");
		menuItemHuongDan.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/manual_img.png")));
		menuItemHuongDan.setForeground(new Color(255, 255, 255));
		menuItemHuongDan.setBackground(new Color(136, 74, 57));
		menuItemHuongDan.setFont(new Font("Tahoma", Font.PLAIN, 12));
		menuItemHuongDan.setPreferredSize(new Dimension(163, 58));
		popupMenuTrGip.add(menuItemHuongDan);
// 		ấn vào hướng dẫn sẽ hiện ra trang web hướng dẫn sử dụng 
		menuItemHuongDan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Desktop.getDesktop().browse(new URL("https://hieunhan1919.github.io/ptudwebhelp/").toURI());
				}
				catch(Exception ex){}
			}
		});
		
		JMenuItem menuItemDoiMatKhau = new JMenuItem("Đổi mật khẩu (F12)");
		menuItemDoiMatKhau.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/reload_img.png")));
		menuItemDoiMatKhau.setForeground(new Color(255, 255, 255));
		menuItemDoiMatKhau.setBackground(new Color(136, 74, 57));
		menuItemDoiMatKhau.setFont(new Font("Tahoma", Font.PLAIN, 14));
		menuItemDoiMatKhau.setPreferredSize(new Dimension(163, 58));
		menuItemDoiMatKhau.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				GUI_DoiMK guiDMK = new GUI_DoiMK();
				guiDMK.setVisible(true);
				dispose();
				
			}
		});
		popupMenuTrGip.add(menuItemDoiMatKhau);

		
		btnTroGiup.setComponentPopupMenu(popupMenuTrGip);

	
		btnTroGiup.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		       
		        popupMenuTrGip.show(btnTroGiup, 0, btnTroGiup.getHeight());
		    }
		});

		menuItemHuongDan.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		   
		    }
		});

		menuItemDoiMatKhau.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		
		    }
		});
		
		JButton btnThoat = new JButton("Thoát (Alt + 4)");
		btnThoat.setHorizontalAlignment(SwingConstants.LEFT);
		btnThoat.setIcon(new ImageIcon(GUI_TrangChu.class.getResource("/imgs/exit_img.png")));
		btnThoat.setForeground(new Color(255, 255, 255));
		btnThoat.setBackground(new Color(17, 57, 70));
		btnThoat.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnThoat.setBounds(978, 59, 165, 58);
		btnThoat.setContentAreaFilled(false);
		btnThoat.setFocusPainted(false);
		btnThoat.setOpaque(true);
		contentPane.add(btnThoat);
		
		
		btnThoat.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseEntered(MouseEvent e) {
		    	btnThoat.setBackground(new Color(195,129,84));
		    }

		    @Override
		    public void mouseExited(MouseEvent e) {
		    	btnThoat.setBackground(new Color(17, 57, 70));
		    }
		});
		
		
		
		JButton btnDangXuat = new JButton("Đăng xuất");
		btnDangXuat.setHorizontalAlignment(SwingConstants.LEFT);
		btnDangXuat.setIcon(new ImageIcon(GUI_TrangChu.class.getResource("/imgs/logout_img.png")));
		btnDangXuat.setForeground(new Color(255, 255, 255));
		btnDangXuat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnDangXuat.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnDangXuat.setBackground(new Color(17, 57, 70));
		btnDangXuat.setBounds(946, 33, 190, 27);
		btnDangXuat.setContentAreaFilled(false);
		btnDangXuat.setFocusPainted(false);
		btnDangXuat.setOpaque(true);
		contentPane.add(btnDangXuat);
		
		btnDangXuat.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseEntered(MouseEvent e) {
		    	btnDangXuat.setBackground(new Color(195,129,84));
		    }

		    @Override
		    public void mouseExited(MouseEvent e) {
		    	btnDangXuat.setBackground(new Color(17, 57, 70));
		    }
		});

		btnDangXuat.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        if (JOptionPane.showConfirmDialog(null, "Bạn có muốn đăng xuất!", null, JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
		        	GUI_DangNhap lg = new GUI_DangNhap();
		            lg.setVisible(true);
		            tk_dao.dangXuat(maNV);
		            dispose();
		        }
		    }
		});
		
		

		
		
		
		JPanel pnUser = new JPanel();
		pnUser.setBackground(new Color(17, 57, 70));
		pnUser.setBounds(946, 0, 190, 32);
		contentPane.add(pnUser);
		pnUser.setLayout(null);
		
		JLabel lblNewLabel_1_1 = new JLabel("");
		lblNewLabel_1_1.setBounds(107, 11, 0, 0);
		lblNewLabel_1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1_1.setForeground(Color.WHITE);
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 32));
		lblNewLabel_1_1.setBackground(new Color(17, 57, 70));
		pnUser.add(lblNewLabel_1_1);
		
		JLabel lb_chucVu = new JLabel("Chức Vụ :");
		lb_chucVu.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lb_chucVu.setForeground(new Color(255, 255, 255));
		lb_chucVu.setBounds(29, 19, 78, 13);
		pnUser.add(lb_chucVu);
		
		JLabel lb_chucVuCuaNhanVien = new JLabel(chucVu);
		lb_chucVuCuaNhanVien.setForeground(new Color(255, 255, 255));
		lb_chucVuCuaNhanVien.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lb_chucVuCuaNhanVien.setBounds(89, 20, 59, 13);
		pnUser.add(lb_chucVuCuaNhanVien);
		
		JLabel lbUserName = new JLabel(hoTenNhanVien);
		lbUserName.setForeground(new Color(255, 255, 255));
		lbUserName.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lbUserName.setBounds(89, 2, 101, 13);
		pnUser.add(lbUserName);
		
		JLabel lblUser = new JLabel(maNV);
		lblUser.setIcon(new ImageIcon(GUI_KhacHang.class.getResource("/imgs/user_img.png")));
		lblUser.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblUser.setForeground(new Color(255, 255, 255));
		lblUser.setBounds(0, 0, 108, 17);
		pnUser.add(lblUser);
		
		Timer timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                lblNewLabel_1.setText("" + sdf.format(new Date()));
            }
        });
        timer.start();
		
        this.setLocationRelativeTo(null);
		
		JLabel label = new JLabel("New label");
		label.setBounds(169, 72, 36, 32);
		contentPane.add(label);
		
		JLabel lblNewLabel_4 = new JLabel("l");
		lblNewLabel_4.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/gui_ncc.png")));
		lblNewLabel_4.setBounds(0, 0, 1143, 663);
		contentPane.add(lblNewLabel_4);
		
	
		
		if(chucVu.equalsIgnoreCase("NhanVien") == true) {
			btnNhaCungCap.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					GUI_NhaCungCap gui_ncc = new GUI_NhaCungCap();
					gui_ncc.setVisible(false);
					JOptionPane.showMessageDialog(frame, "Bạn Không Có Quyền Truy Cập Vào QLNCC: " , "Thông báo", JOptionPane.INFORMATION_MESSAGE);
				}
			});
			
			btnNhanVIen.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					GUI_NhanVien gui_nv = new GUI_NhanVien();
					gui_nv.setVisible(false);
					JOptionPane.showMessageDialog(frame, "Bạn Không Có Quyền Truy Cập Vào QLNV", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
				}
			});
		}
	}
	
	
	
	
	

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		
	}
	
	
	
}
