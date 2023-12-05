package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
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
import DAO.ChiTietHoaDon_Dao;
import DAO.HoaDon_Dao;
import DAO.NhanVien_Dao;
import DAO.TaiKhoan_Dao;
import entity.ChiTietHoaDon;
import entity.HoaDon;
import entity.NhanVien;
import entity.TaiKhoan;



import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;

import javax.swing.UIManager;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.Button;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat.Encoding;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DropMode;
import javax.swing.JSpinner;
import javax.swing.JSeparator;

import java.nio.charset.StandardCharsets;
import org.apache.poi.xwpf.usermodel.*;

import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfStream;
import com.itextpdf.text.pdf.codec.Base64.InputStream;
import com.itextpdf.text.pdf.codec.Base64.OutputStream;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.*;



public class GUI_XemChiTietHoaDon extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable table_hd;
	private JTable table_cthd;
	private JTextField textField;
	private JTextField tfDoanhthu;
	private JTextField tfttmn;
	private DefaultTableModel DatamodelHoaDon , Datamodel_cthd;
	private JButton btnThngK , btnXhd , btnSearch , btnLamMoi;
	
	 private List<HoaDon> dshd = new ArrayList<HoaDon>();
	private List<ChiTietHoaDon> ds_cthd = new ArrayList<ChiTietHoaDon>();
	private HoaDon_Dao hd_dao = new HoaDon_Dao();
	private ChiTietHoaDon_Dao cthd_dao = new ChiTietHoaDon_Dao();
	private Date ngayLapHoaDon;
	
	private String fileNameWord =  "D:\\hoaDonBanHang.docx";
	private String fileNamepdf =  "D:\\hoaDonBanHang.pdf";
	
	private TaiKhoan_Dao tk_dao = new TaiKhoan_Dao();
	private List<TaiKhoan> dstk = new ArrayList<TaiKhoan>();
	
	private String maNV = "USER";
	private String chucVu = "";
	private String hoTenNhanVien = "User Name";
	
	private JFrame frame = new JFrame();
	private JComboBox<String> cbttmn;

	private JSpinner spinner_nam , spinner_ngay , spinner_thang;
	private JLabel lb_dataWriteFile;

	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI_XemChiTietHoaDon frame = new GUI_XemChiTietHoaDon();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	

	/**
	 * Create the frame.
	 */
	public GUI_XemChiTietHoaDon() {
		try {
			ConnectDB.getInstance().connect();
		} catch (Exception ex) {
			// TODO: handle exception
			ex.printStackTrace();
		}
		
		
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		dstk = tk_dao.getAllTaiKhoan();
		for (TaiKhoan taiKhoan : dstk) {
			if(taiKhoan.isTrangThai() == true) {
				maNV  = taiKhoan.getMaNV();
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
		
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(GUI_XemChiTietHoaDon.class.getResource("/imgs/qls.png")));
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Quản lý hóa đơn");
		setBounds(100, 100, 1150, 680);
		contentPane = new JPanel();
		contentPane.setForeground(new Color(234, 198, 150));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(17, 57, 70));
		panel.setBounds(0, 1, 165, 58);
		contentPane.add(panel);
		
		 JLabel lblNewLabel_1 = new JLabel("");
		 panel.add(lblNewLabel_1);
		 lblNewLabel_1.setForeground(new Color(255, 255, 255));
		 lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 32));
		 lblNewLabel_1.setBackground(new Color(17, 57, 70));
		 lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lbCTHD = new JLabel("CHI TIẾT HÓA ĐƠN");
		lbCTHD.setHorizontalAlignment(SwingConstants.CENTER);
		lbCTHD.setForeground(new Color(234, 198, 150));
		lbCTHD.setFont(new Font("Tahoma", Font.BOLD, 28));
		lbCTHD.setBounds(0, 264, 1136, 68);
		contentPane.add(lbCTHD);
		
		JButton btnKhachHang = new JButton("Khách hàng");
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
	        menuItemThem.setIcon(new ImageIcon(GUI_NhanVien.class.getResource("/imgs/add_img.png")));
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
//	        menuItemCapNhat.setIcon(new ImageIcon(GUI_NhanVien.class.getResource("/imgs/updated_img.png")));
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
//	        menuItemTimKiem.setIcon(new ImageIcon(GUI_NhanVien.class.getResource("/imgs/loupe_img.png")));
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
		
		JButton btnNhanVIen = new JButton("Nhân viên");
		btnNhanVIen.setHorizontalAlignment(SwingConstants.LEFT);
		btnNhanVIen.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/nhanvien_img.png")));
		btnNhanVIen.setForeground(new Color(255, 255, 255));
		btnNhanVIen.setBackground(new Color(17, 57, 70));
		btnNhanVIen.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		btnNhanVIen.addActionListener(new ActionListener() {
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
//		
//		
//		menuItemCapNhatNhanVien.addActionListener(new ActionListener() {
//			
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
//		
//		menuItemTimKiemNhanVien.addActionListener(new ActionListener() {
//			
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
		btnNhanVIen.setComponentPopupMenu(popupMenuNhanVien);

		
		btnNhanVIen.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        
		        popupMenuNhanVien.show(btnNhanVIen, 0, btnNhanVIen.getHeight());
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
		
		btnNhaCungCap.addActionListener(new ActionListener() {				
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
//		
//		menuItemCapNhatNCC.addActionListener(new ActionListener() {
//			
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
//		popupMenuNCC.add(menuItemTimKiemNCC);

	
		btnNhaCungCap.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        
		        popupMenuNCC.show(btnNhaCungCap, 0, btnNhaCungCap.getHeight());
		    }
		});

		menuItemThemNCC.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		   
		    }
		});

