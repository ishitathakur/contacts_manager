package contact_project;
import java.awt.*;
import java.awt.event.*;
import java.applet.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.*;

public class Contacts extends Applet implements ActionListener 
{
	JTextField dobt, namet, phonet,field,delt,searcht,upnamet,upphonet,namelogt,passwordlogt;
	JButton saveb, dispb, backb ,searchb, deleteb, clearb, updateb,delb,bynameb,bynob,sorta,sortd,loginb,cancelb;
	JLabel dobl, namel, phonel,dell,searchl,namelog,passwordlog;
	Connection con;
	Statement st;
	ResultSet rs;
	JPanel mainPan, dispPan, delPan,searchPan,loginPan;
	JFrame frame;
	String data[][]=new String [100][5];
	DefaultTableModel model =new DefaultTableModel ();
	JTable table= new JTable(model);
	JScrollPane jsp;
	String colHeads[]={"ID","Name","Date of Birth","Phone"};
	String search,nameup,phoneup,phones;
	int phone;
	boolean flag=true;
	//Shows the contents of main window
	
	
	public  void showMainWin()
	{
		mainPan=new JPanel();
		dispPan=new JPanel();
		delPan=new JPanel();
		searchPan=new JPanel();
		loginPan=new JPanel();
		frame.setSize(new Dimension(500,550));
		frame.setTitle("Contact");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(mainPan);
		mainPan.setLayout(new GridLayout(0,2,10,10));
		delPan.setLayout(new GridLayout(0,1,10,10));
		//searchPan.setLayout(new GridLayout(0,2,5,5));
		frame.setVisible(true);
		frame.setResizable(false);
		namel=new JLabel("Name");
		dobl=new JLabel("Date of Birth");
		searchl=new JLabel("Search");
		bynameb=new JButton("By Name");
		bynob=new JButton("By No.");
		phonel=new JLabel("Phone");
		dobt=new JTextField(12);
		namet=new JTextField(12);
		phonet=new JTextField(12); 
		delt=new JTextField(10);
		searcht=new JTextField(10);
		dell=new JLabel("Entry to be removed");
		delb=new JButton("Done");
		saveb=new JButton("Save");
		dispb=new JButton("Display");
		backb=new JButton("Back");
		sorta=new JButton("Ascending Order");
		sortd=new JButton("Descending Order");
		searchb=new JButton("Search");
		deleteb=new JButton("Delete");
		updateb=new JButton("Update");
		clearb=new JButton("Clear");
		upnamet=new JTextField(12);
		upphonet=new JTextField(12);
		mainPan.add(namel);
		
		mainPan.add(namet);
		mainPan.add(dobl);
		mainPan.add(dobt);
		mainPan.add(phonel);
		mainPan.add(phonet);
		mainPan.add(saveb);
		mainPan.add(dispb);
		mainPan.add(searchb);
		mainPan.add(deleteb);
		mainPan.add(updateb);
		mainPan.add(clearb);
		
		frame.validate();
		frame.repaint();
		
		table=new JTable(data,colHeads);
		jsp=new JScrollPane(table);
		saveb.addActionListener(this);
		dispb.addActionListener(this);
		backb.addActionListener(this);
		clearb.addActionListener(this);
		deleteb.addActionListener(this);
		updateb.addActionListener(this);
		searchb.addActionListener(this);
		delb.addActionListener(this);
		bynameb.addActionListener(this);
		bynob.addActionListener(this);
		sorta.addActionListener(this);
		sortd.addActionListener(this);
		try{
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection("jdbc:mysql://localhost/contacts?"+"user=root&password=123edsaqw");
		
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(frame,"Error in connection");
		}
	}
	//Constructor 
	public Contacts()
	{
		frame=new JFrame();//initializes the frame
		showMainWin();
	}
	//Actions performed
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getActionCommand().equals("Save"))//Saving a contact
		{
			validateSave();
		}
		else if(ae.getActionCommand().equals("Display"))// displaying contacts
		{
			validateDisplay();
		}
		else if(ae.getActionCommand().equals("Back"))
		{
			validateBack();
		}
		else if(ae.getActionCommand().equals("Clear"))//Clearing the text fields
		{
			validateClear();
		}
		else if(ae.getActionCommand().equals("Delete"))//Deleting a record
		{	
			validateDelete();
		}
		else if(ae.getActionCommand().equals("Update"))//Updating a record
		{	
			validateUpdate();
		}
		else if(ae.getActionCommand().equals("Search"))//Searching for a record
		{
			validateSearch();
		}
		
		else if(ae.getActionCommand().equals("By Name"))//Searching by name
		{
			search=JOptionPane.showInputDialog("Enter Name");
			try
			{
			emptyTable();
			searchPan.add(jsp);
			st=con.createStatement();
			rs=st.executeQuery("Select * from contacts where name REGEXP '^"+search+".*'");
			int i=0;
			while(rs.next())
			{
			for(int j=0;j<4;j++)
			{
				table.setValueAt(rs.getString(j+1), i, j);
			}
			i++;
			}
			st.close();
			frame.validate();
			frame.repaint();
			}
			catch(Exception e)
			{
				JOptionPane.showMessageDialog(frame,"SQL error.234..");
			}
		}
		else if(ae.getActionCommand().equals("By No."))//Searching by contact no.
		{
			search=JOptionPane.showInputDialog("Enter no.");
			int ph=Integer.parseInt(search);
			JOptionPane.showMessageDialog(null,"yooo");
			try
			{
			emptyTable();
			searchPan.add(jsp);
			st=con.createStatement();
			rs=st.executeQuery("Select * from contacts where phone REGEXP = "+ph+".*");
			int i=0;
			
			while(rs.next())
			{
			for(int j=0;j<4;j++)
			{
				table.setValueAt(rs.getString(j+1), i, j);
			}
			i++;
			}
			st.close();
			frame.validate();
			frame.repaint();
			}
			catch(Exception e)
			{
				JOptionPane.showMessageDialog(frame,"SQL error.234..");
			}
		}
		else if(ae.getActionCommand().equals("Ascending Order"))
		{
			dispPan.add(jsp);
			emptyTable();
		
			try
			{
				emptyTable();
				st=con.createStatement();
				rs=st.executeQuery("Select * from contacts order by name");
				int i=0;
				while(rs.next())
				{
					for(int j =0;j<4;j++)
					{
						table.setValueAt(rs.getString(j+1), i, j);
					}
					i++;
				}
				st.close();
				dispPan.add(backb);
				frame.validate();
				frame.repaint();
				
			}
			catch(Exception e)
			{}
		}
		
		else if(ae.getActionCommand().equals("Descending Order"))
		{
			dispPan.add(jsp);
			emptyTable();
		
			try
			{
				emptyTable();
				st=con.createStatement();
				rs=st.executeQuery("Select * from contacts order by name desc");
				int i=0;
				while(rs.next())
				{
					for(int j =0;j<4;j++)
					{
						table.setValueAt(rs.getString(j+1), i, j);
					}
					i++;
				}
				st.close();
				dispPan.add(backb);
				frame.validate();
				frame.repaint();
			}
			catch(Exception e)
			{}
		}
		else if(ae.getActionCommand().equals("Done"))//Deleting the entry
		{
		try
		{	
			String name1=delt.getText();
			String SQL = "DELETE FROM contacts WHERE name = '"+name1+"' ";
			st=(Statement)con.createStatement();
			st.executeUpdate(SQL);
			st.close();
			JOptionPane.showMessageDialog(frame,"Record deleted ");
			delt.setText(null);		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(frame,"SQL error...");
		}
        }
		if(ae.getActionCommand().equals("Login"))
		{
			showMainWin();
		}
	}
	
	public int getRecCount()// Count the no. of records
	{
		int c=0;
		try
		{
			st=con.createStatement();
			rs=st.executeQuery("Select * from contacts");
			while(rs.next())
			{
				c++;
			}
			st.close();
		}
		catch(Exception e)
		{}
		return c;
		
	}
	
	public void emptyTable()// Clear the table
	{
		for(int i=0;i<table.getRowCount();i++)
		{
			for(int j=0;j<4;j++)
			{
				table.setValueAt("",i,j);
				
			}
		}
	}
	
	public void validatePhone (String phones)
	{
		if(phones.length()>10)
		{
			JOptionPane.showMessageDialog(frame,"Contact no. must have 10 digits...");
		}
	}
	
	public void validateDelete()
	{
		frame.remove(mainPan);
		frame.setContentPane(delPan);
		delPan.add(dell);
		delPan.add(delt);
		delPan.add(delb);
		delPan.add(backb);
		frame.validate();
		frame.repaint();
		emptyTable();
	}
	
	public void validateUpdate()
	{
		nameup=JOptionPane.showInputDialog("Enter Name");
		phoneup=JOptionPane.showInputDialog("Enter the new Phone");
		try
		{
			emptyTable();
			int phone =Integer.parseInt(phoneup);
			
			st=con.createStatement();
			st.executeUpdate("Update contacts set phone ="+phoneup+" where name='"+nameup+"'");
			st.close();
			JOptionPane.showMessageDialog(frame,"Record Successfully updated...");
		}
		catch(Exception e)
		{}
	}
	
	public void validateSave()
	{
		try
		{	emptyTable();
		    phones=phonet.getText();
			validatePhone(phones);
			phone =Integer.parseInt(phones);
			String name=namet.getText();
			String dob=dobt.getText();
			validateDate(dob);
			if(flag)
			{
			st=(Statement)con.createStatement();
			st.executeUpdate("insert into contacts (name,dob,phone)"+" values('"+name+"','"+dob+"',"+phone+")");
			st.close();
			JOptionPane.showMessageDialog(frame,"Record Successfully inserted...");
			namet.setText(null);
			dobt.setText(null);
			phonet.setText(null);
			}
			else
			{
				dobt.setText(null);
				
			}
		}
		catch(Exception e)
		{JOptionPane.showMessageDialog(frame,"SQL error...");}
	}
	
	public void validateBack()
	{
		emptyTable();
		frame.remove(dispPan);
		frame.remove(delPan);
		frame.remove(searchPan);
		frame.setContentPane(mainPan);
		frame.validate();
		frame.repaint();
	}
	
	public void validateClear()
	{
		namet.setText("");
		dobt.setText("");
		phonet.setText(null);
	}
	
	public void validateDisplay()
	{
		//int reccount=getRecCount();
		
		frame.remove(mainPan);
		frame.setContentPane(dispPan);
		dispPan.add(sorta);
		dispPan.add(sortd);
		
		dispPan.add(jsp);
		emptyTable();
		try
		{
			emptyTable();
			st=con.createStatement();
			rs=st.executeQuery("Select * from contacts");
			int i=0;
			while(rs.next())
			{
				for(int j =0;j<4;j++)
				{
					table.setValueAt(rs.getString(j+1), i, j);
				}
				i++;
			}
			st.close();
			dispPan.add(backb);
			frame.validate();
			frame.repaint();
			
		}
		catch(Exception e)
		{}
	}
	
	public void validateSearch()
	{
		frame.remove(mainPan);
		frame.setContentPane(searchPan);
		searchPan.add(bynameb);
		searchPan.add(bynob);
		searchPan.add(backb);
		frame.validate();
		frame.repaint();
	}
	
	public void validateDate(String a)
	{
		if(a.length()!=10)
		{
			JOptionPane.showMessageDialog(frame,"Invalid Date...");
			flag=false;
		}
		else if(Integer.parseInt(a.substring(0, 2))>31)
		{
			JOptionPane.showMessageDialog(frame,"Invalid Date...");
			flag=false;
	
		}
		else if(Integer.parseInt(a.substring(2, 2))!='/')
		{
			JOptionPane.showMessageDialog(frame,"Invalid Date...");
			flag=false;
		}
		else if(Integer.parseInt(a.substring(3, 5))>12)
		{
			JOptionPane.showMessageDialog(frame,"Invalid Date...");
			flag=false;
		}
	}

	public static void main()
	{
		Contacts ob= new Contacts();
		
	}
}					

