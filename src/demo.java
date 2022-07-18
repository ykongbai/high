import java.io.*;
import java.text.Collator;
import java.util.*;
import java.util.Map.Entry;

public class demo {
    public static void main(String[] args) {
        // 答案
        File file = new File("E:\\DT108班-java基础考试\\Java基础笔试答案.txt");
        // 学生答案
        File file2 = new File("e:\\DT108班-java基础考试");
        // 保存姓名分数
        Map<String,String> map = new HashMap<>();
            //所有学生答案
        File[] files = file2.listFiles();
        for (File file1 : files) {
            if(file1.isDirectory()){
                File[] listFiles = file1.listFiles();
                for (File listFile : listFiles) {
                    // 分数
                    int score = 0;
                    try {
                        // 学生答案
                        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(listFile)));
                        // 答案
                        BufferedReader br2 = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                        // readLine()
                        String stu = "";
                        String tech = "";
                        // 内容有数据
                        while((stu = br.readLine()) != null && (tech = br2.readLine()) != null){
                            // 数据里面不为空
                            if(!stu.equals("")&&!tech.equals("")){
                                if(stu.equalsIgnoreCase(tech)){
                                    score += 10;
                                }else{
                                    String[] split = null;
                                    String[] split1 = tech.split(",");
                                    if(stu.contains(",")){
                                        split = stu.split(",");
                                    }else{
                                        split = stu.split(" ");
                                    }
                                    // 数据
                                    for(int i = 0 ; i < split.length;i++){
                                        if(split[i].equalsIgnoreCase(split1[i])){
                                            score += 2;
                                        }
                                    }
                                }
                            }
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    map.put(listFile.getName(),score+"分");
                }
            }
        }

        //定义排序格式
        Comparator cmp = Collator.getInstance(Locale.CHINA);
        List<Map.Entry<String,String>> list = new ArrayList<>(map.entrySet());
        // 比较器比较
        Collections.sort(list, new Comparator<Entry<String, String>>() {
            @Override
            public int compare(Entry<String, String> o1, Entry<String, String> o2) {
                if(o1.getValue().compareTo(o2.getValue()) == 0){
                    return ((Comparator) cmp).compare(o1.getKey(),o2.getKey());
                }else{
                    return o2.getValue().compareTo(o1.getValue());
                }
            }
        });

        //放入文件夹中
        BufferedWriter bw = null;
        try {
            bw  = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("e:\\DT108班-java基础考试\\成绩表.txt")));
//            Set<Entry<String, String>> entries = map.entrySet();
            for (Entry<String, String> entry : list) {
                bw.write(entry.getKey().substring(0,entry.getKey().indexOf("."))+":" + entry.getValue());
                bw.newLine();
                bw.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