//		menuItemCapNhatNCC.addActionListener(new ActionListener() {
//		    public void actionPerformed(ActionEvent e) {
//		   
//		    }
//		});
//
//		menuItemTimKiemNCC.addActionListener(new ActionListener() {
//		    public void actionPerformed(ActionEvent e) {
//		     
//		    }
//		});
		
		JButton btnTroGiup = new JButton("Trợ giúp");
		btnTroGiup.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/help_img.png")));
		btnTroGiup.setHorizontalAlignment(SwingConstants.LEFT);
		btnTroGiup.setForeground(new Color(255, 255, 255));
		btnTroGiup.setBackground(new Color(17, 57, 70));
		btnTroGiup.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnTroGiup.setBounds(814, 59, 165, 58);
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
				GUI_DoiMK guiDMK = new GUI_DoiMK();
				guiDMK.setVisible(true);
				dispose();
				
			}
		});
		
		JButton btnThoat = new JButton("Thoát (Alt + 4)");
		btnThoat.setIcon(new ImageIcon(GUI_XemChiTietHoaDon.class.getResource("/imgs/exit_img.png")));
		btnThoat.setHorizontalAlignment(SwingConstants.LEFT);
		btnThoat.setForeground(new Color(255, 255, 255));
		btnThoat.setBackground(new Color(17, 57, 70));
		btnThoat.setFont(new Font("Tahoma", Font.PLAIN, 12));
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
		
		btnThoat.setBounds(978, 59, 165, 58);
		contentPane.add(btnThoat);
		btnThoat.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        // Đóng giao diện hiện tại (GUI_NhanVien)
		        dispose();

		        // Hiển thị lại giao diện GUI_TrangChu
		        GUI_TrangChu guiTrangChu = new GUI_TrangChu();
		        guiTrangChu.setVisible(true);
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
		
		Timer timer = new Timer(1000, new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	                lblNewLabel_1.setText("" + sdf.format(new Date()));
	            }
	        });
	        timer.start();
		
	        
	        this.setLocationRelativeTo(null);
		
//		Table Hóa Đơn
		JScrollPane scrollPane1 = new JScrollPane();
		scrollPane1.setBounds(0, 142, 1136, 108);
		String[] headers_hd = "Mã HD; Mã NV; Mã KH; Ngày Lập; VAT; Mã Quầy; Ưu Đãi; Tổng Tiền".split(";");
		DatamodelHoaDon = new DefaultTableModel (headers_hd,0);
		scrollPane1.setViewportView(table_hd = new JTable(DatamodelHoaDon));
		table_hd.setRowHeight(25);
		table_hd.setAutoCreateRowSorter(true);
		table_hd.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		contentPane.add(scrollPane1);
		
		
//		Table Chi Tiết Hóa Đơn 0, 142, 1136, 108
		JLabel lbQuanLyHoaDon = new JLabel("QUẢN LÝ HÓA ĐƠN");
		lbQuanLyHoaDon.setHorizontalAlignment(SwingConstants.CENTER);
		lbQuanLyHoaDon.setForeground(new Color(234, 198, 150));
		lbQuanLyHoaDon.setFont(new Font("Tahoma", Font.BOLD, 28));
		lbQuanLyHoaDon.setBounds(164, -10, 815, 68);
		contentPane.add(lbQuanLyHoaDon);
		
		JScrollPane scrollPane2 = new JScrollPane();
		scrollPane2.setBounds(77, 317, 984, 108);
		String[] headers_cthd = "Mã sách ; Tên sách ; Số lượng ; Đơn giá  ; Thành tiền".split(";");
		Datamodel_cthd = new DefaultTableModel (headers_cthd,0);
		scrollPane2.setViewportView(table_cthd = new JTable(Datamodel_cthd));
		table_hd.setRowHeight(25);
		table_hd.setAutoCreateRowSorter(true);
		table_hd.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		contentPane.add(scrollPane2);

		
		this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int choice = JOptionPane.showConfirmDialog(frame, "Bạn có chắc chắn muốn thoát không?", "Xác nhận thoát", JOptionPane.YES_NO_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                	tk_dao.dangXuat(maNV);
                    System.exit(0);
                }
            }
        });
		
		
		
		
