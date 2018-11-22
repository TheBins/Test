package com.cn.test;

import java.util.List;
import java.util.Scanner;

import com.cn.entity.Userinfo;
import com.cn.server.UserServerImpl;

public class Test {
	
	public static void main(String[] args) {
		Scanner sca = new Scanner(System.in);
		System.out.println("��ѡ����ѡ��:");
		System.out.println("1.��¼ \t 2.ע��");
		int a = sca.nextInt();
		switch (a) {
		case 1:
			login();
			break;
		case 2:
			regist();
			break;
		default:
			System.out.println("�������,���򼴽��˳�.....");
			break;
		}
		
	}
	
	public static void login() {
		Scanner sca = new Scanner(System.in);
		System.out.println("����������˺�:");
		String name =sca.nextLine();
		System.out.println("�������������");
		String pwd =sca.nextLine();
		List<Userinfo> list = new UserServerImpl().queryAll();
		for (int i =0;i<list.size();i++) {
			Userinfo userinfo  =list.get(i);
			if(userinfo.getName().equals(name)) {
				if(userinfo.getPwd().equals(pwd)) {
					if(userinfo.getType().equals("����Ա")) {
						System.out.println("��¼�ɹ�,��ӭ����Ա:"+userinfo.getName());
						userHome(userinfo);
						return;
					}else {
						System.out.println("��¼�ɹ�,��ӭ�û�:"+userinfo.getName());
						userHome(userinfo);
						return;
					}	
				}
			
			}
		}
		System.out.println("����˻����������");
		login();
	}
	
	public static void regist() {
		List<Userinfo> list = new UserServerImpl().queryAll();
		Scanner sca = new Scanner(System.in);
		Userinfo user = new Userinfo();
		System.out.println("����������û���:");
		String username =sca.nextLine();
		user.setName(username);
		System.out.println("�������������:");
		String userpwd = sca.nextLine();
		user.setId(list.size()+1);
		user.setPwd(userpwd);
		user.setType("�û�");
		new UserServerImpl().addUser(user);
		System.out.println("ע��ɹ�,��Ϊ��������¼ҳ��");
		login();
		
	}
	
	public static void userHome(Userinfo  user) {
		Userinfo newuser =user;
		if(user.getType().equals("����Ա")) {		
			System.out.println("��ѡ����Ҫ������ѡ��:");
			System.out.println("1.����û�\t 2.ɾ���û� \t 3.�����û�\t 4.�޸�����");
			int a =new Scanner(System.in).nextInt();
			switch (a) {
			case 1:
				Scanner sca = new Scanner(System.in);
				Userinfo userinfo  = new Userinfo();
				System.out.println("--------����û�--------");
				System.out.println("�������û���:");
				String username = sca.nextLine();
				System.out.println("����������:");
				String userpwd = sca.nextLine();
				System.out.println("�������û���(�û�/����Ա):");
				String usertype = sca.nextLine();
				userinfo.setId(new UserServerImpl().queryAll().size()+1);
				userinfo.setName(username);
				userinfo.setPwd(userpwd);
				userinfo.setType(usertype);
				new UserServerImpl().addUser(userinfo);
				System.out.println("��ӳɹ�,Ϊ��������ҳ");
				userHome(newuser);
				break;
			case 2:	
				Scanner sca2 = new Scanner(System.in);
				List<Userinfo> list = new UserServerImpl().queryAll();
				while(true) {
					System.out.println("--------ɾ���û�--------");
					System.out.println("��������Ҫɾ�����û���:");
					String name =sca2.nextLine();
					for (Userinfo userinfo2 : list) {
						if(userinfo2.getName().equals(name)) {
							new UserServerImpl().deleteUser(userinfo2.getId());
							System.out.println("ɾ���ɹ���");
							userHome(newuser);
							break;
						}
					}
					System.out.println("�û�������");
					continue;
				}
			
			case 3:	
				Scanner sca3 = new Scanner(System.in);
				System.out.println("--------�û�����--------");
				
				List<Userinfo> lists = new UserServerImpl().queryAll();
				for (Userinfo userinfo2 : lists) {
					System.out.println(userinfo2.getId()+"��"+userinfo2.getName());
				}
				System.out.println("��������Ҫ�����û���ID:");
				int id =sca3.nextInt();
				Userinfo us = new UserServerImpl().queryById(id);
				System.out.println("�����û�:"+us.getName());
				System.out.println("�����������: 1.�޸�����    2.�޸��û���");
				int b = sca3.nextInt();
				if(b==1) {
					Scanner sca3_1 = new Scanner(System.in);
					while(true) {
					System.out.println("������������");
					String newpwd = sca3_1.nextLine();
					System.out.println("��ȷ��������");
					String newpwds = sca3_1.nextLine();
					if(!newpwd.equals(newpwds)) {
						System.out.println("��������벻һ��,����������");
						continue;
					}
						us.setPwd(newpwd);
						new UserServerImpl().updateUser(us);
						System.out.println("�޸ĳɹ�");
						userHome(newuser);
					}
				}else {
					
					Scanner sca3_1 = new Scanner(System.in);
					System.out.println("�������û���(�û�/����Ա):");
					String type = sca3_1.nextLine();
					
					us.setType(type);
					System.out.println("�޸ĳɹ�");
					userHome(newuser);
				}
				break;
			case 4:	
				
				Userinfo uss = new UserServerImpl().queryById(newuser.getId());
				Scanner sca3_1 = new Scanner(System.in);
				while(true) {
				System.out.println("������������");
				String newpwd = sca3_1.nextLine();
				System.out.println("��ȷ��������");
				String newpwds = sca3_1.nextLine();
				if(!newpwd.equals(newpwds)) {
					System.out.println("��������벻һ��,����������");
					continue;
				}
					uss.setPwd(newpwd);
					new UserServerImpl().updateUser(uss);
					System.out.println("�޸ĳɹ�");
					userHome(newuser);
				}
				
			default:
				break;
			}
			
		}else {
			System.out.println("��ѡ����Ҫ������ѡ��:");
			System.out.println("1.�޸�����");
			int a =new Scanner(System.in).nextInt();
			Userinfo uss = new UserServerImpl().queryById(newuser.getId());
			Scanner sca3_1 = new Scanner(System.in);
			while(true) {
			System.out.println("������������");
			String newpwd = sca3_1.nextLine();
			System.out.println("��ȷ��������");
			String newpwds = sca3_1.nextLine();
			if(!newpwd.equals(newpwds)) {
				System.out.println("��������벻һ��,����������");
				continue;
			}
				uss.setPwd(newpwd);
				new UserServerImpl().updateUser(uss);
				System.out.println("�޸ĳɹ�");
				userHome(newuser);
			}
		}
	}

}
