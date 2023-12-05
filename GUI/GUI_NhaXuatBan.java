package GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import ConnectDB.ConnectDB;
import DAO.KhachHang_DAO;
import DAO.NXB_DAO;
import DAO.TacGia_DAO;
import DAO.TaiKhoan_Dao;
import entity.KhachHang;
import entity.NhaXuatBan;
import entity.TacGia;
import entity.TaiKhoan;
import entity.TheLoai;

public class GUI_NhaXuatBan extends JFrame {

	
	private static final long serialVersionUID = 1L;
	protected static final Component ActionListener = null;
	private JPanel contentPane;
	private JLabel lblmanxb,lbltennxb,lbldiachi;
	private JTextField txtmanxb,txttennxb,txtdiachi;
	
	DefaultTableModel DataModel;
	JTable table;
	private DefaultComboBoxModel<String> cboModelTK;
	private JComboBox<String> comboBox;
	private JTextField textField_4;
	private NXB_DAO nxb=new NXB_DAO();
	private List<NhaXuatBan> dsnxb = new ArrayList<NhaXuatBan>();
	private List<TaiKhoan> dstk = new ArrayList<>();
	private TaiKhoan_Dao tk_dao = new TaiKhoan_Dao();
	private JFrame frame = new JFrame();
	private String maNV = "USER";
	private String hoTenNhanVien = "UserName";
	private String chucVu = "";
	