//		Bảng Chi Tiết Hóa Đơn
		btnKhachHang.setBounds(0, 59, 165, 58);
		contentPane.add(btnKhachHang);
		
	      
		
		JLabel label = new JLabel("New label");
		label.setBounds(169, 72, 36, 32);
		contentPane.add(label);
		
		JButton btnTaohd = new JButton("Tạo hóa đơn");
		btnTaohd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				GUI_TaoHoaDon gui=new GUI_TaoHoaDon();
				gui.setVisible(true);
				dispose();
			}
			
		});
		btnTaohd.setForeground(new Color(249, 224, 187));
		btnTaohd.setFont(new Font("Tahoma", Font.PLAIN, 25));
		btnTaohd.setBackground(new Color(17, 57, 70));
		btnTaohd.setBounds(0, 497, 202, 38);
		contentPane.add(btnTaohd);
		
		
		
		btnXhd = new JButton("Xuất hóa đơn");
		btnXhd.setForeground(new Color(249, 224, 187));
		btnXhd.setFont(new Font("Tahoma", Font.PLAIN, 25));
		btnXhd.setBackground(new Color(17, 57, 70));
		btnXhd.setBounds(814, 567, 322, 41);
		contentPane.add(btnXhd);
		
//		JComboBox comboBox = new JComboBox();
//		comboBox.setBounds(485, 493, 52, 38);
//		contentPane.add(comboBox);
		cbttmn = new JComboBox<String>();
		cbttmn.setForeground(new Color(249, 224, 187));
		cbttmn.setBackground(new Color(17, 57, 70));
		cbttmn.setFont(new Font("Tahoma", Font.PLAIN, 18));
		cbttmn.setModel(new DefaultComboBoxModel(new String[] {"Tìm theo", "Mã KH", "Ngày Lập"}));
		cbttmn.setBounds(814, 499, 142, 41);
		btnSearch = new JButton("");
		btnSearch.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/search_img.png")));
		btnSearch.setBackground(new Color(17, 57, 70));
		btnSearch.setBounds(1091, 497, 45, 42);
		contentPane.add(btnSearch);
		
	
		
		contentPane.add(cbttmn);
		
		tfttmn = new JTextField();
		tfttmn.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tfttmn.setBounds(956, 499, 142, 41);
		contentPane.add(tfttmn);
		tfttmn.setColumns(10);
		
		
		tfDoanhthu = new JTextField();
		tfDoanhthu.setFont(new Font("Tahoma", Font.PLAIN, 18));
		tfDoanhthu.setBounds(355, 571, 449, 38);
		contentPane.add(tfDoanhthu);
		tfDoanhthu.setEditable(false);
		tfDoanhthu.setColumns(10);
		
		JButton btnNewButton = new JButton("");
		
//		BTN thống kê 
		btnThngK = new JButton("Thống kê");
		btnThngK.setForeground(new Color(249, 224, 187));
		btnThngK.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnThngK.setBackground(new Color(17, 57, 70));
		btnThngK.setBounds(673, 498, 129, 41);
		contentPane.add(btnThngK);
		btnLamMoi = new JButton("Làm mới");
		btnLamMoi.setForeground(new Color(249, 224, 187));
		btnLamMoi.setFont(new Font("Tahoma", Font.PLAIN, 25));
		btnLamMoi.setBackground(new Color(17, 57, 70));
		btnLamMoi.setBounds(0, 567, 202, 39);
		contentPane.add(btnLamMoi);
		
		spinner_nam = new JSpinner();
		spinner_nam.setFont(new Font("Tahoma", Font.PLAIN, 14));
		spinner_nam.setBounds(591, 498, 72, 38);
		contentPane.add(spinner_nam);
		
		
	
		int namMacDinhThongKe = 2000;
		LocalDate currentDate = LocalDate.now();
		int namHienTai = currentDate.getYear();
		
		
		spinner_nam.setValue(namMacDinhThongKe);
		spinner_nam.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				int nam = (int) spinner_nam.getValue();
				if(nam < 1990 || nam > namHienTai) {
					spinner_nam.setValue(namHienTai);
				}
				
			}
		});
		
		
		
		
		spinner_ngay = new JSpinner();
		spinner_ngay.setFont(new Font("Tahoma", Font.PLAIN, 14));
		spinner_ngay.setBounds(304, 498, 45, 38);
		contentPane.add(spinner_ngay);
		
		spinner_ngay.setValue(0);
		
		spinner_ngay.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				int ngay = (int) spinner_ngay.getValue();
				if(ngay > 31) {
					spinner_ngay.setValue(0);
				}else if (ngay < 0) {
					spinner_ngay.setValue(31);
				}				
			}
		});
		
		
		spinner_thang = new JSpinner();
		spinner_thang.setFont(new Font("Tahoma", Font.PLAIN, 14));
		spinner_thang.setBounds(448, 498, 52, 38);
		
		
		spinner_thang.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				int thangThongKe = (int) spinner_thang.getValue();
				if(thangThongKe > 12) {
					spinner_thang.setValue(1);
				}else if(thangThongKe < 0) {
					spinner_thang.setValue(0);
				}
			}
		});
		
		contentPane.add(spinner_thang);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(273, 499, 1, 2);
		contentPane.add(separator);
		
		JLabel lblNewLabel = new JLabel("Chọn Ngày");
		lblNewLabel.setForeground(new Color(249, 224, 187));
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setBounds(214, 495, 92, 42);
		contentPane.add(lblNewLabel);
		
		JLabel lblChnThng = new JLabel("Chọn Tháng");
		lblChnThng.setForeground(new Color(249, 224, 187));
		lblChnThng.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblChnThng.setBounds(355, 495, 108, 42);
		contentPane.add(lblChnThng);
		
		JLabel lblChnNm = new JLabel("Chọn Năm");
		lblChnNm.setForeground(new Color(249, 224, 187));
		lblChnNm.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblChnNm.setBounds(510, 495, 79, 42);
		contentPane.add(lblChnNm);
		
		JLabel lblDoanhThu = new JLabel("Doanh Thu");
		lblDoanhThu.setForeground(new Color(249, 224, 187));
		lblDoanhThu.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblDoanhThu.setBounds(212, 566, 159, 42);
		contentPane.add(lblDoanhThu);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(GUI_NhaCungCap.class.getResource("/imgs/gui_ncc.png")));
		lblNewLabel_2.setBounds(0, 0, 1136, 633);
		contentPane.add(lblNewLabel_2);
		
		dshd = hd_dao.getAllHoaDon();
		ds_cthd = cthd_dao.getAllChiTietHoaDon();
		
		
