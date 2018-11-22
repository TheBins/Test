package com.cn.test;

import java.util.List;
import java.util.Scanner;

import com.cn.entity.Userinfo;
import com.cn.server.UserServerImpl;

public class Test {
	
	public static void main(String[] args) {
		Scanner sca = new Scanner(System.in);
		System.out.println("请选择功能选项:");
		System.out.println("1.登录 \t 2.注册");
		int a = sca.nextInt();
		switch (a) {
		case 1:
			login();
			break;
		case 2:
			regist();
			break;
		default:
			System.out.println("输入错误,程序即将退出.....");
			break;
		}
		
	}
	
	public static void login() {
		Scanner sca = new Scanner(System.in);
		System.out.println("请输入你的账号:");
		String name =sca.nextLine();
		System.out.println("请输入你的密码");
		String pwd =sca.nextLine();
		List<Userinfo> list = new UserServerImpl().queryAll();
		for (int i =0;i<list.size();i++) {
			Userinfo userinfo  =list.get(i);
			if(userinfo.getName().equals(name)) {
				if(userinfo.getPwd().equals(pwd)) {
					if(userinfo.getType().equals("管理员")) {
						System.out.println("登录成功,欢迎管理员:"+userinfo.getName());
						userHome(userinfo);
						return;
					}else {
						System.out.println("登录成功,欢迎用户:"+userinfo.getName());
						userHome(userinfo);
						return;
					}	
				}
			
			}
		}
		System.out.println("你的账户或密码错误");
		login();
	}
	
	public static void regist() {
		List<Userinfo> list = new UserServerImpl().queryAll();
		Scanner sca = new Scanner(System.in);
		Userinfo user = new Userinfo();
		System.out.println("请输入你的用户名:");
		String username =sca.nextLine();
		user.setName(username);
		System.out.println("请输入你的密码:");
		String userpwd = sca.nextLine();
		user.setId(list.size()+1);
		user.setPwd(userpwd);
		user.setType("用户");
		new UserServerImpl().addUser(user);
		System.out.println("注册成功,已为你跳到登录页面");
		login();
		
	}
	
	public static void userHome(Userinfo  user) {
		Userinfo newuser =user;
		if(user.getType().equals("管理员")) {		
			System.out.println("请选择你要操作的选项:");
			System.out.println("1.添加用户\t 2.删除用户 \t 3.管理用户\t 4.修改密码");
			int a =new Scanner(System.in).nextInt();
			switch (a) {
			case 1:
				Scanner sca = new Scanner(System.in);
				Userinfo userinfo  = new Userinfo();
				System.out.println("--------添加用户--------");
				System.out.println("请输入用户名:");
				String username = sca.nextLine();
				System.out.println("请输入密码:");
				String userpwd = sca.nextLine();
				System.out.println("请设置用户组(用户/管理员):");
				String usertype = sca.nextLine();
				userinfo.setId(new UserServerImpl().queryAll().size()+1);
				userinfo.setName(username);
				userinfo.setPwd(userpwd);
				userinfo.setType(usertype);
				new UserServerImpl().addUser(userinfo);
				System.out.println("添加成功,为你跳到首页");
				userHome(newuser);
				break;
			case 2:	
				Scanner sca2 = new Scanner(System.in);
				List<Userinfo> list = new UserServerImpl().queryAll();
				while(true) {
					System.out.println("--------删除用户--------");
					System.out.println("请输入你要删除的用户名:");
					String name =sca2.nextLine();
					for (Userinfo userinfo2 : list) {
						if(userinfo2.getName().equals(name)) {
							new UserServerImpl().deleteUser(userinfo2.getId());
							System.out.println("删除成功！");
							userHome(newuser);
							break;
						}
					}
					System.out.println("用户不存在");
					continue;
				}
			
			case 3:	
				Scanner sca3 = new Scanner(System.in);
				System.out.println("--------用户管理--------");
				
				List<Userinfo> lists = new UserServerImpl().queryAll();
				for (Userinfo userinfo2 : lists) {
					System.out.println(userinfo2.getId()+"、"+userinfo2.getName());
				}
				System.out.println("请输入你要操作用户的ID:");
				int id =sca3.nextInt();
				Userinfo us = new UserServerImpl().queryById(id);
				System.out.println("操作用户:"+us.getName());
				System.out.println("请输入操作项: 1.修改密码    2.修改用户组");
				int b = sca3.nextInt();
				if(b==1) {
					Scanner sca3_1 = new Scanner(System.in);
					while(true) {
					System.out.println("请输入新密码");
					String newpwd = sca3_1.nextLine();
					System.out.println("请确定新密码");
					String newpwds = sca3_1.nextLine();
					if(!newpwd.equals(newpwds)) {
						System.out.println("输入的密码不一致,请重新输入");
						continue;
					}
						us.setPwd(newpwd);
						new UserServerImpl().updateUser(us);
						System.out.println("修改成功");
						userHome(newuser);
					}
				}else {
					
					Scanner sca3_1 = new Scanner(System.in);
					System.out.println("请设置用户组(用户/管理员):");
					String type = sca3_1.nextLine();
					
					us.setType(type);
					System.out.println("修改成功");
					userHome(newuser);
				}
				break;
			case 4:	
				
				Userinfo uss = new UserServerImpl().queryById(newuser.getId());
				Scanner sca3_1 = new Scanner(System.in);
				while(true) {
				System.out.println("请输入新密码");
				String newpwd = sca3_1.nextLine();
				System.out.println("请确定新密码");
				String newpwds = sca3_1.nextLine();
				if(!newpwd.equals(newpwds)) {
					System.out.println("输入的密码不一致,请重新输入");
					continue;
				}
					uss.setPwd(newpwd);
					new UserServerImpl().updateUser(uss);
					System.out.println("修改成功");
					userHome(newuser);
				}
				
			default:
				break;
			}
			
		}else {
			System.out.println("请选择你要操作的选项:");
			System.out.println("1.修改密码");
			int a =new Scanner(System.in).nextInt();
			Userinfo uss = new UserServerImpl().queryById(newuser.getId());
			Scanner sca3_1 = new Scanner(System.in);
			while(true) {
			System.out.println("请输入新密码");
			String newpwd = sca3_1.nextLine();
			System.out.println("请确定新密码");
			String newpwds = sca3_1.nextLine();
			if(!newpwd.equals(newpwds)) {
				System.out.println("输入的密码不一致,请重新输入");
				continue;
			}
				uss.setPwd(newpwd);
				new UserServerImpl().updateUser(uss);
				System.out.println("修改成功");
				userHome(newuser);
			}
		}
	}

}
