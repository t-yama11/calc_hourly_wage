package ボタン;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class JButtonSample extends JFrame implements ActionListener{
	private static final int hour_day=24; //1日の時間
	private static final int minute_day=60;//一時間の分
	private final int hourly_wage = 1000; //時給
	private static boolean flag1=false, flag2=false, flag3=false, flag4=false; //コンボボックスが押されたかの判別
	private double money=0.0; //給料の金額
	private double h_start, h_end, min_start, min_end; //出社時間、退勤時間を格納する変数
	private JButton b; //給料の表示ボタン
	//始業時間と終業時間の配列
	private String[] hour_start=new String[hour_day+1]; //コンボボックスに表示する出社時間の時間（h）
	private String[] minute_start=new String[minute_day+1]; //コンボボックスに表示する出社時間の分（min）
	private String[] hour_end=new String[hour_day+1]; //コンボボックスに表示する退勤時間の時間（h）
	private String[]minute_end=new String[minute_day+1]; //コンボボックスに表示する退勤時間の時間（min）
	private JComboBox combo_hour_start, combo_hour_end, combo_minute_start, combo_minute_end;

	/*メイン関数*/
	public static void main(String[] args) {
		new JButtonSample();
	}

	public JButtonSample() {

		/*ウィンドウの設定*/
	    setBounds(100, 100, 800, 600); // ウィンドウの位置とサイズを指定
	    setTitle("時給計算"); //ウィンドウのタイトル
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // ウィンドウクローズでアプリケーションを終了する設定

	    /*計算ボタンの設置*/
	    this.b = new JButton("計算");    // ボタンの作成
	    this.b.addActionListener(this);

	    /*コンボボックスの初期化*/
	    this.hour_start=init_combobox(hour_start, hour_day, "始業時間（h）");
	    this.minute_start=init_combobox(minute_start, minute_day, "始業時間（min）");
	    this.hour_end=init_combobox(hour_end, hour_day, "終業時間（h）");
	    this.minute_end=init_combobox(minute_end, minute_day, "終業時間（min）");

	    /*出社時間と退勤時間のコンボボックスに文字列を代入*/
	    this.combo_hour_start = new JComboBox(this.hour_start);
	    this.combo_minute_start = new JComboBox(this.minute_start);
	    this.combo_hour_end = new JComboBox(this.hour_start);
	    this.combo_minute_end = new JComboBox(this.minute_end);

	    /*パネルに計算ボタンと出社時間と退勤時間のコンボボックスを設置*/
	    JPanel p = new JPanel(); //ボタンを置くパネル
	    p.add(this.b);
	    p.add(this.combo_hour_start);
	    p.add(this.combo_minute_start);
	    p.add(this.combo_hour_end);
	    p.add(this.combo_minute_end);

	    /*コンテナにパネルを設置*/
	    Container contentPane = getContentPane();
	    contentPane.add(p, BorderLayout.CENTER);

	    // ウィンドウを表示する
	    setVisible(true);
	}

	/*コンボボックスの初期化*/
	public static String[] init_combobox(String[] combo, int max, String init_text) {
		combo[0]=init_text;
		for(int i=0; i<max; i++) {
			combo[i+1]=String.valueOf(i); //コンボボックスに文字列を代入
		}
		return combo;
	}

	/*時給の計算*/
	public  double calcMoney() {
		if(this.h_end >= this.h_start) {
			 //退勤時間の分（min）が出社時間の分（min）を上回っている場合
			if(min_end >= min_start) return this.hourly_wage*
					(this.h_end-this.h_start + (this.min_end-this.min_start)/minute_day);
			//退勤時間の分（min）が出社時間の分（min）を下回っている、又は同じである場合
			else return this.hourly_wage*
					(this.h_end-this.h_start -1 + (minute_day + this.min_end - this.min_start)/minute_day); //
		}
		return -1.0; //日付を跨いだ場合はエラー
	}

	/*文字列が数字かどうか判定*/
	public boolean isNum(String s) {
	    try {
	        Integer.parseInt(s);
	        return true;
	    } catch (NumberFormatException e) {
	        return false;
	    }
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//出社時間の時間（h）のコンボボックスが選択された場合、flag1の値を変更
		if(isNum((String)this.combo_hour_start.getSelectedItem())) {
			this.h_start=Double.parseDouble((String)this.combo_hour_start.getSelectedItem());
			flag1=true;
		}
		//出社時間の分（min）のコンボボックスが選択された場合、flag2の値を変更
		if(isNum((String)this.combo_hour_end.getSelectedItem())) {
			this.h_end=Double.parseDouble((String)this.combo_hour_end.getSelectedItem());
			flag2=true;
		}
		//退勤時間の時間（h）のコンボボックスが選択された場合、flag3の値を変更
		if(isNum((String)this.combo_minute_start.getSelectedItem())) {
			this.min_start=Double.parseDouble((String)this.combo_minute_start.getSelectedItem());
			flag3=true;
		}
		//退勤時間の分（min）のコンボボックスが選択された場合、flag4の値を変更
		if(isNum((String)this.combo_minute_end.getSelectedItem())) {
			this.min_end=Double.parseDouble((String)this.combo_minute_end.getSelectedItem());
			flag4=true;
		}

		//全てのコンボボックスが選択された場合のみ、計算ボタンをクリックすると時給が表示される
		if(flag1 && flag2 && flag3 && flag4) {
			this.money=calcMoney();
			JLabel label = new JLabel(this.money + "円");
		    JOptionPane.showMessageDialog(this, label);
		}
	}
}