//		"Mã HD; Mã NV; Mã KH; Ngày Lập; VAT; Mã Quầy; Ưu Đãi; Tổng Tiền"
		for (HoaDon dsHoaDon : dshd) {
			String maHD = dsHoaDon.getMaHD();
			String maNV = dsHoaDon.getMaNV();
			String maKH = dsHoaDon.getMaKH();
			Date ngayLap = dsHoaDon.getNgayLap();
			float vat = dsHoaDon.getVAT();
			String maQuay = dsHoaDon.getMaQuay();
			float uuDai = dsHoaDon.getUuDai();
			double tongTien = dsHoaDon.getTongTien();
			
			Object[] row_data = {maHD , maNV , maKH , ngayLap , vat , maQuay , uuDai , tongTien};
			DatamodelHoaDon.addRow(row_data);
		}
		
//		"Mã sách ; Tên sách ; Số lượng ; Đơn giá  ; VAT ; Ưu đãi ;Thành tiền"
//		for (ChiTietHoaDon cthd : ds_cthd) {
//			String maSach = cthd.getMaSach();
//			String tenSach = cthd.getTenSach();
//			int soLuong = cthd.getSoLuong();
//			float donGia = cthd.getDonGia();
//			double thanhTien = cthd.getThanhTien();
//			
//			Object[] row_data_cthd = {maSach , tenSach , soLuong , donGia , thanhTien};
//			Datamodel_cthd.addRow(row_data_cthd);
//		}
	
		
		table_hd.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				hienThiCacChiTietHoaDon();
			}
		});
		
//		Textflied nhap ngay / thang de thong ke
//		tftkhdtnt.setEditable(false);
		
		btnThngK.addActionListener(this);
		btnXhd.addActionListener(this);
		btnTaohd.addActionListener(this);
		btnSach.addActionListener(this);
		btnSearch.addActionListener(this);
		btnLamMoi.addActionListener(this);
		
	}


	public void hienThiCacChiTietHoaDon() {
		Datamodel_cthd.setRowCount(0);
		int row = table_hd.getSelectedRow();
		if(row != -1) {
			String maHDDuocChon = DatamodelHoaDon.getValueAt(row, 0).toString();
			
			for (ChiTietHoaDon cthd : ds_cthd) {
				boolean soSanhMHD = maHDDuocChon.equalsIgnoreCase(cthd.getMaHD());
				if(soSanhMHD == true) {
					String maSach = cthd.getMaSach();
					String tenSach = cthd.getTenSach();
					int soLuong = cthd.getSoLuong();
					float donGia = cthd.getDonGia();
					double thanhTien = cthd.getThanhTien();
				
					Object[] row_data_cthd = {maSach , tenSach , soLuong , donGia , thanhTien};
					Datamodel_cthd.addRow(row_data_cthd);
				}
			}
		}
		
	}
	
	
	
	public String titleCacSanPhamCuaHoaDon() {
		return String.format("%-8s %-22s %-16s %-10s %-10s" , "Mã Sách" , "Tên Sách" , "Số Lượng" , "Đơn Giá" , "Thành Tiền");
	}
	
	
	
	public String sanPhamTrongHoaDon(String maSach , String tenSach , int soLuong , float donGia , double thanhTien ) {
			DecimalFormat decimalFormat = new DecimalFormat("#.##");
		  String formattedDonGia = decimalFormat.format(donGia);
	       String formattedThanhTien = decimalFormat.format(thanhTien);
		 
	       
	       
		return String.format("%-7s %-28s %-14d %-10s %-10s" , maSach , tenSach , soLuong , formattedDonGia , formattedThanhTien);
	}
	
	
