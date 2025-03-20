import java.io.*;

public class Main {
    public static void main(String[] args){
        if(args.length == 0 || args[0].isEmpty()){
            throw new IllegalArgumentException("помилка. Треба передати шлях до файлу через аргумент");
        }
    try(BufferedReader buff = new BufferedReader(new FileReader(args[0]))){
        String file;
        while ((file=buff.readLine()) != null){
            System.out.println(file);
        }
    } catch (FileNotFoundException e){
        System.out.println("файл не знайдено");
    }catch (IOException e){
        System.out.println("помилка");
    }
    }

}