	private int idNXB;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI_NhaXuatBan frame = new GUI_NhaXuatBan();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI_NhaXuatBan() {
		ConnectDB.getInstance().connect();
		setIconImage(Toolkit.getDefaultToolkit().getImage(GUI_NhaXuatBan.class.getResource("/imgs/qls.png")));
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setTitle("Quản lý Nhà Xuất Bản");
		
		
		
		dstk = tk_dao.getAllTaiKhoan();
		for (TaiKhoan taiKhoan : dstk) {
			if(taiKhoan.isTrangThai() == true) {
				maNV  = taiKhoan.getMaNV();
				chucVu = taiKhoan.getChucVu();
				hoTenNhanVien = taiKhoan.getHoTenNhanVien();
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
		
		
		
		
		setBounds(100, 100, 1150, 680);
		contentPane = new JPanel();
		contentPane.setForeground(new Color(234, 198, 150));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 165, 58);
		panel.setBackground(new Color(17, 57, 70));
		contentPane.add(panel);
		
		 JLabel lblNewLabel_1 = new JLabel("");
		 panel.add(lblNewLabel_1);
		 lblNewLabel_1.setForeground(new Color(255, 255, 255));
		 lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 32));
		 lblNewLabel_1.setBackground(new Color(17, 57, 70));
		 lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblNewLabel = new JLabel("Quản lý nhà xuất bản");
		lblNewLabel.setBounds(387, -12, 387, 68);
		lblNewLabel.setForeground(new Color(234, 198, 150));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 35));
		contentPane.add(lblNewLabel);
		
		JButton btnKhachHang = new JButton("Khách hàng");
		btnKhachHang.setBounds(0, 59, 165, 58);
		btnKhachHang.setVerticalAlignment(SwingConstants.BOTTOM);
		btnKhachHang.setHorizontalAlignment(SwingConstants.LEFT);
		btnKhachHang.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/khachhang_img.png")));
		btnKhachHang.setForeground(new Color(255, 255, 255));
		btnKhachHang.setBackground(new Color(17, 57, 70));
		btnKhachHang.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnKhachHang.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {			
			}
		});
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
		btnHoaDon.setBounds(322, 59, 165, 58);
		btnHoaDon.setHorizontalAlignment(SwingConstants.LEFT);
		btnHoaDon.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/bill_imng.png")));
		btnHoaDon.setForeground(new Color(255, 255, 255));
		btnHoaDon.setBackground(new Color(17, 57, 70));
		btnHoaDon.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnHoaDon.addActionListener(new ActionListener() {	
			public void actionPerformed(ActionEvent e) {
			}
		});
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


	
		btnHoaDon.setComponentPopupMenu(popupMenuHoaDon);


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
		
		JButton btnNhanVien = new JButton("Nhân viên");
		btnNhanVien.setBounds(486, 59, 165, 58);
		btnNhanVien.setHorizontalAlignment(SwingConstants.LEFT);
		btnNhanVien.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/nhanvien_img.png")));
		btnNhanVien.setForeground(new Color(255, 255, 255));
		btnNhanVien.setBackground(new Color(17, 57, 70));
		btnNhanVien.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		btnNhanVien.addActionListener(new ActionListener() {
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
		
		
		btnNhanVien.setContentAreaFilled(false);
		btnNhanVien.setFocusPainted(false);
		btnNhanVien.setOpaque(true);
		contentPane.add(btnNhanVien);
		
		btnNhanVien.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseEntered(MouseEvent e) {
		    	btnNhanVien.setBackground(new Color(195,129,84));
		    }

		    @Override
		    public void mouseExited(MouseEvent e) {
		    	btnNhanVien.setBackground(new Color(17, 57, 70));
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
//		});
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
		
		btnNhanVien.setComponentPopupMenu(popupMenuNhanVien);

		
		btnNhanVien.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        
		        popupMenuNhanVien.show(btnNhanVien, 0, btnNhanVien.getHeight());
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
		btnNhaCungCap.setBounds(650, 59, 165, 58);
		btnNhaCungCap.setHorizontalAlignment(SwingConstants.LEFT);
		btnNhaCungCap.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/ncc_img.png")));
		btnNhaCungCap.setForeground(new Color(255, 255, 255));
		btnNhaCungCap.setBackground(new Color(17, 57, 70));
		btnNhaCungCap.setFont(new Font("Tahoma", Font.PLAIN, 10));
		btnNhaCungCap.setContentAreaFilled(false);
		btnNhaCungCap.setFocusPainted(false);
		btnNhaCungCap.setOpaque(true);
		contentPane.add(btnNhaCungCap);
		
		btnNhaCungCap.addActionListener(new ActionListener() {
			
			@Override
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
				if(chucVu.trim().equals("Nhân Viên Bán Hàng")) {
					JOptionPane.showMessageDialog(null, "Bạn Không Có Quyền Truy Cập Vào Chức Năng Này", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
				}else {
					GUI_NhaCungCap guiNCC = new GUI_NhaCungCap();
					guiNCC.setVisible(true);
					dispose();
				}
				
			}
		});
		
		popupMenuNCC.add(menuItemThemNCC);

		
//		JMenuItem menuItemCapNhatNCC = new JMenuItem("Cập nhật NCC");
//		menuItemCapNhatNCC.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/updated_img.png")));
//		menuItemCapNhatNCC.setForeground(new Color(255, 255, 255));
//		menuItemCapNhatNCC.setBackground(new Color(136, 74, 57));
//		menuItemCapNhatNCC.setFont(new Font("Tahoma", Font.PLAIN, 14));
//		menuItemCapNhatNCC.setPreferredSize(new Dimension(163, 58));
//		
//		menuItemCapNhat.addActionListener(new ActionListener() {
//			
//			@Override
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
//		});
//		
//		popupMenuNCC.add(menuItemCapNhatNCC);
//
//	
//		JMenuItem menuItemTimKiemNCC = new JMenuItem("Tìm NCC");
//		menuItemTimKiemNCC.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/loupe_img.png")));
//		menuItemTimKiemNCC.setForeground(new Color(255, 255, 255));
//		menuItemTimKiemNCC.setBackground(new Color(136, 74, 57));
//		menuItemTimKiemNCC.setFont(new Font("Tahoma", Font.PLAIN, 14));
//		menuItemTimKiemNCC.setPreferredSize(new Dimension(163, 58));
//		
//		menuItemTimKiemNCC.addActionListener(new ActionListener() {
//			
//			@Override
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
//		});
//		
//		popupMenuNCC.add(menuItemTimKiemNCC);

		
		btnNhaCungCap.setComponentPopupMenu(popupMenuNCC);

	
		btnNhaCungCap.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        
		        popupMenuNCC.show(btnNhaCungCap, 0, btnNhaCungCap.getHeight());
		    }
		});

		
		
		JButton btnTroGiup = new JButton("Trợ giúp");
		btnTroGiup.setBounds(814, 59, 165, 58);
		btnTroGiup.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/help_img.png")));
		btnTroGiup.setHorizontalAlignment(SwingConstants.LEFT);
		btnTroGiup.setForeground(new Color(255, 255, 255));
		btnTroGiup.setBackground(new Color(17, 57, 70));
		btnTroGiup.setFont(new Font("Tahoma", Font.PLAIN, 14));
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
		btnThoat.setBounds(978, 59, 165, 58);
		btnThoat.setHorizontalAlignment(SwingConstants.LEFT);
		btnThoat.setIcon(new ImageIcon(GUI_TrangChu.class.getResource("/imgs/exit_img.png")));
		btnThoat.setForeground(new Color(255, 255, 255));
		btnThoat.setBackground(new Color(17, 57, 70));
		btnThoat.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnThoat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
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
		
		btnThoat.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        // Đóng giao diện hiện tại (GUI_NhanVien)
		        dispose();

		        // Hiển thị lại giao diện GUI_TrangChu
		        GUI_TrangChu guiTrangChu = new GUI_TrangChu();
		        guiTrangChu.setVisible(true);
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
		
		
		
		////
		lblmanxb=new JLabel("Mã NXB:");
		lblmanxb.setBounds(32, 141, 151, 44);
		lblmanxb.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblmanxb.setForeground(new Color(234, 198, 150));
        contentPane.add(lblmanxb);
		
		 lbltennxb = new JLabel("Tên NXB:");
		 lbltennxb.setBounds(32, 195, 165, 44);
		lbltennxb.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lbltennxb.setForeground(new Color(234, 198, 150));
		contentPane.add(lbltennxb);
		
		 lbldiachi = new JLabel("Địa Chỉ:");
		 lbldiachi.setBounds(32, 249, 73, 32);
		 lbldiachi .setFont(new Font("Tahoma", Font.PLAIN, 18));
		 lbldiachi .setForeground(new Color(234, 198, 150));
		 contentPane.add( lbldiachi );
	
	txtmanxb = new JTextField();
	txtmanxb.setBounds(183, 142, 239, 42);
	txtmanxb.setFont(new Font("Tahoma", Font.PLAIN, 18));
	contentPane.add(txtmanxb);
	txtmanxb.setEditable(false);
	idNXB = getIDNXB();
	
	if(idNXB < 10) {
		txtmanxb.setText("NXB00" + idNXB);
	}else if (idNXB >= 10 && idNXB < 100) {
		txtmanxb.setText("NXB0" + idNXB);
	}else if(idNXB >= 100) {
		txtmanxb.setText("NXB" + idNXB);
	}
	
	txtmanxb.setColumns(10);
	
	txttennxb = new JTextField();
	txttennxb.setBounds(183, 195, 239, 42);
	txttennxb.setFont(new Font("Tahoma", Font.PLAIN, 18));
	txttennxb.setColumns(10);
	contentPane.add(txttennxb);
	
	txtdiachi = new JTextField();
	txtdiachi.setBounds(183, 249, 239, 42);
	txtdiachi.setFont(new Font("Tahoma", Font.PLAIN, 18));
	txtdiachi.setColumns(10);
	contentPane.add(txtdiachi);
		
		
		
	
	
	JScrollPane scrollPane = new JScrollPane();
	scrollPane.setBounds(77, 330, 973, 186);
	String[] headers= "Mã NXB;Ten NXB;DiaChi".split(";");
	DataModel=new DefaultTableModel(headers,0);
	
	scrollPane.setViewportView(table=new JTable(DataModel));
	table.setRowHeight(25);
	table.setAutoCreateRowSorter(true);
	table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
	//table.setPreferredSize(new Dimension(500, 300));
	contentPane.add(scrollPane);
		
		
	JLabel label = new JLabel("New label");
	label.setBounds(169, 72, 36, 32);
	contentPane.add(label);
	
	JButton btnThem = new JButton("Thêm");
	btnThem.setBackground(new Color(17, 57, 70));
	btnThem.setForeground(new Color(234, 198, 150));
	btnThem.setFont(new Font("Tahoma", Font.PLAIN, 18));
	btnThem.setBounds(25, 526, 140, 44);
	contentPane.add(btnThem);
	btnThem.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(checkEmpty()) {
			nxb.addNXB(new NhaXuatBan(txtmanxb.getText(),txttennxb.getText(), txtdiachi.getText()));
			
			//hàm này cập nhật vào bảng
			DataModel.setRowCount(0);
			for(NhaXuatBan n:nxb.getAllNXB()) {
				
					
				Object[] rowData= {n.getMaNXB(),n.getTenNXB(),n.getDiaChi()};
					DataModel.addRow(rowData);
					
				
			}
			JOptionPane.showMessageDialog(null, "Thêm thành công!");
			idNXB +=1;
			if(idNXB < 10) {
				txtmanxb.setText("NXB00" + idNXB);
			}else if (idNXB >= 10 && idNXB < 100) {
				txtmanxb.setText("NXB0" + idNXB);
			}else if(idNXB >= 100) {
				txtmanxb.setText("NXB" + idNXB);
			}
		}
		}
	});
	
	JButton btnLammoi = new JButton("Làm mới");
	btnLammoi.setBackground(new Color(17, 57, 70));
	btnLammoi.setForeground(new Color(234, 198, 150));
	btnLammoi.setFont(new Font("Tahoma", Font.PLAIN, 18));
	btnLammoi.setBounds(218, 526, 140, 44);
	contentPane.add(btnLammoi);
	btnLammoi.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Object o = e.getSource();
				if (o.equals(btnLammoi)) {
					try {
						lammoi();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				
				}
				
				
			
		});
	
	cboModelTK = new DefaultComboBoxModel<String>();
	comboBox = new JComboBox<String>(cboModelTK);
	comboBox.setForeground(new Color(249, 224, 187));
	comboBox.setBackground(new Color(17, 57, 70));
	comboBox.setFont(new Font("Tahoma", Font.PLAIN, 12));
	comboBox.setBounds(702, 528, 135, 42);
	contentPane.add(comboBox);
	updateJComboBox();
	
	textField_4 = new JTextField();
	textField_4.setFont(new Font("Tahoma", Font.PLAIN, 18));
	textField_4.setBounds(837, 528, 142, 42);
	contentPane.add(textField_4);
	textField_4.setColumns(10);
	JButton btnSearch = new JButton("");
	btnSearch.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/search_img.png")));
	btnSearch.setBackground(new Color(17, 57, 70));
	btnSearch.setBounds(978, 528, 45, 42);
	contentPane.add(btnSearch);
	btnSearch.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			Object o = e.getSource();
			if (o.equals(btnSearch) || o.equals(textField_4)) {
				xoaBang();
				if (comboBox.getSelectedIndex() == 0)
					timTheoMa();

				if (comboBox.getSelectedIndex() == 1)
					try {
						if (textField_4.getText().matches("( *)")) {
							updateTable();
							JOptionPane.showMessageDialog(null, "Nhập tên cần tìm!");
							textField_4.setText("");
							textField_4.requestFocus();

						} else {
							timTheoTen();
						}

					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				
					 
					

				if (o.equals(comboBox)) {
					textField_4.selectAll();
					textField_4.requestFocus();
				}

			}
		}
	});
	
	//1.Add data dô bảng
			nxb =new NXB_DAO();
			table.setRowHeight(25);
			for(NhaXuatBan n:nxb.getAllNXB()) {
				
				Object[] rowData= {n.getMaNXB(),n.getTenNXB(),n.getDiaChi()};
				DataModel.addRow(rowData);
			}
			
			table.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					int row = table.getSelectedRow();
					assert row >= 0;
					napLopHocVaoTextfields();
				}
			});
			
			
			
	
	
	
	
	
	JLabel lblNewLabel_4 = new JLabel("l");
	lblNewLabel_4.setBounds(0, 0, 1143, 643);
	lblNewLabel_4.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/gui_ncc.png")));
	contentPane.add(lblNewLabel_4);
	}
	
	
	
	
	private void updateJComboBox() {
		cboModelTK.addElement("Tìm mã nhà xuất bản");
		cboModelTK.addElement("Tìm tên nhà Xuất bản");
		
	}
	
	private void napLopHocVaoTextfields() {
		int row = table.getSelectedRow();
		if (row >= 0) {
			txtmanxb.setText((String) table.getValueAt(row, 0));
			txttennxb.setText((String) table.getValueAt(row, 1));
			
			txtdiachi.setText((String) table.getValueAt(row, 2));
			

			
		}
	}
	
	
	public int getIDNXB() {
		dsnxb = nxb.getAllNXB();
		if(dsnxb.isEmpty()) {
			return 1;
		}else {
			int i = 1;
			for (NhaXuatBan dsNVDaDuocTai : dsnxb) {
				i++;
			}
			return i;
		}	
	}
	// Kiểm Tra Xác Thực ràng buộc
			private Boolean checkEmpty() {
				String ma = txtmanxb.getText();
				String ten = txttennxb.getText();
				String diachi = txtdiachi.getText();
				
				
				
				
				
				
				
				if (ma.trim().equals("")) {
					return showErrorTx(txtmanxb, "Mã  NXB không được để trống");

				}else if (!(ma.length() > 0 && ma.matches("^(NXB)[0-9]{3}$"))) {
					JOptionPane.showMessageDialog(null, "Không đúng định dạng" + "\nVui lòng nhập theo VD: NXB001");
					txtmanxb.selectAll();
					txtmanxb.requestFocus();
					return false;
				} else if (nxb.isManxbTonTai(ma)) {
			        JOptionPane.showMessageDialog(this, "Mã NXB đã tồn tại. Vui lòng chọn mã khác.", "Thông báo lỗi", JOptionPane.ERROR_MESSAGE);
			        txtmanxb.selectAll();
			        txtmanxb.requestFocus();
			        return false;
				}
				else if (ten.trim().equals("")) {
					return showErrorTx(txttennxb,"Tên NXB không được để trống");
				}else if (diachi.trim().equals("")) {
					return showErrorTx(txtdiachi,"Địa chỉ NXB không được để trống");
				}
					return true;
					
				}
		private Boolean showErrorTx(JTextField tx,String errorInfo) {
				JOptionPane.showMessageDialog(tx, errorInfo);
				tx.requestFocus();
				return false;
			}
		
		
		private void lammoi() throws SQLException {
			textField_4.setText("");
			comboBox.setSelectedIndex(0);
			if(idNXB < 10) {
				txtmanxb.setText("NXB00" + idNXB);
			}else if (idNXB >= 10 && idNXB < 100) {
				txtmanxb.setText("NXB0" + idNXB);
			}else if(idNXB >= 100) {
				txtmanxb.setText("NXB" + idNXB);
			}
			txttennxb.setText("");
			xoaBang();
			updateTable();
			
		}
		
		//Hàm cập nhật dữ liệu
	  	private void updateTable() {
	  		DataModel.setRowCount(0);
	  		for(NhaXuatBan n:nxb.getAllNXB()) {
	  			
	  				
	  			Object[] rowData= {n.getMaNXB(),n.getTenNXB(),n.getDiaChi()};
	  				DataModel.addRow(rowData);
	  				
	  			
	  		}
	  	}
	  	private void xoaBang() {
	  		for (int j = 0; j < table.getRowCount(); j++) {
	  			DataModel.removeRow(j);
	  			j--;
	  		}
	  	}
	  	
	  	
	  //Hàm tìm kiếm
		

		private void timTheoTen() throws SQLException {
			table.clearSelection();
			String ten = textField_4.getText();
			
			 
			NXB_DAO nxb=new NXB_DAO();
			
			//List<TacGia> dstg = TG.getTGtheoTen(ten);
			List<NhaXuatBan> dsnxb = nxb.getNXBtheoTen(ten);
			if (dsnxb.size() == 0) {
				JOptionPane.showMessageDialog(null, "Không tìm thấy tên NXB  theo ten! ");

			} else {
				for (NhaXuatBan n : dsnxb) {

					
			       

					String[] row = {n.getMaNXB(),n.getTenNXB(),n.getDiaChi()};
					DataModel.addRow(row);
				}

				table.setModel(DataModel);
			}
		}
		
		private void timTheoMa() {
			table.clearSelection();
			String matg = textField_4.getText();
			NXB_DAO daonxb=new NXB_DAO();
			 
			NhaXuatBan nxb=new NhaXuatBan();
			nxb=daonxb.getNXBTheoMa(matg) ;
			
			

			if (nxb == null) {
				JOptionPane.showMessageDialog(null, "Không tìm thấy Nhà Xuất Bản ");
			} else {

				String[] row = {nxb.getMaNXB(),nxb.getTenNXB(),nxb.getDiaChi()};
				DataModel.addRow(row);
			}

			table.setModel(DataModel);
		}


}