//	FNC Ghi File 
	public void ghiFlied(List<ChiTietHoaDon> cacSanPhamTrongHoaDon) {
		
		String maHDMuonXuat = "";
		HoaDon hdMuonXuat = null;
		NhanVien thongTinNV = null;
		List<NhanVien> dsnv = new ArrayList<NhanVien>();
		NhanVien_Dao nv_dao = new NhanVien_Dao();
		
		int row = table_hd.getSelectedRow();
		if(row != -1) {
			maHDMuonXuat = (String) table_hd.getValueAt(row, 0);
			dshd = hd_dao.getAllHoaDon();
			for (HoaDon hdDuocChon : dshd) {
				if(maHDMuonXuat.equals(hdDuocChon.getMaHD())) {
					hdMuonXuat = hdDuocChon;
				}
			}
		}
		
		String tenNhanVien = "";
		dsnv = nv_dao.getAllNhanVien();
		
		for (NhanVien nhanVien : dsnv) {
			if(nhanVien.getMaNV().trim().equals(hdMuonXuat.getMaNV())) {
				tenNhanVien = nhanVien.getHoTenNV();
			}
		}
		
		// Tạo định dạng cho ngày tháng
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDateNgayXuatHoaDon = dateFormat.format(hdMuonXuat.getNgayLap());
		
        
        
		XWPFDocument document = new XWPFDocument();
		
//		Chứa Tiêu Đề Hóa Đơn Hiệu Sách Tư Nhân HTND
		XWPFParagraph boldParagraphLogo = document.createParagraph();
	    XWPFRun boldRun = boldParagraphLogo.createRun();

//		Chứa Các Thông Tin Về Hóa Đơn Được Xuất
		XWPFParagraph paragraphInfo_order = document.createParagraph();
        XWPFRun runInfoOrder = paragraphInfo_order.createRun();
		
        
//      Chứa Tiêu Đề Của Các Sản Phẩm
        XWPFParagraph paragraph_title_order = document.createParagraph();
        XWPFRun runTitleProduct = paragraph_title_order.createRun();
        
//		Chứa Các Bảng Trong Hóa Đơn        
        XWPFParagraph table = document.createParagraph();
        XWPFRun runTable = table.createRun();
        
//		Chứa Các Sản Phẩm Trong Hóa Đơn        
        XWPFParagraph paragraph_product = document.createParagraph();
        XWPFRun runProduct = paragraph_product.createRun();
        
        
        String tieuDeCuaHoaDon = "Hóa Đơn Bán Hàng Hiệu Sách Tư Nhân HTND";
		
        String tieuDeCuaSanPham = titleCacSanPhamCuaHoaDon();
        
        String tableInFile = "--------------------------------------------------------------------------------------------------------------------------------";
        
		String thongTinCuaHoaDon = 
				
				" Mã Hóa Đơn : " + hdMuonXuat.getMaHD() + "\t\t\t\t\t\t\t\t\t" + hdMuonXuat.getMaKH() +
				System.lineSeparator() + System.lineSeparator() +
				
				
				"Mã Nhân Viên : " + hdMuonXuat.getMaNV() + "\t\t\t\t\t\t\t\t" + "Ưu Đãi :" + hdMuonXuat.getUuDai() +
				System.lineSeparator()  + System.lineSeparator() + 
				
				
				"Tên Nhân Viên : " + tenNhanVien   + "\t\t\t\t\t\t\t\t" + "Tổng Tiền : " +   hdMuonXuat.getTongTien()+
				System.lineSeparator() + System.lineSeparator() +
				
				
				"Ngày Lặp : " + formattedDateNgayXuatHoaDon + "\t\t\t\t\t\t\t\t" + "VAT : " + hdMuonXuat.getVAT() + "\t\t\t" +  System.lineSeparator()  + System.lineSeparator() + "Mã Quầy : " + hdMuonXuat.getMaQuay() +  System.lineSeparator() +  System.lineSeparator(); 
				
		
		 
		 
	        try {
	        	boldRun.setBold(true);
	        	boldRun.setFontSize(30);
	        	boldParagraphLogo.setAlignment(ParagraphAlignment.CENTER);
	            boldRun.setText(tieuDeCuaHoaDon + System.lineSeparator());
	            runInfoOrder.setText(thongTinCuaHoaDon);
	            
	            
	            runTable.setText(tableInFile);
	            
	            runTitleProduct.setBold(true);
	            runTitleProduct.setFontSize(12);
	            
	            runTitleProduct.setText(tieuDeCuaSanPham);
	            
	            
	            
	            runProduct.setFontSize(12);
	            for (ChiTietHoaDon chiTietHoaDon : cacSanPhamTrongHoaDon) {
	                runProduct.setText(sanPhamTrongHoaDon(chiTietHoaDon.getMaSach(), chiTietHoaDon.getTenSach() , chiTietHoaDon.getSoLuong(), chiTietHoaDon.getDonGia(), chiTietHoaDon.getThanhTien()));
	            }
	            
	            
	            
	            FileOutputStream out = new FileOutputStream(fileNameWord);
	            document.write(out);
	            out.close();


	         
	            
	            System.out.println("Successfully wrote to the file world: " + fileNameWord);
	            convertWordToPDF(fileNameWord, fileNamepdf);

	            
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	}
	
//	
	public void convertWordToPDF(String inputWordFile, String outputPDFFile) {
	
		
		String maHDMuonXuat = "";
		HoaDon hdMuonXuat = null;
		NhanVien thongTinNV = null;
		List<NhanVien> dsnv = new ArrayList<NhanVien>();
		NhanVien_Dao nv_dao = new NhanVien_Dao();
		
		int row = table_hd.getSelectedRow();
		if(row != -1) {
			maHDMuonXuat = (String) table_hd.getValueAt(row, 0);
			dshd = hd_dao.getAllHoaDon();
			for (HoaDon hdDuocChon : dshd) {
				if(maHDMuonXuat.equals(hdDuocChon.getMaHD())) {
					hdMuonXuat = hdDuocChon;
				}
			}
		}
		
		String tenNhanVien = "";
		dsnv = nv_dao.getAllNhanVien();
		
		for (NhanVien nhanVien : dsnv) {
			if(nhanVien.getMaNV().trim().equals(hdMuonXuat.getMaNV())) {
				tenNhanVien = nhanVien.getHoTenNV();
			}
		}
		
		// Tạo định dạng cho ngày tháng
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDateNgayXuatHoaDon = dateFormat.format(hdMuonXuat.getNgayLap());
		
		 try {
			 
			 String thongTinCuaHoaDon = 
						
						" Mã Hóa Đơn : " + hdMuonXuat.getMaHD() + "\t\t\t\t\t\t\t\t\t" + hdMuonXuat.getMaKH() +
						System.lineSeparator() + System.lineSeparator() +
						
						
						"Mã Nhân Viên : " + hdMuonXuat.getMaNV() + "\t\t\t\t\t\t\t\t" + "Ưu Đãi :" + hdMuonXuat.getUuDai() +
						System.lineSeparator()  + System.lineSeparator() + 
						
						
						"Tên Nhân Viên : " + tenNhanVien   + "\t\t\t\t\t\t\t\t" + "Tổng Tiền : " +   hdMuonXuat.getTongTien()+
						System.lineSeparator() + System.lineSeparator() +
						
						
						"Ngày Lặp : " + formattedDateNgayXuatHoaDon + "\t\t\t\t\t\t\t\t" + "VAT : " + hdMuonXuat.getVAT() + "\t\t\t" +  System.lineSeparator()  + System.lineSeparator() + "Mã Quầy : " + hdMuonXuat.getMaQuay() +  System.lineSeparator() +  System.lineSeparator(); 
						
			 
	            FileInputStream fis = new FileInputStream(inputWordFile);
	            XWPFDocument document = new XWPFDocument(fis);

	            
	            
	            FileOutputStream out = new FileOutputStream(outputPDFFile);
	            Document pdfDocument = new Document();
	            PdfWriter.getInstance(pdfDocument, out);
	            pdfDocument.open();
	            
	            

	            BaseFont unicodeFont = BaseFont.createFont("D:\\Tahoma Regular font.ttf", BaseFont.IDENTITY_H,BaseFont.EMBEDDED);
	            com.itextpdf.text.Font vietnameseFont = new com.itextpdf.text.Font(unicodeFont, 12,com.itextpdf.text.Font.NORMAL);
	            
	            Paragraph thongTinHoaDon = new Paragraph(thongTinCuaHoaDon);       
	            pdfDocument.add(thongTinHoaDon);
	            
	            
	            for (XWPFParagraph paragraph : document.getParagraphs()) {
	                String text = paragraph.getText();
	                Paragraph pdfParagraph = new Paragraph(text , vietnameseFont);
	                
	                
	                pdfDocument.add(pdfParagraph);
	            }
	            
	            
	            pdfDocument.close();
	            out.close();
	            fis.close();

	            System.out.println("Successfully converted Word to PDF: " + outputPDFFile);

	        } catch (IOException | com.itextpdf.text.DocumentException e) {
	            e.printStackTrace();
	        }
	    
	}
	
	
	
	

	
	
	
	
	
	
	
	
// FNC lay cac san pham theo ma hoa don
	public List<ChiTietHoaDon> getCacSanPhamTrongHoaDon(String maHD){
		ds_cthd = cthd_dao.getAllChiTietHoaDon();
		List<ChiTietHoaDon> ds_sanPhamTrongHoaDon = new ArrayList<ChiTietHoaDon>();
		
		for (ChiTietHoaDon chiTietHoaDon : ds_cthd) {
			boolean checkMaHD = maHD.equalsIgnoreCase(chiTietHoaDon.getMaHD());
			if(checkMaHD == true) {
				ds_sanPhamTrongHoaDon.add(chiTietHoaDon);
			}
		}
		
		return ds_sanPhamTrongHoaDon;
	}
	
	
	//Function Thong Ke Doanh Thu
	public void thongKeDoanhThu() {
		int ngayCanThongKe = (int) spinner_ngay.getValue();
		int thangCanThongKe = (int) spinner_thang.getValue();
		int namCanThongKe = (int) spinner_nam.getValue();
		
//		System.out.println(ngay + "" + thang + "" + nam);
		
		List<HoaDon> dsHdTheoNgayThangNam = new ArrayList<HoaDon>();
		List<HoaDon> dsHdTheoThangVaNam = new ArrayList<HoaDon>();
		List<HoaDon> dsHdTheoNam = new ArrayList<HoaDon>();
		
		
		dshd = hd_dao.getAllHoaDon();
		

		
		
		if (ngayCanThongKe == 0 && thangCanThongKe == 0) {  //Thong Ke Hoa Don Theo Nam
			int namCuaHoaDonTrongDB;
			double doanhThuTheoNam = 0;
			
			for (HoaDon hoaDon : dshd) {
				Calendar calendar = Calendar.getInstance();
		        calendar.setTime(hoaDon.getNgayLap());
		        
		        namCuaHoaDonTrongDB = calendar.get(Calendar.YEAR);

		        if(namCuaHoaDonTrongDB == namCanThongKe) {
		        	dsHdTheoNam.add(hoaDon);
		        }
		        
			}
			
			for (HoaDon hoaDonTheoNam : dsHdTheoNam) {
				doanhThuTheoNam = doanhThuTheoNam + hoaDonTheoNam.getTongTien();
			}
			
			 JOptionPane.showMessageDialog(null, "Thống Kê Doanh Thu Theo Năm", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
			tfDoanhthu.setText("" + doanhThuTheoNam);
			
			
			
			
		}else if(ngayCanThongKe == 0) {  //Thong Ke Hoa Don Theo Thang Va Nam
			
			int thangCuaHoaDonTrongDB , namCuaHoaDonTrongDB;
			double doanhThuTheoThangCuaNam = 0;
			
			for (HoaDon hoaDon : dshd) {
				Calendar calendar = Calendar.getInstance();
		        calendar.setTime(hoaDon.getNgayLap());
				
		        thangCuaHoaDonTrongDB = calendar.get(Calendar.MONTH) + 1;
		        namCuaHoaDonTrongDB = calendar.get(Calendar.YEAR);
		        
		        if(thangCanThongKe == thangCuaHoaDonTrongDB &&  namCanThongKe == namCuaHoaDonTrongDB) {
		        	dsHdTheoThangVaNam.add(hoaDon);
		        }
		        
				
			}
			
			
			for (HoaDon hoaDonTheoThangVaNam : dsHdTheoThangVaNam) {
				doanhThuTheoThangCuaNam = doanhThuTheoThangCuaNam + hoaDonTheoThangVaNam.getTongTien();
			}
			
			
			 JOptionPane.showMessageDialog(null, "Thống Kê Doanh Thu Theo Tháng Của Năm", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
			tfDoanhthu.setText("" + doanhThuTheoThangCuaNam);
			
			
			
		} else if (ngayCanThongKe != 0 && thangCanThongKe != 0) {			//Thong Ke Hoa Don Theo Ngay Thang Nam 
			double doanhThuTheoNgayThangNam = 0;
			int ngayCuaHoaDonTrongDB ,  thangCuaHoaDonTrongDB , namCuaHoaDonTrongDB;
			
			for (HoaDon hoaDon : dshd) {
				Calendar calendar = Calendar.getInstance();
		        calendar.setTime(hoaDon.getNgayLap());
				
		        ngayCuaHoaDonTrongDB = calendar.get(Calendar.DAY_OF_MONTH);
		        thangCuaHoaDonTrongDB = calendar.get(Calendar.MONTH) + 1;
		        namCuaHoaDonTrongDB = calendar.get(Calendar.YEAR);
		        
		        if (ngayCuaHoaDonTrongDB == ngayCanThongKe && thangCuaHoaDonTrongDB == thangCanThongKe && namCuaHoaDonTrongDB == namCanThongKe) {
		        	dsHdTheoNgayThangNam.add(hoaDon);
		        }
				
			}
			
			
			for (HoaDon hoaDonTheoNgayThangNam : dsHdTheoNgayThangNam) {
				doanhThuTheoNgayThangNam = doanhThuTheoNgayThangNam + hoaDonTheoNgayThangNam.getTongTien();
			}
			
			JOptionPane.showMessageDialog(null, "Thống Kê Doanh Thu Theo Ngày Tháng  Năm", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
			tfDoanhthu.setText("" + doanhThuTheoNgayThangNam);
			
			
		}
		
		
		
		
		
		dsHdTheoNam = new ArrayList<HoaDon>();
		dsHdTheoThangVaNam = new ArrayList<HoaDon>();
		dsHdTheoNgayThangNam = new ArrayList<HoaDon>();
		
		
		
	}
	
	
	
	public void timHoaDon() {
		String type_of_find = (String) cbttmn.getSelectedItem();
		if(type_of_find.trim().equalsIgnoreCase("Mã KH")) {
			String maKH = tfttmn.getText();
			dshd = hd_dao.getAllHoaDon();
			List<HoaDon> dsHDCuaKH = new ArrayList<HoaDon>();
			
			for (HoaDon hoaDon : dshd) {
				if(hoaDon.getMaKH().equalsIgnoreCase(maKH)) {
					dsHDCuaKH.add(hoaDon);
				}
			}
			
			if(dsHDCuaKH.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Không Tìm Thấy Mã Khách Hàng Này", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
			}else {
				JOptionPane.showMessageDialog(null, "Tìm Thấy Mã Khách Hàng Này", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
				xoaBangHD();
				xoaBangCTHD();
				
				for (HoaDon hoaDonTimThay : dsHDCuaKH) {
					Object[] rowDataFind = {hoaDonTimThay.getMaHD() , hoaDonTimThay.getMaNV() , hoaDonTimThay.getMaKH() , hoaDonTimThay.getNgayLap() , hoaDonTimThay.getVAT() , hoaDonTimThay.getMaQuay() , hoaDonTimThay.getUuDai() , hoaDonTimThay.getTongTien()};
					DatamodelHoaDon.addRow(rowDataFind);
					hienThiCacChiTietHoaDon();
				}
				
			}
			

			
			
		}else if(type_of_find.trim().equals("Ngày Lập")) {
			String ngayLapDuocNhap = tfttmn.getText();
			try {
				List<HoaDon> dsHdTheoNgayLap = new ArrayList<HoaDon>();
				
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
				Date dateCanTim = dateFormat.parse(ngayLapDuocNhap);
				
				
				Calendar calendarCanTim = Calendar.getInstance();
				calendarCanTim.setTime(dateCanTim);
				
				int ngayCanTim = calendarCanTim.get(Calendar.DAY_OF_MONTH);
				int thangCanTim = calendarCanTim.get(Calendar.MONTH) + 1;
				int namCanTim  = calendarCanTim.get(Calendar.YEAR);
				
	
				
				dshd = hd_dao.getAllHoaDon();
				for (HoaDon hoaDon : dshd) {
					Calendar calendarTrongHeThong = Calendar.getInstance();
					Date dateTrongHeThong = hoaDon.getNgayLap();
					calendarTrongHeThong.setTime(dateTrongHeThong);
					
					int ngayTrongHeThong = calendarTrongHeThong.get(Calendar.DAY_OF_MONTH);
					int thangTrongHeThong = calendarTrongHeThong.get(Calendar.MONTH) + 1;
					int namTrongHeThong  = calendarTrongHeThong.get(Calendar.YEAR);
					
					
					if(ngayCanTim == ngayTrongHeThong && thangCanTim == thangTrongHeThong && namCanTim == namTrongHeThong) {
						dsHdTheoNgayLap.add(hoaDon);
						
					}
				}
				
				
				
				
				if(dsHdTheoNgayLap.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Không Tìm Thấy Hóa Đơn Theo Ngày Lập Đã Nhập", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
				}else {
					JOptionPane.showMessageDialog(null, "Tìm Thấy Hóa Đơn Theo Ngày Lập Đã Nhập", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
					xoaBangHD();
					xoaBangCTHD();
					
					for (HoaDon hoaDonTimThay : dsHdTheoNgayLap) {
						Object[] rowDataFind = {hoaDonTimThay.getMaHD() , hoaDonTimThay.getMaNV() , hoaDonTimThay.getMaKH() , hoaDonTimThay.getNgayLap() , hoaDonTimThay.getVAT() , hoaDonTimThay.getMaQuay() , hoaDonTimThay.getUuDai() , hoaDonTimThay.getTongTien()};
						DatamodelHoaDon.addRow(rowDataFind);
						hienThiCacChiTietHoaDon();
					}
					
				}
				
				
			}catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "Ngày Nhập Phải Theo Định Dạng YYYY-MM--DD", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
	
	
	private void xoaBangHD() {
		for (int j = 0; j < table_hd.getRowCount(); j++) {
			DatamodelHoaDon.removeRow(j);
			j--;
		}
	}
	
	private void xoaBangCTHD() {
		for (int j = 0; j < table_cthd.getRowCount(); j++) {
			Datamodel_cthd.removeRow(j);
			j--;
		}
	}
	
	
	
	void loadDuLieuLaiTuDau() {
		for (HoaDon dsHoaDon : dshd) {
			String maHD = dsHoaDon.getMaHD();
			String maNV = dsHoaDon.getMaNV();
			String maKH = dsHoaDon.getMaKH();
			Date ngayLap = dsHoaDon.getNgayLap();
			float vat = dsHoaDon.getVAT();
			String maQuay = dsHoaDon.getMaQuay();
			float uuDai = dsHoaDon.getUuDai();
			double tongTien = dsHoaDon.getTongTien();
			
			Object[] row_data = {maHD , maNV , maKH , ngayLap , vat , maQuay , uuDai , tongTien};
			DatamodelHoaDon.addRow(row_data);
		}
		
		
	}
	

	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		

		
		if(o.equals(btnThngK)) {
			thongKeDoanhThu();
			
			
		}else if(o.equals(btnXhd)) {
			int r = table_hd.getSelectedRow();
			if(r != -1) {
				String maHD = DatamodelHoaDon.getValueAt(r, 0).toString();
				List<ChiTietHoaDon> spTrongHoaDons = new ArrayList<ChiTietHoaDon>();
				spTrongHoaDons = getCacSanPhamTrongHoaDon(maHD);
				ghiFlied(spTrongHoaDons);
				JOptionPane.showMessageDialog(btnXhd, "Xuất Thành công");
			}
		} else if(o.equals(btnSearch)) {
			timHoaDon();
		}else if(o.equals(btnLamMoi)) {
			
			xoaBangHD();
			xoaBangCTHD();
			
			loadDuLieuLaiTuDau();
			tfttmn.setText("");
		}
		
		
			

		
		
		
		
		
	}
}
