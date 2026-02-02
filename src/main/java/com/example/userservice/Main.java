package com.example.userservice;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static UsersDao usersDao = new UsersDao();  // Изменили UserDao на UsersDao

    public static void main(String[] args) {
        System.out.println("=== Система управления пользователями ===");

        try {
            boolean exit = false;
            while (!exit) {
                printMenu();
                String choice = scanner.nextLine();

                switch (choice) {
                    case "1" -> addUser();
                    case "2" -> showUser();
                    case "3" -> showAllUsers();
                    case "4" -> updateUser();
                    case "5" -> deleteUser();
                    case "6" -> exit = true;
                    default -> System.out.println("Неверный выбор!");
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
        } finally {
            HibernateUtil.shutdown();
            scanner.close();
            System.out.println("Программа завершена.");
        }
    }

    private static void printMenu() {
        System.out.println("\n--- Меню ---");
        System.out.println("1. Добавить пользователя");
        System.out.println("2. Найти пользователя по ID");
        System.out.println("3. Показать всех пользователей");
        System.out.println("4. Обновить пользователя");
        System.out.println("5. Удалить пользователя");
        System.out.println("6. Выйти");
        System.out.print("Выберите: ");
    }

    private static void addUser() {
        try {
            System.out.print("Имя: ");
            String name = scanner.nextLine();

            System.out.print("Email: ");
            String email = scanner.nextLine();

            System.out.print("Возраст: ");
            int age = Integer.parseInt(scanner.nextLine());

            if (usersDao.emailExists(email)) {
                System.out.println("Ошибка: Email уже существует!");
                return;
            }

            Users user = new Users(name, email, age);  // Users вместо User
            Long id = usersDao.create(user);
            System.out.println("Пользователь создан! ID: " + id);
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private static void showUser() {
        try {
            System.out.print("Введите ID пользователя: ");
            Long id = Long.parseLong(scanner.nextLine());

            Users user = UsersDao.findById(id);  // Users вместо User
            if (user == null) {
                System.out.println("Пользователь не найден!");
            } else {
                System.out.println("Найден: " + user);
            }
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private static void showAllUsers() {
        try {
            List<Users> users = usersDao.findAll();  // List<Users>
            if (users.isEmpty()) {
                System.out.println("Нет пользователей.");
            } else {
                System.out.println("Всего пользователей: " + users.size());
                for (Users user : users) {  // Users вместо User
                    System.out.println("  " + user);
                }
            }
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private static void updateUser() {
        try {
            System.out.print("Введите ID пользователя для обновления: ");
            Long id = Long.parseLong(scanner.nextLine());

            Users user = usersDao.findById(id);  // Users
            if (user == null) {
                System.out.println("Пользователь не найден!");
                return;
            }

            System.out.print("Новое имя: ");
            user.setName(scanner.nextLine());

            System.out.print("Новый email: ");
            String newEmail = scanner.nextLine();

            if (!user.getEmail().equals(newEmail) && usersDao.emailExists(newEmail)) {
                System.out.println("Ошибка: Email уже используется!");
                return;
            }
            user.setEmail(newEmail);

            System.out.print("Новый возраст: ");
            user.setAge(Integer.parseInt(scanner.nextLine()));

            usersDao.update(user);
            System.out.println("Пользователь обновлен!");
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    private static void deleteUser() {
        try {
            System.out.print("Введите ID пользователя для удаления: ");
            Long id = Long.parseLong(scanner.nextLine());

            System.out.print("Удалить пользователя " + id + "? (да/нет): ");
            String confirm = scanner.nextLine();

            if (confirm.equalsIgnoreCase("да")) {
                usersDao.delete(id);
                System.out.println("Пользователь удален!");
            } else {
                System.out.println("Отменено.");
            }
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}